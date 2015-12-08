import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.ComboPopup;

import javax.swing.text.StyleConstants;

///**
//* Created by Lumbini on 11/7/2015.
// * */
//

public class EndUserGUI extends JPanel implements ActionListener{

	private static final long serialVersionUID = 2270760135813536905L;
	private LinkedList<Map> maps = new LinkedList<Map>();
	private LinkedList<Node> currentStartNodes = new LinkedList<Node>();
	//private LinkedList<Edge> currentStartEdges = new LinkedList<Edge>();
	private LinkedList<Node> currentEndNodes = new LinkedList<Node>();
	private LinkedList<Map> mapsForPaths = new LinkedList<Map>();
	private LinkedList<Node> nodesInMap = new LinkedList<Node>();

	//private static LinkedList<Edge> currentEndEdges = new LinkedList<Edge>();
	private String[] startRooms;
	private String[] endRooms;
	//	private String buildingSelectedSTART;   //track which building is selected to start in.
	//	private String buildingSelectedEND;
	//private String currentMapName;
	private static ImageIcon currentMapFile;

	private boolean startClicked = false;
	private boolean endClicked = false;
	//private boolean startHoverFlag = false;
	//private boolean endHoverFlag = false;


	private JTextArea directions;

	private JFrame frame;		//Creates the main frame for the GUI
	private JPanel uiPanel;		//Panel to hold the interface buttons
	private ImagePanel mapPanel;	//Panel to hold the map
	//private Image mapImage;		
	private ImageZoom zoom;
	//Represents the map to be chosen
	//private Image pathImage;	//Image that draws the path on the map

	//Labels on the GUI
	private JLabel startPoint;
	private JLabel buildingStart;
	private JLabel roomStart;
	private JLabel endPoint;
	private JLabel buildingEnd;
	private JLabel roomEnd;
	
	private JLabel tutView;
	//private JLabel floorStart;

	//Combo Boxes on the GUI
	private JComboBox<String> startBuildingSEL;
	private XComboBox startRoomSEL;
	private JComboBox<String> endBuildingSEL;
	private XComboBox endRoomSEL;
	//private JComboBox startFloorSEL;

	//Buttons on the UI
	private JButton searchButton;
	private JButton clearButton;
	Graphics g;
	boolean updatePath = false;
	private JButton leftArrow;
	private JButton rightArrow;

	//Start-End Nodes
	private Node startNode;
	private Node endNode;
	private LinkedList<Node> listPath = new LinkedList<Node>();
	private Djikstra pathCalc = new Djikstra();

	//List of buildings to be shown to the user
	private String buildingSelectedSTART;	//track which building is selected to start in.
	private String buildingSelectedEND;		//track which building is selected to end in.
	public ImageIcon mapIcon;
	//private Node hovered;
	private JTextPane mapNumber;
	private Integer totalMaps = 1;
	private int arrowCounter = 0;
	private int floor = -1;

	private Map currentlyShownMap;

	private JButton emergency;
	private Icon emergencyIcon;
	private JButton email;
	private Icon emailIcon;
	private Icon historyIcon;
	private JButton history;
	private JButton transport;
	private Icon transportIcon;
	private JButton nearestBathroom;
	private Icon bathroomIcon;

	private JButton tutorial;
	private Icon tutIcon;
	int count;
	
	private String emailDirections;
	private int totalDistance;

	private LinkedList<Node> historicalNodes;
	private ToolTipManager ttManager;

	private JScrollPane scrollMapPanel;
	/**
	 * Create the application.
	 */
	@SuppressWarnings("unchecked")
	public EndUserGUI(){
		Serialize serialize = new Serialize();
		Object tempMaps = serialize.deSerialize("MapList");
		if(tempMaps instanceof LinkedList<?>){
			maps = (LinkedList<Map>) serialize.deSerialize("MapList");
		}
		startRoomSEL = new XComboBox(this);
		endRoomSEL = new XComboBox(this);
		initialize();
	}

	public void setMaps(LinkedList<Map> maps){
		this.maps = maps;
	}

	public JFrame getFrame(){
		return frame;
	}

