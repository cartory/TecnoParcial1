package mail;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class SMTP {
    private static final int PORT = 25;
    private static final String HOST = "mail.tecnoweb.org.bo";
    private static final String FROM = "grupo@tecnoweb.org.bo";

    private String COMMAND = "";
    private static final String JUMP = "\n";

    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;

    public SMTP() {
        this.input = null;
        this.output = null;
        this.socket = null;
    }

    public void connect() throws IOException, UnknownHostException {
        this.socket = new Socket(HOST, PORT);
        this.output = new DataOutputStream(this.socket.getOutputStream());
        this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        System.out.println("S: " + this.input.readLine());
    }

    public void close() throws IOException {
        this.input.close();
        this.output.close();
        this.socket.close();
        System.out.println("CONNECTION CLOSED !!");
    }

    public void reset() throws IOException {
        this.COMMAND = "RESET " + JUMP;
        this.output.writeBytes(this.COMMAND);
        System.out.println("S :" + this.input.readLine());
    }

    public void logIn() throws IOException {
        this.COMMAND = "HELLO 👋 FROM " + HOST + JUMP;
        this.output.writeBytes(this.COMMAND);
        System.out.println("S :" + this.input.readLine());
    }

    public void logOut() throws IOException {
        this.COMMAND = "QUIT" + JUMP;
        this.output.writeBytes(this.COMMAND);
    }

    public void sendMail(String RCPT, String SUBJECT, String DATA) throws IOException {
        this.COMMAND = "MAIL FROM: " + FROM + JUMP;
        this.output.writeBytes(this.COMMAND);
        System.out.println("S:" + this.input.readLine());

        this.COMMAND = "RCPT TO: " + RCPT + JUMP;
        this.output.writeBytes(this.COMMAND);
        System.out.println("S:" + this.input.readLine());

        this.COMMAND = "DATA" + JUMP;
        this.output.writeBytes(this.COMMAND);
        System.out.println("S:" + this.input.readLine());

        this.COMMAND = "SUBJECT: " + SUBJECT + JUMP;
        this.output.writeBytes(this.COMMAND);
        this.COMMAND = DATA + JUMP;
        this.output.writeBytes(this.COMMAND);
        this.COMMAND = "." + JUMP;
        this.output.writeBytes(this.COMMAND);
        System.out.println("S:" + input.readLine());
    }
}
