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
		jugador.metaClass.ultimosTres = obtenerUltimosPartidos(codigoJugador, 3).collect { it.totalValoracion }.sum() / 3
		jugador.metaClass.valMedia = obtenerValMedia(codigoJugador)
		jugador.metaClass.valJornada = obtenerUltimosPartidos(codigoJugador, 1)[0].totalValoracion
		return jugador
    }
	
	/**
	 * Obtiene la valoracion en los ultimos n partidos de 
	 * un jugador.
	 * 
	 * @param codigoJugador codigo acb del jugador
	 * @param numeroPartidos numero de partidos a tener en cuenta
	 * @return valoracion del jugador en los ultimos n partidos
	 */
	def obtenerUltimosPartidos(String codigoJugador, int numeroPartidos){
		return  ValoracionPartido.executeQuery("select val from ValoracionPartido as val" +
						" inner join val.partido as partido"  +
						" where val.jugador.codigoAcb = :codigoJugador" +
						" order by  partido.jornada desc",
						 [codigoJugador:codigoJugador], [max:numeroPartidos, offset:0])
	}
	
	
	def obtenerValMedia(String codigoJugador){
		return  ValoracionPartido.executeQuery("select avg(val.totalValoracion) from ValoracionPartido as val" +
						" inner join val.partido as partido"  +
						" where val.jugador.codigoAcb = :codigoJugador",
						 [codigoJugador:codigoJugador], [offset:0])[0]
	}
}
