import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class EasyLink implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4160348487406450453L;
	private Polygon poly;
	private Map m;
	private ImageIcon streetViewImage;
	
	EasyLink(){
		
	}
	EasyLink(Polygon p, Map m){
		this.poly = p;
		this.m = m;
	}
	
	public void setStreetViewImage(String path){
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		this.streetViewImage = new ImageIcon(img, "History");
	}

	public ImageIcon getStreetViewImage(){
		return streetViewImage;
	}
	
	public Polygon getPoly(){
		return this.poly;
	}
	public Map getMap(){
		return this.m;
	}
}
