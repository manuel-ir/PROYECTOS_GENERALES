package ficheros.EX;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GestorPeliculasXML {

    private static final String RUTA_XML = "PRÁCTICAS/src/main/java/ADA/UD1/EX/coleccion_peliculas.xml";

    public static void main(String[] args) {

        // Comprobamos si el fichero existe antes de empezar
        File f = new File(RUTA_XML);
        if (!f.exists()) {
            System.err.println("No se encuentra el fichero XML:");
            return;
        }

        try {
            

            // Añade una nueva película
            System.out.println("\nAñadiendo 'Dune: Part Two'...");
            anadirPelicula("Dune: Part Two", "Denis Villeneuve", 2024, "Ciencia Ficción", 711000000);
            System.out.println("Película añadida.");

            // Consulta películas antes de 1970
            System.out.println("\nPelículas estrenadas antes de 1970:");
            mostrarPeliculasAntesDe(1970);

            // Consulta películas por recaudación (más de 200M)
            System.out.println("\nPelículas que recaudaron más de 200,000,000:");
            mostrarPeliculasPorRecaudacion(200000000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /*Añade una nueva película al fichero XML pasando las características como parámetro*/

    public static void anadirPelicula(String titulo, String director, int ano,
            String genero, double recaudacion)
            throws Exception {

        // Carga el documento DOM
        File xmlFile = new File(RUTA_XML);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        doc.getDocumentElement().normalize();

        // Obtiene el nodo raíz de películas (<peliculas>)
        Node nodoPeliculas = doc.getElementsByTagName("peliculas").item(0);

        // Calcula el nuevo ID (buscamos el ID más alto y sumamos 1)
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList idNodos = (NodeList) xpath.evaluate("//pelicula/@id", doc, XPathConstants.NODESET);
        int maxId = 0;
        for (int i = 0; i < idNodos.getLength(); i++) {
            int idActual = Integer.parseInt(idNodos.item(i).getNodeValue());
            if (idActual > maxId) {
                maxId = idActual;
            }
        }
        int nuevoId = maxId + 1;

        // Crea el nuevo elemento <pelicula> y ponerle su atributo ID
        Element pelicula = doc.createElement("pelicula");
        pelicula.setAttribute("id", String.valueOf(nuevoId));

        // Crea sus elementos hijos y añadirles contenido
        Element eTitulo = doc.createElement("titulo");
        eTitulo.setTextContent(titulo);

        Element eDirector = doc.createElement("director");
        eDirector.setTextContent(director);

        // Usamos la etiqueta "anno_estreno" del XML
        Element eAno = doc.createElement("anno_estreno");
        eAno.setTextContent(String.valueOf(ano));

        Element eGenero = doc.createElement("genero");
        eGenero.setTextContent(genero);

        Element eRecaudacion = doc.createElement("recaudacion");
        eRecaudacion.setTextContent(String.valueOf(recaudacion));

        // Añade los hijos a <pelicula>
        pelicula.appendChild(eTitulo);
        pelicula.appendChild(eDirector);
        pelicula.appendChild(eAno);
        pelicula.appendChild(eGenero);
        pelicula.appendChild(eRecaudacion);

        // Añade la nueva <pelicula> al nodo <peliculas>
        nodoPeliculas.appendChild(pelicula);

        // Guarda los cambios del DOM al fichero
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }

    /* Muestra películas estrenadas antes de un año. */

    public static void mostrarPeliculasAntesDe(int ano) throws Exception {

        // Carga el documento DOM
        File xmlFile = new File(RUTA_XML);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        doc.getDocumentElement().normalize();

        // Consulta XPath: Selecciona películas donde <anno_estreno> sea menor al año
        String xpathQuery = "//pelicula[anno_estreno < " + ano + "]";
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile(xpathQuery);

        NodeList peliculas = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        // Recorremos los resultados
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element peli = (Element) peliculas.item(i);

            // Obtenemos los datos de cada película
            String titulo = peli.getElementsByTagName("titulo").item(0).getTextContent();
            String director = peli.getElementsByTagName("director").item(0).getTextContent();
            String anoEstreno = peli.getElementsByTagName("anno_estreno").item(0).getTextContent();

            System.out.printf("  -> Título: %s, Director: %s, Año: %s\n",
                    titulo, director, anoEstreno);
        }
    }

    /*
     * Muestra películas que superan cierta recaudación.
     */
    public static void mostrarPeliculasPorRecaudacion(double recaudacionMinima)
            throws Exception {

        // Carga el documento DOM
        File xmlFile = new File(RUTA_XML);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        doc.getDocumentElement().normalize();

        // Consulta XPath: Selecciona películas donde <recaudacion> sea mayor
        String xpathQuery = "//pelicula[recaudacion > " + recaudacionMinima + "]";
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile(xpathQuery);

        NodeList peliculas = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        // Recorremos los resultados
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element peli = (Element) peliculas.item(i);

            // Obtenemos los datos
            String titulo = peli.getElementsByTagName("titulo").item(0).getTextContent();
            String recaudacion = peli.getElementsByTagName("recaudacion").item(0).getTextContent();

            System.out.printf("  -> Título: %s, Recaudación: %s\n",
                    titulo, recaudacion);
        }
    }
}
