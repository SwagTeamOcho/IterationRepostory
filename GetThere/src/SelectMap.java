// SelectMap.java
// A simple file chooser to see what it takes to make one of these work.
//
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class SelectMap extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -610827094799028793L;
	private String filePath;
	
   public SelectMap(final DevGUI gui) {
	   
    super("Import map");
    
    setSize(500, 75);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    Container c = getContentPane();
    c.setLayout(new FlowLayout());
    
    JButton openButton = new JButton("Open");
    JButton importButton = new JButton("Import");
    final JTextField mapName = new JTextField("Map name", 13);
    final JTextField scale = new JTextField("Scale", 6);
    

    // Create a file chooser that opens up as an Open dialog
    openButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        int option = chooser.showOpenDialog(SelectMap.this);
        if (option == JFileChooser.APPROVE_OPTION) {
          File sf = chooser.getSelectedFile();
          filePath = sf.toString();
         
        }
      }
    });

    
    importButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        
        try {
          double formatedText = Double.parseDouble(scale.getText()) ;
          Map newMap = new Map(filePath, mapName.getText(), formatedText);
          DevGUI.maps.add(newMap);
          System.out.println("TEST");  
            
           } catch (Exception z) { 

         scale.setText("");
         return;
      }
        
        
            
      }
    });


    c.add(openButton);
    c.add(mapName);
    c.add(scale);
    c.add(importButton);
    

  }

  
}