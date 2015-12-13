import java.awt.Polygon;
import java.io.Serializable;

public class EasyLink implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4160348487406450453L;
	private Polygon poly;
	private Map m;
	
	EasyLink(){
		
	}
	EasyLink(Polygon p, Map m){
		this.poly = p;
		this.m = m;
	}
	
	public Polygon getPoly(){
		return this.poly;
	}
	public Map getMap(){
		return this.m;
	}
}
