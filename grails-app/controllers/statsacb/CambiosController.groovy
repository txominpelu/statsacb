package statsacb

class CambiosController {

	def jugadorService

	def index = {
		session.equipoSM = new EquipoSM()
		session.equipoSM.jugadores = []
		[jugadoresMercado : Jugador.getAll().collect { jugadorService.datosJugadorTabla(it) }]
	}
	
	def sustituir = {
		def jugador = Jugador.findWhere(codigoAcb:params.jugFinal)
		assert session?.equipoSM.puedeFichar(jugador)
		session.equipoSM.fichar(jugador)
		render(template:"tablaJugadores", model:[jugadores:session.equipoSM.jugadores.collect { jugadorService.datosJugadorTabla(it)}, min:11])
	}
	
	
}
