import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseAdapter;
import java.awt.*;
import java.awt.geom.Line2D;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.File;

import java.awt.image.BufferedImage;
import java.beans.*;



///**
//* Created by Lumbini on 11/7/2015.
//* modified by Billy and Jeff
// * */
//

public class DevGUI extends JPanel{

 /**
  * 
  */
 private static final long serialVersionUID = 2270760135813536905L;
 public static LinkedList<Map> maps = new LinkedList<>();
 private static LinkedList<Node> nodesOnCurrentMap = new LinkedList<>();
 private static LinkedList<Edge> currentStartEdges = new LinkedList<>();
 private String[] startRooms = new String[1000];
 private String buildingSelectedSTART;   //track which building is selected to start in.
 private String currentMapName;
 private SelectMap loadMap;
 static DevGUI window;
 private ImageIcon currentMapFile;
 private ImageIcon tempMapFile;
 private NodeType currentType;
 private Node currentNode;
 
 
// //error1 
 private Map selectedMap;

 String outputVar = "src/output.txt";
 String inputVar = "src/output.txt";

 boolean createNodes = false;
 boolean createSpecial = false;
 boolean createEdges = false;
 boolean importPushed = true;
 boolean updateMap = false;
 boolean createMapLink = false;
 int indexOfCurrentMap;
 private LinkedList<String> currentMapList;

 public boolean developerMode = true;

 private JFrame frame;       //Creates the main frame for the GUI
 private JPanel uiPanel;     //Panel to hold the interface buttons
 private JPanel mapPanel;    //Panel to hold the map

 //Labels on the GUI
 private JLabel buildingStart;

 //Combo Boxes on the GUI
 private JComboBox<String> startBuildingSEL;

 /**
  * Create the application.
  */
 public DevGUI(){
  initialize();
 }

 @SuppressWarnings("unchecked")
 public static void main(String[] args) {
  
  if(new File("MapList.ser").canRead()){
   System.out.println("maplist exists");
   maps.addAll((LinkedList<Map>) deserialize("MapList"));
  } else {
   /*
   for(int c = 1; c < 8; c++){
    if(new File("MapList"+c+".ser").canRead()) {
     maps.addAll((LinkedList<Map>) deserialize("MapList"+c));
    }
   }
   */
  }
  
  System.out.println(maps);



  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     DevGUI window = new DevGUI();
     window.frame.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 // saves Map object "m" in a file named "s"
 public void serialize(String s, LinkedList<Map> maplist){
  try {
   FileOutputStream fileOut = new FileOutputStream(s + ".ser");
   ObjectOutputStream out = new ObjectOutputStream(fileOut);
   out.writeObject(maplist);
   out.close();
   fileOut.close();
   System.out.println("Serialized data is saved in " + s + ".ser");
  } catch(IOException i){
   i.printStackTrace();
  }
 }

 // loads the map stored in file name "s"
 public static Object deserialize(String s){
  Object m = null;
  try {
   FileInputStream fileIn = new FileInputStream(s + ".ser");
   ObjectInputStream in = new ObjectInputStream(fileIn);
   m = in.readObject();
   in.close();
   fileIn.close();
  } catch(IOException i){
   i.printStackTrace();
  } catch(ClassNotFoundException c){
   System.out.println("Map class not found");
   c.printStackTrace();
  }
  return m;
 }

 /**
  * Initialize the contents of the frame.
  */

 private void initialize() {

  //Frame operations
  frame = new JFrame();
  frame.setBounds(100, 100, 900, 700);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
  frame.setTitle("Get There");
  frame.setResizable(false);
  frame.setVisible(true);

  //Panel Operations
  uiPanel = new JPanel();
  frame.getContentPane().add(uiPanel);
  uiPanel.setLayout(null);

  mapPanel = new JPanel();
  mapPanel.setBounds(5, 5, 750, 650);
  uiPanel.add(mapPanel);
  MouseEvents m1 = new MouseEvents();
  mapPanel.add(m1);

  //Creating Labels
  buildingStart = new JLabel("Select Map:");
  buildingStart.setBounds(762, 26, 132, 29);

  //Add Labels to the uiPanel
  uiPanel.add(buildingStart);

  //Construct Combo boxes to select start point

  
  JComboBox dropDown = new JComboBox<>(maps.toArray());
  
  dropDown.setBounds(762, 46, 132, 29);
  dropDown.setVisible(true);
//  dropDown.setSelectedIndex(0);
  dropDown.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e) {
    JComboBox cb1 = (JComboBox)e.getSource();
    selectedMap = (Map)cb1.getSelectedItem();
    System.out.println();
    System.out.println(selectedMap);
    System.out.println(selectedMap.getNodes());
    currentMapFile = selectedMap.getImage();
    nodesOnCurrentMap = selectedMap.getNodes();
    currentStartEdges = selectedMap.getEdges();
    
//    dropDown.removeAllItems();
//    for(int dd = 0; maps.size()<dd; dd++)
//    {
//          dropDown.addItem(maps.get(dd));
//    }
    uiPanel.repaint();
    frame.repaint();
    
       }
  });
  uiPanel.add(dropDown);
  


  
