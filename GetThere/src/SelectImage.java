import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SelectImage extends JFrame {

	private static final long serialVersionUID = -610827094799028793L;
	private String filePath;
	private static SelectImage instance = new SelectImage();
	
	public SelectImage(){
		super("Import image");
	}
	
	public static SelectImage getSelectImage(){
		return instance;
	}

	public void getImage(final Node node) {

		setSize(500, 75);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		JButton openButton = new JButton("Open");
		JButton importButton = new JButton("Import");


		// Create a file chooser that opens up as an Open dialog
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();

				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");

				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileHidingEnabled(true);
				chooser.setMultiSelectionEnabled(false);
				int option = chooser.showOpenDialog(SelectImage.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					File sf = chooser.getSelectedFile();
					filePath = sf.toString();

				}
			}
		});


		importButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent ae) {

				if(filePath != null)
					node.setHistoricalImage(filePath);
				//	Map newMap = new Map(filePath, mapName.getText(), formatedText);
				//DevGUI.maps.add(newMap);
				System.out.println("TEST");  
				setVisible(false);

			}});


		c.add(openButton);
		c.add(importButton);


	}
	
	public void getImage(final EasyLink easyLink) {

		setSize(500, 75);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		JButton openButton = new JButton("Open");
		JButton importButton = new JButton("Import");


		// Create a file chooser that opens up as an Open dialog
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();

				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");

				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileHidingEnabled(true);
				chooser.setMultiSelectionEnabled(false);
				int option = chooser.showOpenDialog(SelectImage.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					File sf = chooser.getSelectedFile();
					filePath = sf.toString();

				}
			}
		});


		importButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent ae) {

				if(filePath != null)
					easyLink.setStreetViewImage(filePath);
				//	Map newMap = new Map(filePath, mapName.getText(), formatedText);
				//DevGUI.maps.add(newMap);
				System.out.println("TEST");  
				setVisible(false);

			}});


		c.add(openButton);
		c.add(importButton);


	}

}
