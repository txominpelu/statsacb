package statsacb.scraping;

import org.ccil.cowan.tagsoup.Parser;

@Grapes( @Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='1.2') )
class CustomParser {
	
	static final String ENCODING = "ISO-8859-1"
		
	static final PARSER = new XmlSlurper(new Parser() )
	
	def parseUrl(String url){
		new URL(url).withReader (ENCODING) { reader -> return PARSER.parse(reader) }
	}

}