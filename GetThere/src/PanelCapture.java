import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelCapture {
	 
	private JPanel panel;
	
	public PanelCapture(JPanel panel) {
		this.panel = panel;
		saveImage();
		
	}

	public BufferedImage saveImage() {
	    BufferedImage img = new BufferedImage(this.panel.getWidth()/2, this.panel.getHeight()/2, BufferedImage.TYPE_INT_RGB);
	    this.panel.paint(img.getGraphics());
	    try {
	        ImageIO.write(img, "png", new File("Screen.png"));
	        System.out.println("panel saved as image");

	    } catch (Exception e) {
	        System.out.println("panel not saved" + e.getMessage());
	    }
	    return img;
	}
}
