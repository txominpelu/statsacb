package statsacb.scraping

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import statsacb.*;
import java.text.DecimalFormat;

@Grapes( @Grab(group='org.jsoup', module='jsoup', version='1.6.1' ))
class LectorPartidosEquipo{
    
    private String codigoEquipo
    private int edicionAcb
	
	def main (String[] args){
		for(equipo in EquipoAcb.getAll()){
			println equipo.nombreCompleto
			def lector = new LectorPartidosEquipo()
			lector.codigoEquipo = equipo.codigo
			lector.edicionAcb = 55
			lector.leer()
		}
	}
 
    
    def leer(){
        def documento = Jsoup.connect("http://www.acb.com/partclub.php?regular=1&cod_equipo=$codigoEquipo&jornada1=0&cod_edicion1=$edicionAcb&jornada2=0&x=4&y=9&cod_competicion=LACB").get()
        for(partido in documento.select("table.resultados")[0].select("tr").not("[class]")){
            
            def puntosMatch = (partido.select("td.negro2 a")[0].ownText() =~ /(\d*) - (\d*)/)[0]
            def puntosLocal = puntosMatch[1] as int
            def puntosVisitante = puntosMatch[2] as int
            
            def numJornada = leerNumJornada(partido.select("td.naranjaclaro2 a")[0].attr("href"))
			def p = Partido.executeQuery("select p from Partido as p" +
						" inner join p.local as local" +
						" inner join p.visitante as visitante" +
						" where (local.codigo=:equipo or visitante.codigo=:equipo) and p.jornada=:jornada",[jornada:numJornada, equipo:codigoEquipo])[0]
			p.puntosLocal = puntosLocal
			p.puntosVisitante = puntosVisitante
			p.save(flush:true)
			
        }
    }

    def leerNumJornada(String enlace){
        def numPartido = (enlace =~ /stspartido.php\?cod_competicion=LACB&cod_edicion=$edicionAcb&partido=(\d*)/) [0][1]

        def numPartFormat = new DecimalFormat('000').format(numPartido as int)
        def docPartido = Jsoup.connect("http://www.acb.com/fichas/LACB${edicionAcb}${numPartFormat}.php").get()
        def textoJornada = docPartido.select("table.estadisticas tr.estnegro > td")[0].ownText().split("\\|")[0].substring(1)
        return (textoJornada =~ /J (\d*) /)[0][1] as int
    }

	
}

