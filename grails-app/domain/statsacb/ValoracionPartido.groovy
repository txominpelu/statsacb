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
	
	static transients = [ "equipoLocal", "equipoRival", "partidoGanado", "valoracionSM" ]
	
	
    Jugador jugador
    Partido partido
	/**
	 * Valoracion individual del jugador en el partido
	 */
	Integer minutosJugados
    Integer totalValoracion
	
	/**
	 * Devuelve el equipo rival del jugador
	 * en este partido.
	 * 
	 * @return equipo rival del jugador en este partido
	 */
	public EquipoAcb getEquipoRival(){
		if(isEquipoLocal()){
			return partido.visitante
		}else{
			return partido.local
		}
		
	}
	
	/**
	* Devuelve si el jugador fue local en este partido.
	*
	* @return verdadero si el jugador jugo local en el partido
	*/
	private boolean isEquipoLocal(){
		return jugador.equipo.equals(partido.local)
	}
	
	/**
	* Devuelve si el jugador gano este partido.
	*
	* @return verdadero si el jugador gano el partido
	*/
   public boolean isPartidoGanado(){
	   if(!jugador.equipo.isAttached()) {
			jugador.equipo.attach()
	   }
	   return partido.getGanador().id == jugador.equipo.id
   }
   
   /**
    * Obtiene la valoracion del jugador
    * en este partido segun el SM.
    * 
    * @return valoracion en el SM
    */
   public float getValoracionSM(){
	   if(totalValoracion > 0 && isPartidoGanado()){
		   return totalValoracion * 1.20f
	   }
	   return totalValoracion
	   
   }
	
	
}
