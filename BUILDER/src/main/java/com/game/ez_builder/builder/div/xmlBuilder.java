package com.game.ez_builder.builder.div;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.jbox2d.common.Vec2;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Kristoffer on 18.06.2014.
 */
public class xmlBuilder {
    Document doc;
    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Element rootElement;
    Element Zone;
    public xmlBuilder(int level) {

        try {

             docFactory = DocumentBuilderFactory.newInstance();
             docBuilder = docFactory.newDocumentBuilder();

            // root elements
            doc = docBuilder.newDocument();
             rootElement = doc.createElement("Level");
            doc.appendChild(rootElement);



        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }

    }
    public void writeNewZone(int zone){
        // Zone elements
        Zone = doc.createElement("Zone");
        rootElement.appendChild(Zone);

        // set attribute to Zone element
        Attr attr = doc.createAttribute("id");
        attr.setValue(""+zone);
        Zone.setAttributeNode(attr);

    }

    public void writeNewStatic(int heigth, int width, Vec2 position, int type, float rotation ,float radius, int texture){
        Element objectType = null;

        if(type == 1) {
            objectType = doc.createElement("BOX");
            Zone.appendChild(objectType);
        }else if(type == 2){

            objectType= doc.createElement("Triangle");
            Zone.appendChild(objectType);
        }else if(type == 3){

        }

        Attr attr = doc.createAttribute("heigth");
        attr.setValue("" + heigth);
        objectType.setAttributeNode(attr);


        attr = doc.createAttribute("width");
        attr.setValue(""+width);
        objectType.setAttributeNode(attr);

       attr = doc.createAttribute("posX");
        attr.setValue(""+position.x);
        objectType.setAttributeNode(attr);

        attr = doc.createAttribute("posY");
         attr.setValue(""+position.y);
        objectType.setAttributeNode(attr);

        attr = doc.createAttribute("rotation");
        attr.setValue(""+rotation);
        objectType.setAttributeNode(attr);
        if(type == 3) {
            attr = doc.createAttribute("raidius");
            attr.setValue("" + radius);
            objectType.setAttributeNode(attr);
        }
       attr =  doc.createAttribute("texture");
        attr.setValue(""+texture);
        objectType.setAttributeNode(attr);



    }
    public void printXML(String filename){
        File mediaStorage = new File(Environment.getExternalStorageDirectory()
                + "/EZLEVEL/");
        if (checkStateOfSDCard(BuilderConstants.getBuilderContext())) {
            if (!mediaStorage.exists()) {
                mediaStorage.mkdirs();
               }
            try {


                File outputPath = new File(mediaStorage, filename);
                StreamResult result = new StreamResult(outputPath);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);

                //DEBUG
                StreamResult res =  new StreamResult(System.out);
                transformer.transform(source, res);

                transformer.transform(source, result);

                Log.d("FileMADE", "Done");
            }catch (TransformerException tfe) {
            tfe.printStackTrace();
            }
        }

    }
    public static Boolean checkStateOfSDCard(Context mContext) {
        String auxSDCardStatus = Environment.getExternalStorageState();

        if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED))
            return true;
        else if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            Toast.makeText(
                    mContext,
                    "Warning, the SDCard it's only in read mode.\nthis does not result in malfunction"
                            + " of the read aplication", Toast.LENGTH_LONG
            )
                    .show();
            return true;
        } else if (auxSDCardStatus.equals(Environment.MEDIA_NOFS)) {
            Toast.makeText(
                    mContext,
                    "Error, the SDCard can be used, it has not a corret format or "
                            + "is not formated.", Toast.LENGTH_LONG)
                    .show();
            return false;
        } else if (auxSDCardStatus.equals(Environment.MEDIA_REMOVED)) {
            Toast.makeText(
                    mContext,
                    "Error, the SDCard is not found, to use the reader you need "
                            + "insert a SDCard on the device.",
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (auxSDCardStatus.equals(Environment.MEDIA_SHARED)) {
            Toast.makeText(
                    mContext,
                    "Error, the SDCard is not mounted beacuse is using "
                            + "connected by USB. Plug out and try again.",
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTABLE)) {
            Toast.makeText(
                    mContext,
                    "Error, the SDCard cant be mounted.\nThe may be happend when the SDCard is corrupted "
                            + "or crashed.", Toast.LENGTH_LONG).show();
            return false;
        } else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTED)) {
            Toast.makeText(
                    mContext,
                    "Error, the SDCArd is on the device but is not mounted."
                            + "Mount it before use the app.",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
