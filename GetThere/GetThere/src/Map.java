import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Map implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8156376045776386768L;
	private transient BufferedImage mapImage;
	private LinkedList<Node> nodes;
	private LinkedList<Edge> edges;
	private String mapName;
	private double scale;
	
	public Map(String mapPath, String mapName, double s){
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(mapPath));
		} catch (IOException e) {}
		this.mapImage = img;
		this.mapName = mapName;
		nodes = new LinkedList<Node>();
		edges = new LinkedList<Edge>();
		this.scale = s;
	} 
		
	public Map(BufferedImage img, String mapName){
		this.mapImage = img;
		this.mapName = mapName;
		nodes = new LinkedList<Node>();
		edges = new LinkedList<Edge>();
	} 
	
	private void writeObject(ObjectOutputStream out)throws IOException{
        out.defaultWriteObject();
        //write buff with imageIO to out
        ImageIO.write(mapImage, "png", out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        //read buff with imageIO from in
        mapImage = ImageIO.read(in);
    }
	
	public BufferedImage getImage(){
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
}