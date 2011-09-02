package statsacb

class JugadorService {

    static transactional = true

	/**
	* Dado un jugador obtiene los datos del jugador
	* a mostrar en la tabla.
	*
	* @param codigoJugador codigo ACB del jugador
	* @return diccionario con los datos del jugador
	*/
   def datosJugadorTabla(String codigoJugador) {
	   def jugador = Jugador.findWhere(codigoAcb:codigoJugador)
	   return datosJugadorTabla(jugador)
   }
   
	/**
	 * Dado un jugador obtiene los datos del jugador
	 * a mostrar en la tabla.
	 * 
	 * @param jugador jugador
	 * @return diccionario con los datos del jugador
	 */
    def datosJugadorTabla(Jugador jugador) {
		jugador.metaClass.partJugados = getNumPartidosJugados(jugador.codigoAcb)
		def ultimosTresPartidos = obtenerUltimosPartidos(jugador.codigoAcb, 3)
		jugador.metaClass.ultimosTres = ultimosTresPartidos.collect { it.getValoracionSM() }.sum() / 3 as Double
	
		jugador.metaClass.valJornada = ultimosTresPartidos[-1].getValoracionSM()
		jugador.metaClass.varPrecio = getVariacionTotalPrecio(jugador.ultimosTres, jugador.valMedia, getNumPartidosJugados(jugador.codigoAcb), jugador.precio)
		
		return jugador
    }
	
	
	
	/**
	 * Obtiene la valoracion en los ultimos n partidos de 
	 * un jugador.
	 * 
	 * @param jugador jugador
	 * @param numeroPartidos numero de partidos a tener en cuenta
	 * @return valoracion del jugador en los ultimos n partidos
	 */
	def obtenerUltimosPartidos(String codigoJugador, int numeroPartidos){
		return  ValoracionPartido.executeQuery("""select val from ValoracionPartido as val
						 inner join val.partido as partido  
						 where val.jugador.codigoAcb = :codigoJugador and
						 partido.ligaRegular = true
						 order by  partido.jornada desc""",
						 [codigoJugador:codigoJugador], [max:numeroPartidos, offset:0])
	}
	
	
	def getNumPartidosJugados(String codigoJugador){
		return  ValoracionPartido.executeQuery("""select count(*) from ValoracionPartido as val
						 inner join val.partido as partido
						 where val.jugador.codigoAcb = :codigoJugador and
						 partido.ligaRegular = true""",
						 [codigoJugador:codigoJugador], [offset:0])[0]
	}
	
	/**
	 * Obtiene la variacion porcentual de precio prevista para un jugador
	 * si hace en la proxima jornada la valoracion prevista pasada.
	 * 
	 * @param valPrev valoracion prevista para el jugador
	 * @param valMedia valoracion media del jugador en la temporada
	 * @param numJornadasJugadas numero de jornadas en las que el jugador ha jugado
	 * @param precioActual precio actual del jugador
	 * @return variacion porcentual de precio prevista 
	 */
	def getVariacionPrecioPorcentual(Double valPrev, float valMedia, Long numJornadasJugadas, int precioActual){
		def numerador = (valPrev + (valMedia * numJornadasJugadas)) * 70000
		def denominador = (numJornadasJugadas + 1) * precioActual
		return numerador/denominador
	}
	
	/**
	* Obtiene la variacion absoluta en euros de precio prevista para un jugador
	* si hace en la proxima jornada la valoracion prevista pasada.
	*
	* @param valPrev valoracion prevista para el jugador
	* @param valMedia valoracion media del jugador en la temporada
	* @param numJornadasJugadas numero de jornadas en las que el jugador ha jugado
	* @param precioActual precio actual del jugador
	* @return variacion absoluta de precio prevista
	*/
	def getVariacionTotalPrecio(Double valPrev, float valMedia, Long numJornadasJugadas, int precioActual){
		return ((valPrev + (valMedia * numJornadasJugadas)) * 70000 / (numJornadasJugadas + 1)) - precioActual
		//return (variacionPorciento * precioActual) - precioActual
	}
}
