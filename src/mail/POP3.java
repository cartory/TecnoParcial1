package mail;

import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class POP3 {
    private static final int PORT = 110;
    private static final String JUMP = "\n";
    private static final String USER = "grupo18sc";
    private static final String PASS = "grup018grup018";
    private static final String HOST = "mail.tecnoweb.org.bo";

    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;

    private String COMMAND = "";
    public int currentEmail = 1, emails = 0;

    public void connect() throws IOException {
        this.socket = new Socket(HOST, PORT);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new DataOutputStream(socket.getOutputStream());
        System.out.println("CONNECTION DONE!");
    }

    public void close() throws IOException {
        this.socket.close();
        this.input.close();
        this.output.close();
        System.out.println("CONNECTION CLOSE!");
    }

    public void logIn() throws IOException {
        COMMAND = "USER " + USER + JUMP;
        this.output.writeBytes(COMMAND);
        this.input.readLine();
        COMMAND = "PASS " + PASS + JUMP;
        this.output.writeBytes(COMMAND);
        System.out.println("S:" + this.input.readLine());
    }

    public void logOut() throws IOException {
        COMMAND = "QUIT" + JUMP;
        this.output.writeBytes(COMMAND);
        System.out.println("S:" + this.input.readLine());
    }

    public String getList() throws IOException {
        COMMAND = "LIST" + JUMP;
        this.output.writeBytes(COMMAND);
        return getData(this.input);
    }

    public void getListAmmount() throws IOException {
        String list = getList();
        String lines[] = list.split("\\r?\\n");
        String[] data = new String[] {};
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = lines[i].toLowerCase();
            if (lines[i].contains("messages") || lines[i].contains("message")) {
                data = lines[i].split("\\s+");
                break;
            }
        }

        this.emails = Integer.parseInt(data[1]);
    }

    public String getMail(String mail) throws IOException {
        COMMAND = "retr " + mail + JUMP;
        this.output.writeBytes(COMMAND);
        return getData(this.input);
    }

    public String getStat() {
        String line = "";
        try {
            this.output.writeBytes("STAT" + JUMP);
            line = this.input.readLine();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (line.compareTo("") != 0) {
            line = line.substring(4);
            int i = 1;
            while (line.charAt(i) != ' ') {
                i++;
            }
            line = line.substring(0, i);
        }
        return line;
    }

    public String getSubject(String mail) {
        String line = "";
        String lines[] = mail.split("\\n");

        int pos_found = -1;
        boolean found = false;
        
        for (int i = 0; i < lines.length; ++i) {
            line = lines[i];
            if (line.contains("Subject:")) {
                found = true;
                pos_found = i;
                break;
            }
        }

        if (found) {
            String sub = "Subject:";
            String fila = lines[pos_found + 1];
            int pos = line.indexOf(sub);

            return line.substring(pos + sub.length() + 1, line.length()) + fila;
        } else {
            return "";
        }
    }

    public String getFrom(String mail) {
        String line = "";
        String[] lines = mail.split("\\r?\\n");

        boolean found = false;
        for (int i = 0; i < lines.length; ++i) {
            line = lines[i];
            if (line.contains("Return-Path:")) {
                found = true;
                break;
            }
        }

        if (found) {
            String[] currencies = line.split(":");
            String email = currencies[1];
            email = email.replace('<', ' ');
            email = email.replace('>', ' ');
            email = email.trim();
            return email;
        } else {
            return "";
        }
    }

    static protected String getData(BufferedReader buffer) throws IOException {
        String Data = "", Line = "";

        while ((Line = buffer.readLine()) != null) {
            // LAST LINE
            if (Line.equals(".")) {
                break;
            }

            if ((Line.length() > 0) && (Line.charAt(0) == '.')) {
                Line = Line.substring(1);
            }
            Data = Data + Line + JUMP;
        }
        return Data;
    }

    public void nextEmail() {
        if (currentEmail + 1 <= emails) {
            currentEmail++;
        }
    }

    public void previousEmail() {
        if (currentEmail - 1 >= 1) {
            currentEmail--;
        }
    }
}