//  currentMapList = new LinkedList<String>();
//  startBuildingSEL = new JComboBox<String>();
//  startBuildingSEL.setBounds(762, 46, 132, 29);
//  startBuildingSEL.setEditable(false);
//  startBuildingSEL.setVisible(true);
//  startBuildingSEL.addActionListener(new ActionListener(){
//   public void actionPerformed(ActionEvent e) {
//    @SuppressWarnings("rawtypes")
//    JComboBox cb = (JComboBox)e.getSource();
//    buildingSelectedSTART = (String)cb.getSelectedItem();
//    for(indexOfCurrentMap = 0; indexOfCurrentMap < maps.size(); ++indexOfCurrentMap){
//     if(buildingSelectedSTART.equals(maps.get(indexOfCurrentMap).getMapName()))
//      break;
//    }
//    currentMapName = maps.get(indexOfCurrentMap).getMapName();
////    nodesOnCurrentMap = maps.get(indexOfCurrentMap).getNodes();
////    currentStartEdges = maps.get(indexOfCurrentMap).getEdges();
////    currentMapFile = maps.get(indexOfCurrentMap).getImage();
//    for(int i = 0; i < nodesOnCurrentMap.size(); ++i){
//     startRooms[i] = nodesOnCurrentMap.get(i).getName();
//    }
//    System.out.println("Updating maps");
//    uiPanel.repaint();
//    frame.repaint();
//   }
//  });
//
//  for (int i = 0; i < maps.size(); i++) {
//   if(maps.get(i).getMapName() != null){
//    startBuildingSEL.addItem(maps.get(i).getMapName());
//    currentMapList.add(maps.get(i).getMapName());
//   }
//  }
//
//  //Add Combo Boxes to UIPanel
//  uiPanel.add(startBuildingSEL);

  if(developerMode)
  {
   JButton newMap = new JButton("Load New Map");
   newMap.setBounds(762, 76, 132, 29);
   uiPanel.add(newMap);
   newMap.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
     loadMap = new SelectMap(window);
     loadMap.setVisible(true);
     loadMap.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
     updateMap = true;
    }
   });

   JButton btnCreateNodes = new JButton("Create Nodes");
   btnCreateNodes.setBounds(762, 136, 132, 29);
   uiPanel.add(btnCreateNodes);
   btnCreateNodes.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
     System.out.println("Create Nodes Pushed");
     createNodes = true;
     createSpecial = false;
     createEdges = false;
     createMapLink = false;

    }
   });

   JButton btnCreateSpecialNodes = new JButton("Create Special");
   btnCreateSpecialNodes.setBounds(762, 166, 132, 29);
   uiPanel.add(btnCreateSpecialNodes);
   btnCreateSpecialNodes.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e){
     System.out.println("Create Special Nodes Pushed.");
     createNodes = false;
     createSpecial = true;
     createEdges = false;
     createMapLink = false;

    }
   });
   

   //Construct button and add action listener
   JButton btnMakeNeighbors = new JButton("Make Neighbors");
   btnMakeNeighbors.setBounds(762, 196, 132, 29);
   uiPanel.add(btnMakeNeighbors);
   btnMakeNeighbors.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e){
     System.out.println("Make Neighbors Pushed");
     createNodes = false;
     createSpecial = false;
     createEdges = true;
     createMapLink = false;
    }
   });
   
  
 
   

   //Construct button and add action listener
   JButton btnExport = new JButton("Save Changes");
   btnExport.setBounds(762, 226, 132, 29);
   uiPanel.add(btnExport);
   btnExport.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e){
     System.out.println("Export Pushed");

     serialize("MapList", maps);
     if(updateMap){
      
      
      
      for (int i = 0; i < maps.size(); i++) {
       if(maps.get(i).getMapName() != null){
        if(!currentMapList.contains(maps.get(i).getMapName()))
        {
         dropDown.addItem(maps.get(i).getMapName());
         currentMapList.add(maps.get(i).getMapName());
        }
       }
      }
      updateMap = false;
     }
  
     uiPanel.repaint();
     uiPanel.revalidate();
     
    }
   });


   JButton btnDeleteMap = new JButton("Delete Map");
   btnDeleteMap.setBounds(762, 286, 132, 29);
   uiPanel.add(btnDeleteMap);
   btnDeleteMap.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e){
     System.out.println("Delete Map Pushed");
     int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this map?", "Confirm",
       JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

     if (response == JOptionPane.NO_OPTION) {
      System.out.println("No button clicked");

     } else if (response == JOptionPane.YES_OPTION) {
      System.out.println("Yes button clicked");
      
//      for(int i = 0; i < maps.size(); ++i){
//       if(currentMapName == maps.get(i).getMapName()){
//        maps.remove(i);
//       }
      
//      }
      System.out.println(maps);
      System.out.println(selectedMap);
      
      dropDown.removeItem(selectedMap);
      
      System.out.println(maps.remove(selectedMap));
      
      
      
      updateMap = true;
      
      
      
      
      
      serialize("MapList", maps);

     } else if (response == JOptionPane.CLOSED_OPTION) {
      System.out.println("JOptionPane closed");
     } 
    }
   });     
  }    

  uiPanel.setVisible(true);
  frame.setVisible(true);
  
  
 }

 public class MouseEvents extends JComponent implements MouseMotionListener{
  private static final long serialVersionUID = 1L;
  private static final int SquareWidth = 5;
  public String nodes;
  private String nodeName;
  int nodeIndex;

  MouseEvents() {
   setPreferredSize(new Dimension(760, 666));
   addMouseMotionListener(this);
   addMouseListener(new MouseAdapter(){
    public void mousePressed(MouseEvent evt) {
     int x = evt.getX();
     int y = evt.getY();
     nodeIndex = getNodeIndex(x, y);   
    }
    int staringEdgeIndex;
    int count = 0;

    public void mouseClicked(MouseEvent evt) {
     int x = evt.getX();
     int y = evt.getY();

     System.out.println("\nX: " + x + "\t Y: " + y);
     repaint();


     if(createNodes){
      if (nodeIndex < 0){ // not inside a square
       Node newNode = new Node(x, y);
       newNode.setMapName(currentMapName);
       nodesOnCurrentMap.add(newNode);
       
      }
     }
     if(createSpecial){
      if (nodeIndex < 0){ // not inside a square
       nodeName = JOptionPane.showInputDialog("Enter Node Name:");
       if(nodeName == null || (nodeName != null && ("".equals(nodeName)))){
        return;
       } else {
        String[] types = {"No Type", "Men's Bathroom", "Women's Bathroom", "Blue Tower", "Elevator", 
          "Stairs", "Food", "Emergency Exit", "Lecture Hall", "Office", "Door",
          "Room"};
        Object selectedValue = JOptionPane.showInputDialog(null,
          "Choose a Node Type", "Input",
          JOptionPane.INFORMATION_MESSAGE, null,
          types, types[0]);
        if(selectedValue != null){
         switch((String)selectedValue){
         case "No Type":
          nodesOnCurrentMap.add(new Node(x, y, nodeName, NodeType.NOTYPE));
          nodesOnCurrentMap.get(nodesOnCurrentMap.size() - 1).setMapName(currentMapName);
          break;
         case "Men's Bathroom":
          nodesOnCurrentMap.add(new Node(x, y, nodeName, NodeType.MBATHROOM));
          nodesOnCurrentMap.get(nodesOnCurrentMap.size() - 1).setMapName(currentMapName);
          break;
         case "Women's Bathroom":
          nodesOnCurrentMap.add(new Node(x, y, nodeName, NodeType.FBATHROOM));
          nodesOnCurrentMap.get(nodesOnCurrentMap.size() - 1).setMapName(currentMapName);
          break;
         case "Blue Tower":
          nodesOnCurrentMap.add(new Node(x, y, nodeName, NodeType.BLUETOWER));
          nodesOnCurrentMap.get(nodesOnCurrentMap.size() - 1).setMapName(currentMapName);
          break;
         case "Elevator":
          makeLink(x, y, nodeName, NodeType.ELEVATOR);
          break;
         case "Stairs":
          makeLink(x, y, nodeName, NodeType.STAIRS);
          break;
         case "Food":
          nodesOnCurrentMap.add(new Node(x, y, nodeName, NodeType.FOOD));
          nodesOnCurrentMap.get(nodesOnCurrentMap.size() - 1).setMapName(currentMapName);
          break;
         case "Emergency Exit":
          makeLink(x, y, nodeName, NodeType.EMERGEXIT);
          break;
         case "Lecture Hall":
          nodesOnCurrentMap.add(new Node(x, y, nodeName, NodeType.LECTUREHALL));
          nodesOnCurrentMap.get(nodesOnCurrentMap.size() - 1).setMapName(currentMapName);
          break;
         case "Office":
          nodesOnCurrentMap.add(new Node(x, y, nodeName, NodeType.OFFICE));
          nodesOnCurrentMap.get(nodesOnCurrentMap.size() - 1).setMapName(currentMapName);
          break;
         case "Door":
          makeLink(x, y, nodeName, NodeType.DOOR);
          break;
         case "Room":
          nodesOnCurrentMap.add(new Node(x, y, nodeName, NodeType.ROOM));
          nodesOnCurrentMap.get(nodesOnCurrentMap.size() - 1).setMapName(currentMapName);
          break;
         default:
          nodesOnCurrentMap.add(new Node(x, y, nodeName, NodeType.NOTYPE));
          nodesOnCurrentMap.get(nodesOnCurrentMap.size() - 1).setMapName(currentMapName);
         }
        }
       }
      }
     }
     if(createMapLink){
      if (nodeIndex < 0){ // not inside a square
       Node newNode = new Node(x, y, nodeName, currentType);
       newNode.setMapName(currentMapName);
       newNode.setName(newNode.getName());
       nodesOnCurrentMap.add(newNode);
       Edge newEdge = new Edge(currentNode, newNode, 0);
       currentStartEdges.add(newEdge);
       for(int k = 0; k < maps.size(); k++){
        for(int j = 0; j < maps.get(k).getNodes().size(); j++){
         if(maps.get(k).getNodes().get(j).equals(currentNode)){
          maps.get(k).getEdges().add(newEdge);
          break;
         }
        }
       }
       createMapLink = false;
      }
     }
     if(createEdges){

      if(count == 0 && nodeIndex >= 0){
       staringEdgeIndex = nodeIndex;
       System.out.println(nodeIndex);
       count++;
      } else if(count > 0 && nodeIndex >= 0){
       System.out.println(nodeIndex);
       currentStartEdges.add(new Edge(nodesOnCurrentMap.get(staringEdgeIndex), 
         nodesOnCurrentMap.get(nodeIndex),
         (int) calcDistance(nodesOnCurrentMap.get(staringEdgeIndex), nodesOnCurrentMap.get(nodeIndex), maps.get(indexOfCurrentMap).getScale())));
       count = 0;
      }
     }
     if (evt.getClickCount() >= 2 && (createNodes || createSpecial)) {

      LinkedList<Edge> tempList = new LinkedList<Edge>();
      for (int i = 0; i < currentStartEdges.size(); i++){
       if(currentStartEdges.get(i).getNode1().equals(nodesOnCurrentMap.get(nodeIndex))||
         currentStartEdges.get(i).getNode2().equals(nodesOnCurrentMap.get(nodeIndex)))
       {
        tempList.add(currentStartEdges.get(i));
       }
      }
      currentStartEdges.removeAll(tempList);
      nodesOnCurrentMap.remove(nodeIndex);
     }
     repaint();
    }
   });
   addMouseMotionListener(this);

  }
  
  public void makeLink(int x, int y, String nodeName, NodeType type){
   Object[] mapNames = maps.toArray();
   
   Object connectingMap = JOptionPane.showInputDialog(null, 
         "Choose a map to connect to",
         "Input",
         JOptionPane.INFORMATION_MESSAGE, null,
         mapNames, mapNames[0]);


  
  System.out.println("First map: " + selectedMap);
  System.out.println("Second map: " + connectingMap);
  
  int indexInListOfMaps = maps.indexOf(connectingMap);
  
  
  
  

//   linkNode.setMapName(currentMapName);
//   currentNode = linkNode;
//   nodesOnCurrentMap.add(linkNode);
//   createMapLink = true;
//   currentMapName = ((Map)connectingMap).getMapName();
//   nodesOnCurrentMap = ((Map)connectingMap).getNodes();
//   currentStartEdges = ((Map)connectingMap).getEdges();
//   currentMapFile = ((Map)connectingMap).getImage();
currentType = type;
String tempMapName = ((Map)connectingMap).getMapName(); 
tempMapFile = maps.get(indexInListOfMaps).getImage();
Node linkNode = new Node(x, y, nodeName, type);
linkNode.setMapName(currentMapName);
currentNode = linkNode;
nodesOnCurrentMap.add(linkNode);
createMapLink = true;
currentMapName = tempMapName;
nodesOnCurrentMap = maps.get(indexInListOfMaps).getNodes();
currentStartEdges = maps.get(indexInListOfMaps).getEdges();
currentMapFile = maps.get(indexInListOfMaps).getImage();




  repaint();
  revalidate();
  }
  @Override
  public void paintComponent(Graphics g) {
   super.paintComponent(g);

   if (currentMapFile != null) {
    g.drawImage(currentMapFile.getImage(), 0, 0, this);
   }
   else
  {
    g.drawString("Select a map or load a new map to begin", 300,300);
  }
   
  // if(selectedMap.getImage() != null)
  // g.drawImage(selectedMap.getImage(), 0, 0, this);
   
   if(createMapLink){
    if (tempMapFile != null) {
     g.drawImage(tempMapFile.getImage(), 0, 0, this);
    }
   }
 

   

   for (int i = 0; i < nodesOnCurrentMap.size(); i++){
    
    
    if(nodesOnCurrentMap.get(i).getType() == null)
    {
     System.out.println("The following node was null: " + nodesOnCurrentMap.get(i));
    nodesOnCurrentMap.get(i).setType(NodeType.NOTYPE);
    }
    
    
    switch ((NodeType)nodesOnCurrentMap.get(i).getType()){
     case NOTYPE:
      g.setColor(Color.BLACK);
      break;
     case MBATHROOM:
      g.setColor(Color.CYAN);
      break;
     case FBATHROOM:
      g.setColor(Color.PINK);
      break;
     case BLUETOWER:
      g.setColor(Color.BLUE);
      break;
     case ELEVATOR:
      g.setColor(Color.GREEN);
      break;
     case STAIRS:
      g.setColor(Color.RED);
      break;
     case FOOD:
      g.setColor(Color.YELLOW);
      break;
     case EMERGEXIT:
      g.setColor(Color.RED);
      break;
     case LECTUREHALL:
      g.setColor(Color.GRAY);
      break;
     case OFFICE:
      g.setColor(Color.LIGHT_GRAY);
      break;
     case DOOR:
      g.setColor(Color.ORANGE);
      break;
     case ROOM:
      g.setColor(Color.DARK_GRAY);
      break;  
     
     
     }
 
  // System.out.println(nodesOnCurrentMap.get(i));
    
    ((Graphics2D)g).fill(new Rectangle (nodesOnCurrentMap.get(i).getX()-SquareWidth/2, 
             nodesOnCurrentMap.get(i).getY()-SquareWidth/2,
             SquareWidth, SquareWidth));
   
   if(isPortal(nodesOnCurrentMap.get(i)))
     g.drawString(nodesOnCurrentMap.get(i).getName(), nodesOnCurrentMap.get(i).getX(),nodesOnCurrentMap.get(i).getY());
    
   g.setColor(Color.BLACK);
   }
  
  
   
   
   for (int i = 0; i < currentStartEdges.size(); i++){
    if(!(isPortal(currentStartEdges.get(i).getNode1())&&isPortal(currentStartEdges.get(i).getNode2())))
    {
      currentStartEdges.get(i).updateWeight((int)calcDistance(currentStartEdges.get(i).getNode1(), currentStartEdges.get(i).getNode2(), selectedMap.getScale()));
    }
    else
     g.setColor(Color.RED);
    
    
    
    
 //  if(!isPortal(currentStartEdges.get(i).getNode1())||!isPortal(currentStartEdges.get(i).getNode2()))
    ((Graphics2D)g).draw(new Line2D.Double(currentStartEdges.get(i).getNode1().getX(), 
              currentStartEdges.get(i).getNode1().getY(),
              currentStartEdges.get(i).getNode2().getX(),
              currentStartEdges.get(i).getNode2().getY() ));
   
   
    
      g.setColor(Color.BLACK);         
    System.out.println(currentStartEdges.get(i));
   }
  }
  public boolean isPortal(Node n)
  {
   switch ((NodeType)n.getType()){
    case ELEVATOR:
    case STAIRS:
    case DOOR:
    case EMERGEXIT:
     return true;
     
    
    }
    return false;
  }

  
  public double calcDistance(Node n1, Node n2, double scale)
  {
   return (Math.sqrt((n1.getX()-n2.getX())*(n1.getX()-n2.getX()) + (n1.getY()-n2.getY())*(n1.getY()-n2.getY())))/scale;
  }




  public void removeEdgesToNode(int nodeIndex)
  {
   for (int i = 0; i < currentStartEdges.size(); i++)
   {
    if(currentStartEdges.get(i).getNode1().equals(nodesOnCurrentMap.get(nodeIndex))||
      currentStartEdges.get(i).getNode2().equals(nodesOnCurrentMap.get(nodeIndex)))
    {
     currentStartEdges.remove(currentStartEdges.get(i));
    }
   }

  }


  public int getNodeIndex(int x, int y)
  {
   int thres = 10;
   for (int i = 0; i < nodesOnCurrentMap.size(); i++)
   {
    if((nodesOnCurrentMap.get(i).getX() > x-thres)&&(nodesOnCurrentMap.get(i).getX() < x+thres) 
      && (nodesOnCurrentMap.get(i).getY() > y-thres)&&(nodesOnCurrentMap.get(i).getY() < y+thres))
     return i;

   }
   return -1;
  }



  public void mouseMoved(MouseEvent evt) {
   repaint();
   revalidate();
   
   int x = evt.getX();
   int y = evt.getY();

   if (getNodeIndex(x, y) >= 0){
    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    String fullName = nodesOnCurrentMap.get(getNodeIndex(x, y)).getName();
    int a = fullName.indexOf(".") ;
    if(a != -1){
     fullName = fullName.substring(a+1, fullName.length()-1);
    }

    setToolTipText("Node: " + getNodeIndex(x, y) 
    + " Name: " + fullName);
    setVisible(true);
   }
   else{
    setCursor(Cursor.getDefaultCursor());
   }
  }

  public void mouseDragged(MouseEvent evt) {
   repaint();
   revalidate();
   
   int x = evt.getX();
   int y = evt.getY();

   if(nodeIndex >= 0) {
    nodesOnCurrentMap.get(nodeIndex).setX(x);
    nodesOnCurrentMap.get(nodeIndex).setY(y);
   }
  }
 }
 public Boolean getDeveloperMode(){
  return developerMode;
 }
 public void setDeveloperMode(Boolean modeSelect){
  this.developerMode = modeSelect;
 }

}