package statsacb

class JugadorController {

	def index = {
		def jugador = Jugador.get(params.id)
		def valPartidos = ValoracionPartido.executeQuery("select val from ValoracionPartido as val" + 
                        " inner join val.partido as partido order by  partido.jornada desc",
                         [], [max:3, offset:0])
		
		def rivales = valPartidos.collect {
			
				if ( it.partido.local.nombreCorto == 'PEV') {
					return it.partido.visitante.nombreCorto
				}else{
					return it.partido.local.nombreCorto
				}
		}
		
		[jugador:jugador, valPartidos: valPartidos, rivales: rivales]
		
	}
}
