package ficheros.EJ_XML;

import java.io.PrintStream;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class SAXParserHandler extends DefaultHandler {
   SAXParserHandler() {
   }

   public void startDocument() throws SAXException {
      super.startDocument();
      System.out.println("Comienza la lectura del fichero personas.xml");
   }

   public void endDocument() throws SAXException {
      super.endDocument();
      System.out.println();
      System.out.println("Aquí finaliza la lectura del documento");
   }

   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      super.startElement(uri, localName, qName, attributes);
      System.out.print("<" + qName);
      if (attributes != null) {
         for(int i = 0; i < attributes.getLength(); ++i) {
            PrintStream var10000 = System.out;
            String var10001 = attributes.getQName(i);
            var10000.print(" " + var10001 + "=\"" + attributes.getValue(i) + "\"");
         }
      }

      System.out.print(">");
   }

   public void endElement(String uri, String localName, String qName) throws SAXException {
      super.endElement(uri, localName, qName);
      System.out.print("</" + qName + ">");
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      super.characters(ch, start, length);
      String contenido = new String(ch, start, length);
      System.out.print(contenido);
   }
}
