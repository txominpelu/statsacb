package statsacb

import grails.test.*

class JugadorControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {

		def jugador = new Jugador(nombre:"Juan", id:1)
		mockDomain(Jugador, [jugador])
		
		def local = new Equipo(nombreCorto:"local")
		def visitante = new Equipo(nombreCorto:"visitante")
		mockDomain(Equipo, [local, visitante])
		
		def partido = new Partido(local:local, visitante:visitante)
		mockDomain(Partido, [partido])
		
		def performance = new ValoracionPartido(jugador:jugador, partido: partido, totalValoracion:4) 
		mockDomain(ValoracionPartido, [performance])
		
		controller.params.id = 1
	    def returnValue = controller.index()
		
		assertSame jugador, returnValue
    }
}
