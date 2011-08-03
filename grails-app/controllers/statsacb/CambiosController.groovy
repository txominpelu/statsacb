package statsacb

class CambiosController {

	def jugadorService

	def index = {
		[jugadores : Jugador.getAll().collect { jugadorService.datosJugadorTabla(it.codigoAcb) }]
	}
}
