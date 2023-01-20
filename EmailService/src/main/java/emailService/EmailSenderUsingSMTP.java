package emailService;

import domain.Product;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class EmailSenderUsingSMTP {

    static Properties props = System.getProperties();
    static Session l_session = null;
    static Properties emailProperties = createEmailProperties();

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
    public String connectAndSendSmtp(String serverName, String portNo, String secureConnection, String userName, String password, String toEmail, String subject, String msg){

        emailSettings(serverName, portNo, secureConnection);
        createSession(userName, password);
        String issend = sendMessage(userName, toEmail,subject,msg);
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
     * @return a String that says if the mail was sent with success or an error occured
     */
    public String sendMessage(String fromEmail, String toEmail, String subject, String msg) {
        String msgsendresponse="";
        try {

            MimeMessage message = new MimeMessage(l_session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);


            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Set TEXT/HTML message here
            messageBodyPart.setContent(msg, "text/html");
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
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

    public static String styleTemplate ()
    {
        return "  <!DOCTYPE html>\n" +
                "  <html>\n" +
                "  <head>\n" +
                "    <style type=\"text/css\">\n" +
                "    \n" +
                "      h1 {\n" +
                "        color: #040F0E;\n" +
                "        font-size: 56px;\n" +
                "      }\n" +
                "      \n" +
                "        h2{\n" +
                "        color: #040F0E;\n" +
                "        font-size: 28px;\n" +
                "        font-weight: 900; \n" +
                "      }\n" +
                "      \n" +
                "      p {\n" +
                "        color: #040F0E;\n" +
                "        font-weight: bold;\n" +
                "        font-size: 20px;\n" +
                "     }\n" +
                "      \n" +
                "    </style>\n" +
                "    \n" +
                "  </head>";
    }

    public static String htmlContentGreeting()
    {
       return "  <table role=\"presentation\" width=\"100%\">\n" +
                "  <tr>\n" +
                "    <td bgcolor=\"#00A4BD\" align=\"center\" style=\"color: white;\">                    \n" +
                "      <h1> Out of stock notification </h1>\n" +
                "    </td>\n" +
                "   </table>" ;
    }


    public static String htmMessageContent(String productsOutOfStock)
    {
        return "  <table role=\"presentation\" width=\"100%\">\n" +
                "  <tr>\n" +
                "    <td bgcolor=\"#DEF5F1\" align=\"center\" >                    \n" +
                "        <h2> Hello, there! </h2>\n" +
                "        <p> This is an email to notify you that some products from your watchlist are out of stock at the moment.   </p> \n" +
                "        <p> The products that are out of stock are" + productsOutOfStock +" </p> \n" +

                "        <p> Stay tuned because they will come back in stock soon. </p>\n" +
                "        <p> Best regards from our team! &#129303; </p>                 \n" +
                "    </td>\n" +
                " </table>  ";
    }

    /**
     * This method is used to set the parameters for the sending email method which is implemented above
     */
    public static void sendNotificationOutOfStock(String toEmail, List<Product> productsList) {
        List<String> products = new ArrayList<>();
        for(Product product : productsList)
        {
            products.add(product.toString());
        }
        String serverName = emailProperties.getProperty("serverName");		//  smtp.mail.yahoo.com
        String portNo = emailProperties.getProperty("portNo");							// 465 , 587 , 25 ... 465 best for text emails
        String secureConnection = emailProperties.getProperty("secureConnection");					// ssl , tls , never
        String userName = emailProperties.getProperty("userName");
        String password =  emailProperties.getProperty("password");
        String subject = "Out of stock";
        String productsOutOfStock = ": ";
        for(String product: products)
        {
            productsOutOfStock += product;
            productsOutOfStock += "<br>";
        }
        productsOutOfStock +=".";
        String msg = htmlContentGreeting() + htmMessageContent(productsOutOfStock);


        EmailSenderUsingSMTP oe = new EmailSenderUsingSMTP();
        oe.connectAndSendSmtp(serverName, portNo, secureConnection, userName, password, toEmail, subject, msg);

        //Check methods - used this for debug
        /*oe.createSession(userName, password);
        oe.emailSettings(serverName, portNo, secureConnection);
        oe.sendMessage(userName, toEmail, subject, msg);*/

    }

    public static Properties createEmailProperties() {
        Properties emailProps = new Properties();
        try {
            emailProps.load(new ClassPathResource("email.properties").getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return emailProps;
    }

}