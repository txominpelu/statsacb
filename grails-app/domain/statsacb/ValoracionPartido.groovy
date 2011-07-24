package statsacb

/**
 * A player performance in a match.
 * 
 * @author imediava
 *
 */
class ValoracionPartido {

    static constraints = {
    }
    Jugador jugador
    Partido partido
	/**
	 * Valoracion individual del jugador en el partido
	 */
	Integer minutosJugados
    Integer totalValoracion
	
	
}
