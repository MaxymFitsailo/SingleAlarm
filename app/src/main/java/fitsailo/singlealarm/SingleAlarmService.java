package fitsailo.singlealarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SingleAlarmService extends Service {
    SharedPreferences share;
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
        if(share.getBoolean(MainActivity.ACTIVE,false)) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    startAlarm(share.getInt(MainActivity.HOURS, 6), share.getInt(MainActivity.MINUTES, 0));

                }
            });
            thread.start();
        }
        else stopSelf();
        return Service.START_STICKY;
    }

    void startAlarm(final int hours, final int minutes) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                startActivity(new Intent(SingleAlarmService.this, WakeUp.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                stopSelf();
            }
        };

        Calendar now = Calendar.getInstance();
        Calendar alarmtime = Calendar.getInstance();
        alarmtime.set(Calendar.HOUR_OF_DAY, hours);
        alarmtime.set(Calendar.MINUTE, minutes);
        alarmtime.set(Calendar.SECOND,0);

        if (now.after(alarmtime)) alarmtime.set(Calendar.DATE, Calendar.DATE + 1);
        Timer timer = new Timer();
        timer.schedule(task, alarmtime.getTime());


    }

    @Override
    public void onDestroy() {

        Toast.makeText(this,"service stop",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
