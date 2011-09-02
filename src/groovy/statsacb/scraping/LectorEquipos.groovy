package statsacb.scraping

import statsacb.Posicion;
import statsacb.EquipoAcb
import org.jsoup.nodes.Document;

import org.jsoup.Jsoup;

@Grapes( @Grab(group='org.jsoup', module='jsoup', version='1.6.1' ))
class LectorEquipos{


	static final int EDICION_ACB = 55

	static final codigoToNombreCorto = [ 'CLA':'BAS',  'ALI':'ALI',
		'GCA':'CLA', 'GRN':'GRN', 'FUE':'FUE', 'PEV':'PAM', 'CAI':'ZZA',
		'FCB':'BAR', 'MEN':'MEN', 'CAJ':'SEV', 'BBB':'BLB', 'RMA':'MAD',
		'MAN':'MAN', 'ASE':'EST', 'GBC':'GBC', 'DKV':'JOV', 'BRV':'VAL',
		'UNI':'RON']

	/**
	 * Lee plantillas de una temporada de la
	 * ACB y asigna a los jugadores su el equipo
	 * al que pertenecen.
	 *
	 *
	 */
	def main (String[] args){
		def lector = new LectorEquipos()
		lector.leerDocumento()
	}


	/**
	 * Lista todos los equipos de una
	 * temporada con los nombres que esos equipos
	 * tienen para la web de la acb que son nombres
	 * constantes y que sirven para identificar al
	 * equipo.
	 *
	 * @return
	 */
	def leerDocumento(){
		def documento = getDocumento()
		for (enlace in documento.select("td.menuclubs > a").not(":has(img)")){
			def codigo = leerCodigo(enlace.attr("href").toString())
			def equipoLocal = EquipoAcb.findWhere(codigo:codigo)
			if(!equipoLocal){
				equipoLocal = new EquipoAcb(nombreCorto:codigoToNombreCorto[codigo], codigo:codigo)
			}
			equipoLocal.nombreCompleto = enlace.ownText()
			println equipoLocal
			equipoLocal.save(flush:true)
		}
		
	}

	/**
	 * Lee el codigo que representara siempre
	 * al equipo en la pagina de la ACB.
	 */
	private String leerCodigo(String url){
		def match = url =~ /plantilla.php\?cod_equipo=(\w\w\w)&cod_competicion=LACB&cod_edicion=55/
		return match[0][1]
	}

	/**
	 * Se conecta a la pagina del supermanager y
	 * obtiene el html de la página que contiene
	 * la lista de equipos de una temporada.
	 *
	 * @param posicion posicion a obtener
	 * @return documento html con la tabla de jugadores de la posicion
	 */
	protected Document getDocumento(){
		int edicionAcb = EDICION_ACB
		return Jsoup.connect("http://www.acb.com/menuplantillas.php?cod_edicion=$edicionAcb").get()
	}
}
