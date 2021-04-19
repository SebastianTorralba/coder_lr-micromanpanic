package ar.com.twoboot.panico.receivers;

import java.util.Date;

import android.content.Context;

public class LlamadaReceiver extends PhonecallReceiver {

	@Override
	protected void onIncomingCallReceived(Context ctx, String number, Date start) {
		PantallaOnReceiver.LLAMADA=true;
	}

	@Override
	protected void onIncomingCallAnswered(Context ctx, String number, Date start) {

	}

	@Override
	protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
		PantallaOnReceiver.LLAMADA=false;
	}

	@Override
	protected void onOutgoingCallStarted(Context ctx, String number, Date start) {

	}

	@Override
	protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {

	}

	@Override
	protected void onMissedCall(Context ctx, String number, Date start) {

	}

}
