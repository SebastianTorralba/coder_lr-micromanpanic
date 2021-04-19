package ar.com.twoboot.microman.util;

import android.content.Context;
import android.telephony.SmsManager;

public class SmsNotificacion  {
	private static final String central_telefono="543804930969";
	private Context context;
	private SmsManager smsManager;
	
	public void sendSms(SmsMensaje mensaje){
	
		try {
			smsManager=SmsManager.getDefault();
			smsManager.sendTextMessage(central_telefono, null, mensaje.toString(), null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 	
}
