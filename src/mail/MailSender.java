package mail;

public class MailSender {
    String TO;
    String SUBJECT;
    String CONTENT;

    public MailSender(String TO, String SUBJECT, String CONTENT) {
        this.TO = TO;
        this.SUBJECT = SUBJECT;
        this.CONTENT = CONTENT;
    }
}
