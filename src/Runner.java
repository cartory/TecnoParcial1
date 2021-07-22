import mail.Mail;

import java.io.IOException;

public class Runner {
    public static int MAX_T = 20;

    public static void main(String[] args) throws InterruptedException, IOException {
        int mails = Mail.getinstance().getMails();

        while (true) {
            int newMails = Mail.getinstance().getMails();
            if (newMails > mails) {
                Mail.getinstance().sendMail(mails + 1, newMails);
                mails = newMails;
            }

            System.out.println("Waiting to check new emails...");
            Thread.sleep(10000);
        }
    }
}
