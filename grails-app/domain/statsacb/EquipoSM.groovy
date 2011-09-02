package statsacb

class EquipoSM {
	
	static int DINERO_INICIAL = 2000000

    static constraints = {
    }
	
	static hasMany = [jugadores:Jugador]
	
	List jugadores
	
	Integer dineroDisponible = DINERO_INICIAL
	
	/**
	 * Ficha al jugador si cumple con los
	 * requisitos necesarios.
	 * 
	 * @param jugador jugador a fichar
	 */
	protected void fichar(Jugador jugador){
		if(puedeFichar(jugador)){
			jugadores.add(jugador)
			dineroDisponible = dineroDisponible - jugador.precio
			this.save()
		}
	}
	
	/**
	 * Comprueba si este equipo puede fichar al jugador
	 * pasado.
	 * 
	 * @param jugador jugador que se ficharia
	 * @return verdadero si el jugador se puede incorporar, falso en caso contrario
	 */
	protected boolean puedeFichar(Jugador jugador){
		return dineroDisponible >= jugador.precio && 
				getJugadores(jugador.posicion).size() < jugador.posicion.getMaxJugadores()
	}
	
	/**
	 * Obtiene todos los jugadores de una
	 * posicion dada.
	 * 
	 * @param posicion posicion
	 * @return lista de jugadores que juegan en la posicion pasada
	 */
	protected List<Jugador> getJugadores(Posicion posicion){
		return this.jugadores.findAll { it.posicion == posicion } as List
	}
	
}
