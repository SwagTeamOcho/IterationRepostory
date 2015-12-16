
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.BorderFactory;
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
import javax.swing.text.StyleConstants;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.thehowtotutorial.splashscreen.JSplash;

import javafx.scene.layout.Border;

public class EndUserGUI extends JPanel implements ActionListener{

	private static final long serialVersionUID = 2270760135813536905L;
	private LinkedList<Map> maps = new LinkedList<Map>();
	private LinkedList<Node> currentStartNodes = new LinkedList<Node>();
	private LinkedList<Node> currentEndNodes = new LinkedList<Node>();
	private LinkedList<Map> mapsForPaths = new LinkedList<Map>();
	private LinkedList<Node> nodesInMap = new LinkedList<Node>();

	private String[] startRooms;
	private String[] endRooms;
	
	private boolean mousePressedFlag;

	//private static ImageIcon currentMapFile;

	private boolean startClicked = false;
	private boolean endClicked = false;

	private JTextArea directions;

	private JFrame frame;		//Creates the main frame for the GUI
	private JPanel uiPanel;		//Panel to hold the interface buttons
	private ImagePanel mapPanel;	//Panel to hold the map
	//private ImageZoom zoom;
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

	//Combo Boxes on the GUI
	private JComboBox<String> startBuildingSEL;
	private XComboBox startRoomSEL;
	private JComboBox<String> endBuildingSEL;
	private XComboBox endRoomSEL;


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

	private Map currentlyShownMap;

	private MyGraphics graph;
	private JButton findProf;
	private Icon findProfIcon;
	private JButton emergency;
	private Icon emergencyIcon;
	private JButton email;
	private Icon emailIcon;
	private Icon bluetowerIcon;
	private JButton transport;
	private Icon transportIcon;
	private JButton nearestBathroom;
	private JButton nearestBluetower;
	private Icon bathroomIcon;

	private JButton tutorial;
	private Icon tutIcon;

	private JButton backToCampus;

	int count;

	private String emailDirections;
	private int totalDistance;

	private LinkedList<Node> historicalNodes;
	private ToolTipManager ttManager;

	private JScrollPane scrollMapPanel;
	
	private Color burgandy = new Color(74, 1, 1);
	private Color beige = new Color(230, 224, 200);
	int xMouse;
	int yMouse;

	
	
	private ImageIcon currentAboutPage;
	
	HandScrollListener scrollListener;

	private LinkedList<Node> startTransitionNodes = new LinkedList<Node>();
	private LinkedList<Node> endTransitionNodes = new LinkedList<Node>();
	/**
	 * Create the application.
	 */
	@SuppressWarnings("unchecked")
	public EndUserGUI(){
		JSplash loadingScreen = new JSplash(LoadingScreen.class.getResource("loadingScreen.png"),
								true, true, false, null, null, beige, burgandy);
		Serialize serialize = new Serialize();
		Object tempMaps = serialize.deSerialize("MapList");
		if(tempMaps instanceof LinkedList<?>){
			maps = (LinkedList<Map>) tempMaps;
		}
		startRoomSEL = new XComboBox(this);
		endRoomSEL = new XComboBox(this);
		initialize();
	}

	public JScrollPane getScrollMapPanel(){
		return this.scrollMapPanel;
	}

	public void setMaps(LinkedList<Map> maps){
		this.maps = maps;
	}

	public ImagePanel getMapPanel(){
		return this.mapPanel;
	}

	public LinkedList<Node >getStartTransitionNodes(){
		return this.startTransitionNodes;
	}

	public LinkedList<Node >getEndTransitionNodes(){
		return this.endTransitionNodes;
	}

	public JFrame getFrame(){
		return frame;
	}

	public Map getCurrentlyShownMap(){
		return currentlyShownMap;
	}

	public LinkedList<Node> getHistoricalNodes(){
		return this.historicalNodes;
	}

	public void setStartClicked(boolean set){
		startClicked = set;
	}
	
	public MyGraphics getGraph(){
		return this.graph;
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
		leftArrow.setEnabled(false);
		rightArrow.setEnabled(false);
		email.setEnabled(false);
	}
	/**
	 * Initialize the contents of the frame.
	 */


