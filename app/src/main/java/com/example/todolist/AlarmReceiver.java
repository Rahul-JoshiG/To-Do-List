package com.example.todolist;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;


public class AlarmReceiver extends BroadcastReceiver {
	static Ringtone ringtone;
	@Override
	public void onReceive(Context context, Intent intent) {
		// Play default alarm sound
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

		if (alarmSound == null) {
			alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

			if (alarmSound == null) {
				alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			}
		}

		ringtone = RingtoneManager.getRingtone(context, alarmSound);

		if (ringtone != null) {
			ringtone.play();
			// Create an intent to launch your pop-up activity
			Intent popupIntent = new Intent(context, AfterAlarmPopScreenActivity.class);
			popupIntent.putExtra("todoTitleFromAlarm", intent.getStringExtra("todoTitleFromMain"));
			popupIntent.putExtra("todoDescriptionFromAlarm", intent.getStringExtra("todoDescriptionFromMain"));

			popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(popupIntent);
		}
	}

	public static void stopRingtone() {
		if (ringtone != null && ringtone.isPlaying()) {
			ringtone.stop();
		}
	}
}
