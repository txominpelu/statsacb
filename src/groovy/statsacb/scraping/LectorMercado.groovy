package statsacb.scraping

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.Connection

import statsacb.Posicion
import statsacb.Jugador


@Grapes( @Grab(group='org.jsoup', module='jsoup', version='1.6.1' ))
class LectorMercado {
	
	/**
	 * Obtiene de internet los ultimos datos
	 * del mercado de jugadores.
	 * 
	 * @param args argumentos que se ignoran
	 * @return
	 */
	def static main (String[] args){
		def lector = new LectorMercado()
		for(posicion in Posicion.values()){
			lector.leerTabla(getDocumentoMercado(posicion), posicion)
		}
		
	}
	/**
	 * Se conecta a la pagina del supermanager y 
	 * obtiene el html de la página html que contiene
	 * la tabla de los jugadores de la posicion dada.
	 * 
	 * @param posicion posicion a obtener
	 * @return documento html con la tabla de jugadores de la posicion
	 */
	public static Document getDocumentoMercado(Posicion posicion){
		def response = Jsoup.connect("http://supermanager.acb.com").data(["control" : "1","usuario" : "imediava","clave":"quetepet"]).method(Connection.Method.POST).execute();
		def conexion = Jsoup.connect("http://supermanager.acb.com/vermercado.php?id_pos=${posicion.ordinal() + 1}")
		for(cookie in response.cookies()){
			conexion.cookie(cookie.key, cookie.value)
		}
		return conexion.get()
	}
	
	/**
	 * Lee la tabla de jugadores de la posicion dada a 
	 * partir del documento html pasado.
	 * 
	 * @param documento documento html
	 * @param posicion posicion pasada
	 * @return
	 */
	def leerTabla(Document documento, Posicion posicion){
		def filas = documento.select("table")[5].select("tr")
		for(fila in filas[2..-1]){
			def codigoAcb = leerCodigo(fila)
			def precio = leerPrecio(fila)
			def jugador = Jugador.findByCodigoAcb(codigoAcb)
			if(jugador){
				jugador.posicion = posicion
				jugador.precio = precio
			}else{
				println "No encontrado: $codigoAcb"
			}
			//jugador.save()
		}
	}
	
	def leerPrecio(Element filaJugador){
		return filaJugador.select('td.grisdcha').text()
	}
	
	def leerCodigo(Element filaJugador){
		return filaJugador.select('td a').attr("href").
				replace("http://www.acb.com/stspartidojug.php?cod_jugador=", "");
	}

	/**
	 * Cosas a probar:
	 * 
	 * Que lee cada fila de la tabla
	 * Que guarda los datos de posicion y precio por cada fila leida en el jugador correspondiente
	 * Que parsea bien los numeros
	 */
}
