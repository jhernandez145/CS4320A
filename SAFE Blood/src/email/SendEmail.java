package email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	public static void sendEmail(String user, String password, String recipient) throws Exception {
		Properties props = null;
		if (props == null) {
			props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.host", "smtp.live.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.user", user);
			props.put("mail.smtp.pwd", password);
		}
		Session session = Session.getInstance(props, null);
		session.setDebug(true);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(user));
		msg.setSubject("Auto Generated Mail");
		msg.setText("Testing Mail");
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.live.com", 587, user, password);
		transport.sendMessage(msg, msg.getAllRecipients());
		System.out.println("Mail sent successfully at " + recipient);
		transport.close();
	}
}
