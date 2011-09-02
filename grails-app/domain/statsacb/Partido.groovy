package statsacb

/**
 * A match between two teams.
 *  
 * @author imediava
 *
 */
class Partido {

	static constraints = {
		local(nullable:false)
		visitante(nullable:false)
		jornada(nullable:false)
	}
	
	static transients = ["ganador" ]

	Integer jornada
    EquipoAcb local
	Integer puntosLocal
    EquipoAcb visitante
	Integer puntosVisitante
	Boolean jugado = false
	
	/**
	 * Indica si el partido es de la
	 * liga regular.
	 */
	Boolean ligaRegular 
	
	/**
	 * Devuelve el equipo ganador de este partido.
	 * @return
	 */
	public EquipoAcb getGanador(){
		return (puntosLocal > puntosVisitante ? local : visitante)
	}

}
