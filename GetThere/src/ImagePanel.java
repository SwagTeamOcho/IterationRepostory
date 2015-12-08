package src;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImagePanel extends JPanel 

{
	private static final int CircleDiam = 10;
	BufferedImage image;
	double scale;
	GeneralPath path;
	Node startNode;
	Node endNode;

	public ImagePanel()
	{
		scale = 1.0;
		setBackground(new Color(74, 1, 1));
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		int w = getWidth();
		int h = getHeight();
		if(image != null){
			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();
			double x = (w - scale * imageWidth)/2;
			double y = (h - scale * imageHeight)/2;
			AffineTransform at = AffineTransform.getTranslateInstance(x,y);
			at.scale(scale, scale);
			g2.drawRenderedImage(image, at);


			if(path != null){
				path.transform(at);
				g2.setColor(Color.BLACK);
				g2.draw(path);
				g2.setStroke(new BasicStroke(2));
				g2.draw(path);
				g2.setColor(Color.BLUE);
				g2.draw(path);
			}

			if(startNode != null){
				Point2D before1 = new Point(), after1 = new Point(), before2 = new Point(), after2 = new Point();

				before1.setLocation(startNode.getX()-(CircleDiam+3)/2, startNode.getY()-(CircleDiam+3)/2);
				before2.setLocation(startNode.getX()-CircleDiam/2, startNode.getY()-CircleDiam/2);
				at.transform(before1, after1);
				at.transform(before2, after2);
				g.setColor(Color.BLACK);
				g.fillOval((int)after1.getX(), (int)after1.getY(), CircleDiam+3, CircleDiam+3);
				g.setColor(Color.GREEN);
				g.fillOval((int)after2.getX(), (int)after2.getY(), CircleDiam, CircleDiam);
			}

			if(endNode != null){
				Point2D before1 = new Point(), after1 = new Point(), before2 = new Point(), after2 = new Point();

				before1.setLocation(endNode.getX()-(CircleDiam+3)/2, endNode.getY()-(CircleDiam+3)/2);
				before2.setLocation(endNode.getX()-CircleDiam/2, endNode.getY()-CircleDiam/2);
				at.transform(before1, after1);
				at.transform(before2, after2);
				g.setColor(Color.BLACK);
				g.fillOval((int)after1.getX(), (int)after1.getY(), CircleDiam+3, CircleDiam+3);
				g.setColor(Color.RED);
				g.fillOval((int)after2.getX(), (int)after2.getY(), CircleDiam, CircleDiam);
			}


		}
	}

	public BufferedImage getImage(){
		return this.image;
	}

	/**
	 * For the scroll pane.
	 */
	 public Dimension getPreferredSize()
	{	int w, h;
	if(image != null){
		w = (int)(scale * image.getWidth());
		h = (int)(scale * image.getHeight());
	}
	else{
		w = 0;
		h = 0;
	}
	return new Dimension(w, h);
	}


	public void setScale(double s)
	{
		scale = s;
		revalidate();      // update the scroll pane
		repaint();
	}


	public double getScale(){
		return this.scale;
	}

	public void setImage(ImageIcon img){
		Image im = img.getImage();
		BufferedImage buffered = convertToBufferedImage(im);
		this.image = buffered;
		revalidate();
		repaint();
	}

	public void setPath(GeneralPath path){
		this.path = path;
		revalidate();
		repaint();
	}

	public void setStartNode(Node startNode){
		this.startNode = startNode;
		revalidate();
		repaint();
	}

	public void setEndNode(Node endNode){
		this.endNode = endNode;
		revalidate();
		repaint();
	}

	public static BufferedImage convertToBufferedImage(Image image)
	{
		BufferedImage newImage = new BufferedImage(
				image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}

} 