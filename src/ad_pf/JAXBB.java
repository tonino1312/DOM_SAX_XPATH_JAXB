/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad_pf;

import Coches.Coches;
import Coches.Coches.Coche;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author carlo
 */
public class JAXBB {
        Coches misCoches;
   public int abrir_XML_JAXB(File fichero) { 
      try{
         //crea un objeto de tipo JAXBContext que se usa para realizar la transformacion del archivo XML en un objeto java
         //se pasa como parametro la clase Coches que es la clase raiz del archivo XML 
          JAXBContext contexto = JAXBContext.newInstance(Coches.class);
          //creamos un objeto de tipo Unmarshaller que se usara para realizar la transformacion del archivo XML en objetos java 
          Unmarshaller u = contexto.createUnmarshaller();
          misCoches=(Coches)u.unmarshal(fichero);
          return 0;
      }catch(Exception e){
          e.getMessage();
          return -1;
        }
    }
   public int guardar_XML_JAXB(File fichero){
        try{
            JAXBContext contexto = JAXBContext.newInstance(Coches.class);
            Marshaller m =contexto.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(misCoches, new FileOutputStream(fichero));
            return 0;
            
        }catch(Exception e){
            e.getMessage();
            return -1;
        }
    }
   
   
    public void modificarCoche(File fichero, int idCocheModificar, String nuevaMarca, String nuevoModelo, String nuevoColor, String nuevoHex,
            int nuevaPotencia, int nuevoPrecio, int nuevasPuertas, List<String> nuevosExtras) {
        //iteramos a traves de la lista de coches, es decir pasar por todos lo objetos coche de la lista misCoches
        for (Coche coche : misCoches.getCoche()) {
            //cogemos el id del coche y lo comparamos con el id que nosotros deseamos modificar 
            //es decir que cuando el coche se pase de id de coche no dejara hacerlo
            if (coche.getId() == idCocheModificar) {
                //establecemos el valor de marca que es una de las variable de la clase creada con el xsd
                coche.setMarca(nuevaMarca);
                //establecemos el valor de modelo que es una de las variable de la clase creada con el xsd
                coche.setModelo(nuevoModelo);
                //creamos un nuevo objeto color para el coche actual y establecemos sus valores de las nuevas entradas de color y color hexadecimal
                Coche.Color color = new Coche.Color();
                //se establece como el color del coche actual 
                color.setValue(nuevoColor);
                //setteamos los valores
                color.setHex(nuevoHex);
                coche.setColor(color);
                //setteamos las distintas cosas del coche actual
                coche.setPotencia(nuevaPotencia);
                coche.setPrecio(nuevoPrecio);
                coche.setPuertas(nuevasPuertas);

                System.out.println("Extras actuales: " + coche.getExtras().getExtra());

                // Eliminar elementos vacíos de la lista de nuevos extras
                nuevosExtras.removeIf(String::isEmpty);

                // Eliminar los extras actuales y agregar los nuevos extras
                coche.getExtras().getExtra().clear(); // Elimina los extras actuales
                if (!nuevosExtras.isEmpty()) {
                    coche.getExtras().getExtra().addAll(nuevosExtras); // Añade los nuevos extras
                }

                System.out.println("Nuevos extras: " + coche.getExtras().getExtra());

            }
        }
    }

}
