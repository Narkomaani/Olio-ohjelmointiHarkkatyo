package com.example.ht.movie;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ht.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TheaterManager {
    private final ArrayList<Theater> theaters;
    private static final TheaterManager theaterManager = new TheaterManager();

    TheaterManager(){
        theaters = new ArrayList<>();
        addTheaters();
    }
    public static TheaterManager getInstance(){
        return theaterManager;
    }
    private void addTheaters(){
        String Url = "https://www.finnkino.fi/xml/TheatreAreas/";
        Document doc = null;

        try {
            DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = dbuilder.parse(Url);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("##########done#########");
        }

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
        for(int i=0;i<nList.getLength();i++){
            Node node =nList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                String name = element.getElementsByTagName("Name").item(0).getTextContent();
                this.theaters.add(new Theater(id, name));
            }
        }
    }

    public String[] theaterarray(){

        String[] payload = new String[this.theaters.size()];

        for(int i =0; i<this.theaters.size(); i++){
            payload[i]=this.theaters.get(i).GetName();
        }

        return payload;
    }

}

