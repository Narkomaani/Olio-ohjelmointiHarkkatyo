package com.example.ht.movie;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.ht.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class HomePageFragment extends Fragment {
    private int positionTheatre;
    private TheaterManager tm;
    ArrayList<String> theaters = new ArrayList<>();
    ArrayList<String> theaterIds = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        //spinner for theaters from the homepage

        Spinner theater_spinner = rootView.findViewById((R.id.theater_spinner));

        theaters = setTheaterlist();

        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, theaters);
        theater_spinner.setAdapter(arrayAdapter);

        //Clear problem here
        /*
       tm = TheaterManager.getInstance();

        theater_spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tm.theaterarray()));
        theater_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionTheatre = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        return rootView;
    }

    public ArrayList setTheaterlist() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String url = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    //System.out.println(element.getElementsByTagName("ID").item(0).getTextContent());
                    //System.out.println(element.getElementsByTagName("Name").item(0).getTextContent());
                    theaters.add(element.getElementsByTagName("Name").item(0).getTextContent());

                    theaterIds.add(element.getElementsByTagName("ID").item(0).getTextContent());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Done");
        }
        return theaters;
    }
}
