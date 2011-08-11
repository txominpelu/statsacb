package statsacb

enum Posicion {
	
	BASE (3),
	ALERO (4),
	PIVOT (4)
	
	
	
	/**
	 * Numero maximo de jugadores para la posicion.
	 */
	private final int maxJugadores;
	
	/**
	 * 
	 * @param __str
	 * @param __int
	 * @param maxJugadores maximo numero de jugadores por posicion en un equipo del SM
	 */
	Posicion(int maxJugadores){
		this.maxJugadores = maxJugadores
	}
	
	/**
	* Obtiene el numero maximo de jugadores para la posicion.
	*/
	public int getMaxJugadores(){
		return this.maxJugadores
	}

}
