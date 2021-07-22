package mail;

import Utils.Command;
import java.util.concurrent.Callable;

public class MailTask implements Callable<MailSender> {

    String to;
    String subject;
    Command command;

    MailTask(String to, String subject) {
        this.to = to;
        this.subject = subject;
        this.command = new Command();
    }

    @Override
    public MailSender call() throws Exception {
        String responseHTML = this.command.getResponseHTML(subject);
        return new MailSender(this.to, this.subject, responseHTML);
    }
}
