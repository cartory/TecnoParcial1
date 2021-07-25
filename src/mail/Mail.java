package mail;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Mail {
    private static Mail instance = null;

    private static final int MAX_T = 10;

    private POP3 pop3;
    private SMTP smtp;
    private ExecutorService pool;

    private Mail() {
        this.pop3 = new POP3();
        this.smtp = new SMTP();
        this.pool = Executors.newFixedThreadPool(MAX_T);
    }

    public static Mail getInstance() {
        if (instance == null) {
            instance = new Mail();
        }

        return instance;
    }

    public int getMails() {
        int mails;
        try {
            this.pop3.connect();
            this.pop3.logIn();
            this.pop3.getListAmmount();

            mails = this.pop3.emails;

            this.pop3.logOut();
            this.pop3.close();
        } catch (IOException e) {
            mails = 0;
        }

        return mails;
    }

    public void sendMail(int from, int to) throws IOException, InterruptedException {
        // System.out.println("Mensajes por responder: " + (to - from + 1));
        // ExecutorService pool = Executors.newFixedThreadPool(10);
        this.pool = Executors.newFixedThreadPool(10);

        List<MailTask> emails = new ArrayList<>();

        pop3.connect();
        pop3.logIn();

        for (int i = to; i <= from; ++i) {

            String mensaje = pop3.getMail(Integer.toString(i));
            String emisor = pop3.getFrom(mensaje);
            String subject = pop3.getSubject(mensaje);

            System.out.println(emisor);
            System.out.println(subject);

            emails.add(new MailTask(emisor, subject));
        }

        pop3.logOut();
        pop3.close();

        List<Future<MailSender>> pending;
        pending = pool.invokeAll(emails);
        pool.shutdownNow();

        smtp.connect();
        smtp.logIn();

        for (int i = 0; i < pending.size(); ++i) {
            Future<MailSender> future = pending.get(i);
            if (future.isDone()) {
                try {
                    MailSender m = future.get();
                    smtp.sendMail(m.TO, m.SUBJECT, m.CONTENT);
                    smtp.reset();
                } catch (IOException | InterruptedException | ExecutionException e) {
                    Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }

        smtp.logOut();
        smtp.close();
    }
}
