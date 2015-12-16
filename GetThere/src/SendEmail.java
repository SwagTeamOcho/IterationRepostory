
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
 
    private String userName;
	private String password;
	private String subject;
	private String message;
	private Properties properties;
	private Authenticator auth;
	private Session session;
	private MimeMessage msg;
	private String toAddress;
	private String[] attachFiles;
	public SendEmail(String toAddress, String message,  String[] attachFiles)
             {
        // sets SMTP server properties
    	this.userName = "wpigetthere@gmail.com";
    	this.password = "softenglife888$$%";
    	this.subject = "Your GetThere Directions";
    	this.message = "Here are your directions: \n\n" + message + "Get There safe.\n Team Ocho Software Engineering";
        this.properties = new Properties();
        this.toAddress = toAddress;
        this.attachFiles = attachFiles;
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        this.auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        this.session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        this.msg = new MimeMessage(session);
             }
    public void sendMessage() throws AddressException, MessagingException{
 
        this.msg.setFrom(new InternetAddress(this.userName));
        InternetAddress[] toAddresses = { new InternetAddress(this.toAddress) };
        this.msg.setRecipients(Message.RecipientType.TO, toAddresses);
        this.msg.setSubject(this.subject);
        this.msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(this.message);
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        if(this.message == null) {
        	throw new NullPointerException();
        }
        Transport.send(msg);
        System.out.println("Email sent.");
 
    }
    /*
    JButton eamilButton = new JButton("Email Directions");
	newMap.setBounds(900, 150, 132, 29);
	uiPanel.add(emailButton);
	emailButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
		}
	}
     */
 
    /**
     * Test sending e-mail with attachments
     */
    /*
    public static void main(String[] args) {
        // SMTP info
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "tovarishchpetrosina@gmail.com";
        String password = ;
 
        // message info
        String mailTo = "teamocho@wpi.edu";
        String subject = "Sending email with attachments test";
        String message = "Hey guys. Here's the code for sending files via email. And yes, the code just set itself.";
 
        // attachments
        String[] attachFiles = new String[1];
        attachFiles[0] = "C:/Users/Pablo/workspace/TestFile/src/SendEmail.java";
//        attachFiles[1] = "C:/Users/Pablo/Downloads/T8D2.docx";
//        attachFiles[2] = "C:/Users/Pablo/Downloads/T8D2.docx";
 
        try {
            sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
                subject, message, attachFiles);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }*/
}