package fitsailo.singlealarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class WakeUp extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );

        Button stopBtn=(Button)findViewById(R.id.button);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                mediaPlayer.stop();
                SharedPreferences share = getSharedPreferences(MainActivity.SETTINGS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=share.edit();
                editor.putBoolean(MainActivity.ACTIVE,false);
                editor.apply();
                startActivity(new Intent(WakeUp.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {

            mediaPlayer = MediaPlayer.create(WakeUp.this, R.raw.aces);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();


        super.onResume();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

}
