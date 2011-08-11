package statsacb

import grails.test.*
import groovy.mock.interceptor.StubFor

class CambiosControllerTests extends ControllerUnitTestCase {
	
	def jugadorService
	
	static String CODIGO = "BCL"
	static String NOMBRE = "Lopez, Pepe"
	static float VAL_ULTIMOS_TRES = 23.4
	static float VAL_MEDIA = 24.5
	static float VAL_JORNADA = 20f
	static String EQUIPO = "BAS"
		
    protected void setUp() {
        super.setUp()
		def jugadorServiceStub = new StubFor(JugadorService)
		jugadorServiceStub.demand.datosJugadorTabla { codigoJugador ->
			def datos = [:]
			datos.nombre = NOMBRE
			datos.ultimosTres = VAL_ULTIMOS_TRES
			datos.valMedia = VAL_MEDIA
			datos.valJornada = VAL_JORNADA
			datos.codigo = codigoJugador
			datos.equipo = EQUIPO
			return datos
		}
		
		jugadorService = jugadorServiceStub.proxyInstance()
		
		Jugador.metaClass.static.getAll = { [new Jugador(codigoAcb:CODIGO)] }
			
    }

    protected void tearDown() {
        super.tearDown()
    }

	/**
	 * Comprueba que el controlador genera datos validos sobre
	 * los jugadores disponibles en el dominio.
	 */
    void testObtenerModelo() {
		def controller = new CambiosController()
		controller.jugadorService = jugadorService
		def model = controller.index()
		assert model?.jugadoresMercado.size() == 1
		assertEquals NOMBRE, model.jugadoresMercado[0].nombre
		assertEquals VAL_ULTIMOS_TRES, model.jugadoresMercado[0].ultimosTres
    }
	
	/**
	 * Comprueba que el controlador funciona correctamente cuando
	 * el usuario todavia no ha creado ningun equipo.
	 */
	void testEquipoVacio() {
		def controller = new CambiosController()
		controller.jugadorService = jugadorService
		controller.index()
		
		assert controller.session.equipoSM.dineroDisponible == EquipoSM.DINERO_INICIAL
		assert controller.session.equipoSM.jugadores.size() == 0
	}
}
