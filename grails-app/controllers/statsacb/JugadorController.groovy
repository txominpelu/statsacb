package statsacb

class JugadorController {
	
	def jugadorService 

	def index = {
		
		def valPartidos = jugadorService.obtenerUltimosPartidos(params.codigo, 3)
		valPartidos.each{
			if(it.jugador.equipo.equals(it.partido.local)){
				it.metaClass.rival = it.partido.visitante.nombreCorto
			}else{
				it.metaClass.rival = it.partido.local.nombreCorto
			}
		}
		[valPartidos: valPartidos]
		
	}
}
