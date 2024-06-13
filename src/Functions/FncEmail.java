package Functions;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FncEmail {

	public FncEmail() {
		try {
			FncEmail.sendEmail(new String[]{"jfatallo@yahoo.com"}, new String[]{""}, "Alvin Gonzales", "Sample Content");
			//FncEmail.sendEmail(new String[]{"alvingonzales00@gmail.com"}, new String[]{""}, "Alvin Gonzales", "Sample Content");
		} catch (MessagingException e) { }
	}

	public static Boolean validate(String email) {
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		return email.matches(emailreg);
	}

	public static void sendEmail(String[] to, String[] cc, String subject,
			String content) throws MessagingException {
		try {
			/**
			 * mail.verdantpoint.com is working even without authentication
			 * (password not necessary)
			 **/
			String mailFrom = "jschedule@verdantpoint.com";

			Properties properties = new Properties();
			properties.put("mail.smtp.starttsl.enable", "true");
			properties.put("mail.smtp.host", "mail.verdantpoint.com");
			properties.put("mail.smtp.port", "25");

			/*String mailFrom2 = "alvingonzales00@gmail.com";

			Properties defaultProps = new Properties();
			// sets default properties
			defaultProps.setProperty("mail.smtp.host", "smtp.gmail.com");
			defaultProps.setProperty("mail.smtp.port", "587");
			defaultProps.setProperty("mail.user", "alvingonzales00@gmail.com");
			defaultProps.setProperty("mail.password", "alipunga211213");
			defaultProps.setProperty("mail.smtp.starttls.enable", "true");
			defaultProps.setProperty("mail.smtp.auth", "true");*/

			Session emailSession = Session.getDefaultInstance(properties);

			/**
			 * mail.yahoo.com & etc works only with authentication (password
			 * necessary) String mailFrom = "ran.cabrera@yahoo.com"; String
			 * mailPass = "********";
			 * 
			 * properties.put("mail.smtp.auth", "true");
			 * properties.put("mail.smtp.ssl.enable", "true");
			 * properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
			 * properties.put("mail.smtp.port", "465");
			 * 
			 * Authenticator auth = new SMTPAuthenticator( mailFrom, mailPass );
			 * Session emailSession = Session.getDefaultInstance(properties,
			 * auth);
			 **/
			emailSession.setDebug(true);

			InternetAddress from = null;
			try {
				from = new InternetAddress(mailFrom, "JSchedule");
			} catch (UnsupportedEncodingException e) { }

			Message emailMessage = new MimeMessage(emailSession);
			emailMessage.setFrom(from);
			emailMessage.setSubject(subject);
			// emailMessage.setText( content );
			emailMessage.setContent(content, "text/html");

			for (int x = 0; x < to.length; x++) {
				if (!to[x].trim().isEmpty()) {
					emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to[x]));
				}
			} // for TO

			for (int x = 0; x < cc.length; x++) {
				if (!cc[x].trim().isEmpty()) {
					emailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[x]));
				}
			} // for CC

			Transport.send(emailMessage);
		} catch (AddressException e) {
			e.printStackTrace();
		}
	} // sendEmail

	public static void main(String[] arg0) {
		/*String email = "alvingonzales00@gmail.com";
		System.out.printf("Valid: %s (%s)%n", FncEmail.validate(email), email);*/

		try {
			//FncEmail.sendEmail(new String[]{"jfatallo@yahoo.com"}, new String[]{""}, "Alvin Gonzales", "Sample Content");
			FncEmail.sendEmail(new String[]{"jfatallo0707@gmail.com"}, new String[]{""}, "JSystem2.2 Testing", "Sample Content");
		} catch (MessagingException e) { }
	}

}