package ar.com.twoboot.microman.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import ar.com.twoboot.panico.MicroMan;

public class MailNotificacion extends Notificacion {
	private static final String username = "microman.notificaciones@gmail.com";
	private static final String password = "m1crom4n";
	private Context context;
	
	public MailNotificacion(Context context,Integer cantidad,String estadoTransmisionRuta,String avance) {
		super();
		this.context = context;
		String[] email=new String[1] ;
		email[0]="twoboot@gmail.com";
		
		
		if(estadoTransmisionRuta==null)estadoTransmisionRuta="";
		if(cantidad==null)cantidad=0;
		if(avance==null)avance="N/A";
		String subject = "Notificacion de Envio " +estadoTransmisionRuta;
        String message ="";
        message+="Version MicroManLite: "+Util.VERSION_MICROMAN+ "\n";
        message+="IMEI: "+MicroMan.imei+ "\n";
        
        sendMail(email, subject, message);
	}

	private void sendMail(String[] email, String subject, String messageBody) {
        Session session = createSessionObject();

        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private Message createMessage(String[] email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username, "Notificacion MicroMan"));
        for (int i = 0; i < email.length; i++) {
        	message.addRecipient(Message.RecipientType.TO, new InternetAddress(email[i], email[i]));	
		}
        
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }

    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, "Espere", "Notificando...", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
	
}
