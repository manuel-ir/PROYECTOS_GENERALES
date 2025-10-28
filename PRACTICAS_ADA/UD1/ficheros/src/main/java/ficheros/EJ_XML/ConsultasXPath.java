package ficheros.EJ_XML;


import java.io.IOException;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class ConsultasXPath {
   public ConsultasXPath() {
   }

   public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
      Document doc = pasarDeXMLaDOM("personas.xml");
      consultarPersonaMenosDeX(doc, 20);
   }

   public static Document pasarDeXMLaDOM(String ruta) throws SAXException, IOException, ParserConfigurationException {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(ruta);
      return doc;
   }

   public static void verContenidoPersona(NodeList nodos) {
      for(int i = 0; i < nodos.getLength(); ++i) {
         Node n = nodos.item(i);
         Element p = (Element)n;
         System.out.print(p.getTagName() + ":");
         NodeList hijosP = p.getChildNodes();

         for(int j = 0; j < hijosP.getLength(); ++j) {
            Node n2 = hijosP.item(j);
            switch (n2.getNodeType()) {
               case 1:
                  Element e2 = (Element)n2;
                  PrintStream var10000 = System.out;
                  String var10001 = e2.getTagName();
                  var10000.print(var10001 + ":" + e2.getTextContent());
               case 2:
               default:
                  break;
               case 3:
                  Text t = (Text)n2;
                  System.out.print(t.getWholeText());
            }
         }
      }

   }

   public static void consultarPersonaMenosDeX(Document doc, int edadRef) throws XPathExpressionException {
      XPath xpath = XPathFactory.newInstance().newXPath();
      String xPathExpression = "/personas/persona[./edad<" + edadRef + "]";
      NodeList nodos = (NodeList)xpath.evaluate(xPathExpression, doc, XPathConstants.NODESET);
      System.out.println(nodos.getLength());
      verContenidoPersona(nodos);
   }

   public static void consultarPersonaLlamadasX(Document doc, String nombreRef) throws XPathExpressionException {
      XPath xpath = XPathFactory.newInstance().newXPath();
      String xPathExpression = "/personas/persona[./nombre='" + nombreRef + "']";
      NodeList nodos = (NodeList)xpath.evaluate(xPathExpression, doc, XPathConstants.NODESET);
      System.out.println(nodos.getLength());
      verContenidoPersona(nodos);
   }
}
