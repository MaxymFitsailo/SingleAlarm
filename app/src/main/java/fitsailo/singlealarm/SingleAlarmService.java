package fitsailo.singlealarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SingleAlarmService extends Service {
    SharedPreferences share;
    MediaPlayer mediaPlayer;
    Timer timer;

    public SingleAlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
     }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"service start",Toast.LENGTH_SHORT).show();
        share=getSharedPreferences(MainActivity.SETTINGS, Context.MODE_PRIVATE);

        //проверяем включен ли будильник
        if(share.getBoolean(MainActivity.ACTIVE,false)) startAlarm();
        else stopSelf();
        return Service.START_STICKY;
    }

    void startAlarm() {
        TimerTask startRinging = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SingleAlarmService.this, WakeUp.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                mediaPlayer = MediaPlayer.create(SingleAlarmService.this, R.raw.aces);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        };

        Date alarmtime = getTimeForTimer(share.getInt(MainActivity.HOURS, 6), share.getInt(MainActivity.MINUTES, 0));

        Timer timer = new Timer();
        timer.schedule(startRinging, alarmtime);
    }

    Date getTimeForTimer(int hours, int minutes){
        Calendar now = Calendar.getInstance();
        Calendar alarmtime = Calendar.getInstance();
        alarmtime.set(Calendar.HOUR_OF_DAY, hours);
        alarmtime.set(Calendar.MINUTE, minutes);
        alarmtime.set(Calendar.SECOND,0);
        if (now.after(alarmtime)) alarmtime.set(Calendar.DATE, Calendar.DATE + 1);
        return alarmtime.getTime();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        timer=null;
        Toast.makeText(this,"service stop",Toast.LENGTH_SHORT).show();

    }
}