	private void initialize() {
		try{
			JSplash loadingScreen = new JSplash(LoadingScreen.class.getResource("loadingScreen.png"), true, true, false, null, 
					null, beige, burgandy);
			loadingScreen.splashOn();
			loadingScreen.setProgress(30);
			Thread.sleep(1000);
			loadingScreen.setProgress(60);
			Thread.sleep(1000);
			loadingScreen.setProgress(100);
			Thread.sleep(600);
			loadingScreen.splashOff();

		}catch(Exception e){
			e.printStackTrace();
		}

		graph = new MyGraphics(this);
		ttManager = ToolTipManager.sharedInstance();
		ttManager.setEnabled(true);


		//Frame operations
		frame = new JFrame();
		frame.setBounds(50, 25, 1200, 700);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		frame.setTitle("Get There");
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setVisible(true);

		
		//Panel Operations
		uiPanel = new JPanel();
		frame.getContentPane().add(uiPanel);
		uiPanel.setLayout(null);

		uiPanel.setBackground(beige);
		tutView = new JLabel("",JLabel.CENTER);    
		tutView.setLocation(0, 0);
		tutView.setSize(1194,702);
		uiPanel.add(tutView);

		mapPanel = new ImagePanel(this);
		mapPanel.add(graph);
		//zoom = new ImageZoom(mapPanel);
		scrollMapPanel = new JScrollPane(mapPanel);
		scrollMapPanel.setBounds(5, 20+7, 750, 620);
		scrollMapPanel.setBackground(burgandy);

		//uiPanel.add(zoom.getUIPanel());
		//uiPanel.add(mapPanel);

		scrollMapPanel.getViewport().addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
			}
		});
		uiPanel.add(scrollMapPanel);
		
		//Customizing the Title Bar
		JPanel titleBar = new JPanel();
		titleBar.setLayout(null);
		titleBar.setBounds(0, 0, 1200, 20);
		titleBar.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX();
            	yMouse = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
		titleBar.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent me) {
            	int x = me.getXOnScreen();
            	int y = me.getYOnScreen();
            	frame.setLocation(x-xMouse, y-yMouse);
            	//System.out.println(x + y);
            }

			public void mouseMoved(MouseEvent e) {
				
			}
			
		});
		titleBar.setBackground(beige);
		uiPanel.add(titleBar);
		
		//About Button Operation
		final JFrame aboutFrame = new JFrame();
		aboutFrame.setBounds(100, 70, 920, 650);
		aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		ImageIcon aboutPg1 = new ImageIcon("IconImages/aboutPg1.jpg");
		ImageIcon aboutPg2 = new ImageIcon("IconImages/aboutPg2.jpg");
		final ImageIcon[] aboutPages = {aboutPg1, aboutPg2};
		
		currentAboutPage = aboutPages[0];
		final JPanel aboutPanel = new JPanel();
		aboutPanel.setBackground(beige);
		final JLabel aboutLabel = new JLabel();
		aboutPanel.add(aboutLabel);
    	aboutLabel.setIcon(currentAboutPage);
		aboutLabel.setBounds(50, 50, 900, 620);
		aboutPanel.addMouseListener(new MouseListener() {
	        public void mouseClicked(MouseEvent e) {
	        	e.getClickCount();
	            if(e.getClickCount() ==1){
	            	
	            	currentAboutPage = aboutPages[0];
	            	//System.out.print(e.getClickCount());
	            	aboutLabel.setIcon(currentAboutPage);
	            	
	            }else if(e.getClickCount() == 2){
	            	currentAboutPage = aboutPages[1];
	            	aboutLabel.setIcon(currentAboutPage);
	            	//System.out.print(e.getClickCount());
	            }
	        }

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    }); 

		
		JLabel about = new JLabel("About");
		about.setFont(new Font("Calisto MT Bold Italic", Font.BOLD, 14));
		about.setForeground(burgandy);
		about.setBounds(1140, 0, 50, 24);
		about.setCursor(new Cursor(Cursor.HAND_CURSOR));
		about.addMouseListener(new MouseListener() {
	        public void mouseClicked(MouseEvent e) {
				aboutFrame.setVisible(true);
				aboutFrame.getContentPane().add(aboutPanel);
	        }

			@Override
			public void mousePressed(MouseEvent e) {				
			}

			@Override
			public void mouseReleased(MouseEvent e) {				
			}

			@Override
			public void mouseEntered(MouseEvent e) {				
			}

			@Override
			public void mouseExited(MouseEvent e) {				
			}
	    }); 
		titleBar.add(about);
		
		JLabel closeButt = new JLabel();
		//closeButt.setForeground(beige);
		//closeButt.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		ImageIcon close = new ImageIcon("IconImages/close.png");
		closeButt.setIcon(close);
		closeButt.setBounds(7, 0, 24, 24);
		closeButt.setCursor(new Cursor(Cursor.HAND_CURSOR));
		closeButt.addMouseListener(new MouseListener() {
	        public void mouseClicked(MouseEvent e) {
	            // TODO Auto-generated method stub
	        	System.exit(0);;
	        }

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    }); 
		JLabel minButt = new JLabel();
		ImageIcon minimize = new ImageIcon("IconImages/min.png");
		minButt.setIcon(minimize);
		minButt.setBounds(25, 5, 15, 15);
		minButt.setCursor(new Cursor(Cursor.HAND_CURSOR));
		minButt.addMouseListener(new MouseListener() {
	        public void mouseClicked(MouseEvent e) {
	            // TODO Auto-generated method stub
	        	frame.setState(frame.ICONIFIED);
	        }

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    }); 
		
		titleBar.add(minButt);
		titleBar.add(closeButt);
		
