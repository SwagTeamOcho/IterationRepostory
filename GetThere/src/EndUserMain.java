package src;

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
