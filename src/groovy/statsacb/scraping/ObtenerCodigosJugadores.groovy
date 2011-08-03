package statsacb.scraping

@Grapes( @Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='1.2') )
class ObtenerCodigosJugadores {

	static final String ENCODING = "ISO-8859-1"

	static final String EDICION_ACB = "55"
	
	static final PARSER = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser() )

	/**
	 * Imprime por pantalla la lista de todos los jugadores
	 * que han participado en la edicion de la liga ACB.
	 * 
	 * @param args
	 * @return
	 */
	def static main(String[] args){

		def rangoLetrasList = new java.util.ArrayList('A'..'Z')
		rangoLetrasList.addAll('0'..'9')

		for(i in rangoLetrasList){
			for(j in rangoLetrasList){
				for(k in rangoLetrasList){
					if(esJugadorValido("$i$j$k")){
						println "$i$j$k"
					}
				}
			}
		}
	}

	/**
	 * Dado el codigo de un jugador devuelve si acb.com
	 * tiene informacion sobre ese jugador.
	 * 
	 * @param codigoJugador
	 * @return verdadero si para ese jugador existe
	 */
	def static boolean esJugadorValido(String codigoJugador){
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
