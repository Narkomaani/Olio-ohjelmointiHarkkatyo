package com.example.ht;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ht.movie.TheaterManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class HomePageFragment extends Fragment {
    private int positionTheatre;
    private TheaterManager tm;
    ArrayList<String> theaters = new ArrayList<>();
    ArrayList<String> theaterIds = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    ArrayList<String> shows = new ArrayList<>();

    ArrayList<String> events = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterEvents;
    ArrayList<String> eventIds = new ArrayList<>();

    int idPosition;
    int theaterIdInt;
    String eventTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        //spinner for theaters from the homepage

        Spinner theater_spinner = rootView.findViewById((R.id.theater_spinner));

        theaters = setTheaterlist();
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, theaters);
        theater_spinner.setAdapter(arrayAdapter);

        Spinner event_spinner = rootView.findViewById(R.id.event_spinner);
        event_spinner.setVisibility(View.GONE);

        events = setEventlist();
        arrayAdapterEvents = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, events);
        event_spinner.setAdapter(arrayAdapterEvents);

        Button searchButton = rootView.findViewById(R.id.searchButton);
        ListView listview = rootView.findViewById(R.id.movieList);

        theater_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (theater_spinner.getSelectedItemPosition() != 0) {
                    event_spinner.setVisibility(View.VISIBLE);
                } else {
                    event_spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                event_spinner.setVisibility(View.GONE);
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theater_spinner.getSelectedItemPosition() != 0) {
                    System.out.println(theater_spinner.getSelectedItemPosition());
                    idPosition = theater_spinner.getSelectedItemPosition();

                    String theaterId = theaterIds.get(idPosition);
                    theaterIdInt = Integer.parseInt(theaterId);


                } else {
                    theaterIdInt = 0;
                }
                if (event_spinner.getSelectedItemPosition() != 0) {
                    idPosition = event_spinner.getSelectedItemPosition();

                    eventTitle = events.get(idPosition);

                } else {
                    eventTitle = "";
                }

                shows = showShows(theaterIdInt, eventTitle);

                String showsText = "";
                for (int i = 0; i < shows.size(); i++) {
                    showsText = showsText + shows.get(i);
                }

                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, shows);

                listview.setAdapter(arrayAdapter2);

            }
        });

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

    public ArrayList setEventlist() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String url = "https://www.finnkino.fi/xml/Events/";
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("Event");

            events.add("Valitse elokuva");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println(element.getElementsByTagName("ID").item(0).getTextContent());
                    System.out.println(element.getElementsByTagName("Title").item(0).getTextContent());
                    events.add(element.getElementsByTagName("Title").item(0).getTextContent());

                    eventIds.add(element.getElementsByTagName("ID").item(0).getTextContent());
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
        return events;
    }

    public ArrayList showShows(int theaterID, String eventTitle) {
        if (theaterID != 0 && eventTitle.isEmpty()) {
            try {
                String pattern = "dd.MM.yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date today = Calendar.getInstance().getTime();
                String datetoday = simpleDateFormat.format(today);
                String urlShows = "https://www.finnkino.fi/xml/Schedule/?area=" + theaterID + "&dt=" + datetoday;
                DocumentBuilder builder2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc2 = builder2.parse(urlShows);
                doc2.getDocumentElement().normalize();

                    NodeList nList2 = doc2.getDocumentElement().getElementsByTagName("Show");

                for (int i = 0; i < nList2.getLength(); i++) {
                    Node node = nList2.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        shows.add("\nTitle: " + element.getElementsByTagName("Title").item(0).getTextContent() + "\nTime and date: " + element.getElementsByTagName("dttmShowStart").item(0).getTextContent() + "\n Theatre: " + element.getElementsByTagName("Theatre").item(0).getTextContent() + "\n");
                    }
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Done");
            }
        /*} else if (theaterID == 0 && eventTitle.isEmpty() == false) {
            try {
                String pattern = "dd.MM.yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date today = Calendar.getInstance().getTime();
                String datetoday = simpleDateFormat.format(today);

                for (String theaterid : theaterIds) {
                    String urlShows = "https://www.finnkino.fi/xml/Schedule/?area=" + theaterid + "&dt=" + datetoday;
                    DocumentBuilder builder2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    Document doc2 = builder2.parse(urlShows);
                    doc2.getDocumentElement().normalize();

                NodeList nList2 = doc2.getDocumentElement().getElementsByTagName("Show");

                    for (int i = 0; i < nList2.getLength(); i++) {
                        Node node = nList2.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;

                            if (element.getElementsByTagName("Title").item(0).getTextContent().equals(eventTitle)) {
                                shows.add("\nTitle: " + element.getElementsByTagName("Title").item(0).getTextContent() + "\nTime and date: " + element.getElementsByTagName("dttmShowStart").item(0).getTextContent() + "\n Theatre: " + element.getElementsByTagName("Theatre").item(0).getTextContent() + "\n");
                            }
                        }
                    }
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Done");
            }*/
        } else if (theaterID != 0 && eventTitle.isEmpty() == false) {
            try {
                String pattern = "dd.MM.yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date today = Calendar.getInstance().getTime();
                String datetoday = simpleDateFormat.format(today);
                String urlShows = "https://www.finnkino.fi/xml/Schedule/?area=" + theaterID + "&dt=" + datetoday;
                DocumentBuilder builder2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc2 = builder2.parse(urlShows);
                doc2.getDocumentElement().normalize();

                NodeList nList2 = doc2.getDocumentElement().getElementsByTagName("Show");

                for (int i = 0; i < nList2.getLength(); i++) {
                    Node node = nList2.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        if (element.getElementsByTagName("Title").item(0).getTextContent().equals(eventTitle)) {
                            shows.add("\nTitle: " + element.getElementsByTagName("Title").item(0).getTextContent() + "\nTime and date: " + element.getElementsByTagName("dttmShowStart").item(0).getTextContent() + "\n Theatre: " + element.getElementsByTagName("Theatre").item(0).getTextContent() + "\n");
                        }
                    }
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Done");
            }
        }
        return shows;
    }
}
