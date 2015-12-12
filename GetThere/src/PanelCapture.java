import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelCapture {
	 
	private JPanel panel;
	private int instance;
	
	public PanelCapture(JPanel panel, int instance) {
		this.panel = panel;
		this.instance = instance;
		saveImage();
		
	}

	public BufferedImage saveImage() {
	    BufferedImage img = new BufferedImage(this.panel.getWidth(), this.panel.getHeight(), BufferedImage.TYPE_INT_RGB);
	    this.panel.paint(img.getGraphics());
	    try {
	        ImageIO.write(img, "png", new File("Screen"+this.instance+".png"));
	        System.out.println("panel saved as image");

	    } catch (Exception e) {
	        System.out.println("panel not saved" + e.getMessage());
	    }
	    return img;
	}
}
