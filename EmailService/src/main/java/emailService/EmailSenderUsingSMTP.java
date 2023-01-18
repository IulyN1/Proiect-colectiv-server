package emailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;


public class EmailSenderUsingSMTP {

    static Properties props = System.getProperties();
    static Session l_session = null;

    /**
     * Connect and send smtp.
     *
     * @param serverName
     *            the server name / host name
     * @param portNo
     *            the port number
     * @param secureConnection
     *            the secure connection i.e, STARTTLS or STARTSSL settings keep true or false or never
     * @param userName
     *            the email used for login
     * @param password
     *            the password of email account
     * @param toEmail
     *            the email of the receiver
     * @param subject
     *            the subject of email
     * @param msg
     *            the message body of email
     * @return the string which gives response for email is send or not
     */
    public String connectAndSendSmtp(String serverName, String portNo, String secureConnection, String userName, String password, String toEmail, String subject, String msg, String[] attachFiles){

        emailSettings(serverName, portNo, secureConnection);
        createSession(userName, password);
        String issend = sendMessage(userName, toEmail,subject,msg, attachFiles);
        return issend;
    }

    /**
     * All the params bellow have been used to create the email sending session
     * @param host
     * @param port
     * @param secureCon
     */
    public void emailSettings(String host, String  port, String secureCon) {
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.port", port);
        if(secureCon.equalsIgnoreCase("tls")){
            props.put("mail.smtp.starttls.enable", "true");
        }else if(secureCon.equalsIgnoreCase("ssl")){
            props.put("mail.smtp.startssl.enable", "true");  // make this true if port=465
        }else{
            props.put("mail.smtp.starttls.enable", "false");
            props.put("mail.smtp.startssl.enable", "false");
        }
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

    }


    /**
     * This method adds an authentification when creating an email sending session
     * @param username
     * @param pass
     */
    public void createSession(final String username, final String pass) {

        l_session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, pass);
                    }
                });
        l_session.setDebug(true); // Enable the debug mode
    }


    /**
     * The method sends the composed email on the sending email session
     * @param fromEmail - String - which address the email will be send from
     * @param toEmail - String - "TO" part of the email
     * @param subject - String - the subject of the email
     * @param msg   - String - the text body of the email
     * @param attachFiles - String[] - represents the path of the file which is attached to the email
     * @return
     */
    public String sendMessage(String fromEmail, String toEmail, String subject, String msg, String[] attachFiles) {
        String msgsendresponse="";
        try {

            MimeMessage message = new MimeMessage(l_session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);


            //-----------* Send mail with attachments code Starts here *---------------------
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Set TEXT/HTML message here
            messageBodyPart.setContent(msg, "text/html");
            multipart.addBodyPart(messageBodyPart);

            // adds attachments
            MimeBodyPart attachPart = new MimeBodyPart();
            if (attachFiles != null && attachFiles.length > 0) {
                for (String filePath : attachFiles) {
                    try {
                        attachPart.attachFile(filePath);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    multipart.addBodyPart(attachPart);
                }
            }
            message.setContent(multipart);
            //------------* Send mail with attachments code Ends here *------------------------
            try {
                Transport.send(message);
                msgsendresponse="Message_Sent";		//Don't change this message String
                System.out.println(msgsendresponse);
            } catch (Exception ex) {
                msgsendresponse="Email sending failed due to: " +ex.getLocalizedMessage();
                System.out.println(msgsendresponse);		//gives error message for failure

            }

        } catch (MessagingException mex) {
            msgsendresponse = "Error occured in creation of MIME message due to:  "+mex.getLocalizedMessage();
            System.out.println(msgsendresponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgsendresponse;
    }


    /**
     * This method is used to set the parameters for the sending email method which is implemented above
     */
    public static void send() {

        String serverName = "smtp.gmail.com";		//  smtp.mail.yahoo.com
        String portNo = "465";							// 465 , 587 , 25 ... 465 best for text emails
        String secureConnection = "ssl";					// ssl , tls , never
        String userName = "rpaubb@gmail.com";
        String password = "xyjuiexsojgkuvaz";
        String toEmail = "flaviad2@yahoo.com";
        String subject = "New Assessment mail";
        String msg = "<h1> This is test mail please ignore... </h1>";

        // attachments
        String[] attachFiles = new String[1];
        attachFiles[0] = "C:\\Users\\home\\Desktop\\FCSB.xlsx";


        EmailSenderUsingSMTP oe = new EmailSenderUsingSMTP();
        oe.connectAndSendSmtp(serverName, portNo, secureConnection, userName, password, toEmail, subject, msg, attachFiles);

        //Check methods - used this for debug
        /*oe.createSession(userName, password);
        oe.emailSettings(serverName, portNo, secureConnection);
        oe.sendMessage(userName, toEmail, subject, msg,attachFiles);*/

    }
}