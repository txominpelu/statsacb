package statsacb

class JugadorController {
	
	def jugadorService 

	def index = {
		
		def valPartidos = jugadorService.obtenerUltimosPartidos(params.codigo, 3)
		[valPartidos: valPartidos, jugador: jugadorService.datosJugadorTabla(params.codigo)]
		
	}
}
