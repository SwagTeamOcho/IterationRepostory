package src;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
import javax.swing.ImageIcon;

public class Map implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8156376045776386768L;
	private ImageIcon mapImage;
	private LinkedList<Node> nodes;
	private LinkedList<Edge> edges;
	private String mapName;
	private double scale;
	
	public Map(String mapPath, String mapName, double s){
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(mapPath));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		this.mapImage = new ImageIcon(img, "map");
		this.mapName = mapName;
		nodes = new LinkedList<Node>();
		edges = new LinkedList<Edge>();
		this.scale = s;
	} 
		
	public Map(BufferedImage img, String mapName) {
		if (img == null) {
			throw new RuntimeException("No Image found");
		}
		this.mapImage = new ImageIcon(img, "map");
		this.mapName = mapName;
		nodes = new LinkedList<Node>();
		edges = new LinkedList<Edge>();
	} 
	
	public ImageIcon getImage(){
		return this.mapImage;
	}
	
	//public void setImage(Image mapImage){
	//	this.mapImage = mapImage;
	//}
	
	public LinkedList<Node> getNodes(){
		return this.nodes;
	}
	
	public LinkedList<Edge> getEdges(){
		return this.edges;
	}
	
	public String getMapName(){
		return this.mapName;
	}
	
	public void setMapName(String mapName){
		this.mapName = mapName;
	}
	
	public void addNode(Node node){
//		node.setName(mapName + "." + node.getName());
		this.nodes.add(node);
	}
	
	public void addEdge(Edge edge){
		this.edges.add(edge);
	}
	
	public double getScale(){
		return this.scale;
	}
	
	public void deleteNode(Node node){
		for(int i = 0; i < nodes.size(); i++){
			if(node.equals(nodes.get(i)))
				nodes.remove(i);
		}
		for(int j = 0; j < node.getEdgesList().size(); j++){
			Edge tempEdge = node.getEdgesList().get(j);
			this.deleteEdge(tempEdge);
		}
	}
	
	public void deleteEdge(Edge edge){
		for(int i = 0; i < edges.size(); i++){
			if(edge.equals(edges.get(i)))
				edges.remove(i);
		}
	}

    @Override
    public String toString() {
    return this.mapName;
 }
}