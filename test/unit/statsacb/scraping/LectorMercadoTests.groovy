package statsacb.scraping;

import static org.junit.Assert.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import statsacb.Jugador
import statsacb.Posicion

import grails.test.GrailsUnitTestCase

@Grapes( @Grab(group='org.jsoup', module='jsoup', version='1.6.1' ))
class LectorMercadoTests extends GrailsUnitTestCase{

	static final String PRECIO_JUGADOR = "1.106.424"
	static final String OTRO_PRECIO_JUGADOR = "1.106.535"
	/**
	 * Codigo Acb del jugador en la prueba.
	 */
	static final String CODIGO_ACB = "B72"
	/**
	* Codigo Acb del otro jugador en la prueba.
	*/
	static final String OTRO_CODIGO_ACB = "B73"
	
	
	static final Posicion POSICION = Posicion.ALERO
	
	def lector
	/**
	 * Conjunto de elementos del dominio (mocking).
	 */
	def testInstances
	
	 protected void setUp() {
        super.setUp()
		testInstances = [new Jugador(nombre: "Jugador X.", codigoAcb: CODIGO_ACB)]
		mockDomain(Jugador, testInstances)
		lector = new LectorMercado()
    }
	
	def testLeerPrecioValido(){
		assertEquals PRECIO_JUGADOR, lector.leerPrecio(crearDocumento().select("table")[5].select("tr")[2])
	}
	
	def testLeerCodigoValido(){
		assertEquals CODIGO_ACB, lector.leerCodigo(crearDocumento().select("table")[5].select("tr")[2])
	}
	
	/**
	* Prueba la lectura de una tabla html
	* con una fila para un jugador.
	*
	* @return
	*/
	def testLeerTabla(){
		lector.leerTabla(crearDocumento(), POSICION)
		def jugador = Jugador.findByCodigoAcb(CODIGO_ACB)
		assert Jugador.count() ==  1
		assertEquals POSICION, jugador.posicion
		assertEquals PRECIO_JUGADOR, jugador.precio
	}
	
	/**
	 * Prueba la lectura de una tabla html
	 * con varias filas de jugadores.
	 * 
	 * @return
	 */
	def testLeerTablaConVariasFilas(){
		def documento = crearDocumento()
		testInstances.add(new Jugador(nombre: "Otro Jugador X.", codigoAcb: OTRO_CODIGO_ACB))
		mockDomain(Jugador, testInstances)
		crearFilaJugador(documento.select("table")[5],OTRO_CODIGO_ACB,OTRO_PRECIO_JUGADOR)
		
		lector.leerTabla(documento, POSICION)
		def jugador = Jugador.findByCodigoAcb(OTRO_CODIGO_ACB)
		assert Jugador.count() ==  2
		assertEquals POSICION, jugador.posicion
		assertEquals OTRO_PRECIO_JUGADOR, jugador.precio
	}
	
	/**
	 * Crea un documento html en memoria
	 * que se pasara al lector para ponerlo
	 * a prueba.
	 * 
	 * @return documento html creado
	 */
	private Document crearDocumento(){
		Document documento = new Document("")
		5.times {  documento.appendElement("table") }
		def tablaValida = documento.appendElement("table")

		tablaValida.appendElement("tr")
		tablaValida.appendElement("tr")
		
		crearFilaJugador(tablaValida,CODIGO_ACB,PRECIO_JUGADOR)
		
		return documento
	}

	/**
	 * Crea una fila con los datos de un jugador 
	 * en la tabla html.
	 * 
	 * @param documento tabla html
	 * @param codigo codigo del jugador
	 * @param precio precio del jugador
	 * @return
	 */
	private crearFilaJugador(Element tablaValida, String codigo, String precio ) {
		def trValido = tablaValida.appendElement("tr")
		def tdPrecio = trValido.appendElement("td")
		tdPrecio.addClass("grisdcha")
		tdPrecio.prependText(precio)

		def tdEnlace = trValido.appendElement("td")
		def enlace = tdEnlace.appendElement("a")
		enlace.attr("href","http://www.acb.com/stspartidojug.php?cod_jugador=$codigo")
	}

}
