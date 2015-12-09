// NodeEditor.java
// created by Joe Kaiser 12/5/2016

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NodeEditor{
private String typeString;
private String typeSel;
private int xCord;
private int yCord;
private int typeIndex;
private String name;
JPanel uiPanel;

String[] types = {"No Type", "Men's Bathroom", "Women's Bathroom", "Blue Tower", "Elevator", 
		"Stairs", "Food", "Emergency Exit", "Lecture Hall", "Office", "Door",
		"Room", "Historical"};
private Node node;

	public NodeEditor(JPanel panel, Node node){
		this.uiPanel = panel;
		xCord = node.getX();
		yCord = node.getY();
		this.node = node;

		
		
		typeString = "";
		switch(node.getType()){
		case NOTYPE:
			typeString = "No Type";
			typeIndex = 0;
			break;
		case MBATHROOM:
			typeString = " Men's Bathroom";
			typeIndex = 1;
			break;
		case FBATHROOM:
			typeString = "Women's Bathroom";
			typeIndex = 2;
			break;
		case BLUETOWER:
			typeString = "Blue Tower";
			typeIndex = 3;
			break;
		case FOOD:
			typeString = "Food";
			typeIndex = 4;
			break;
		case LECTUREHALL:
			typeString = "Lecture Hall";
			typeIndex = 5;
			break;
		case OFFICE:
			typeString = "Office";
			typeIndex = 6;
			break;
		case ROOM:
			typeString = "Room";
			typeIndex = 7;
			break;
		case BATHROOM:
			typeString = "Bathroom";
			typeIndex = 8;
			break;
		case DOOR:
			typeString = "Door";
			typeIndex = 9;
			break;
		case ELEVATOR:
			typeString = "Elevator";
			typeIndex = 10;
			break;
		case EMERGEXIT:
			typeString = "Emergency Exit";
			typeIndex = 11;
			break;
		case STAIRS:
			typeString = "Stairs";
			typeIndex = 12;
			break;
		default:
			break;
		}

		placeComponents();
	}
	

	private void placeComponents() {
		
		uiPanel.setLayout(null);

		JLabel nameLabel = new JLabel("Node Name:");
		nameLabel.setBounds(762, 286, 80, 25);
		uiPanel.add(nameLabel);
		System.out.println("added nameLabel");
		
		JLabel xLabel = new JLabel("X Coordinate");
		xLabel.setBounds(762, 331, 132, 25);
		uiPanel.add(xLabel);
		
		JLabel yLabel = new JLabel("Y Coordinate");
		yLabel.setBounds(762, 376, 80, 25);
		uiPanel.add(yLabel);
		
		JTextField nameText = new JTextField(name, 20);
		nameText.setBounds(762, 306, 132, 25);
		uiPanel.add(nameText);

		JTextField xText = new JTextField(Integer.toString(xCord), 20);
		xText.setBounds(762, 351, 132, 25);
		uiPanel.add(xText);
		
		JTextField yText = new JTextField(Integer.toString(yCord), 20);
		yText.setBounds(762, 396, 132, 25);
		uiPanel.add(yText);
		
		JLabel typeLabel = new JLabel("Node Type");
		typeLabel.setBounds(762, 421, 80, 25);
		uiPanel.add(typeLabel);
		
		JComboBox<String> typeBox = new JComboBox<String>(types);
		typeBox.setSelectedIndex(typeIndex);
		typeBox.setBounds(762, 441, 132, 25);
		uiPanel.add(typeBox);

		JButton saveButton = new JButton("Save Node");
		saveButton.setBounds(762, 526, 132, 29);
		uiPanel.add(saveButton);
		
		uiPanel.repaint();
		uiPanel.revalidate();
	
	
	saveButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae) {
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
			uiPanel.remove(saveButton);
			return;
		}
	});
	
	typeBox.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>)e.getSource();
        String typeSel = (String)cb.getSelectedItem();
		}
	});
	}
}