	public Map getCurrentlyShownMap(){
		return currentlyShownMap;
	}

	public void setStartClicked(boolean set){
		startClicked = set;
	}

	public void setEndClicked(boolean set){
		endClicked = set;
	}

	public void setStartNode(Node node){
		startNode = node;
	}

	public void setEndNode(Node node){
		endNode = node;
	}
	
	
	public void clear(){
		updatePath = false;
        startClicked = false;
        endClicked = false;
        endNode = null;
        directions.setText("");
        mapsForPaths = null;
        mapNumber.setText("");
        mapPanel.setStartNode(null);
        mapPanel.setEndNode(null);
        mapPanel.setPath(null);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	
	
	private void initialize() {

		MyGraphics graph = new MyGraphics();
		
		
		//Frame operations
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		frame.setTitle("Get There");
		frame.setResizable(false);
		frame.setVisible(true);

		//Panel Operations
		uiPanel = new JPanel();
		frame.getContentPane().add(uiPanel);
		uiPanel.setLayout(null);
		
		tutView = new JLabel("",JLabel.CENTER);    
		tutView.setLocation(0, 0);
		tutView.setSize(1194,672);
		uiPanel.add(tutView);

		mapPanel = new ImagePanel();
		mapPanel.add(graph);
		scrollMapPanel = new JScrollPane(mapPanel);
		scrollMapPanel.setBounds(5, 5, 750, 620);
		zoom = new ImageZoom(mapPanel);
		//uiPanel.add(zoom.getUIPanel());
		//uiPanel.add(mapPanel);
		
		scrollMapPanel.getViewport().addChangeListener(new ChangeListener(){
			

			@Override
			public void stateChanged(ChangeEvent e) {
				
				
				
			}
		});
		uiPanel.add(scrollMapPanel);
		
		
		//scrollMapPanel.add(graph);
		
		revalidate();
		repaint();

		//Creating Labels
		startPoint = new JLabel("FROM");
		startPoint.setBounds(780, 6, 132, 29);

		buildingStart = new JLabel("Select Building:");
		buildingStart.setBounds(762, 26, 132, 29);

		roomStart = new JLabel("Select Room:");
		roomStart.setBounds(983, 26, 132, 29);

		endPoint = new JLabel("TO");
		endPoint.setBounds(780, 72, 132, 29);

		buildingEnd = new JLabel("Select Building:");
		buildingEnd.setBounds(762, 92, 132, 29);

		roomEnd = new JLabel("Select Room:");
		roomEnd.setBounds(983, 92, 132, 29);

		//Add Labels to the uiPanel
		uiPanel.add(startPoint);
		uiPanel.add(buildingStart);
		uiPanel.add(roomStart);
		uiPanel.add(endPoint);
		uiPanel.add(buildingEnd);
		uiPanel.add(roomEnd);
		
		//startRoomSEL.setModel(new DefaultComboBoxModel(new String[]{}));
		startRoomSEL.setBounds(983, 50, 210, 29);
		startRoomSEL.setEditable(false);
		startRoomSEL.setVisible(true);
		startRoomSEL.setName("Start");

		mapNumber = new JTextPane();
		mapNumber.setBounds(360, 634, 47, 20);
		mapNumber.setEditable(false);
		mapNumber.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		mapNumber.setAlignmentX(StyleConstants.ALIGN_CENTER);
		mapNumber.setAlignmentY(StyleConstants.ALIGN_CENTER);
		uiPanel.add(mapNumber);

		//Construct Combo boxes to select start point
		startBuildingSEL = new JComboBox<String>();
		startBuildingSEL.setBounds(755, 50, 232, 29);
		startBuildingSEL.setEditable(false);
		startBuildingSEL.setVisible(true);
		startBuildingSEL.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//currentMapFile = maps.get(maps.size()-1).getImage();
				updatePath = false;
				mapNumber.setText("");
				repaint();
				revalidate();
				int indexOfCurrentMap;
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				buildingSelectedSTART = (String)cb.getSelectedItem();
				for(indexOfCurrentMap = 0; indexOfCurrentMap < maps.size(); ++indexOfCurrentMap){
					if(buildingSelectedSTART.equals(maps.get(indexOfCurrentMap).getMapName()))
						break;
				}
				currentStartNodes = maps.get(indexOfCurrentMap).getNodes();
				startRooms = new String[currentStartNodes.size()];
				//currentStartEdges = maps.get(indexOfCurrentMap).getEdges();
				currentMapFile = maps.get(indexOfCurrentMap).getImage();
				currentlyShownMap = maps.get(indexOfCurrentMap);
				arrowCounter = 0;
				mapsForPaths = null;

				historicalNodes = new LinkedList<>();
				for(int m = 0; m < currentStartNodes.size(); m++){
					if(currentStartNodes.get(m).getType().equals(NodeType.HISTORICAL)){
						historicalNodes.add(currentStartNodes.get(m));
					}
				}

				startRoomSEL.removeAllItems();
				startRoomSEL.setMap(maps.get(indexOfCurrentMap));
				for(int i = 0; i < currentStartNodes.size(); ++i){
					startRooms[i] = currentStartNodes.get(i).getName();
					if(startRooms[i] != "" && currentStartNodes.get(i).getType() != NodeType.NOTYPE)
						startRoomSEL.addItem(startRooms[i]);
				}
				
				mapPanel.setImage(currentlyShownMap.getImage());
				mapPanel.add(graph);
				zoom = new ImageZoom(mapPanel);
				JLabel scaleLabel = new JLabel("Scale");
				scaleLabel.setBounds(680, 630, 50, 30);
				uiPanel.add(scaleLabel);
				uiPanel.add(zoom.getZoomingSpinner());
				uiPanel.add(zoom.getZoomInButton());
				uiPanel.add(zoom.getZoomOutButton());
				uiPanel.add(scrollMapPanel);
				uiPanel.repaint();
				frame.repaint();
			}
		});

		for (int i = 0; i < maps.size(); i++) {
			if(maps.get(i).getMapName() != null)
				startBuildingSEL.addItem(maps.get(i).getMapName());
		}

		//endRoomSEL.setModel(new DefaultComboBoxModel(new String[]{}));
		endRoomSEL.setBounds(983, 116, 210, 29);
		endRoomSEL.setEditable(false);
		endRoomSEL.setVisible(true);
		endRoomSEL.setName("End");

		//Construct Combo boxes to select end point
		endBuildingSEL = new JComboBox<String>();
		endBuildingSEL.setBounds(755, 116, 232, 29);
		endBuildingSEL.setEditable(false);
		endBuildingSEL.setVisible(true);
		endBuildingSEL.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				updatePath = false;
				int indexOfCurrentMap;
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				buildingSelectedEND = (String)cb.getSelectedItem();
				for(indexOfCurrentMap = 0; indexOfCurrentMap < maps.size(); ++indexOfCurrentMap){
					if(buildingSelectedEND.equals(maps.get(indexOfCurrentMap).getMapName()))
						break;
				}
				currentEndNodes = maps.get(indexOfCurrentMap).getNodes();
				endRooms = new String[currentEndNodes.size()];
				currentMapFile = maps.get(indexOfCurrentMap).getImage();
				currentlyShownMap = maps.get(indexOfCurrentMap);

				historicalNodes = new LinkedList<>();
				for(int m = 0; m < currentEndNodes.size(); m++){
					if(currentEndNodes.get(m).getType().equals(NodeType.HISTORICAL)){
						historicalNodes.add(currentEndNodes.get(m));
					}
				}

				endRoomSEL.removeAllItems();
				endRoomSEL.setMap(maps.get(indexOfCurrentMap));
				arrowCounter = 0;
				mapsForPaths = null;

				for(int i = 0; i < currentEndNodes.size(); i++){
					endRooms[i] = currentEndNodes.get(i).getName();
					if(endRooms[i] != "" && currentEndNodes.get(i).getType() != NodeType.NOTYPE)
						endRoomSEL.addItem(endRooms[i]);
				}
				//endHoverFlag = false;
				uiPanel.repaint();
				frame.repaint();
			}
		});

		for (int i = 0; i < maps.size(); i++) {
			if(maps.get(i).getMapName() != null)
				endBuildingSEL.addItem(maps.get(i).getMapName());
		}


		//Add Button for tuturial
		tutIcon = new ImageIcon("IconImages/help9.png");
		tutorial = new JButton();
		tutorial.setToolTipText ("Tutorial");
		tutorial.setIcon(tutIcon);
		tutorial.setBounds(6, 632, 40, 40);
		uiPanel.add(tutorial);
		count = 1;
		ImageIcon icon = new ImageIcon("IconImages/Tut.png");
		tutorial.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (count > 0)
					tutView.setIcon(icon);
				if (count < 0)
					tutView.setIcon(null);
				count *= -1;
			}
		});

		//Add Combo Boxes to UIPanel

		uiPanel.add(startBuildingSEL);
		uiPanel.add(startRoomSEL);
		uiPanel.add(endBuildingSEL);
		uiPanel.add(endRoomSEL);

		//Construct button and add button to uiPanel
		searchButton = new JButton ("Search");
		searchButton.setBounds(987, 150, 132, 30);
		uiPanel.add(searchButton);

		clearButton = new JButton ("Clear");
        clearButton.setBounds(853, 150, 132, 30);
        uiPanel.add(clearButton);
        clearButton.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e)
            {
                clear();
            }
        });

        

		leftArrow = new JButton("<<");
		leftArrow.setBounds(275, 630, 80, 29);
		uiPanel.add(leftArrow);
		if(arrowCounter == 0){
			leftArrow.setEnabled(false);
		}



		rightArrow = new JButton(">>");
		rightArrow.setBounds(412, 630, 80, 29);
		uiPanel.add(rightArrow);
		rightArrow.setEnabled(false);

		JLabel instructions = new JLabel("How to get there?");
		instructions.setBounds(930, 180, 132, 29);
		uiPanel.add(instructions);

		directions = new JTextArea();
		directions.setBounds(762, 210, 255, 420);
		directions.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		directions.setLineWrap(true);
		directions.setEditable(false);
		JScrollPane scrollDire = new JScrollPane(directions);
		scrollDire.setBounds(835, 210, 300, 420);
		scrollDire.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		uiPanel.add(scrollDire);

		emergencyIcon = new ImageIcon("IconImages/emergencyIcon.png");
		Icon emergencyIconBIG = new ImageIcon("IconImages/emergencyIconBIG.png");
		emergency = new JButton();
		emergency.setToolTipText("Emergency Information");
		emergency.setIcon(emergencyIcon);
		emergency.setBounds(872, 632, 40, 40);
		uiPanel.add(emergency);
		String emergencyInfo = "Call Campus Police:" + "\n" + "508-831-5555";
		emergency.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, emergencyInfo, "Incase of emergency", JOptionPane.PLAIN_MESSAGE, emergencyIconBIG);
			}
		});

		emailIcon = new ImageIcon("IconImages/emailIcon.png");
		email = new JButton();
		email.setToolTipText("Send Directions via Email");
		email.setIcon(emailIcon);
		email.setBounds(920, 632, 40, 40);
		uiPanel.add(email);
		email.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent e)
			{
				if(emailDirections != null) {
					EMailDialogue em = new EMailDialogue(frame, emailDirections);
					em.setVisible(true);
				}
				else {
				}
			}
		});

		transportIcon = new ImageIcon("IconImages/transportIcon.png");
		Icon transportIconBIG = new ImageIcon("IconImages/transportIconBIG.png");
		Icon gatewaySchedule = new ImageIcon("IconImages/gatewaySchedule.png");
		Icon eveningSchedule = new ImageIcon("IconImages/eveningSchedule.png");
		Icon wpiumassSchedule = new ImageIcon("IconImages/wpi-umassSchedule.png");
		Icon snapSchedule = new ImageIcon("IconImages/snapSchedule.png");
		transport = new JButton();
		transport.setToolTipText("View Transport Schedule");
		transport.setIcon(transportIcon);
		transport.setBounds(968, 632, 40, 40);
		uiPanel.add(transport);
		transport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String[] schedules = {"Gateway Shuttle", "Evening Shuttle", "WPI-Umass Shuttle", "SNAP"};
				Object selectedValue = JOptionPane.showInputDialog(null, "Which transport would you like to use?", "Select Transport type",
						JOptionPane.INFORMATION_MESSAGE, transportIconBIG,schedules, schedules[0]);
				if(selectedValue != null){
					switch((String) selectedValue){
					case "Gateway Shuttle":
						JOptionPane.showMessageDialog(null, null, "Gateway Shuttle Schedule", JOptionPane.INFORMATION_MESSAGE, gatewaySchedule);
						break;
					case "Evening Shuttle":
						JOptionPane.showMessageDialog(null, null, "Evening Shuttle Schedule", JOptionPane.INFORMATION_MESSAGE, eveningSchedule);
						break;
					case "WPI-Umass Shuttle":
						JOptionPane.showMessageDialog(null, null, "WPI-Umass Shuttle Schedule", JOptionPane.INFORMATION_MESSAGE, wpiumassSchedule);
						break;
					case "SNAP":
						JOptionPane.showMessageDialog(null, null, "SNAP Shuttle", JOptionPane.INFORMATION_MESSAGE, snapSchedule);
						break;
					default:
						break;
					}
				}
			}
		});

		bathroomIcon = new ImageIcon("IconImages/bathroomIcon.png");
		Icon bathroomIconBIG = new ImageIcon("IconImages/bathroomIconBIG.png");
		nearestBathroom = new JButton();
		nearestBathroom.setToolTipText("Find nearest Bathroom");
		nearestBathroom.setIcon(bathroomIcon);
		nearestBathroom.setBounds(1017, 632, 40, 40);
		uiPanel.add(nearestBathroom);
		nearestBathroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String[] bathroomTypes = {"Female", "Male"};
				Object selectedValue = JOptionPane.showInputDialog(null, "Bathroom Type", "Select Gender",
						JOptionPane.INFORMATION_MESSAGE, bathroomIcon, bathroomTypes, bathroomTypes[0]);
				if(selectedValue != null){
					switch((String) selectedValue){
					case "Female":

						if(listPath != null && startNode != null){
							listPath = pathCalc.nearestSpecialNode(startNode, NodeType.FBATHROOM);
							updatePath = true;
						}
						break;
					case "Male":
						if(listPath != null && startNode != null){
							listPath = pathCalc.nearestSpecialNode(startNode, NodeType.MBATHROOM);
							updatePath = true;
						}
						break;
					default:
						break;
					}
				}
			}
		});

		historyIcon = new ImageIcon("IconImages/historyIcon.png");
		ImageIcon historyIconBIG = new ImageIcon("IconImages/historyIconBIG.png");
		history = new JButton();
		history.setToolTipText("...Coming Soon");
		history.setIcon(historyIcon);
		history.setBounds(1064, 632, 40, 40);
		uiPanel.add(history);
		history.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "....Coming Soon", "...Coming Soon", JOptionPane.PLAIN_MESSAGE, historyIconBIG);
			}
		});

		//Construct buttons and add action listener
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int i;
				updatePath = true;
				uiPanel.setVisible(true);
				frame.setVisible(true);
				//pathCalc = new Djikstra();
				if(!startClicked && !endClicked){
					for (i = 0; i < currentStartNodes.size(); i++){
						if(startRoomSEL.getSelectedItem() == currentStartNodes.get(i).getName())
							startNode = currentStartNodes.get(i);
					}
					for(i = 0; i < currentEndNodes.size(); i++){
						if(endRoomSEL.getSelectedItem() == currentEndNodes.get(i).getName())
							endNode = currentEndNodes.get(i);
					}
				}
				if(startClicked && !endClicked){
					directions.setText("Please Select End point");
					updatePath = false;
				}
				if(updatePath && startNode != null && endNode != null){
					listPath = pathCalc.navigate(startNode, endNode);
					mapsForPaths = new LinkedList<Map>();
					for (i = 0; i < listPath.size(); i++){
						for (int j = 0; j < maps.size(); j++){
							nodesInMap = maps.get(j).getNodes();
							for(int k = 0; k<nodesInMap.size(); k++){
								if(listPath.get(i) == nodesInMap.get(k)){
									if(!mapsForPaths.contains(maps.get(j))){
										mapsForPaths.add(maps.get(j));
									}
								}
							}
						}
						currentMapFile = mapsForPaths.getFirst().getImage();
						currentlyShownMap = mapsForPaths.getFirst();
						totalMaps = mapsForPaths.size();

						if(mapsForPaths.size() > 1){
							rightArrow.setEnabled(true);
							mapNumber.setText(String.valueOf(1) + " of " + String.valueOf(totalMaps));
						}
					}
					//					emailDirections = "From: " + startNode.getMapName() + " " + startNode.getName() + "\n" + "to "
					//                            + Node.getMapName() + ", " + endRoomSEL.getSelectedItem() + "\n" + "\n" +
					emailDirections = pathCalc.gpsInstructions(pathCalc.navigate(startNode, endNode));
					if (listPath != null){
						totalDistance = Djikstra.getDistance(listPath);
					}
					directions.setText("From: " + startNode.getMapName() + ", " + startNode.getName() + "\n" + "to " 
										+ endNode.getMapName() + ", " + endRoomSEL.getSelectedItem() + "\n" + "\n" 
										+ "Total Distance to Destination: " + totalDistance  + " ft" + "\n"+ "Time to Destination: " +
										(double)totalDistance/4.11 +"mins" + "\n" + emailDirections);
					repaint();
					revalidate();
				}
			}
		});

		leftArrow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(mapsForPaths!= null){
					if(arrowCounter != 0){
						rightArrow.setEnabled(true);
						arrowCounter -= 1;
						mapNumber.setText(String.valueOf(arrowCounter + 1) + " of " + String.valueOf(totalMaps));
						currentMapFile = mapsForPaths.get(arrowCounter).getImage();
						currentlyShownMap = mapsForPaths.get(arrowCounter);
					}else if (arrowCounter == 0){
						leftArrow.setEnabled(false);
					}
				}
			}
		});
		rightArrow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(mapsForPaths != null){
					if(arrowCounter != totalMaps-1){
						leftArrow.setEnabled(true);
						arrowCounter += 1;
						mapNumber.setText(String.valueOf(arrowCounter + 1) + " of " + String.valueOf(totalMaps));
						currentMapFile = mapsForPaths.get(arrowCounter).getImage();
						currentlyShownMap = mapsForPaths.get(arrowCounter);
					}else if (arrowCounter == totalMaps-1){
						rightArrow.setEnabled(false);
					}
				}
			}
		});
		uiPanel.setVisible(true);
		frame.setVisible(true);
		clear();
	}

	public class MyGraphics extends JComponent implements MouseMotionListener{

		private static final long serialVersionUID = 1L;
		private static final int SquareWidth = 5;
		

		MyGraphics() {
			setPreferredSize(new Dimension(760, 666));
			addMouseMotionListener(this);
			

			addMouseListener(new MouseAdapter(){
				
				
				public void mouseClicked(MouseEvent evt) {
					int x = evt.getX();
					int y = evt.getY();
					repaint();
					if(!startClicked){
						startNode = findClosestNode(x,y);
						startClicked = true;
					}
					else if(!endClicked){
						endNode = findClosestNode(x,y);
						endClicked = true;
					}
				}
				
				private Node findClosestNode(int x, int y) {
					double shortestDistance = Double.MAX_VALUE;
					double previousShortestDistance;
					Node result = null;
					for(int i = 0; i < currentlyShownMap.getNodes().size(); i++){
						previousShortestDistance = shortestDistance;
						Node temp = currentlyShownMap.getNodes().get(i);
						shortestDistance = Math.min(Math.sqrt((temp.getX()-x)*(temp.getX()-x) + (temp.getY()-y)*(temp.getY()-y)), shortestDistance);
						if(previousShortestDistance != shortestDistance){
							result = temp;
						}
					}
					return result;
				}});
			addMouseMotionListener(this);
			
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			

			
			
			//g.drawImage(currentMapFile.getImage(), 0, 0, this);
			if(currentlyShownMap != null){
			
			mapPanel.setImage(currentlyShownMap.getImage());
			
			uiPanel.setVisible(true);
			frame.setVisible(true);
			}

			GeneralPath path = null;

			repaint();
			revalidate();
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform at = new AffineTransform();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			BasicStroke s = new BasicStroke(
					3f, 
					BasicStroke.CAP_ROUND, 
					BasicStroke.JOIN_ROUND);
			g2d.setStroke(s);
			double scale =  mapPanel.getScale();
			

			int w = mapPanel.getWidth();
	        int h = mapPanel.getHeight();
	        BufferedImage image = mapPanel.getImage();
	        if(image != null){
	        int imageWidth = image.getWidth();
	        int imageHeight = image.getHeight();
	        double x = (w - scale * imageWidth)/2;
	        double y = (h - scale * imageHeight)/2;
	        at = AffineTransform.getTranslateInstance(0,0);
	        at.scale(scale, scale);
	       
	        }
			repaint();
			revalidate();
			if (path==null && updatePath == true && listPath.size() > 0) {
				removeAll();
				int i;
				path = new GeneralPath();
				path.moveTo(listPath.getFirst().getX(), listPath.getFirst().getY());; 
				for (i=1; i<listPath.size(); i++){
					if(currentlyShownMap.getNodes().contains(listPath.get(i-1)) && 
							currentlyShownMap.getNodes().contains(listPath.get(i))){
						path.lineTo(listPath.get(i).getX(),listPath.get(i).getY());
						//path.transform(at);
						//g2d.draw(path);
					}
					else{
						//path.transform(at);
						path.moveTo(listPath.get(i).getX(), listPath.get(i).getY());
					}
				}
				mapPanel.setPath(path);

				endNode = listPath.get(listPath.size() - 1);
				
				if(mapsForPaths != null){
					if(mapsForPaths.get(arrowCounter).getNodes().contains(startNode)){
						mapPanel.setStartNode(startNode);
						
					}
	
					if(mapsForPaths.get(arrowCounter).getNodes().contains(endNode)){
					mapPanel.setEndNode(endNode);
					}
				}

				repaint();
				revalidate();
			}
			if(startClicked && (startNode != null) && (currentlyShownMap.getNodes().contains(startNode))){
				mapPanel.setStartNode(startNode);

			}
			if(endClicked && (endNode != null) && (currentlyShownMap.getNodes().contains(endNode))){
				mapPanel.setEndNode(endNode);
			}
		}
		

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			if(ttManager == null){
				ttManager = ToolTipManager.sharedInstance();
			}

			if(nearHistoricalNode(x, y) != null){
				// + nearHistoricalNode(x, y).getName() + 
				URL url = getClass().getResource("/historicalimages/" + nearHistoricalNode(x, y).getName() + ".jpg");
				if(url == null){
					url = getClass().getResource("/historicalimages/default.jpg");
				}
				String tt = "<html><body><img src='" + url + "'></body></html>";
				setToolTipText(tt);
				ttManager.setEnabled(true);
			} else{
				if(ttManager.isEnabled()){
					ttManager.setEnabled(false);
				}
			}
		}
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
		}


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	private int IDCount;
	public void fixIDs(LinkedList<Map> mapList){
		IDCount = 0;
		for(int i = 0; i < mapList.size(); i++){
			for (int j = 0; j < mapList.get(i).getNodes().size(); j++){
				mapList.get(i).getNodes().get(j).setID(IDCount);
				IDCount++;
			}
		}
	}

	public Node nearHistoricalNode(int x, int y){
		if(historicalNodes != null){
			if(historicalNodes.size() > 0){
				for(int i = 0; i < historicalNodes.size(); i++){
					if(((x - historicalNodes.get(i).getX()) < 6) && ((y - historicalNodes.get(i).getY()) < 6)){
						if(((historicalNodes.get(i).getX() - x) < 6) && ((historicalNodes.get(i).getY() - y) < 6)){
							return historicalNodes.get(i);
						}
					}
				}
			}
		}
		return null;
	}
}