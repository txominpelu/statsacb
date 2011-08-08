package statsacb.scraping

import statsacb.Posicion

@Grapes( @Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='1.2') )
class ObtenerCodigosJugadores {

	static final String ENCODING = "ISO-8859-1"

	static final int EDICION_ACB = 55

	static final PARSER = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser() )

	/**
	 * Imprime por pantalla la lista de todos los jugadores
	 * que han participado en la edicion de la liga ACB.
	 * 
	 * @param args
	 * @return
	 */
	def static main(String[] args){
		def lectorCodigos = new ObtenerCodigosJugadores()
		for(codigo in lectorCodigos.getJugadoresTemporada(EDICION_ACB)){
			println ""
		}
	}

	/**
	 * Obtiene una lista con los codigos de todos los jugadores que han
	 * participado en una temporada (aunque ya no esten activos).
	 * 
	 * Tarda mucho mas porque tiene que hacer muchas peticiones para 
	 * descubrir a todos los jugadores.
	 * 
	 * @param edicionAcb numero de temporada de la que se quieren obtener los jugadores
	 * @return lista ordenada de codigos de los jugadores que han participado en la temporada
	 */
	private Set<String> getJugadoresTemporada(int edicionAcb){
		def codigosOrdenados = new TreeSet<String>()
		def rangoLetrasList = new java.util.ArrayList('A'..'Z')
		rangoLetrasList.addAll('0'..'9')

		for(i in rangoLetrasList){
			for(j in rangoLetrasList){
				for(k in rangoLetrasList){
					if(esJugadorValido("$i$j$k", edicionAcb)){
						println "$i$j$k"
						codigosOrdenados.add("$i$j$k")
					}
				}
			}
		}
	}

	/**
	 * Obtiene una lista de los jugadores
	 * en activo en competición a través de la
	 * lectura de las páginas del mercado.
	 * 
	 * @return lista ordenada de codigos de los jugadores activos
	 */
	private Set<String> getJugadoresActivos() {
		def codigosOrdenados = new TreeSet<String>()
		def lector = new LectorMercado()
		for(posicion in Posicion.values()){
			def documento = lector.getDocumentoMercado(posicion)
			for(fila in lector.getFilasJugadores(documento)){
				codigosOrdenados.add(lector.leerCodigo(fila))
			}
		}
		return codigosOrdenados
	}

	/**
	 * Dado el codigo de un jugador devuelve si acb.com
	 * tiene informacion sobre ese jugador.
	 * 
	 * @param edicionAcb numero de temporada de la que se quieren obtener si el jugador participo
	 * @param codigoJugador
	 * @return verdadero si para ese jugador existe
	 */
	def static boolean esJugadorValido(String codigoJugador, int edicioAcb){
		try{

			def url = new URL("http://www.acb.com/stspartidojug.php?cod_jugador=$codigoJugador&cod_competicion=LACB&cod_edicion=$EDICION_ACB")
			url.withReader (ENCODING) { reader ->

				def html = PARSER.parse(reader)
				def tablaEstadisticas = html.'**'.find{
					it.name() == 'table' && it['@class'] == 'estadisticas2'
				}

				return tablaEstadisticas.tr.size() > 2
			}
		}catch(java.net.ConnectException e){
			println "Fallo al conectar al jugador $codigoJugador."
		}
	}
}
