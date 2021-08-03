package main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class AplicarCambios {

    public void EscrivirXML(String ruta, String nameCiv, String nameHero, String nameDog, String lvl, String xp, String pntDesbCart) {
        // Instantiate the Factory
        System.out.println(ruta);
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(ruta));

            doc.getDocumentElement().normalize();

            // get <staff>
            NodeList list = doc.getElementsByTagName("savedhomecity");

            Node item = list.item(0);

            Element element = (Element) item;

            NodeList nombreN = element.getElementsByTagName("name");
            NodeList nombreHeroeN = element.getElementsByTagName("heroname");
            NodeList nombrePerroN = element.getElementsByTagName("herodogname");
            NodeList nivelN = element.getElementsByTagName("level");
            NodeList xpN = element.getElementsByTagName("xp");
            NodeList puntDesbCartasN = element.getElementsByTagName("skillpoints");
            NodeList shipname = element.getElementsByTagName("shipname");
            NodeList hcid = element.getElementsByTagName("hcid");

            nombreN.item(0).setTextContent(nameCiv);
            nombreHeroeN.item(0).setTextContent(nameHero);
            nombrePerroN.item(0).setTextContent(nameDog);
            nivelN.item(0).setTextContent(lvl);
            xpN.item(0).setTextContent(xp);
            puntDesbCartasN.item(0).setTextContent(pntDesbCart); 
            
            shipname.item(0).setTextContent("");
            hcid.item(0).setTextContent("");
            
            System.out.println("Modificando...");

            try {

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                //DOMSource source = new DOMSource(doc);
                Source source = new DOMSource(doc);

                Result result = new StreamResult(ruta);

                transformer.transform(source, result);

                System.out.println("Modificacion exitosa!");
                
                JOptionPane.showMessageDialog(null, "Los cambios se han aplicado correctamente.");

            } catch (TransformerException ex) {
                Logger.getLogger(AplicarCambios.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "No se ha podido modificar el archivo. \n" + ex);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            JOptionPane.showMessageDialog(null, "Algo salio mal!, \n" + e);
        }
    }
}
