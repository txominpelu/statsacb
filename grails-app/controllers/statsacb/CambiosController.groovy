package statsacb

import grails.converters.*

class CambiosController {

	def jugadorService

	def index = {
		session.equipoSM = new EquipoSM()
		session.equipoSM.jugadores = []
	}
	
	def sustituir = {
		def jugador = Jugador.findWhere(codigoAcb:params.jugFinal)
		assert session?.equipoSM?.puedeFichar(jugador)
		session.equipoSM.fichar(jugador)
		redirect(action:equipo)
	}
	
	def equipo = {
		if(!session.equipoSM.isAttached()) {
			session.equipoSM.attach()
		}
		render(template:"tablaJugadores", 
					model:[jugadores:session.equipoSM.jugadores.
						collect { jugadorService.datosJugadorTabla(it)}, 
						equipo:true])
	}
	
	def mercado = {
		def criteria = Jugador.createCriteria()
		def jugadores = criteria {
			and {
				if(params.posicion){
					eq("posicion", Posicion.valueOf(params.posicion))
				}
				or{
					le("precio", session.equipoSM.dineroDisponible)
					eq("precio", session.equipoSM.dineroDisponible)
				}
			}
			order("precio", "desc")
		}
		render(template:"tablaJugadores", model :[jugadores : jugadores.collect { jugadorService.datosJugadorTabla(it) }])
	}
	
	
	def equipojson = {
		render session.equipoSM as JSON
	}
	
}
