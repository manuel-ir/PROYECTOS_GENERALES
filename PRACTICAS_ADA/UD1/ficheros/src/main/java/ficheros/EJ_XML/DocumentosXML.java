package ficheros.EJ_XML;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class DocumentosXML {
   public DocumentosXML() {
   }

   public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
      Document doc = pasarDeXMLaDOM("personas.xml");
      Element nombre = doc.createElement("nombre");
      Element edad = doc.createElement("edad");
      nombre.setTextContent("Pepe");
      edad.setTextContent("65");
      Element pepe = doc.createElement("persona");
      pepe.appendChild(nombre);
      pepe.appendChild(edad);
      pepe.setAttribute("id", "12");
      anadirPersona(doc, pepe);
   }

   public static void verContenido(Document doc) {
      Element raiz = doc.getDocumentElement();
      NodeList hijos = raiz.getChildNodes();
      System.out.print(raiz.getTagName());

      for(int i = 0; i < hijos.getLength(); ++i) {
         Node n = hijos.item(i);
         switch (n.getNodeType()) {
            case 1:
               Element e = (Element)n;
               System.out.print(e.getTagName() + ":");
               NodeList hijos2 = e.getChildNodes();
               if (hijos2.getLength() > 0) {
                  for(int j = 0; j < hijos2.getLength(); ++j) {
                     Node n2 = hijos2.item(j);
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
                           Text t2 = (Text)n2;
                           System.out.print(t2.getWholeText());
                     }
                  }
               }
            case 2:
            default:
               break;
            case 3:
               Text t = (Text)n;
               System.out.print(t.getWholeText());
         }
      }

   }

   public static void anadirPersona(Document doc, Element p) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
      Element raiz = doc.getDocumentElement();
      raiz.appendChild(p);
      pasarDeDOMaXML("personas.xml", doc);
   }

   public static void verContenidoPersona(Document doc) {
      Element raiz = doc.getDocumentElement();
      NodeList personas = raiz.getElementsByTagName("persona");
      System.out.println(personas.getLength());
      System.out.print(raiz.getTagName());

      for(int i = 0; i < personas.getLength(); ++i) {
         Node n = personas.item(i);
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

   public static void buscarUnaPersona(Document doc, int id) {
      Element raiz = doc.getDocumentElement();
      NodeList listaPersona = raiz.getElementsByTagName("persona");
      boolean existe = false;

      for(int i = 0; i < listaPersona.getLength(); ++i) {
         Element persona = (Element)listaPersona.item(i);
         if (persona.getAttribute("id").equals("" + id)) {
            existe = true;
         }
      }

      if (existe) {
         System.out.println("He encontrado la persona.");
      } else {
         System.out.println("No existe en el documento.");
      }

   }

   public static Document pasarDeXMLaDOM(String ruta) throws SAXException, IOException, ParserConfigurationException {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(ruta);
      return doc;
   }

   public static void pasarDeDOMaXML(String ruta, Document doc) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
      File f = new File(ruta);
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("encoding", "UTF-8");
      StreamResult result = new StreamResult(f);
      DOMSource source = new DOMSource(doc);
      transformer.transform(source, result);
   }
}
