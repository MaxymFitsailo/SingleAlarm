package fitsailo.singlealarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String SETTINGS="Settings";
    public final static String ACTIVE="";
    public final static String HOURS="Hours";
    public final static String MINUTES="Minutes";
    private SharedPreferences share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        share = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        loadAlarmSettings(share);
        Button okButton=(Button)findViewById(R.id.apply);
        okButton.setOnClickListener(this);

    }
    //загружаем данные о состоянии будильника из файла  в SharedPreferences
    void loadAlarmSettings(SharedPreferences share){
         TimePicker time= (TimePicker) findViewById(R.id.timePicker);
         time.setIs24HourView(true);
         ToggleButton button= (ToggleButton) findViewById(R.id.toggleButton);
         time.setCurrentHour(share.getInt(HOURS,6));
         time.setCurrentMinute(share.getInt(MINUTES,0));
         button.setChecked(share.getBoolean(ACTIVE,false));

    }
    //записываем данные о состоянии будильника в файл в SharedPreferences
    void saveAlarmSettings(SharedPreferences share){
        SharedPreferences.Editor editor=share.edit();
        TimePicker time= (TimePicker) findViewById(R.id.timePicker);
        ToggleButton button= (ToggleButton) findViewById(R.id.toggleButton);
        editor.putInt(HOURS,time.getCurrentHour());
        editor.putInt(MINUTES,time.getCurrentMinute());
        if (button.isChecked()) editor.putBoolean(ACTIVE,true);
        else editor.putBoolean(ACTIVE,false);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apply:
                saveAlarmSettings(share);
                stopService(new Intent(this,SingleAlarmService.class));
                startService(new Intent(this,SingleAlarmService.class));
                break;
        }
    }
}
