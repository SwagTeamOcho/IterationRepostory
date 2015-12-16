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

    public HandScrollListener(ImagePanel mapPanel)
    {
        this.image = mapPanel;
    }

    public void mouseDragged(MouseEvent e)
    {
    	JViewport vport;
    	if(e.getSource().getClass().getSimpleName().equals("MyGraphics")){
    	EndUserGUI.MyGraphics mygraph = (EndUserGUI.MyGraphics) e.getSource();
        vport = (JViewport) mygraph.getGui().getScrollMapPanel().getViewport();
    	}
    	
    	else{
    	vport = (JViewport) e.getSource();
    	}
        Point cp = e.getPoint();
        Point vp = vport.getViewPosition();
        
        vp.translate(pp.x-cp.x, pp.y-cp.y);
        image.scrollRectToVisible(new Rectangle(vp, vport.getSize()));
        pp.setLocation(cp);
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