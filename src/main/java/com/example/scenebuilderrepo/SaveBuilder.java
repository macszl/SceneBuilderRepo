package com.example.scenebuilderrepo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class SaveBuilder
{
    ArrayList<Vector<MapTile>> mapTiles = Board.getAllMapTiles();
    String savenamePath;

    public SaveBuilder(String _savenamePath)
    {
        this.savenamePath = _savenamePath;
    }

    public void saveGameToXML()
    {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            //root element
            Element root = document.createElement("game");
            document.appendChild(root);

            //board information
            Element board = document.createElement("board");
            root.appendChild(board);

            for (int i = 0; i < mapTiles.size(); i++) {
                for (int j = 0; j < mapTiles.get(i).size(); j++) {
                    Element hex = document.createElement("hex");
                    board.appendChild(hex);

                    Element x = document.createElement("x");
                    x.appendChild(document.createTextNode(Integer.toString(i)));
                    hex.appendChild(x);
                    Element y = document.createElement("y");
                    y.appendChild(document.createTextNode(Integer.toString(j)));
                    hex.appendChild(y);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("save.xml"));

            transformer.transform(domSource, streamResult);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
