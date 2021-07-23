// import mail.Mail;

import java.io.IOException;

import Models.User;

public class Main {
    public static int MAX_T = 20;

    public static void main(String[] args) throws InterruptedException, IOException {
        User user = new User();
        System.out.println("USER CREATE ");

        String data[] = new String[] { "cari", "cari@gmail.com", "cari", "74942908", "M" };
        user.create(data);

        System.out.println("USER CREATE ");
        System.out.println(user);
    }
}
