package statsacb.scraping

import groovy.util.slurpersupport.GPathResult;
import statsacb.ValoracionPartido
import statsacb.EquipoAcb
import statsacb.Partido;
import statsacb.Jugador;

class LectorJugadores{

	

	static final Integer COLUMNA_NUM_JORNADA = 1
	
	static final int EDICION_ACB = 55

	int COLUMNA_VALORACION_PARTIDO = 17;
	int COLUMNA_EQUIPOS_PARTIDO = 2;
	int COLUMNA_MINUTOS = 3

	def static main(String[] args){
		LectorJugadores lector = new LectorJugadores();
		new File( '/home/imediava/Mis-Proyectos-Programacion/Groovy/Grails/statsacb/codigosJugadores.txt' ).eachLine { codigo ->
			println codigo
			lector.leerJugador(codigo)
		}
	}



	/**
	 * 
	 */
	def leerJugador(String codigoIdentificador){
		def html = new CustomParser().parseUrl("http://www.acb.com/stspartidojug.php?cod_jugador=$codigoIdentificador&cod_competicion=LACB&cod_edicion=$EDICION_ACB")

		if (ObtenerCodigosJugadores.esJugadorValido(codigoIdentificador, EDICION_ACB)) {


			def tablaEstadisticas = html.'**'.find{
				it.name() == 'table' && it['@class'] == 'estadisticas2'
			}
			def nombre = html.'**'.find{
				it.name() == 'div' && it['@class'] == 'nombrejug'
			}.text()
			
			def jugador = new Jugador(nombre:nombre, codigoAcb:codigoIdentificador)
			println "Nombre: ${jugador.nombre}"
			jugador.save()
			tablaEstadisticas.tr.findAll{ it['@class'] != 'estverde' }[0..-3].each { leerFilaActuacionIndividual(jugador, it) }
		}
	}


	/**
	 * Lee una fila con la actuacion individual de un jugador
	 * en un partido.
	 * 
	 * @param jugador jugador del que se leera la actuacion individual
	 * @param filaPartido fila de la tabla en html que contiene la actuacion
	 */
	def leerFilaActuacionIndividual (Jugador jugador, GPathResult filaPartido ) {
		def valoracion = filaPartido.td[COLUMNA_VALORACION_PARTIDO].text()
		def minutos = filaPartido.td[COLUMNA_MINUTOS].text().replace('\'',' ').trim()
		if(valoracion.isNumber() && minutos.isNumber()){
			def actuacion = new ValoracionPartido(totalValoracion:valoracion, minutosJugados: minutos,  partido:leerPartido(filaPartido),
					jugador:jugador)
			actuacion.save()
		}
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
		return  EquipoAcb.findWhere(nombreCorto:nombreCorto)
	}
}