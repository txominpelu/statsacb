package statsacb

import grails.test.*

class EquipoSMTests extends GrailsUnitTestCase {
	
	private EquipoSM equipo
	
	private static List<String> CODIGOS = ['ABC', 'DEF', 'GHI']
	
	private static final String CODIGO_PRUEBA= 'JKL'
	
	private static final int DINERO_DISPONIBLE = 100000
	
    protected void setUp() {
        super.setUp()
		mockDomain(EquipoSM)
		equipo = new EquipoSM()
		equipo.jugadores = []
		for(codigo in CODIGOS){
			equipo.jugadores.add(new Jugador(codigoAcb:codigo,posicion:Posicion.BASE))
		}
		equipo.dineroDisponible = DINERO_DISPONIBLE
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	/**
	* Ficha un jugador que sí
	* se puede fichar.
	*/
   void testFicharJugadorValido() {
	   def jugador = new Jugador(codigoAcb:CODIGO_PRUEBA, posicion:Posicion.ALERO)
	   jugador.precio = DINERO_DISPONIBLE
	   equipo.fichar(jugador)
	   assert equipo.getJugadores(Posicion.ALERO).size() == 1
	   assert equipo.dineroDisponible == 0
   }

	/**
	 * Intenta fichar un jugador que sí
	 * se puede fichar.
	 */
    void testPuedeFicharJugadorValido() {
		def jugador = new Jugador(codigoAcb:CODIGO_PRUEBA, posicion:Posicion.ALERO)
		jugador.precio = DINERO_DISPONIBLE
		assert equipo.puedeFichar(jugador)
    }
	
	/**
	 * Intenta fichar un jugador de una posicion
	 * cuyos puestos estan todos copados.
	 * 
	 */
	void testNoPuedeFicharJugadorMaxPosicion() {
		def jugador = new Jugador(codigoAcb:CODIGO_PRUEBA, posicion:Posicion.BASE)
		jugador.precio = DINERO_DISPONIBLE
		assert ! equipo.puedeFichar(jugador)
	}
	
	/**
	 * Intenta fichar un jugador que cuesta más
	 * del dinero disponible.
	 */
	void testNoPuedeFicharJugadorSinDinero() {
		def jugador = new Jugador(codigoAcb:CODIGO_PRUEBA, posicion:Posicion.ALERO)
		jugador.precio = DINERO_DISPONIBLE + 1
		assert ! equipo.puedeFichar(jugador)
	}
}
