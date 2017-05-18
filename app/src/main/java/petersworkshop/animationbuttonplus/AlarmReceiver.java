package petersworkshop.animationbuttonplus;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message
        AlarmActivity inst = AlarmActivity.instance();
        inst.setAlarmText("Alarm! Wake up! Wake up!");

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        /**Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
         if (alarmUri == null) {
         alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
         }
         Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
         ringtone.play();*/

        MediaPlayer media_player = MediaPlayer.create (context, R.raw.bella);
        media_player.start();

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}