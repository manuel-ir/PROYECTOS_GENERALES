package ficheros.EJ_RESUELTOS;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GestionLibreria {

    // Constante para el nombre del fichero XML que vamos a crear y modificar
    private static final String FILENAME = "libreria.xml";

    public static void main(String[] args) {
        try {
            // --- Ejercicio 1 y 3: Crear y guardar el documento XML ---
            System.out.println("--- Ejercicios 1 y 3: Creando y guardando '" + FILENAME + "' ---");
            crearYGuardarXML();
            System.out.println("XML creado y guardado con éxito.");

            // --- Ejercicio 2: Recorrer el documento ---
            System.out.println("\n--- Ejercicio 2: Recorriendo el DOM inicial ---");
            Document doc = loadDocument(); // Carga el documento que acabamos de guardar
            recorrerDOM(doc.getDocumentElement(), ""); // Llama a la función recursiva

            // --- Ejercicios 4 al 9: Modificar el DOM ---
            System.out.println("\n--- Ejercicios 4-9: Modificando el DOM ---");
            modificarDOM();
            System.out.println("Modificaciones guardadas en '" + FILENAME + "'.");

            // --- Ejercicio 10: Leer con SAX ---
            System.out.println("\n--- Ejercicio 10: Leyendo el XML modificado con SAX ---");
            leerConSAX();

            // --- Ejercicio 11: Consultas XPath ---
            System.out.println("\n--- Ejercicio 11: Consultando el XML modificado con XPath ---");
            consultarConXPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Ejercicio 1 y 3: Crear y Guardar ---
    private static void crearYGuardarXML() throws Exception {
        // 1. Crear la factoría y el constructor
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        // 2. Crear el documento (Ejercicio 1)
        Document doc = db.newDocument();

        // 3. Crear la estructura del DOM
        // <libreria> (elemento raíz)
        Element root = doc.createElement("libreria");
        doc.appendChild(root);

        // <nombre>
        Element nombre = doc.createElement("nombre");
        nombre.setTextContent("Librería Pepe");
        root.appendChild(nombre);

        // <libros>
        Element libros = doc.createElement("libros");
        root.appendChild(libros);

        // Añadir los 3 libros usando un método auxiliar
        libros.appendChild(crearLibro(doc, "1234567890", "Don Quijote de la Mancha", "Miguel de Cervantes"));
        libros.appendChild(crearLibro(doc, "2345678901", "Lazarillo de Tormes", "Anónimo"));
        libros.appendChild(crearLibro(doc, "45678910123", "La vida es sueño", "Pedro Calderón de la Barca"));

        // 4. Guardar el DOM en un fichero XML (Ejercicio 3)
        saveDocument(doc);
    }

    // --- Ejercicio 2: Recorrer DOM ---
    private static void recorrerDOM(Node node, String indent) {
        // Imprimimos solo nodos de tipo Elemento
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) node;
            System.out.print(indent + "<" + e.getTagName());

            // Imprimir atributos si los tiene
            if (e.hasAttributes()) {
                NamedNodeMap attributes = e.getAttributes();
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attr = attributes.item(i);
                    System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
                }
            }
            System.out.print(">");

            // Comprobar si tiene hijos o solo texto
            NodeList children = node.getChildNodes();
            if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
                // Es un nodo simple con texto
                System.out.print(children.item(0).getNodeValue());
                System.out.println("</" + e.getTagName() + ">");
            } else {
                // Tiene elementos hijos, hacer recursión
                System.out.println();
                for (int i = 0; i < children.getLength(); i++) {
                    recorrerDOM(children.item(i), indent + "  ");
                }
                System.out.println(indent + "</" + e.getTagName() + ">");
            }
        }
    }

    // --- Ejercicios 4-9: Modificar DOM ---
    private static void modificarDOM() throws Exception {
        // Cargar el documento existente
        Document doc = loadDocument();
        Element root = doc.getDocumentElement();

        // --- Ejercicio 4: Añade un nuevo libro ---
        Element libros = (Element) root.getElementsByTagName("libros").item(0);
        Element nuevoLibro = crearLibro(doc, "8901234567", "100 años de soledad", "Gabriel García Márquez");
        libros.appendChild(nuevoLibro);
        System.out.println("Ej. 4: Libro '100 años de soledad' añadido.");

        // --- Ejercicio 5: Introduce una nueva etiqueta (dirección) ---
        Element direccion = doc.createElement("direccion");
        direccion.setTextContent("C/ Amiel 12");
        // Insertamos la dirección antes del nodo <libros>
        root.insertBefore(direccion, libros);
        System.out.println("Ej. 5: Etiqueta 'direccion' añadida.");

        // --- Ejercicio 6: Elimina el segundo libro ---
        NodeList listaLibros = libros.getElementsByTagName("libro");
        Node libroAEliminar = listaLibros.item(1); // 0=Quijote, 1=Lazarillo
        libros.removeChild(libroAEliminar);
        System.out.println("Ej. 6: Segundo libro ('Lazarillo de Tormes') eliminado.");

        // --- Ejercicio 7: Cambia la dirección ---
        // La variable 'direccion' (creada en Ej. 5) sigue apuntando al nodo
        direccion.setTextContent("C/ Amiel 26");
        System.out.println("Ej. 7: Dirección actualizada a 'C/ Amiel 26'.");

        // --- Ejercicio 8: Añade el atributo "estado" a los libros ---
        listaLibros = libros.getElementsByTagName("libro"); // Recargamos la lista
        for (int i = 0; i < listaLibros.getLength(); i++) {
            Element libro = (Element) listaLibros.item(i);
            libro.setAttribute("estado", "nuevo"); // Ponemos "nuevo" como valor
        }
        System.out.println("Ej. 8: Atributo 'estado' añadido a todos los libros.");

        // --- Ejercicio 9: Elimina el atributo "estado" del primer y tercer libro ---
        // La lista AHORA es: 0=Quijote, 1=La vida es sueño, 2=100 años
        ((Element) listaLibros.item(0)).removeAttribute("estado"); // Quijote
        ((Element) listaLibros.item(2)).removeAttribute("estado"); // 100 años
        System.out.println("Ej. 9: Atributo 'estado' eliminado del primer y tercer libro.");

        // Guardar todos los cambios
        saveDocument(doc);
    }

    // --- Ejercicio 10: Leer con SAX ---
    private static void leerConSAX() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        // Creamos una instancia de nuestro manejador personalizado
        DefaultHandler handler = new DefaultHandler() {
            private StringBuilder buffer = new StringBuilder();
            private String libroActualISBN = null;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                buffer.setLength(0); // Limpiar buffer
                if (qName.equalsIgnoreCase("libro")) {
                    libroActualISBN = attributes.getValue("isbn");
                    System.out.println("Leyendo Libro (ISBN: " + libroActualISBN + ")");
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                // Acumulamos el texto entre etiquetas
                buffer.append(ch, start, length);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                String data = buffer.toString().trim();
                if (data.isEmpty()) return;

                if (qName.equalsIgnoreCase("nombre")) {
                    System.out.println("  Nombre Librería: " + data);
                } else if (qName.equalsIgnoreCase("direccion")) {
                    System.out.println("  Dirección: " + data);
                } else if (qName.equalsIgnoreCase("titulo")) {
                    System.out.println("    Título: " + data);
                } else if (qName.equalsIgnoreCase("autor")) {
                    System.out.println("    Autor: " + data);
                }
            }
        };

        // Parsear el fichero
        saxParser.parse(FILENAME, handler);
    }

    // --- Ejercicio 11: Consultas XPath ---
    private static void consultarConXPath() throws Exception {
        Document doc = loadDocument();
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();

        // 11.1. El isbn de todos los libros
        System.out.println("11.1. ISBN de todos los libros:");
        String expr = "/libreria/libros/libro/@isbn";
        NodeList nodes = (NodeList) xpath.evaluate(expr, doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println("  - " + nodes.item(i).getNodeValue());
        }

        // 11.2. El nombre de la librería
        System.out.println("\n11.2. Nombre de la librería:");
        expr = "/libreria/nombre";
        String nombre = (String) xpath.evaluate(expr, doc, XPathConstants.STRING);
        System.out.println("  - " + nombre);

        // 11.3. El título de todos los libros
        System.out.println("\n11.3. Título de todos los libros:");
        expr = "/libreria/libros/libro/titulo";
        nodes = (NodeList) xpath.evaluate(expr, doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println("  - " + nodes.item(i).getTextContent());
        }

        // 11.4. Título y autor del libro cuyo isbn es 45678910123
        System.out.println("\n11.4. Título y autor del libro con ISBN 45678910123:");
        expr = "/libreria/libros/libro[@isbn='45678910123']";
        Node libro = (Node) xpath.evaluate(expr, doc, XPathConstants.NODE);
        if (libro != null) {
            String titulo = (String) xpath.evaluate("titulo/text()", libro, XPathConstants.STRING);
            String autor = (String) xpath.evaluate("autor/text()", libro, XPathConstants.STRING);
            System.out.println("  - Título: " + titulo);
            System.out.println("  - Autor: " + autor);
        }

        // 11.5. Toda la información de los libros cuyo isbn es mayor que 2000000000
        System.out.println("\n11.5. Libros con ISBN > 2000000000:");
        expr = "/libreria/libros/libro[@isbn > 2000000000]";
        nodes = (NodeList) xpath.evaluate(expr, doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            System.out.println("  - Libro (ISBN: " + el.getAttribute("isbn") + ")");
            System.out.println("      Título: " + el.getElementsByTagName("titulo").item(0).getTextContent());
            System.out.println("      Autor: " + el.getElementsByTagName("autor").item(0).getTextContent());
        }

        // 11.6. El isbn de los libros cuyo autor sea Anónimo
        System.out.println("\n11.6. ISBN de libros con autor 'Anónimo':");
        expr = "/libreria/libros/libro[autor='Anónimo']/@isbn";
        nodes = (NodeList) xpath.evaluate(expr, doc, XPathConstants.NODESET);
        if (nodes.getLength() == 0) {
            System.out.println("  - No se encontraron (El libro fue borrado en el Ej. 6)");
        } else {
            for (int i = 0; i < nodes.getLength(); i++) {
                System.out.println("  - " + nodes.item(i).getNodeValue());
            }
        }

        // 11.7. El autor del libro Don Quijote de la Mancha
        System.out.println("\n11.7. Autor de 'Don Quijote de la Mancha':");
        expr = "/libreria/libros/libro[titulo='Don Quijote de la Mancha']/autor";
        String autor = (String) xpath.evaluate(expr, doc, XPathConstants.STRING);
        System.out.println("  - " + autor);
    }


    // --- MÉTODOS AUXILIARES ---

    /**
     * Carga un documento XML desde el disco y lo devuelve como un objeto Document.
     */
    private static Document loadDocument() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(new File(FILENAME));
    }

    /**
     * Guarda un objeto Document en un fichero XML en el disco.
     */
    private static void saveDocument(Document doc) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        // Configuración para que el XML de salida esté indentado (formateado)
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(FILENAME));
        t.transform(source, result);
    }

    /**
     * Método de ayuda para crear un nodo <libro> completo.
     */
    private static Element crearLibro(Document doc, String isbn, String titulo, String autor) {
        Element libro = doc.createElement("libro");
        libro.setAttribute("isbn", isbn);

        Element eTitulo = doc.createElement("titulo");
        eTitulo.setTextContent(titulo);
        libro.appendChild(eTitulo);

        Element eAutor = doc.createElement("autor");
        eAutor.setTextContent(autor);
        libro.appendChild(eAutor);

        return libro;
    }
}
