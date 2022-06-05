package com.vedalegal.service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;

@Component
public class EmailService {

//	private String host = "smtp.gmail.com";
//	private String from="session2468@gmail.com";
//	private String password="Session@2468#";
	
	private String host = "smtp.gmail.com";
	private String from="easylaw.web@gmail.com";
	private String password="qcyjvrrjnhlnxabj";
	
	
	public void sendEmail(String to, String mailSub, String mailBody) throws IOException, AddressException, MessagingException
	{
		 Session session=getSession(); 
		 MimeMessage message=composeMail(session, to, mailSub, mailBody );
	     Transport.send(message);  
	         
	     System.out.println("message sent successfully....");  
	  }
	  
	public void sendEmailWithAttachment(String to, String mailSub, String mailBody,String filePath) throws IOException, AddressException, MessagingException
	{
		//get session
		 Session session=getSession();                                                                           
		 MimeMessage message=composeMailWithAttachment(session, to, mailSub, mailBody ,filePath); 
	     Transport.send(message);  
	  
	     System.out.println("message sent successfully....");      
	}
	
	public Session getSession()
	{
		  Properties properties = System.getProperties(); 
	      properties.setProperty("mail.smtp.host", host);  
	      properties.setProperty("mail.smtp.port", "465");  
	      properties.setProperty("mail.smtp.ssl.enable", "true");
	      properties.setProperty("mail.smtp.auth", "true");

	          Session session = Session.getInstance(properties, new Authenticator() {
	    	  @Override
	    	  protected PasswordAuthentication getPasswordAuthentication() {
	    		  return new PasswordAuthentication(from,password);
	    	  			}
		
	          			});
	          return session;
		}
	public MimeMessage composeMail(Session session, String to, String mailSub, String mailBody) throws AddressException, MessagingException
	{
				

		MimeMessage message = new MimeMessage(session);  
		message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
        message.setSubject(mailSub);  
        message.setText(mailBody);
        return message;
	}
	public MimeMessage composeMailWithAttachment(Session session, String to, String mailSub, String mailBody, String filePath) throws AddressException, MessagingException, IOException
	{
		
		File file=new File(filePath);
		MimeMessage message = new MimeMessage(session);  
		message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
        message.setSubject(mailSub);  
        
		MimeMultipart multipart=new MimeMultipart();
        
        MimeBodyPart textmime= new MimeBodyPart();
        MimeBodyPart filemime= new MimeBodyPart();
        textmime.setText(mailBody);
        filemime.attachFile(file);
        
        multipart.addBodyPart(textmime);
        multipart.addBodyPart(filemime);
        message.setContent(multipart);
        return message;
	}
}
