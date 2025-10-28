package ficheros.EJ_REPASO;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EjerciciosRepasoXML {

 
    private static final String RUTA_A_TU_XML = "PRÁCTICAS/src/main/java/ADA/UD1/EJ_REPASO/concesionario.xml";

    /**
     * Método principal (main) para ejecutar todos los ejercicios.
     */
    public static void main(String[] args) {
        
        // Primero, comprobamos si el fichero existe antes de hacer nada
        File xmlFile = new File(RUTA_A_TU_XML);
        if (!xmlFile.exists()) {
            System.err.println("Error: El fichero XML no se encuentra en la ruta:");
            System.err.println(RUTA_A_TU_XML);
            System.err.println("Por favor, crea el fichero 'concesionario.xml' y ajusta la ruta en el código.");
            return; // Salimos del programa si no existe el fichero
        }

        try {
            System.out.println(" Fichero XML cargado correctamente. ");

            // 1. (Ex 2) Modificamos los kilómetros de un coche
            System.out.println("\n Ejercicio 2: Modificar Kilómetros ");
            System.out.println("Modificando KM del coche 4567DEF a 95000...");
            // Llamamos al método para modificar los KM
            modificarKilometros(RUTA_A_TU_XML, "4567DEF", 95000);

            // 2. (Ex 3) Realizamos varias consultas XPath
            System.out.println("\n Ejercicio 3a: Mostrar todos los coches ");
            mostrarTodosCoches(RUTA_A_TU_XML);
            
            System.out.println("\n Ejercicio 3b: Coches fabricados después de 2010 ");
            mostrarCochesDespuesDe(RUTA_A_TU_XML, 2010);
            
            System.out.println("\n Ejercicio 3c: Coches con estado 4 o superior ");
            mostrarCochesPorEstado(RUTA_A_TU_XML, 4);
            
            
            System.out.println("\n Ejercicio 3d: Buscar coche 1234ABC ");
            mostrarCochePorMatricula(RUTA_A_TU_XML, "1234ABC");
            
            System.out.println("\n Ejercicio 3e: Coches con menos de 10000 km ");
            mostrarCochesMenosDe(RUTA_A_TU_XML, 10000);

        } catch (Exception e) {
            // Capturamos cualquier error (XML mal formado, fichero no encontrado, etc.)
            e.printStackTrace();
        }
    }


    /**
     * (Ejercicio 2) Modifica el número de kilómetros de un coche específico,
     * identificado por su matrícula.
     *
     * @param xmlFilePath Ruta del fichero XML.
     * @param matricula Matrícula del coche a buscar.
     * @param nuevosKm Nuevo valor de kilómetros.
     */
    public static void modificarKilometros(String xmlFilePath, String matricula, int nuevosKm) 
            throws ParserConfigurationException, SAXException, IOException, 
                   XPathExpressionException, TransformerException {
        
        // 1. Cargar el XML existente en un árbol DOM
        Document doc = cargarDOM(xmlFilePath);

        // 2. Preparar la consulta XPath
        // "Buscar el nodo <kilometros> que esté dentro de un <coche>
        // cuyo atributo 'matricula' sea el que buscamos"
        String xpathQuery = "//coche[@matricula='" + matricula + "']/kilometros";

        // 3. Obtener el motor de XPath
        XPath xpath = XPathFactory.newInstance().newXPath();
        // Compilar la expresión
        XPathExpression expr = xpath.compile(xpathQuery);
        
        // 4. Evaluar la consulta XPath sobre el documento cargado
        // Buscamos un NODO específico (no un conjunto)
        Node nodoKm = (Node) expr.evaluate(doc, XPathConstants.NODE);

        // 5. Modificar el nodo
        if (nodoKm != null) {
            // setTextContent borra el contenido actual y pone el nuevo.
            // Convertimos el int a String para guardarlo.
            nodoKm.setTextContent(String.valueOf(nuevosKm));
            
            // 6. Guardar los cambios en el fichero
            guardarDOM(doc, xmlFilePath);
            System.out.println("Matrícula " + matricula + " actualizada. Nuevos KM: " + nuevosKm);
        } else {
            System.out.println("No se encontró ningún coche con la matrícula " + matricula);
        }
    }

    /**
     * (Ejercicio 3a) Muestra todos los coches del concesionario.
     * @param xmlFilePath Ruta del fichero XML.
     */
    public static void mostrarTodosCoches(String xmlFilePath) throws Exception {
        // Consulta XPath: "Selecciona todos los elementos <coche>"
        consultarYMostrarCoches(xmlFilePath, "//coche");
    }
    
    /**
     * (Ejercicio 3b) Muestra coches fabricados después de un año.
     * @param xmlFilePath Ruta del fichero XML.
     * @param ano Año límite.
     */
    public static void mostrarCochesDespuesDe(String xmlFilePath, int ano) throws Exception {
        // Consulta XPath: "Selecciona <coche> donde <ano_fabricacion> sea mayor que [ano]"
        String query = "//coche[ano_fabricacion > " + ano + "]";
        consultarYMostrarCoches(xmlFilePath, query);
    }
    
    /**
     * (Ejercicio 3c) Muestra coches con un estado mínimo.
     * @param xmlFilePath Ruta del fichero XML.
     * @param estadoMinimo Valor de estado (1-5).
     */
    public static void mostrarCochesPorEstado(String xmlFilePath, int estadoMinimo) throws Exception {
        // Consulta XPath: "Selecciona <coche> donde <estado> sea mayor o igual que [estadoMinimo]"
        String query = "//coche[estado >= " + estadoMinimo + "]";
        consultarYMostrarCoches(xmlFilePath, query);
    }
    
    /**
     * (Ejercicio 3d) Muestra información de un coche por su matrícula.
     * @param xmlFilePath Ruta del fichero XML.
     * @param matricula Matrícula a buscar.
     */
    public static void mostrarCochePorMatricula(String xmlFilePath, String matricula) throws Exception {
        // Consulta XPath: "Selecciona <coche> donde el atributo 'matricula' sea [matricula]"
        String query = "//coche[@matricula='" + matricula + "']";
        consultarYMostrarCoches(xmlFilePath, query);
    }
    
    /**
     * (Ejercicio 3e) Muestra coches con menos de X kilómetros.
     * @param xmlFilePath Ruta del fichero XML.
     * @param kmMax Kilometraje máximo.
     */
    public static void mostrarCochesMenosDe(String xmlFilePath, int kmMax) throws Exception {
        // Consulta XPath: "Selecciona <coche> donde <kilometros> sea menor que [kmMax]"
        String query = "//coche[kilometros < " + kmMax + "]";
        consultarYMostrarCoches(xmlFilePath, query);
    }


    // --- MÉTODOS AUXILIARES (Helpers) ---

    /**
     * (Helper) Carga un fichero XML y lo convierte en un árbol DOM (Document).
     * @param filename Nombre del fichero a cargar.
     * @return El objeto Document (DOM).
     */
    private static Document cargarDOM(String filename) 
            throws ParserConfigurationException, SAXException, IOException {
        
        // 1. Crear la "fábrica" de constructores de documentos
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // 2. Crear el "constructor"
        DocumentBuilder db = dbf.newDocumentBuilder();
        // 3. "Parsear" (analizar y cargar) el fichero XML y devolver el árbol DOM
        return db.parse(new File(filename));
    }

    /**
     * (Helper) Guarda un árbol DOM (Document) en un fichero XML en disco.
     * @param doc El árbol DOM a guardar.
     * @param filename El nombre del fichero destino.
     */
    private static void guardarDOM(Document doc, String filename) 
            throws TransformerException {
        
        // 1. Creamos la fábrica de "Transformadores" (para pasar de DOM a XML)
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        
        // 2. Opciones de salida (para que el XML se vea bonito e indentado)
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        // 3. Definimos el origen (el DOM que hemos modificado)
        DOMSource source = new DOMSource(doc);
        
        // 4. Definimos el destino (el fichero físico en disco)
        StreamResult result = new StreamResult(new File(filename));

        // 5. Realizamos la transformación (guardado)
        transformer.transform(source, result);
    }

    /**
     * (Helper) Ejecuta una consulta XPath sobre el DOM y muestra los coches
     * (nodos <coche>) que encuentra.
     *
     * @param xmlFilePath La ruta al fichero XML.
     * @param xpathQuery La consulta XPath a ejecutar.
     */
    private static void consultarYMostrarCoches(String xmlFilePath, String xpathQuery) throws Exception {
        // 1. Cargamos el DOM desde el fichero
        Document doc = cargarDOM(xmlFilePath);

        // 2. Preparamos el motor de XPath
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile(xpathQuery);

        // 3. Ejecutamos la consulta. Esperamos un conjunto de nodos (NODESET)
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodos = (NodeList) result; // Lo convertimos a una lista de nodos

        // 4. Recorremos los resultados
        if (nodos.getLength() == 0) {
            System.out.println("\tNo se encontraron coches para esta consulta.");
        } else {
            // Iteramos sobre cada nodo <coche> encontrado
            for (int i = 0; i < nodos.getLength(); i++) {
                // Cada item es un nodo <coche>
                imprimirInfoCoche(nodos.item(i));
            }
        }
    }

    /**
     * (Helper) Recibe un nodo <coche> (de tipo Node) y muestra su información
     * por consola.
     *
     * @param nodoCoche El nodo (debe ser un <coche>).
     */
    private static void imprimirInfoCoche(Node nodoCoche) {
        // Verificamos que el nodo sea de tipo Elemento (una etiqueta, no texto)
        if (nodoCoche.getNodeType() == Node.ELEMENT_NODE) {
            // Convertimos el Nodo genérico a un Elemento para poder usar
            // métodos específicos como getAttribute o getElementsByTagName
            Element coche = (Element) nodoCoche;

            // getAttribute se usa para atributos (matricula)
            String matricula = coche.getAttribute("matricula");
            
            // getElementsByTagName se usa para hijos (marca, modelo...)
            // .item(0) coge el primer (y único) hijo <marca>
            // .getTextContent() coge el texto de dentro
            String marca = coche.getElementsByTagName("marca").item(0).getTextContent();
            String modelo = coche.getElementsByTagName("modelo").item(0).getTextContent();
            String km = coche.getElementsByTagName("kilometros").item(0).getTextContent();
            String estado = coche.getElementsByTagName("estado").item(0).getTextContent();
            String ano = coche.getElementsByTagName("ano_fabricacion").item(0).getTextContent();

            // Imprimimos la información con formato
            System.out.printf("\t-> Matrícula: %s | %s %s (%s) | %s km | Estado: %s/5\n",
                    matricula, marca, modelo, ano, km, estado);
        }
    }
}

