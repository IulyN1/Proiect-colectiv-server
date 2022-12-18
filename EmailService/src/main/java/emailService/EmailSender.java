package emailService;

import javax.mail.MessagingException;

public interface EmailSender {
    void send(String recepient) throws MessagingException, Exception;

}
