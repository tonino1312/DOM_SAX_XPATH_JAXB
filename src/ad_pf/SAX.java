/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad_pf;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author carlo
 */
public class SAX {
     private SAXParser parser;
    private ManejadorSAX sh;
    private File ficheroXML;
     

    // Utilizamos la API de SAX para abrir un archivo XML
    public int abrir_XML_SAX(File fichero) {
    try{
        SAXParserFactory factory= SAXParserFactory.newInstance();
        parser= factory.newSAXParser();
        sh=new ManejadorSAX();
        ficheroXML=fichero;
        return 0;  
    }
    catch(Exception e){
        return -1;
    }
 }

    
    // DefaultHandler es una clase abstracta proporcionada por la API de SAX que define los metodos que se llamaran durante el analisis del XML
    class ManejadorSAX extends DefaultHandler {
   
     
            // definimos la variable de tipo StringBuilder donde se almacenarán los resultados del análisis al XML
            StringBuilder cadena_resultado = new StringBuilder();

            // llamamos al método characters que es el encargado de encontrar los caracteres de un elemento del documento XML
            // el método convierte los caracteres a un String y los agrega al final del StringBuilder cadena_resultado, después de eliminar los espacios en blanco al inicio y al final de cada cadena
     @Override
     public void characters(char[] ch, int start, int length) throws SAXException {
        String valor = new String(ch, start, length).trim();
        if (!valor.isEmpty()) {
            cadena_resultado.append(valor).append("\n");
        }
      
    }

    // se llamará a este método cuando se termine de procesar un elemento del documento XML
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("coche")) {
            cadena_resultado.append("--------------------------------------").append("\n");
        }
    }
        

        //este metodo se ejecutara cuando encuentre un elemento de apertura en el documento XML
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
               if (qName.equals("coche")) {
            String id = attributes.getValue("id");
            cadena_resultado.append("Coche ID: ").append(id).append("\n");
        } else if (qName.equals("marca")) {
            cadena_resultado.append("Marca: ");
        } else if (qName.equals("modelo")) {
            cadena_resultado.append("Modelo: ");
        }else if(qName.equals("color")){
          String hex = attributes.getValue("hex");
          cadena_resultado.append("Color hex: ").append(hex).append("\n");
          cadena_resultado.append("Color: ");
        }else if(qName.equals("potencia")){
            cadena_resultado.append("Potencia: ");  
        }else if(qName.equals("precio")){
            cadena_resultado.append("Precio: "); 
        }else if(qName.equals("puertas")){
            cadena_resultado.append("Puertas: "); 
        }else if(qName.equals("extras")){
             cadena_resultado.append("Extras: "+"\n");
        }else if(qName.equals("extra")){
            cadena_resultado.append("   Extra: ");
    }
    }
    }
        //este metodo se encarga de ejecutar el analisis del documento XML utilizando el analizar SAX y devuelve los resultados en forma de cadena
       public String recorrerSAX() {
        try {
            sh.cadena_resultado.setLength(0); // limpiar el StringBuilder antes de procesar el nuevo archivo
            parser.parse(ficheroXML, sh);
            return sh.cadena_resultado.toString();
        } catch (SAXException | IOException ex) {
            ex.printStackTrace();
            return "Error al parsear con SAX";
        }
    }

}
