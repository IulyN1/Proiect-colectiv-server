import emailService.EmailService;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello proiect colectiv!");
        EmailService emailService = new EmailService();
        emailService.send("flaviad2@yahoo.com");

    }
}
