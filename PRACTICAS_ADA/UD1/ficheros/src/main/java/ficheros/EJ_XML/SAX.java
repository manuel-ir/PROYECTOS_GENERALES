package ficheros.EJ_XML;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class SAX {
   public SAX() {
   }

   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();
      parser.parse("personas.xml", new SAXParserHandler());
   }
}
