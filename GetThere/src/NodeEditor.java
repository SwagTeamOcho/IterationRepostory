// NodeEditor.java
// created by Joe Kaiser 12/5/2016

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NodeEditor{
	//private String typeString;
	private String typeSel;
	private int xCord;
	private int yCord;
	private int typeIndex;
	private String name;
	private DevGUI devGui;
	private Node node;
	private JPanel uiPanel;
	private JLabel nameLabel;
	private JTextField nameText;
	private JLabel xLabel;
	private JTextField xText;
	private JLabel yLabel;
	private JTextField yText;
	private JLabel typeLabel;
	private JComboBox<String> typeBox;
	private JButton alignButton;
	private JButton saveButton;
	private JButton cancelButton;
	private JButton backButton;
	private JButton xAlignButton;
	private JButton yAlignButton;
	boolean i = false;
	boolean i2 = false;

	String[] types = {"No Type", "Men's Bathroom", "Women's Bathroom", "Blue Tower", "Food", "Lecture Hall", "Office",
			"Room", "Historical"};

	public NodeEditor(DevGUI gui){
		this.devGui = gui;
		this.uiPanel = gui.getuiPanel();
	}

	public void initialize(Node node){
		i = true;
		this.node = node;
		xCord = node.getX();
		yCord = node.getY();
		name = node.getName();
		switch(node.getType()){
		case NOTYPE:
			typeIndex = 0;
			break;
		case MBATHROOM:
			typeIndex = 1;
			break;
		case FBATHROOM:
			typeIndex = 2;
			break;
		case BLUETOWER:
			typeIndex = 3;
			break;
		case FOOD:
			typeIndex = 4;
			break;
		case LECTUREHALL:
			typeIndex = 5;
			break;
		case OFFICE:
			typeIndex = 6;
			break;
		case ROOM:
			typeIndex = 7;
			break;
		case HISTORICAL: 
			typeIndex = 8;
			break;
		default:
			break;
		}

		placeComponents();
	}



	private void placeComponents() {

		uiPanel.setLayout(null);

		nameLabel = new JLabel("Node Name:");
		nameLabel.setBounds(762, 256, 80, 25);
		uiPanel.add(nameLabel);

		xLabel = new JLabel("X Coordinate");
		xLabel.setBounds(762, 301, 132, 25);
		uiPanel.add(xLabel);

		yLabel = new JLabel("Y Coordinate");
		yLabel.setBounds(762, 346, 80, 25);
		uiPanel.add(yLabel);

		nameText = new JTextField(name, 20);
		nameText.setBounds(762, 276, 132, 25);
		uiPanel.add(nameText);

		xText = new JTextField(Integer.toString(xCord), 20);
		xText.setBounds(762, 321, 132, 25);
		uiPanel.add(xText);

		yText = new JTextField(Integer.toString(yCord), 20);
		yText.setBounds(762, 366, 132, 25);
		uiPanel.add(yText);

		typeLabel = new JLabel("Node Type");
		typeLabel.setBounds(762, 391, 80, 25);

		typeBox = new JComboBox<String>(types);
		typeBox.setSelectedIndex(typeIndex);
		typeBox.setBounds(762, 411, 132, 25);

		alignButton = new JButton("Align Node");
		alignButton.setBounds(762, 486, 132, 29);
		uiPanel.add(alignButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(762, 516, 132, 29);
		uiPanel.add(cancelButton);

		saveButton = new JButton("Save Node");
		saveButton.setBounds(762, 546, 132, 29);
		uiPanel.add(saveButton);

		xAlignButton = new JButton("X Position");

		yAlignButton = new JButton("Y Position");

		backButton = new  JButton("Back");


		if(!node.isPortal()){
			uiPanel.add(typeLabel);
			uiPanel.add(typeBox);
		}

		uiPanel.repaint();
		uiPanel.revalidate();

		alignButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				i2 = true;
				nameLabel.setVisible(false);
				nameText.setVisible(false);
				xLabel.setVisible(false);
				xText.setVisible(false);
				yLabel.setVisible(false);
				yText.setVisible(false);
				typeLabel.setVisible(false);
				typeBox.setVisible(false);
				cancelButton.setVisible(false);
				saveButton.setVisible(false);
				alignButton.setVisible(false);

				xAlignButton.setBounds(762, 301, 132, 29);
				uiPanel.add(xAlignButton);

				yAlignButton.setBounds(762, 346, 132, 29);
				uiPanel.add(yAlignButton);

				backButton.setBounds(762, 516, 132, 29);
				uiPanel.add(backButton);
			}
		});


		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(typeSel != null){
					switch(typeSel){
					case "No Type":
						node.setType(NodeType.NOTYPE);
						break;
					case "Men's Bathroom":
						node.setType(NodeType.MBATHROOM);
						break;
					case "Women's Bathroom":
						node.setType(NodeType.FBATHROOM);
						break;
					case "Blue Tower":
						node.setType(NodeType.BLUETOWER);
						break;
					case "Elevator":
						node.setType(NodeType.ELEVATOR);
						break;
					case "Stairs":
						node.setType(NodeType.STAIRS);
						break;
					case "Food":
						node.setType(NodeType.FOOD);
						break;
					case "Emergency Exit":
						node.setType(NodeType.EMERGEXIT);
						break;
					case "Lecture Hall":
						node.setType(NodeType.LECTUREHALL);
						break;
					case "Office":
						node.setType(NodeType.OFFICE);
						break;
					case "Door":
						node.setType(NodeType.DOOR);
						break;
					case "Room":
						node.setType(NodeType.ROOM);
						break;
					case "Historical":
						node.setType(NodeType.HISTORICAL);
					default:
						node.setType(NodeType.NOTYPE);
					}
				}
				xCord = Integer.parseInt(xText.getText());
				yCord = Integer.parseInt(yText.getText());
				name = nameText.getText();

				node.setX(xCord);
				node.setY(yCord);
				node.setName(name);
				uiPanel.repaint();
				uiPanel.revalidate();
				uiPanel.remove(nameLabel);
				uiPanel.remove(nameText);
				uiPanel.remove(xLabel);
				uiPanel.remove(xText);
				uiPanel.remove(yLabel);
				uiPanel.remove(yText);
				uiPanel.remove(typeLabel);
				uiPanel.remove(typeBox);
				uiPanel.remove(alignButton);
				uiPanel.remove(saveButton);
				uiPanel.remove(cancelButton);

				return;
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uiPanel.remove(nameLabel);
				uiPanel.remove(nameText);
				uiPanel.remove(xLabel);
				uiPanel.remove(xText);
				uiPanel.remove(yLabel);
				uiPanel.remove(yText);
				uiPanel.remove(typeLabel);
				uiPanel.remove(typeBox);
				uiPanel.remove(alignButton);
				uiPanel.remove(saveButton);
				uiPanel.remove(cancelButton);
				uiPanel.repaint();
				uiPanel.revalidate();
				return;
			}	
		});

		xAlignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				devGui.setCurrentNode(node);
				devGui.editNodes = false;
				devGui.alignNodesX = true;
			}
		});

		yAlignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				devGui.setCurrentNode(node);
				devGui.editNodes = false;
				devGui.alignNodesY = true;
			}
		});

		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				xCord = node.getX();
				yCord = node.getY();
				uiPanel.remove(xText);
				uiPanel.remove(yText);
				xText = new JTextField(Integer.toString(xCord), 20);
				xText.setBounds(762, 321, 132, 25);
				uiPanel.add(xText);
				yText = new JTextField(Integer.toString(yCord), 20);
				yText.setBounds(762, 366, 132, 25);
				uiPanel.add(yText);
				nameLabel.setVisible(true);
				nameText.setVisible(true);
				xLabel.setVisible(true);
				yLabel.setVisible(true);
				typeLabel.setVisible(true);
				typeBox.setVisible(true);
				cancelButton.setVisible(true);
				saveButton.setVisible(true);
				uiPanel.remove(xAlignButton);
				uiPanel.remove(yAlignButton);
				uiPanel.remove(backButton);
			}
		});

		typeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				typeSel = (String) typeBox.getSelectedItem();
			}
		});
	}
	public void clear(){
		uiPanel.remove(nameLabel);
		uiPanel.remove(nameText);
		uiPanel.remove(xLabel);
		uiPanel.remove(xText);
		uiPanel.remove(yLabel);
		uiPanel.remove(yText);
		uiPanel.remove(typeLabel);
		uiPanel.remove(typeBox);
		uiPanel.remove(cancelButton);
		uiPanel.remove(saveButton);
		uiPanel.remove(alignButton);
		if(i2){
			uiPanel.remove(backButton);
			uiPanel.remove(xAlignButton);
			uiPanel.remove(yAlignButton);
		}
	}
}
