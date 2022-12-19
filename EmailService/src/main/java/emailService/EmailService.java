package emailService;

import lombok.AllArgsConstructor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
@AllArgsConstructor
public class EmailService implements EmailSender{


    @Override
    public void send(String recepient) throws Exception {

        System.out.println("Prepare to send email...");
        Properties properties = System.getProperties();
        String host = "smtp.gmail.com";
        properties.put("mail.smtp.starttls.enable", "true");
        //props.put("mail.smtp.host", host);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.trust", "mail.smtp.gmail.com");
        properties.put("mail.smtp.user", "flaviadorobat@gmail.com");
        properties.put("mail.smtp.password", "");
        properties.put("mail.smtp.auth", "true");

       // String accountEmail = "flaviadorobat@gmail.com";
       // Session session = Session.getDefaultInstance(properties);
       // Message message = prepareMessage(session, accountEmail,recepient);
      Session session = Session.getDefaultInstance(properties, new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication("flaviadorobat@gmail.com", "");
          }
      });
        Message message = prepareMessage(session,"flaviadorobat@gmail.com",recepient);
        Transport.send(message);
        System.out.println("Message sent!");
    }

        private static Message prepareMessage(Session session, String accountEmail, String recepient) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(accountEmail));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
                message.setSubject("Notification Email");
                message.setText("Hey there! \n New products arrived in the store! Check them out on this link .... ");
                return message;
            }catch(Exception ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);

        }
            return null;
    }



}