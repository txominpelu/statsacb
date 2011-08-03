package statsacb

/**
 * An ACB basketball player
 * with it's more relevant profile data.
 * 
 * @author imediava
 *
 */
class Jugador {

    static constraints = {
		codigoAcb(nullable:false, unique:true)
		posicion(nullable:true)
		equipo(nullable:true)
    }
	
	static belongsTo = [equipo:Equipo]

    /**
     * Nombre del jugador.
     **/
    String nombre

    /**
     * Codigo del jugador en ACB.com.
     **/
    String codigoAcb
	
	/**
	 * Posicion en la que juega.
	 */
	Posicion posicion
	
	/**
	 * Precio actual del jugador.
	 */
	String precio
	
	
}
