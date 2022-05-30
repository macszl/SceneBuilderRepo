package com.example.scenebuilderrepo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

class ObjStruct {
    public int atk;
    public int def;
    public int hpCurrent;
    public int hpMax;
    public int actionPointMax;
    public int actionPointCurr;
}

class HexStruct {
    public int x;
    public int y;
    public String imageFilename;
    public FactionEnum faction;
    public ObjStruct obj;
}

public class SaveReader {
    File xmlFile;
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document doc;

    public SaveReader() {
        try {
            xmlFile = new File("save.xml");
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<FactionEnum> getFactionList() {

        ArrayList<FactionEnum> factionPriorityList = new ArrayList<>();
        NodeList factionList = doc.getElementsByTagName("factionList");
        for (int i = 0; i < factionList.getLength(); i++) {
            Node nFactionList = factionList.item(i);
            if (nFactionList.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nFactionList;
                NodeList factionEnumList = elem.getElementsByTagName("factionEnum");
                for (int j = 0; j < factionEnumList.getLength(); j++) {
                    Node nFactionEnumList = factionEnumList.item(j);
                    String factionName = nFactionEnumList.getTextContent();
                    if (factionName.equals(FactionEnum.CRYSTALMEN.name())) {
                        factionPriorityList.add(FactionEnum.CRYSTALMEN);
                    } else if (factionName.equals(FactionEnum.SKYMEN.name())) {
                        factionPriorityList.add(FactionEnum.SKYMEN);
                    } else {
                        factionPriorityList.add(FactionEnum.FORESTMEN);
                    }
                    System.out.println(factionName);
                }
            }
        }

        return factionPriorityList;
    }

    public ArrayList<HexStruct> getHexList() {
        ArrayList<HexStruct> hexes = new ArrayList<>();
        NodeList hexList = doc.getElementsByTagName("hex");

        for (int j = 0; j < hexList.getLength(); j++) {
            Element hex = (Element) hexList.item(j);
            HexStruct hexagon = new HexStruct();
            ObjStruct object = new ObjStruct();
            hexagon.x = Integer.parseInt(hex.getElementsByTagName("x").item(0).getTextContent());
            hexagon.y = Integer.parseInt(hex.getElementsByTagName("y").item(0).getTextContent());
            String obj = hex.getElementsByTagName("obj").item(0).getTextContent();
            if (obj != null) {
                object.actionPointMax = Integer.parseInt(hex.getElementsByTagName("actionPointsMax").item(0).getTextContent());
                object.actionPointCurr = Integer.parseInt(hex.getElementsByTagName("actionPointsCurr").item(0).getTextContent());
                object.hpCurrent = Integer.parseInt(hex.getElementsByTagName("hpCurrent").item(0).getTextContent());
                object.hpMax = Integer.parseInt(hex.getElementsByTagName("hpMax").item(0).getTextContent());
                object.atk = Integer.parseInt(hex.getElementsByTagName("attack").item(0).getTextContent());
                object.def = Integer.parseInt(hex.getElementsByTagName("defense").item(0).getTextContent());
            } else {
                object = null;
            }
            if(!hex.getElementsByTagName("tileOwner").item(0).getTextContent().equals("null"))
                hexagon.faction = FactionEnum.valueOf(hex.getElementsByTagName("tileOwner").item(0).getTextContent());
            hexagon.imageFilename = hex.getElementsByTagName("imagefilename").item(0).getTextContent();
            hexagon.obj = object;

            hexes.add(hexagon);
        }
        return hexes;
    }
}
