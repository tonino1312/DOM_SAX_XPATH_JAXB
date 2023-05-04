/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad_pf;

import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *
 * @author carlo
 */
public class DOM {

    Document doc;
    String ruta = "";
    

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Document abrir_XML_DOM(File fichero) {
        // se inicializa la variable doc que es global a null
        // se utilizara para almacenar el contenido del archivo XML que se abre
        doc = null;
        try {
            // creamos una nueva instancia de documentBuilderFactory que es una clase de objetos documentBuilder que analizan el archivo XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // configura la instancia de Document para ignorar los comentarios en el archivo XML
            factory.setIgnoringComments(true);
            // configura DocumentBuilderFactory para ignorar espacios en blanco en el archivo XML
            factory.isIgnoringElementContentWhitespace();
            // creamos un objeto DocumentBuilder utilizando la instancia de DocumentBuilderFactory
            DocumentBuilder builder = factory.newDocumentBuilder();
            // el XML se analiza y se almacena en el objeto doc 
            doc = builder.parse(fichero);

            // se devuelve el objeto doc para indicar que no se ha producido ningún error
            return doc;
        } catch (Exception e) {
            // si se produce un error, se retorna null
            return null;
        }
    }

   
   public void agregarCoche(File fichero, String idNuevo, String marcaNuevo, String modeloNuevo, String colorHexNuevo, String colorNuevo, String potenciaNuevo, String precioNuevo, String puertasNuevo, List<String> extrasNuevo) {
        try {

            doc = abrir_XML_DOM(fichero);

            // definimos la raíz del documento y el nodo que contendrá los elementos
            Node raiz = doc.getFirstChild();
            Element coche = doc.createElement("coche");
            raiz.appendChild(coche);

            // definimos un atributo para el nodo
            Attr attr = doc.createAttribute("id");
            attr.setValue(idNuevo);
            coche.setAttributeNode(attr);

            // definimos nodos hijos
            Element marca = doc.createElement("marca");
            marca.setTextContent(marcaNuevo);
            //marca.appendChild(doc.createTextNode(marcaNuevo));
            coche.appendChild(marca);

            Element modelo = doc.createElement("modelo");
            modelo.appendChild(doc.createTextNode(modeloNuevo));
            coche.appendChild(modelo);

            Element color = doc.createElement("color");
            Attr colorAttr = doc.createAttribute("hex");
            colorAttr.setValue(colorHexNuevo); // valor por defecto si no se proporciona el atributo hex
            color.setAttributeNode(colorAttr);
            color.appendChild(doc.createTextNode(colorNuevo));
            coche.appendChild(color);

            Element potencia = doc.createElement("potencia");
            potencia.appendChild(doc.createTextNode(potenciaNuevo));
            coche.appendChild(potencia);

            Element precio = doc.createElement("precio");
            precio.appendChild(doc.createTextNode(precioNuevo));
            coche.appendChild(precio);

            Element puertas = doc.createElement("puertas");
            puertas.appendChild(doc.createTextNode(puertasNuevo));
            coche.appendChild(puertas);

            // creamos el elemento extras y sus elementos hijos extra
            Element extras = doc.createElement("extras");
            for (String extra : extrasNuevo) {
                Element extraElement = doc.createElement("extra");
                extraElement.appendChild(doc.createTextNode(extra));
                extras.appendChild(extraElement);
            }
            coche.appendChild(extras);

            raiz.appendChild(coche);
            // Mostrar el contenido actual del archivo XML
        } catch (DOMException e) {
            e.printStackTrace();
        }
    }
     //pasamos como parametro el objeto File fichero donde se va a guardar en formato XML
        public void guardar_XML_DOM(File fichero){
        try {
            //creamo una instancia de TransformerFactory, el cual se utiliza para transformar un documento XML en una representacion a otra
           TransformerFactory transformerFactory = TransformerFactory.newInstance();
           //creamos objeto transformer que se utiliza para transformar XML de una representacion a otra
           Transformer transformer = transformerFactory.newTransformer();
           //esto nos da una estructura dentro de lo que guardamos el XML 
           transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
           transformer.setOutputProperty(OutputKeys.INDENT, "yes");
           //creamos el objeto DOMSource a partir de un objeto document , el cual representa el documento que se va a guardar
           DOMSource source = new DOMSource(doc);
           //creamos un objeto steamResult a partir del objeto file, que representa el archivo XML en el que se va a guardar el documento.
           StreamResult result = new StreamResult(fichero);
           //ejecutamos el metodo transform, el cual transforma el documento XML representado por el objeto DOMSource y lo guarda en el archivo represntado por el objeto StreamResult
           transformer.transform(source, result);
           //capturamos excepciones 
    } catch (TransformerException e) {
        System.err.println("Error al transformar el documento: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
        
           //metodo para consultas CONSULTA: /coches/coche/marca[.='Seat']
         public NodeList buscarNodosXPath(String expresionXPath) {
        try {
            // Se crea un objeto XPathFactory para obtener un objeto XPath
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
             

            // Se compila la expresión XPath
            XPathExpression expr = xpath.compile(expresionXPath);

            // Se evalúa la expresión XPath en el documento XML
            NodeList nodos = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            
            return nodos;
        } catch (XPathExpressionException ex) {
            System.out.println("Error al buscar nodos con XPath: " + ex.getMessage());
            return null;
        }
    }  
        

}
