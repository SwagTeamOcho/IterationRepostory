
<<<<<<< HEAD
import java.awt.Color;
=======

>>>>>>> bb844f6236b9b4d1957a3753d7e13af55b6f99bd
import java.awt.EventQueue;



public class EndUserMain {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			EndUserGUI window = new EndUserGUI(); 
			public void run() {
				try {
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}