package com.example.premal2.potholedetector;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.TextView;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Detection extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView x;
    private TextView y;
    private TextView z;
    private TextView status;
    private NotificationCompat.Builder mBuilder;
    private DatabaseReference myRef;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        mSensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        status=(TextView) findViewById(R.id.status);
        mSensorManager.registerListener(Detection.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        x=(TextView) findViewById(R.id.xc);
        y=(TextView) findViewById(R.id.yc);
        z=(TextView) findViewById(R.id.zc);
        mBuilder=new NotificationCompat.Builder(this);
        mBuilder.setAutoCancel(true);
        username=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        myRef= database.getReference(username);
        myRef.setValue("Hello");
    }
    @Override
    public void onAccuracyChanged(Sensor sensor,int i)
    {

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("e", " X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z:" + sensorEvent.values[2]);
        int xa=(int) sensorEvent.values[0];
        int ya=(int) sensorEvent.values[1];
        int za=(int) sensorEvent.values[2];
        x.setText("X: " + xa);
        y.setText("Y: " + ya);
        z.setText("Z: " + za);
        if(Child.val==0)
        {if (sensorEvent.values[0] >= 9 || sensorEvent.values[1] >= 9 || sensorEvent.values[2] >= 9 || sensorEvent.values[0]<=-9 || sensorEvent.values[1]<=-9 || sensorEvent.values[2]<=-9)
        {
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setSmallIcon(R.drawable.common_google_signin_btn_icon_light);
            mBuilder.setContentTitle("Alert");
            mBuilder.setContentText("Phone has been fallen with x coordinate acceleration "+sensorEvent.values[0]+" y coordinate acceleration "+sensorEvent.values[1]+" z coordinate acceleration "+sensorEvent.values[2]);
            Intent intent=new Intent(this,Detection.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(45612,mBuilder.build());
            status.setText("Phone has been fallen with \n x coordinate acceleration "+sensorEvent.values[0]+"\n y coordinate acceleration "+sensorEvent.values[1]+"\n z coordinate acceleration "+sensorEvent.values[2]);
            Child x=new Child();
            x.setPriority(Thread.MIN_PRIORITY);
            x.start();
            Timestamp r=new Timestamp(System.currentTimeMillis());
            String time=" "+r;
            String email_id=FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String id=myRef.push().getKey();
            acceleration p=new acceleration(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2],time,email_id);
            myRef.child(id).setValue(p);
    }}
}}
