
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Container;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EMailDialogue extends JDialog{
	
	static final long serialVersionUID = 102910;

    JTextField addressField = new JTextField(20);
    
    JLabel addressLabel = new JLabel("Enter your email below");
    	
    JButton sendButton = new JButton("Send");
    
    String message;
    
    String[] attachments;
    
    public EMailDialogue(JFrame parent, String message, String[] attachments) {
    	super(parent);
    	
    	this.message = message;
    	this.attachments = attachments;
    	
    	setup();
    }
    	
    private void setup() {
    	sendButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			sendEmailAction(e);
    			dispose();
    		}
    	});
    	
    	createLayout(addressLabel,addressField,sendButton);
    	
    	setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle("Email Directions");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
    }
    
    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createParallelGroup(CENTER)
                .addComponent(arg[0])
                .addComponent(arg[1])
                .addComponent(arg[2])
                .addGap(200)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(30)
                .addComponent(arg[0])
                .addGap(20)
                .addComponent(arg[1])
                .addGap(20)
                .addComponent(arg[2])
                .addGap(30)
        );

        pack();
    }
    
    private void sendEmailAction(ActionEvent e) {
    	try{
    		String[] files = this.attachments;
        	SendEmail email = new SendEmail(addressField.getText(), this.message, files);
        	System.out.println(addressField.getText());
        	email.sendMessage();
        	EMailDialogue.this.dispose();
		} catch (AddressException x) {
			JOptionPane.showMessageDialog(this,
                    "Error: " + x.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
		} catch (MessagingException x) {
			JOptionPane.showMessageDialog(this,
                    "Error: " + x.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException x) {
			JOptionPane.showMessageDialog(this,
                    "No route selected",
                    "Error", JOptionPane.ERROR_MESSAGE);
		}
    	
    	
    }
}
