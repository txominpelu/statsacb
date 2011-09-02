package statsacb.scraping;
	
import org.jsoup.nodes.Document;

@Grapes( @Grab(group='org.jsoup', module='jsoup', version='1.6.1' ))
abstract class Lector {
	
	protected abstract Document getDocumento();
	
	
	protected void leer(){
		def documento = getDocumento()
		leerDocumento(documento)
	}
}
