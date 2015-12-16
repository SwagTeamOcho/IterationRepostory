import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JViewport;

public class HandScrollListener extends MouseAdapter
{
	private final Cursor defCursor = new Cursor (Cursor.DEFAULT_CURSOR);
	private final Cursor hndCursor = new Cursor(Cursor.HAND_CURSOR);
	private final Point pp = new Point();
	private ImagePanel image;
	private Point cp;
	private int counterX = 0;
	private int counterY = 0;
	private boolean moveMouseFlag = false;

	public void setCounterX(int x){
		counterX = x;
	}
	
	public int getCounterX(){
		return this.counterX;
	}
	
	public HandScrollListener(ImagePanel mapPanel)
	{
		this.image = mapPanel;
	}

	public void moveMouse(MouseEvent e){
		moveMouseFlag = true;
		mouseMoved(e);
	}

	public void mouseMoved(MouseEvent e){
		MouseEvent evt;
		if(!moveMouseFlag){
			counterX = e.getX();
		}
		if(moveMouseFlag){
			moveMouseFlag = false;
			evt = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers(), e.getX() + counterX, e.getY(), e.getClickCount(), false);
			image.getGUI().moveMouse(evt);
		} else{
			image.getGUI().moveMouse(e);
		}
	}

	public void mouseDragged(MouseEvent e)
	{
		JViewport vport;
		if(e.getSource().getClass().getSimpleName().equals("MyGraphics")){
			EndUserGUI.MyGraphics mygraph = (EndUserGUI.MyGraphics) e.getSource();
			vport = (JViewport) mygraph.getGui().getScrollMapPanel().getViewport();
			//mygraph.mousePressed(e);
		}

		else{
			return;
			//vport = (JViewport) e.getSource();
		}
		System.out.println(" X ::: " + vport.getViewPosition().getX() + "Y::: " + vport.getViewPosition().getY());
		cp = e.getPoint();
		Point vp = vport.getViewPosition();
		System.out.println("BEFORE : X = " + vp.x + "Y = " + vp.y);

		vp.translate(pp.x-cp.x, pp.y-cp.y);
		System.out.println("AFTER : X = " + vp.x + "Y = " + vp.y);
		image.scrollRectToVisible(new Rectangle(vp, vport.getSize()));
		//pp.setLocation(cp);
	}

	public void mousePressed(MouseEvent e)
	{
		image.setCursor(hndCursor);
		pp.setLocation(e.getPoint());

	}

	public void mouseReleased(MouseEvent e)
	{
		image.setCursor(defCursor);
		image.repaint();
	}
}