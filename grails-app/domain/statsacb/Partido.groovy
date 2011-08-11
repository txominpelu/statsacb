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

	Integer jornada
    EquipoAcb local
    EquipoAcb visitante
	Boolean jugado = false

}
