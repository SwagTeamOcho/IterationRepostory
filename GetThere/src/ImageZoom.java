package src;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


class ImageZoom
{
    ImagePanel imagePanel;
    
    
    
    public ImageZoom(ImagePanel ip)
    {
    	
        imagePanel = ip;
    }
  public JSpinner getZoomingSpinner(){
	  SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.8, 1.4, .01);
      final JSpinner spinner = new JSpinner(model);
      spinner.setPreferredSize(new Dimension(45, spinner.getPreferredSize().height));
      
      spinner.addChangeListener(new ChangeListener()
      {
          public void stateChanged(ChangeEvent e)
          {
              float scale = ((Double)spinner.getValue()).floatValue();
              imagePanel.setScale(scale);
          }
      });
      spinner.setBounds(720, 632, 45, spinner.getPreferredSize().height);
      return spinner;
  }
  
  public JButton getZoomInButton(){
  	ImageIcon zoomInIcon = new ImageIcon("IconImages/zoomInIcon.png");
  	JButton zoomIn = new JButton();
  	zoomIn.setToolTipText("Zoom in");
  	zoomIn.setIcon(zoomInIcon);
  	zoomIn.setBounds(620, 632, 40, 40);
  	zoomIn.addActionListener(new ActionListener(){
  		
		@Override
		public void actionPerformed(ActionEvent e) {
			Double scale = imagePanel.getScale();
			if (scale <= 1.8){
			scale += 0.1;
			}
			imagePanel.setScale(scale);
			getZoomingSpinner().setValue(scale);
			
		}
  		
  	});
  	return zoomIn;
  }
  
  public JButton getZoomOutButton(){
	  	ImageIcon zoomOutIcon = new ImageIcon("IconImages/zoomOutIcon.png");
	  	JButton zoomOut = new JButton();
	  	zoomOut.setToolTipText("Zoom out");
	  	zoomOut.setIcon(zoomOutIcon);
	  	zoomOut.setBounds(575, 632, 40, 40);
	  	zoomOut.addActionListener(new ActionListener(){
	  		
			@Override
			public void actionPerformed(ActionEvent e) {
				Double scale = imagePanel.getScale();
				if (scale >= 0.7){
					scale -= 0.1;
					}
				imagePanel.setScale(scale);
				getZoomingSpinner().setValue(scale);
				
			}
	  		
	  	});
	  	return zoomOut;
	  }
//    public JPanel getUIPanel()
//    {

//    	
//        SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.8, 1.4, .01);
//        final JSpinner spinner = new JSpinner(model);
//        spinner.setPreferredSize(new Dimension(45, spinner.getPreferredSize().height));
//        
//        spinner.addChangeListener(new ChangeListener()
//        {
//            public void stateChanged(ChangeEvent e)
//            {
//                float scale = ((Double)spinner.getValue()).floatValue();
//                imagePanel.setScale(scale);
//            }
//        });
//        
//        
//        JPanel panel = new JPanel();
//        panel.setBounds(720, 625, 100, 55);
//        panel.add(new JLabel("scale"));
//        panel.add(spinner);
//        panel.add(zoomIn);
//        return panel;
//    }
}