package statsacb.scraping

import statsacb.EquipoAcb;
import statsacb.Jugador;
import groovy.util.slurpersupport.NodeChild;

import groovy.util.slurpersupport.GPathResult;

/**
 * Lee plantillas de equipos para obtener
 * los equipos a los que pertenecen los jugadores.
 * 
 * @author imediava
 *
 */
class LectorEquipos {

	static final String EDICION_ACB = '55'

	/**
	* Lee plantillas de una temporada de la
	* ACB y asigna a los jugadores su el equipo
	* al que pertenecen.
	*
	*
	*/
	def static main (String[] args){
		listarEquipos()
		def lector = new LectorEquipos()
		
		for(equipo in EquipoAcb.getAll()){
			println "Codigo: ${equipo.codigo}"
			lector.leerEquipo(equipo)
		}
	}

	/**
	 * Lista todos los equipos de una
	 * temporada con los nombres que esos equipos
	 * tienen para la web de la acb que son nombres
	 * constantes y que sirven para identificar al
	 * equipo.
	 * 
	 * @return
	 */
	def static listarEquipos(){
		def html = new CustomParser().parseUrl("http://www.acb.com/menuplantillas.php?cod_edicion=$EDICION_ACB")

		html.'**'.findAll{
			it.name() == 'td' && it['@class'] == 'menuclubs'
		}.each {
			def match = it.a[0]['@href'].toString() =~ /plantilla.php\?cod_equipo=(\w\w\w)&cod_competicion=LACB&cod_edicion=55/
		}
	}

	/**
	 * Lee la plantilla de un equipo
	 * y estable la pertenencia de los jugadores leidos
	 * al equipo.
	 * 
	 * @param equipo
	 * @return
	 */
	def leerEquipo(EquipoAcb equipo){

		def html = new CustomParser().parseUrl("http://www.acb.com/plantilla.php?cod_equipo=${equipo.codigo}&cod_competicion=LACB&cod_edicion=$EDICION_ACB")
		
		leerTabla(html, 0, 1, equipo)
		leerTabla(html, 2, 0, equipo)
		
	}
	
	def leerTabla(NodeChild html, int tableIndex, int codeCellIndex , EquipoAcb equipo){
		
		def tablas = html.'**'.findAll{
			it.name() == 'table' && it['@class'] == 'plantilla'
		}
		
		if(tablas.size() > tableIndex){
			tablas[tableIndex].tr[1..-2].each {
				leerJugador(it, equipo, codeCellIndex)
			}
		}
	}
	
	
	def leerJugador(GPathResult filaJugador, EquipoAcb equipo, int codeCellIndex){
		
		def codigoJugador = filaJugador.td[codeCellIndex].a[0]['@href'].toString().replace('jugador.php?id=', '')
		def jugador = Jugador.findWhere(codigoAcb: codigoJugador)
		if( jugador ) {
			equipo.addToJugadores(jugador)
			equipo.save(flush:true)
		} 
	}
}