//1. Realiza un método al que se le pasará por parametro el directorio donde se realizará la busqueda del numero entero mas grande dentro del mismo

//si el directorio no existe dbeera dar un mensaje de error y se pedira de nuevo un directorio para realizar la busqueda

//en el caso de que exista el director, se buscara en el mismo y en todos los directores internos que puedan exitir contenidos los archivos con la extension .dat el numero entero más grande

// El metodo devolvera un string que contenga el numero mas alto encontrado y el nombre del documento donde se ha encontrado

//Nota1: en caso de empate, nos deberemos quedar con el primer archivo encontrado

//Nota2: Se supone que todos los archivos.dat siguen exactamente en el mismo formato: cada entero esta separado de un espacio hasta el final del documento

//Nota3: se debe proporcionar archivos y codigo de prueba del metodo

//Dado el archivo "coleccion_peliculas" adjunto: "PRÁCTICAS/src/main/java/ADA/UD1/EX/coleccion_peliculas.xml" Crea un metodo que permite aumentar el presupuesto de todas las peliculas de uin director de un x%. Tanto el director como el porcentaje seran parametros de entrada

//Crea un metodo que permita añadir uina nueva pelicula pasando por parametro las caracteristicas de la misma

// Crea una serie de metodos que permita al usuario consultar la siguiente informacion sobre la empresa usando XPath y DOM:

//a. Muestra el titulo , el dirtector y el año de estreno de las peliculas estrenadas antes del año pasado por parametro
//b. Muestra el titulo y la recaudacion de las peliculas cuya recaudacion supere un valor pasado por parametro
