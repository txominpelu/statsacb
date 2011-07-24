package statsacb

/**
 * Team that competes on the ACB league.
 * @author imediava
 *
 */
class Equipo {

    static constraints = {
		nombreCorto(unique:true)
    }
    
    /**
     * Short name for the team that appears
     * on the individual stats in acb.com.
     */
    String nombreCorto
	
}
