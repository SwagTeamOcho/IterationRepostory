import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;


public class RunUserEnd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		//String programLoc = (new File("")).getAbsolutePath()+"/DevGUI.jar";
		String programName = "EndUserMain.jar";
		String programPath = (new File("")).getAbsolutePath() + "/";
	//	String programPath = "/Users/BillySullivan/Desktop/UserGUI";
		String programLoc = programPath + programName;
		
		if(new File(programLoc).canRead()){
			
			System.out.println("File Read!");
		
		
		 try {  
             // re-launch the app itselft with VM option passed  
			 
			 
			 Runtime runtime = Runtime.getRuntime();
			 

		 
			 String [] cmd = {"java", 
	                 "-Xss4m", 
	                 "-jar",
	                 programLoc};
			 
			 
		//	
			 
			runtime.exec( cmd );
			 
		//	 java.lang.Process process = Runtime.getRuntime().exec("cd "+ "/Users/BillySullivan/Desktop/UserGUI/" + "&& java -jar -Xss4m DevGUI.jar");
             //Runtime.getRuntime().exec(new String[] {"java", "-jar","-Xss4m", "UserEnd.java"});  
         } catch (IOException ioe) { 
        	// JOptionPane.showMessageDialog(new Frame(), "File not found: "+(new File("")).getAbsolutePath()+"/DevGUI.jar");
             ioe.printStackTrace();  
         }
		}
		 else
		 {
			 JOptionPane.showMessageDialog(new Frame(), "The file: "+ programName + "\nCould not be located in: " + programPath, "File Location Error", JOptionPane.ERROR_MESSAGE);
			 System.exit(0);
		 }
		 
		 

	}

}
