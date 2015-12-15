import java.awt.*;

import com.thehowtotutorial.splashscreen.JSplash;

public class LoadingScreen {
	private static Color burgandy = new Color(74, 1, 1);
	private static Color beige = new Color(230, 224, 200);
	
	
	public static void main(String[] args){
		try{
			JSplash loadingScreen = new JSplash(LoadingScreen.class.getResource("loadingScreen.png"), true, true, false, null, 
					null, beige, burgandy);
			loadingScreen.splashOn();
			loadingScreen.setProgress(30);
			Thread.sleep(1000);
			loadingScreen.setProgress(60);
			Thread.sleep(1000);
			loadingScreen.setProgress(100);
			Thread.sleep(1000);
			loadingScreen.splashOff();
//			if(!loadingScreen.isEnabled()){
//				newGUI.setVisible(true);
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
