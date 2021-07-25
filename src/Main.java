import mail.Mail;

import java.io.IOException;
import Controllers.UserController;

public class Main {
    public static int MAX_T = 20;

    public static void main(String[] args) throws InterruptedException, IOException {

        int emails = Mail.getInstance().getMails();
        while (true) {
            int emails_query = Mail.getInstance().getMails();

            if (emails_query > emails) {
                Mail.getInstance().sendMail(emails + 1, emails_query);
                emails = emails_query;
            }
            
            System.out.println("Waiting to check new emails...");
            Thread.sleep(10000);
        }
    }
}
