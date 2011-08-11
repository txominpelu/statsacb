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
		jugador.metaClass.ultimosTres = obtenerUltimosPartidos(jugador, 3).collect { it.totalValoracion }.sum() / 3
		jugador.metaClass.valMedia = obtenerValMedia(jugador)
		jugador.metaClass.valJornada = obtenerUltimosPartidos(jugador, 1)[0].totalValoracion
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
	def obtenerUltimosPartidos(Jugador jugador, int numeroPartidos){
		return  ValoracionPartido.executeQuery("select val from ValoracionPartido as val" +
						" inner join val.partido as partido"  +
						" where val.jugador.codigoAcb = :codigoJugador" +
						" order by  partido.jornada desc",
						 [codigoJugador:jugador.codigoAcb], [max:numeroPartidos, offset:0])
	}
	
	
	def obtenerValMedia(Jugador jugador){
		return  ValoracionPartido.executeQuery("select avg(val.totalValoracion) from ValoracionPartido as val" +
						" inner join val.partido as partido"  +
						" where val.jugador.codigoAcb = :codigoJugador",
						 [codigoJugador:jugador.codigoAcb], [offset:0])[0]
	}
}
