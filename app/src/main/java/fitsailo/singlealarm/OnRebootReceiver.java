package fitsailo.singlealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnRebootReceiver extends BroadcastReceiver {
    public OnRebootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent pushIntent = new Intent(context, SingleAlarmService.class);
            context.startService(pushIntent);
        }


           }

}