//		scrollMapPanel.setBounds(5, 20+15, 750, 620);
		scrollMapPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollMapPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


		scrollListener = new HandScrollListener(mapPanel);
		scrollMapPanel.getViewport().addMouseMotionListener(scrollListener);
		scrollMapPanel.getViewport().addMouseListener(scrollListener);

		//uiPanel.add(zoom.getUIPanel());
		//uiPanel.add(mapPanel);


		uiPanel.add(scrollMapPanel);

		//Creating Labels
		startPoint = new JLabel("FROM");
		startPoint.setForeground(burgandy);
		startPoint.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		startPoint.setBounds(780, 6+15, 132, 29);

		buildingStart = new JLabel("Select Building:");
		buildingStart.setForeground(burgandy);
		buildingStart.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		buildingStart.setBounds(762, 26+15, 132, 29);

		roomStart = new JLabel("Select Room:");
		roomStart.setForeground(burgandy);
		roomStart.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		roomStart.setBounds(983, 26+15, 132, 29);

		endPoint = new JLabel("TO");
		endPoint.setForeground(burgandy);
		endPoint.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		endPoint.setBounds(780, 72+15, 132, 29);

		buildingEnd = new JLabel("Select Building:");
		buildingEnd.setForeground(burgandy);
		buildingEnd.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		buildingEnd.setBounds(762, 92+15, 132, 29);

		roomEnd = new JLabel("Select Room:");
		roomEnd.setForeground(burgandy);
		roomEnd.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		roomEnd.setBounds(983, 92+15, 132, 29);

		//Add Labels to the uiPanel
		uiPanel.add(startPoint);
		uiPanel.add(buildingStart);
		uiPanel.add(roomStart);
		uiPanel.add(endPoint);
		uiPanel.add(buildingEnd);
		uiPanel.add(roomEnd);

		//startRoomSEL.setModel(new DefaultComboBoxModel(new String[]{}));
		startRoomSEL.setBounds(983, 50+15, 210, 29);
		startRoomSEL.setEditable(false);
		startRoomSEL.setVisible(true);
		startRoomSEL.setName("Start");

		mapNumber = new JTextPane();
		mapNumber.setBounds(360, 634+30, 47, 20);
		mapNumber.setEditable(false);
		mapNumber.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		mapNumber.setAlignmentX(StyleConstants.ALIGN_CENTER);
		mapNumber.setAlignmentY(StyleConstants.ALIGN_CENTER);
		uiPanel.add(mapNumber);

		backToCampus = new JButton("Back to Campus");
		backToCampus.setBounds(100, 630 +30, 150, 29);
		uiPanel.add(backToCampus);
		backToCampus.setEnabled(true);


		//Construct Combo boxes to select start point
		startBuildingSEL = new JComboBox<String>();
		startBuildingSEL.setBounds(755, 50+15, 232, 29);
		startBuildingSEL.setEditable(false);
		startBuildingSEL.setVisible(true);
		startBuildingSEL.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//currentMapFile = maps.get(maps.size()-1).getImage();
				updatePath = false;
				mapNumber.setText("");
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
				//currentMapFile = maps.get(indexOfCurrentMap).getImage();
				currentlyShownMap = maps.get(indexOfCurrentMap);
				if (!currentlyShownMap.getMapName().equals("WPI Campus Map")){
					backToCampus.setEnabled(true);
				} else{
					backToCampus.setEnabled(false);
				}
				arrowCounter = 0;
				mapsForPaths = null;

				historicalNodes = new LinkedList<Node>();
				for(int m = 0; m < currentStartNodes.size(); m++){
					if(currentStartNodes.get(m).getType() == NodeType.HISTORICAL){
						historicalNodes.add(currentStartNodes.get(m));
					}
				}
				ArrayList<String> sortedStartRooms = new ArrayList<String>();
				startRoomSEL.removeAllItems();
				startRoomSEL.setMap(maps.get(indexOfCurrentMap));
				for(int i = 0; i < currentStartNodes.size(); ++i){
					startRooms[i] = currentStartNodes.get(i).getName();
					if(startRooms[i] != "" && currentStartNodes.get(i).getType() != NodeType.NOTYPE)
						sortedStartRooms.add(startRooms[i]);
				}
				Collections.sort(sortedStartRooms);
				for(int i = 0; i < sortedStartRooms.size(); i++){
					startRoomSEL.addItem(sortedStartRooms.get(i));
				}
				mapPanel.setImage(currentlyShownMap.getImage());
				mapPanel.add(graph);
				//zoom = new ImageZoom(mapPanel);
				//JLabel scaleLabel = new JLabel("Scale");
				//scaleLabel.setBounds(680, 630+15, 50, 30);
				//uiPanel.add(scaleLabel);
				//uiPanel.add(zoom.getZoomingSpinner());
				//uiPanel.add(zoom.getZoomInButton());
				//uiPanel.add(zoom.getZoomOutButton());
				uiPanel.add(scrollMapPanel);
				mapPanel.setPath(null);
			}

		});

		for (int i = 0; i < maps.size(); i++) {
			if(maps.get(i).getMapName() != null)
				startBuildingSEL.addItem(maps.get(i).getMapName());
		}

		//endRoomSEL.setModel(new DefaultComboBoxModel(new String[]{}));
		endRoomSEL.setBounds(983, 116+15, 210, 29);
		endRoomSEL.setEditable(false);
		endRoomSEL.setVisible(true);
		endRoomSEL.setName("End");

		//Construct Combo boxes to select end point
		endBuildingSEL = new JComboBox<String>();
		endBuildingSEL.setBounds(755, 116+15, 232, 29);
		endBuildingSEL.setEditable(false);
		endBuildingSEL.setVisible(true);
		endBuildingSEL.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//currentMapFile = maps.get(maps.size()-1).getImage();
				updatePath = false;
				mapNumber.setText("");
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
				//currentendEdges = maps.get(indexOfCurrentMap).getEdges();
				//currentMapFile = maps.get(indexOfCurrentMap).getImage();
				currentlyShownMap = maps.get(indexOfCurrentMap);
				if (!currentlyShownMap.getMapName().equals("WPI Campus Map")){
					backToCampus.setEnabled(true);
				} else{
					backToCampus.setEnabled(false);
				}
				arrowCounter = 0;
				mapsForPaths = null;

				historicalNodes = new LinkedList<Node>();
				for(int m = 0; m < currentEndNodes.size(); m++){
					if(currentEndNodes.get(m).getType() == NodeType.HISTORICAL){
						historicalNodes.add(currentEndNodes.get(m));
					}
				}
				ArrayList<String> sortedendRooms = new ArrayList<String>();
				endRoomSEL.removeAllItems();
				endRoomSEL.setMap(maps.get(indexOfCurrentMap));
				for(int i = 0; i < currentEndNodes.size(); ++i){
					endRooms[i] = currentEndNodes.get(i).getName();
					if(endRooms[i] != "" && currentEndNodes.get(i).getType() != NodeType.NOTYPE)
						sortedendRooms.add(endRooms[i]);
				}
				Collections.sort(sortedendRooms);
				for(int i = 0; i < sortedendRooms.size(); i++){
					endRoomSEL.addItem(sortedendRooms.get(i));
				}
				mapPanel.setImage(currentlyShownMap.getImage());
				mapPanel.add(graph);
				//zoom = new ImageZoom(mapPanel);
				//JLabel scaleLabel = new JLabel("Scale");
				//scaleLabel.setBounds(680, 630, 50, 30);
				//uiPanel.add(scaleLabel);
				//uiPanel.add(zoom.getZoomingSpinner());
				//uiPanel.add(zoom.getZoomInButton());
				//uiPanel.add(zoom.getZoomOutButton());
				uiPanel.add(scrollMapPanel);
				mapPanel.setPath(null);
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
		tutorial.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				ttManager.setEnabled(true);
			}
		});
		tutorial.setIcon(tutIcon);
		tutorial.setBounds(6, 632+20, 40, 40);
		uiPanel.add(tutorial);
		count = 1;
		final ImageIcon icon = new ImageIcon("IconImages/Tut.png");
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
		searchButton.setBounds(987, 150+15, 132, 30);
		uiPanel.add(searchButton);
		


		clearButton = new JButton ("Clear");
		clearButton.setBounds(853, 150+15, 132, 30);
		clearButton.setForeground(burgandy);
		clearButton.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		clearButton.setBackground(beige);
		clearButton.setOpaque(true);
		clearButton.setBorder(BorderFactory.createLineBorder(burgandy, 4));
		//clearButton.setBorderPainted(true);
		
		uiPanel.add(clearButton);
		clearButton.addMouseListener(new MouseAdapter() {
			  public void mousePressed(MouseEvent e) {
			    clearButton.setBorder(BorderFactory.createLoweredBevelBorder());
			  }

			  public void mouseReleased(MouseEvent e) {
				  clearButton.setBorder(BorderFactory.createLineBorder(burgandy, 4));
			  }
			});
		clearButton.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent e)
			{
				clear();
			}
		});

		leftArrow = new JButton("<<");
		leftArrow.setBounds(275, 630+30, 80, 29);
		uiPanel.add(leftArrow);
		if(arrowCounter == 0){
			leftArrow.setEnabled(false);
		}

		rightArrow = new JButton(">>");
		rightArrow.setBounds(412, 630+30, 80, 29);
		uiPanel.add(rightArrow);
		rightArrow.setEnabled(false);

		JLabel instructions = new JLabel("How to get there?");
		instructions.setForeground(burgandy);
		instructions.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		instructions.setBounds(930, 180+15, 132, 29);
		uiPanel.add(instructions);

		directions = new JTextArea();
		directions.setBounds(762, 210+15, 255, 420);
		directions.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		directions.setBackground(beige);
		directions.setBorder(BorderFactory.createEtchedBorder(burgandy, beige));
		//directions.setBorder(BorderFactory.createDashedBorder(burgandy, 10, 10));
		directions.setLineWrap(true);
		directions.setEditable(false);
		directions.setForeground(burgandy);
		JScrollPane scrollDire = new JScrollPane(directions);
		scrollDire.setBounds(835, 210+15, 300, 420);
		scrollDire.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		uiPanel.add(scrollDire);
		
		
		findProfIcon = new ImageIcon("IconImages/findProfIcon.png");
		emergencyIcon = new ImageIcon("IconImages/emergencyIcon.png");
		final Icon emergencyIconBIG = new ImageIcon("IconImages/emergencyIconBIG.png");
		emergency = new JButton();
		emergency.setToolTipText("Emergency Information");
		emergency.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				ttManager.setEnabled(true);
			}
		});
		emergency.setIcon(emergencyIcon);
		emergency.setBounds(872, 632+20, 40, 40);

		uiPanel.add(emergency);
		final String emergencyInfo = "Call Campus Police:" + "\n" + "508-831-5555";
		emergency.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, emergencyInfo, "Incase of emergency", JOptionPane.PLAIN_MESSAGE, emergencyIconBIG);
			}
		});

		emailIcon = new ImageIcon("IconImages/emailIcon.png");
		email = new JButton();
		email.setToolTipText("Send Directions via Email");
		email.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				ttManager.setEnabled(true);
			}
		});
		email.setIcon(emailIcon);
		email.setBounds(920, 632+20, 40, 40);
		uiPanel.add(email);
		email.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent e)
			{
				if(emailDirections != null) {
					String[] attachments = new String[1];
					try {
						File file = new File("Directions.pdf");
						FileOutputStream pdfFileout = new FileOutputStream(file);
						Document doc = new Document();
						PdfWriter.getInstance(doc, pdfFileout);

						doc.addAuthor("GetThere");
						doc.addTitle("This is title");
						doc.open();

						while(arrowCounter!=0) {
							leftArrow.doClick();
						}

						//adding a local image and aligned RIGHT
						for(int i = 0; i < totalMaps; i++) {
							mapPanel.paint(mapPanel.getGraphics());
							new PanelCapture(mapPanel);
							Image image = Image.getInstance("Screen.png");
							doc.setPageSize(image);
							doc.newPage();
							image.setAbsolutePosition(0, 0);
							doc.add(image);
							rightArrow.doClick();
						}

						doc.close();
						pdfFileout.close();

					} catch (Exception e1) {
						e1.printStackTrace();
					}
					attachments[0] = ("Directions.pdf");
					EMailDialogue em = new EMailDialogue(frame, emailDirections, attachments);
					em.setVisible(true);
				}
				else {
				}
			}
		});

		transportIcon = new ImageIcon("IconImages/transportIcon.png");
		final Icon transportIconBIG = new ImageIcon("IconImages/transportIconBIG.png");
		final Icon gatewaySchedule = new ImageIcon("IconImages/gatewaySchedule.png");
		final Icon eveningSchedule = new ImageIcon("IconImages/eveningSchedule.png");
		final Icon wpiumassSchedule = new ImageIcon("IconImages/wpi-umassSchedule.png");
		final Icon snapSchedule = new ImageIcon("IconImages/snapSchedule.png");
		transport = new JButton();
		transport.setToolTipText("View Transport Schedule");
		transport.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				ttManager.setEnabled(true);
			}
		});
		transport.setIcon(transportIcon);
		transport.setBounds(968, 632+20, 40, 40);

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
		//Object[] nearestSpots = {new ImageIcon("IconImages/bathroomIcon.png"), new ImageIcon("IconImages/blueTowerIcon.png")};
		bathroomIcon = new ImageIcon("IconImages/bathroomIcon.png");

		final Icon bathroomIconBIG = new ImageIcon("IconImages/bathroomIconBIG.png");
		nearestBathroom = new JButton(bathroomIcon);
		nearestBathroom.setToolTipText("Find nearest Bathroom");
		nearestBathroom.setBounds(1017, 632+20, 40, 40);
		nearestBathroom.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				ttManager.setEnabled(true);
			}
		});
		uiPanel.add(nearestBathroom);
		nearestBathroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String[] bathroomTypes = {"Female", "Male"};
				Object selectedValue = JOptionPane.showInputDialog(null, "Bathroom Type", "Select Gender",
						JOptionPane.INFORMATION_MESSAGE, bathroomIconBIG, bathroomTypes, bathroomTypes[0]);
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
					if(listPath.size() > 0)
						endNode = listPath.get(listPath.size() - 1);
					mapsForPaths = new LinkedList<Map>();
					for (int i = 0; i < listPath.size(); i++){
						for (int j = 0; j < maps.size(); j++){
							nodesInMap = maps.get(j).getNodes();
							for(int k = 0; k<nodesInMap.size(); k++){
								if((listPath.get(i) == nodesInMap.get(k)) && !nodesInMap.get(k).getType().equals(NodeType.ELEVATOR)){
									if(!mapsForPaths.contains(maps.get(j))){
										mapsForPaths.add(maps.get(j));
									}
								}
							}
						}
						//currentMapFile = mapsForPaths.getFirst().getImage();
						currentlyShownMap = mapsForPaths.getFirst();
						if(!currentlyShownMap.getMapName().equals("WPI Campus Map")){
							backToCampus.setEnabled(true);
						} else{
							backToCampus.setEnabled(false);
						}
						mapPanel.setImage(currentlyShownMap.getImage());
						totalMaps = mapsForPaths.size();
						mapPanel.setEndNode(endNode);
						if(mapsForPaths.size() > 1){
							arrowCounter = 0;
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
					revalidate();
					repaint();

				}
			}
		});

		bluetowerIcon = new ImageIcon("IconImages/blueTowerIcon.png");
		nearestBluetower = new JButton();
		nearestBluetower.setToolTipText("Find nearest Emergency Tower");
		nearestBluetower.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {}
			public void mouseMoved(MouseEvent arg0) {
				ttManager.setEnabled(true);
			}
		});
		nearestBluetower.setIcon(bluetowerIcon);
		nearestBluetower.setBounds(1064, 632+20, 40, 40);

		uiPanel.add(nearestBluetower);

		//Find Professor button
		findProf = new JButton();
		findProf.setToolTipText("Find Professor");
		findProf.setIcon(findProfIcon);

		findProf.setBounds(1100, 632, 40, 40);

		//uiPanel.add(findProf);


		
		findProf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						FindProf.createFindProf();
					}
				});
			}
		});
		nearestBluetower.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(listPath != null && startNode != null){
					listPath = pathCalc.nearestSpecialNode(startNode, NodeType.BLUETOWER);
					updatePath = true;
				}

				if(listPath.size() > 0)
					endNode = listPath.get(listPath.size() - 1);
				mapsForPaths = new LinkedList<Map>();
				for (int i = 0; i < listPath.size(); i++){
					for (int j = 0; j < maps.size(); j++){
						nodesInMap = maps.get(j).getNodes();
						for(int k = 0; k<nodesInMap.size(); k++){
							if((listPath.get(i) == nodesInMap.get(k)) && !nodesInMap.get(k).getType().equals(NodeType.ELEVATOR)){
								if(!mapsForPaths.contains(maps.get(j))){
									mapsForPaths.add(maps.get(j));
								}
							}
						}
					}
					//currentMapFile = mapsForPaths.getFirst().getImage();
					currentlyShownMap = mapsForPaths.getFirst();
					if(!currentlyShownMap.getMapName().equals("WPI Campus Map")){
						backToCampus.setEnabled(true);
					} else{
						backToCampus.setEnabled(false);
					}
					mapPanel.setImage(currentlyShownMap.getImage());
					totalMaps = mapsForPaths.size();
					mapPanel.setEndNode(endNode);
					if(mapsForPaths.size() > 1){
						arrowCounter = 0;
						rightArrow.setEnabled(true);
						mapNumber.setText(String.valueOf(1) + " of " + String.valueOf(totalMaps));
					}
				}
			}});

		//Construct buttons and add action listener
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int i;
				email.setEnabled(true);
				updatePath = true;
				uiPanel.setVisible(true);
				frame.setVisible(true);
				//pathCalc = new Djikstra();

				if(!startClicked && !endClicked){
					for (i = 0; i < currentStartNodes.size(); i++){
						if(startRoomSEL.getSelectedItem() == currentStartNodes.get(i).getName())
							startNode = currentStartNodes.get(i);
						mapPanel.setStartNode(startNode);
					}
					for(i = 0; i < currentEndNodes.size(); i++){
						if(endRoomSEL.getSelectedItem() == currentEndNodes.get(i).getName())
							endNode = currentEndNodes.get(i);
						mapPanel.setEndNode(endNode);
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
								if((listPath.get(i) == nodesInMap.get(k)) && !nodesInMap.get(k).getType().equals(NodeType.ELEVATOR)){
									if(!mapsForPaths.contains(maps.get(j))){
										mapsForPaths.add(maps.get(j));
									}
								}
							}
						}
						//currentMapFile = mapsForPaths.getFirst().getImage();
						currentlyShownMap = mapsForPaths.getFirst();
						if(!currentlyShownMap.getMapName().equals("WPI Campus Map")){
							backToCampus.setEnabled(true);
						} else{
							backToCampus.setEnabled(false);
						}
						mapPanel.setImage(currentlyShownMap.getImage());
						totalMaps = mapsForPaths.size();

						if(mapsForPaths.size() > 1){
							rightArrow.setEnabled(true);
							mapNumber.setText(String.valueOf(1) + " of " + String.valueOf(totalMaps));
						}
					}
					int k;
					GeneralPath path = new GeneralPath();
					path.moveTo(listPath.getFirst().getX(), listPath.getFirst().getY());
					for (k=1; k<listPath.size(); k++){
						if(listPath.get(k-1).getMapName().equals(currentlyShownMap.getMapName())
								&& listPath.get(k).getMapName().equals(currentlyShownMap.getMapName())){
							//						if(currentlyShownMap.getNodes().contains(listPath.get(k-1)) && 
							//								currentlyShownMap.getNodes().contains(listPath.get(k))){
							path.lineTo(listPath.get(k).getX(),listPath.get(k).getY());
							//path.transform(at);
							//g2d.draw(path);
						}
						else{
							//path.transform(at);
							if(!(startTransitionNodes.contains(listPath.get(k-1))) && !(endTransitionNodes.contains(listPath.get(k-1)))){
								System.out.println("Adding end");
								endTransitionNodes.add(listPath.get(k-1));
								startTransitionNodes.add(listPath.get(k));

							}

							path.moveTo(listPath.get(k).getX(), listPath.get(k).getY());

						}
					}
					mapPanel.setPath(path);
					endNode = listPath.get(listPath.size() - 1);
					mapPanel.setEndNode(endNode);
					if(mapsForPaths != null){
						if(mapsForPaths.get(arrowCounter).getNodes().contains(startNode)){
							mapPanel.setStartNode(startNode);
						}

						if(mapsForPaths.get(arrowCounter).getNodes().contains(endNode)){
							mapPanel.setEndNode(endNode);
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

										Djikstra.getSpeed(totalDistance) + "\n" + emailDirections);
					repaint();
					revalidate();
				}
			}
		});


		backToCampus.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int i = 0; i < maps.size(); i++){
					if(maps.get(i).getMapName().equals("WPI Campus Map")){
						currentlyShownMap = maps.get(i);
						mapPanel.setImage(currentlyShownMap.getImage());
						mapPanel.setPath(null);
						mapPanel.revalidate();
						mapPanel.repaint();
						backToCampus.setEnabled(false);
						historicalNodes = new LinkedList<Node>();
						for(int m = 0; m < currentlyShownMap.getNodes().size(); m++){
							if(currentlyShownMap.getNodes().get(m).getType() == NodeType.HISTORICAL){
								historicalNodes.add(currentlyShownMap.getNodes().get(m));
							}
						}


						if(mapsForPaths != null){
							for(int j = 0; j < mapsForPaths.size(); j++){
								if(mapsForPaths.get(j) == maps.get(i)){
									arrowCounter = j;
									if(arrowCounter == 0){
										leftArrow.setEnabled(false);
										rightArrow.setEnabled(true);
									} else if(arrowCounter == (mapsForPaths.size() - 1)){
										leftArrow.setEnabled(true);
										rightArrow.setEnabled(false);
									} else{
										rightArrow.setEnabled(true);
										leftArrow.setEnabled(true);
									}
									mapNumber.setText(String.valueOf(arrowCounter + 1) + " of " + String.valueOf(totalMaps));
								}
							}
							return;
						}
					}
				}
			}
		});

		leftArrow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(mapsForPaths!= null){
					arrowCounter -= 1;
					rightArrow.setEnabled(true);
					mapNumber.setText(String.valueOf(arrowCounter + 1) + " of " + String.valueOf(totalMaps));
					//currentMapFile = mapsForPaths.get(arrowCounter).getImage();
					currentlyShownMap = mapsForPaths.get(arrowCounter);
					if(!currentlyShownMap.getMapName().equals("WPI Campus Map")){
						backToCampus.setEnabled(true);
					} else{
						backToCampus.setEnabled(false);
					}
					mapPanel.setImage(currentlyShownMap.getImage());
					if (arrowCounter == 0)
						leftArrow.setEnabled(false);
				}
			}
		});
		rightArrow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(mapsForPaths != null){
					leftArrow.setEnabled(true);
					arrowCounter += 1;
					mapNumber.setText(String.valueOf(arrowCounter + 1) + " of " + String.valueOf(totalMaps));
					//currentMapFile = mapsForPaths.get(arrowCounter).getImage();
					currentlyShownMap = mapsForPaths.get(arrowCounter);
					if(!currentlyShownMap.getMapName().equals("WPI Campus Map")){
						backToCampus.setEnabled(true);
					} else{
						backToCampus.setEnabled(false);
					}
					mapPanel.setImage(currentlyShownMap.getImage());
					if (arrowCounter == totalMaps-1)
						rightArrow.setEnabled(false);
				}
			}
		});
		uiPanel.setVisible(true);
		frame.setVisible(true);
		clear();
		frame.revalidate();
		frame.repaint();
	}


	public class MyGraphics extends JComponent implements MouseMotionListener, MouseListener{

		private static final long serialVersionUID = 1L;
		private static final int SquareWidth = 5;
		private EndUserGUI gui;


		MyGraphics(EndUserGUI gui) {
			this.gui = gui;
			setPreferredSize(new Dimension(760, 666));
			addMouseMotionListener(this);
			addMouseListener(this);

			if(scrollMapPanel != null){
				scrollMapPanel.getViewport().addMouseMotionListener(scrollListener);
				scrollMapPanel.getViewport().addMouseListener(scrollListener);
			}
			addMouseListener(new MouseAdapter(){


				public void mouseClicked(MouseEvent evt) {

					int x = evt.getX();
					int y = evt.getY();

					if (evt.getClickCount() == 2) {
						for(int i = 0; i < maps.size(); i++){
							if(maps.get(i).getEasyLinks().size() > 0){
								for(int j = 0; j < maps.get(i).getEasyLinks().size(); j++){
									if(maps.get(i).getEasyLinks().get(j).getPoly().contains(x, y)){
										currentlyShownMap = maps.get(i).getEasyLinks().get(j).getMap();
										mapPanel.setImage(currentlyShownMap.getImage());
										mapPanel.setPath(null);
									}
								}
							}
						}
					} 
					if(!startClicked){
						startNode = findClosestNode(x,y);
						startClicked = true;
						mapPanel.setStartNode(startNode);
					}
					else if(!endClicked){
						endNode = findClosestNode(x,y);
						endClicked = true;
						mapPanel.setEndNode(endNode);
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
			//addMouseMotionListener(this);

			addMouseWheelListener(new MouseAdapter() {


				public void mouseWheelMoved(MouseWheelEvent e) {
					if(scrollMapPanel.contains(e.getX(), e.getY())){
						double delta = 0.03f * e.getWheelRotation();
						System.out.println(delta);
						System.out.println(mapPanel.scale);
						if((mapPanel.scale > 0.5 && mapPanel.scale < 1.5) || (mapPanel.scale < 0.5 && delta > 0) || (mapPanel.scale > 1.5 && delta < 0)){
							mapPanel.scale += delta;
							mapPanel.revalidate();
							mapPanel.repaint();
						}
					}
				}

			});

		}

		public EndUserGUI getGui(){
			return this.gui;
		}







		@Override
		public void paintComponent(Graphics g) {
			GeneralPath path = null;

			if(path == null && updatePath == true && listPath.size() > 0){
				removeAll();
				int k;
				path = new GeneralPath();
				path.moveTo(listPath.getFirst().getX(), listPath.getFirst().getY());
				for (k=1; k<listPath.size(); k++){
					if(listPath.get(k-1).getMapName().equals(currentlyShownMap.getMapName())
							&& listPath.get(k).getMapName().equals(currentlyShownMap.getMapName())){
						//					if(currentlyShownMap.getNodes().contains(listPath.get(k-1)) && 
						//							currentlyShownMap.getNodes().contains(listPath.get(k))){
						path.lineTo(listPath.get(k).getX(),listPath.get(k).getY());
						//path.transform(at);
						//g2d.draw(path);
					}
					else{
						//path.transform(at);
						path.moveTo(listPath.get(k).getX(), listPath.get(k).getY());
					}
				}
				mapPanel.setPath(path);
				endNode = listPath.get(listPath.size() - 1);
				mapPanel.setEndNode(endNode);
				if(mapsForPaths.size() > 0 && arrowCounter < mapsForPaths.size()){
					if(mapsForPaths.get(arrowCounter).getNodes().contains(startNode)){
						mapPanel.setStartNode(startNode);
					}

					if(mapsForPaths.get(arrowCounter).getNodes().contains(endNode)){
						mapPanel.setEndNode(endNode);
					}
				}
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
			int x = e.getX();
			int y = e.getY();
			if(scrollMapPanel.contains(x, y)){
				scrollListener.mouseDragged(e);
			}

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
				System.out.println(tt);
				setToolTipText(tt);
				ttManager.setEnabled(true);
			} else{
				if(ttManager.isEnabled()){
					ttManager.setEnabled(false);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(!mousePressedFlag){
			System.out.println("Lol what?");
			uiPanel.setCursor(new Cursor (Cursor.HAND_CURSOR));
			int x = e.getX();
			int y = e.getY();
			System.out.println(x);
			System.out.println(y);
			mousePressedFlag = true;
			} else{
				mousePressedFlag = false;
			}
			if(scrollMapPanel.contains(e.getX(), e.getY())){
//				uiPanel.setCursor(new Cursor (Cursor.HAND_CURSOR));
				scrollListener.mousePressed(e);
			}

		}

		@Override
		public void mouseReleased(MouseEvent e){
			uiPanel.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
			int x = e.getX();
			int y = e.getY();
			if(mapPanel.contains(x, y)){
				scrollListener.mouseReleased(e);
			}

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}


	}

	@Override
	public void actionPerformed(ActionEvent e) {

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