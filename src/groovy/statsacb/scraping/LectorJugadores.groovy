package statsacb.scraping

import groovy.util.slurpersupport.GPathResult;
import statsacb.ValoracionPartido
import statsacb.Equipo
import statsacb.Partido;
import statsacb.Jugador;

@Grapes( @Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='1.2') )
class LectorJugadores{

	static final String ENCODING = "ISO-8859-1"

	static final Integer COLUMNA_NUM_JORNADA = 1

	int COLUMNA_VALORACION_PARTIDO = 17;
	int COLUMNA_EQUIPOS_PARTIDO = 2;
	int COLUMNA_MINUTOS = 3

	def static main(String[] args){
		LectorJugadores lector = new LectorJugadores();
		def rangoLetrasList = new java.util.ArrayList('A'..'Z')
		rangoLetrasList.addAll('1'..'9')
		for(i in rangoLetrasList){
			for(j in rangoLetrasList){
				for(k in rangoLetrasList){
					if("$i$j$k".toString().equals("BHM")){
						break;
					}
					println "$i$j$k"
					lector.leerJugador("$i$j$k")
				}
			}
		}
	}
	
	

	/**
	 * 
	 */
	def leerJugador(String codigoIdentificador){

		def slurper = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser() )

		def url = new URL("http://www.acb.com/stspartidojug.php?cod_jugador=$codigoIdentificador&cod_competicion=LACB&cod_edicion=55")

		try{
			url.withReader (ENCODING) { reader ->

				def html = slurper.parse(reader)
				def tablaEstadisticas = html.'**'.find{
					it.name() == 'table' && it['@class'] == 'estadisticas2'
				}
				def jugadorValido = tablaEstadisticas.tr.size() > 2;

				if(jugadorValido) {
					def nombre = html.'**'.find{
						it.name() == 'div' && it['@class'] == 'nombrejug'
					}.text()
					def jugador = new Jugador(nombre:nombre, codigoAcb:codigoIdentificador)
					jugador.save()
					tablaEstadisticas.tr.findAll{ it['@class'] != 'estverde' }[0..-3].each { leerFilaActuacionIndividual(jugador, it) }
				}
			}
		}catch(java.net.ConnectException e){
			println "Fallo al conectar a $codigoIdentificador."
		}
	}


	def leerFilaActuacionIndividual (Jugador jugador, GPathResult filaPartido ) {
		def valoracion = filaPartido.td[COLUMNA_VALORACION_PARTIDO].text()
		def minutos = Integer.valueOf(filaPartido.td[COLUMNA_MINUTOS].text().replace('\'',' ').trim() )
		println minutos
		def actuacion = new ValoracionPartido(totalValoracion:valoracion, minutosJugados: minutos,  partido:leerPartido(filaPartido),
				jugador:jugador)
		actuacion.save()
		println "Jugador:${jugador.nombre} - Valoracion = $valoracion"
	}

	/**
	 * Lee un partido de los disputados en una temporada
	 * por el jugador.
	 * 
	 * @param filaPartido fila html que contiene el partido
	 * @return devuelve los datos leidos del partido
	 */
	def leerPartido (GPathResult filaPartido){
		def nombreLocal = filaPartido.td[COLUMNA_EQUIPOS_PARTIDO].a.text().split('-')[0]
		def local = leerEquipo(nombreLocal)

		def nombreVisitante = filaPartido.td[COLUMNA_EQUIPOS_PARTIDO].a.text().split('-')[1]
		def visitante = leerEquipo(nombreVisitante)

		def partido = Partido.findWhere(local: local, visitante: visitante,
				jornada: Integer.valueOf(filaPartido.td[COLUMNA_NUM_JORNADA].text()))

		if(partido == null){
			partido = new Partido(local: local, visitante: visitante,
					jornada:  Integer.valueOf(filaPartido.td[COLUMNA_NUM_JORNADA].text()), jugado : true)
		}

		partido.jugado = true
		partido.save()


		return partido
	}

	/**
	 * Obtener equipo a partir de su nombre corto.
	 * 
	 * @param nombreCorto nombre corto identificativo del equipo
	 * @return el equipo que tiene dicho nombre corto
	 */
	private leerEquipo(String nombreCorto) {
		def equipoLocal = Equipo.findWhere(nombreCorto:nombreCorto)
		if(equipoLocal == null){
			equipoLocal = new Equipo(nombreCorto:nombreCorto)
			equipoLocal.save()
		}
		return equipoLocal
	}
}