package com.znt.neckcheckapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main2Activity extends Activity implements SensorEventListener {

    private TextView neckText;
    private Sensor mySensor;
    private SensorManager SM;
    RelativeLayout relativeLayout;
    Context mContext = Main2Activity.this;
    SharedPreferences appPreferences;
    boolean isAppInstalled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2main);

        /**
         * check if application is running first time, only then create shorcut
         */
        appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isAppInstalled = appPreferences.getBoolean("isAppInstalled", false);
        if (isAppInstalled == false) {
            /**
             * create short code
             */
            Intent shortcutIntent = new Intent(getApplicationContext(),
                    Main2Activity.class);
            shortcutIntent.setAction(Intent.ACTION_MAIN);
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "NeckCheckApp");
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                    Intent.ShortcutIconResource.fromContext(
                            getApplicationContext(), R.mipmap.ic_launcher));
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            getApplicationContext().sendBroadcast(intent);

            /**
             * Make preference true
             */
            SharedPreferences.Editor editor = appPreferences.edit();
            editor.putBoolean("isAppInstalled", true);
            editor.commit();

        }
        // Create our Sensor Manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);
        relativeLayout=(RelativeLayout)findViewById(R.id.relative);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Assign TextView
        neckText = (TextView)findViewById(R.id.neck_text);
        //yText = (TextView)findViewById(R.id.yText);
       // zText = (TextView)findViewById(R.id.zText);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //  xText.setText("X: " + event.values[0]);

        int angle = (int) event.values[1];

        /*double lat1 = 0;
        double lat2 =  event.values[0];
        double long1 = 0;
        double long2 =  event.values[1];
        double deltaLong = long2 - long1;
        double angle1 = Math.atan2(Math.sin(deltaLong) * Math.cos(lat2), Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLong));
        int o = (int) Math.toDegrees(angle1);
        Log.d("hello angle is","hii"+o);*/
        if (angle<=2) {
            neckText.setText("Your neck is to much band");
            relativeLayout.setBackgroundColor(Color.parseColor("#2A000000"));
            }

           else if(angle<=4 ){
            relativeLayout.setBackgroundColor(Color.parseColor("#2AF91008"));
            neckText.setText("Your neck is band at 35 degree");
             }
             else  if (angle<=6)
                  {
                      relativeLayout.setBackgroundColor(Color.parseColor("#2AEFF70C"));
                      neckText.setText("Your neck is band at 45 degree");
                   }
                  else   if (angle<=8){
            relativeLayout.setBackgroundColor(Color.parseColor("#2A0CF914"));
            neckText.setText("Your neck is band at 49 degree");
                      }
             else   if (angle<=10){
                     relativeLayout.setBackgroundColor(Color.parseColor("#0AFDFAFF"));
                     neckText.setText("");


        }
        }

    //   yText.setText(""+ event.values[1]);
        //  zText.setText("Z: " + event.values[2]);
}
