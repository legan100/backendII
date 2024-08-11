package gg.pixelgruene.oergpbackend.serverhandler;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.User;
import gg.pixelgruene.oergpbackend.utils.FileManager;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailHandler {

    public static User user = new User();

    public static int getNumberOFEmailHandlers = 0;
    FileManager fileManager = new FileManager("email.yml");

    public EmailHandler() {
        getNumberOFEmailHandlers++;
        if(getNumberOFEmailHandlers >= 2){
            Main.getLogger().logError("To much EmailHandlers open.");
            Main.onStop();
        }else {
            if(!fileManager.exists()) {
                fileManager.createFile();
                fileManager.writeInNextFreeLine("hostname: localhost");
                fileManager.writeInNextFreeLine("smtp.port: 587");
                fileManager.writeInNextFreeLine("smtp.auth: true");
                fileManager.writeInNextFreeLine("smtp.start.tls.enable: true");
                fileManager.writeInNextFreeLine("username: info@example.com");
                fileManager.writeInNextFreeLine("password: password");
            }
        }
    }

    public String getHost() {
        String hostname = null;
        for (String str : fileManager.readAll()) {
            if (str.contains("hostname")) {
                hostname = str.split(": ")[1];
            }
        }
        return hostname;
    }

    public String getSmtpPort(){
        String smtpport = null;
        for (String str : fileManager.readAll()) {
            if (str.contains("smtp.port")) {
                smtpport = str.split(": ")[1];
            }
        }
        return smtpport;
    }

    public String getSmtpAuth(){
        String smtpauth = null;
        for(String str: fileManager.readAll()){
            if(str.contains("smtp.auth")){
                smtpauth = str.split(": ")[1];
            }
        }
        return smtpauth;
    }

    public String getStartTLS(){
        String smtpauth = null;
        for(String str: fileManager.readAll()){
            if(str.contains("smtp.start.tls.enable")){
                smtpauth = str.split(": ")[1];
            }
        }
        return smtpauth;
    }

    public String getEmail() {
        String username = null;
        for (String str : fileManager.readAll()) {
            if (str.contains("username")) {
                username = str.split(": ")[1];
            }
        }
        return username;
    }

    public String getEmailPassword() {
        String password = null;
        for (String str : fileManager.readAll()) {
            if (str.contains("password")) {
                password = str.split(": ")[1];
            }
        }
        return password;
    }

    public void sendEmail(User user1, String email, String subject, String text){

        user = user1;
        Properties properties = new Properties();
        properties.put("mail.smtp.host", this.getHost());
        properties.put("mail.smtp.port", this.getSmtpPort());
        properties.put("mail.smtp.auth", this.getSmtpAuth());
        properties.put("mail.smtp.starttls.enable", this.getStartTLS());

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Main.getEmailHandler().getEmail(), Main.getEmailHandler().getEmailPassword());
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.getEmail()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            Main.getLogger().logInfo("Email to " + user1.getEmail(user1.getUsername()) + " sent successfully!");
        } catch (MessagingException e) {
            Main.getLogger().logWarn(e.getCause().toString());
        }
    }

}
