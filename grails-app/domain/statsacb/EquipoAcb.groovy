package statsacb

/**
 * Team that competes on the ACB league.
 * @author imediava
 *
 */
class EquipoAcb {

    static constraints = {
		nombreCorto(unique:true)
		codigo(unique:true)
    }
    
	static hasMany = [jugadores: Jugador]
	
	/**
	* Nombre completo del club en el
	* momento actual.
	*/
	String nombreCompleto
	
    /**
     * Short name for the team that appears
     * on the individual stats in acb.com.
     */
    String nombreCorto
	
	/**
	 * Codigo que identifica al equipo y que
	 * es comun a todas las temporadas.
	 */
	String codigo
	
	
	
}
