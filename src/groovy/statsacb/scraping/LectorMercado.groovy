package statsacb.scraping

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.Connection

import statsacb.Posicion
import statsacb.Jugador


@Grapes( @Grab(group='org.jsoup', module='jsoup', version='1.6.1' ))
class LectorMercado extends Lector{
	
	private Posicion posicion
	
	/**
	 * Obtiene de internet los ultimos datos
	 * del mercado de jugadores.
	 * 
	 * @param args argumentos que se ignoran
	 * @return
	 */
	def static main (String[] args){
		for(posicion in Posicion.values()){
			def lector = new LectorMercado()
			lector.posicion = posicion
			lector.leerTabla()
		}
		
	}
	
	
	/**
	 * Lee la tabla de jugadores de la posicion dada a 
	 * partir del documento html pasado.
	 * 
	 * @param documento documento html
	 * @param posicion posicion pasada
	 * @return
	 */
	def leerTabla(){
		def documento = getDocumento()
		for(fila in getFilasJugadores(documento)){
			def codigoAcb = leerCodigo(fila)
			def precio = leerPrecio(fila)
			def jugador = Jugador.findByCodigoAcb(codigoAcb)
			def valMedia = leerValMedia(fila)
			
			if(jugador){
				println "${jugador.nombre} - $valMedia"
				jugador.posicion = posicion
				jugador.precio = precio
				jugador.valMedia = valMedia
				jugador.save(flush:true)
			}
		}
	}

	/**
	 * Devuelve una lista con las filas de jugadores
	 * del documento html.
	 * 
	 * @param documento documento html
	 * @return lista con las filas html de jugadores
	 */
	protected Elements getFilasJugadores(Document documento) {
		return documento.select("table")[5].select("tr")[2..-1]
	}
	
	protected int leerPrecio(Element filaJugador){
		return filaJugador.select('td.grisdcha').text().replace(".","") as int
	}
	
	protected float leerValMedia(Element filaJugador){
		return filaJugador.select('td.gris')[6].text().replace(",",".") as float
	}
	
	protected  String leerCodigo(Element filaJugador){
		return filaJugador.select('td a').attr("href").
				replace("http://www.acb.com/stspartidojug.php?cod_jugador=", "");
	}

	
	/**
	* Se conecta a la pagina del supermanager y
	* obtiene el html de la página html que contiene
	* la tabla de los jugadores de la posicion dada.
	*
	* @param posicion posicion a obtener
	* @return documento html con la tabla de jugadores de la posicion
	*/
   protected Document getDocumento(){
	   def response = Jsoup.connect("http://supermanager.acb.com").data(["control" : "1","usuario" : "imediava","clave":"quetepet"]).method(Connection.Method.POST).execute();
	   def conexion = Jsoup.connect("http://supermanager.acb.com/vermercado.php?id_pos=${posicion.ordinal() + 1}")
	   for(cookie in response.cookies()){
		   conexion.cookie(cookie.key, cookie.value)
	   }
	   return conexion.get()
   }
   
	/**
	 * Cosas a probar:
	 * 
	 * Que lee cada fila de la tabla
	 * Que guarda los datos de posicion y precio por cada fila leida en el jugador correspondiente
	 * Que parsea bien los numeros
	 */
}
