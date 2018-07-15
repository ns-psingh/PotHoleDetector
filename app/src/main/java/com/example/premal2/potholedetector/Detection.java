package com.example.premal2.potholedetector;

import android.*;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.lang.ref.Reference;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Detection extends AppCompatActivity implements SensorEventListener, OnMapReadyCallback, LocationListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView x;
    private TextView y;
    private TextView z;
    private TextView status;
    // private NotificationCompat.Builder mBuilder;
    private DatabaseReference myRef;
    private String username;
    private MapView mapView;
    private GoogleMap gmap;
    private TextToSpeech toSpeech;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double longitude;
    private double latitude;
    private Button back;
    private Switch voice;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>1 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle("MapViewBundleKey");
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle("MapViewBundleKey", mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        gmap.setIndoorEnabled(true);
        UiSettings uiSettings = gmap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        Log.d("y", latitude + " " + longitude);
        LatLng ny = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ny);
        gmap.addMarker(markerOptions);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        back=(Button) findViewById(R.id.stopdetect);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSpeech.stop();

                startActivity(new Intent(Detection.this,MainMenu.class));
                finish();
            }
        });
        voice=(Switch) findViewById(R.id.voice_switch);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setVisibility(View.INVISIBLE);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null)
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey");
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        status = (TextView) findViewById(R.id.status);
        mSensorManager.registerListener(Detection.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        x = (TextView) findViewById(R.id.xc);
        y = (TextView) findViewById(R.id.yc);
        z = (TextView) findViewById(R.id.zc);
        //       mBuilder=new NotificationCompat.Builder(this);
        //      mBuilder.setAutoCancel(true);
        username = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(username);
        toSpeech = new TextToSpeech(Detection.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS)
                    result = toSpeech.setLanguage(Locale.ENGLISH);
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        onLocationChanged(location);
    }
    public void TTS(String text)
    {
        toSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor,int i)
    {

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Log.d("e", " X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z:" + sensorEvent.values[2]);
        int xa=(int) sensorEvent.values[0];
        int ya=(int) sensorEvent.values[1];
        int za=(int) sensorEvent.values[2];
        x.setText("X: " + xa);
        y.setText("Y: " + ya);
        z.setText("Z: " + za);
        if(Child.val==0)
        {if (  sensorEvent.values[2] >= 9 ||  sensorEvent.values[2]<=-9)
        {
    //        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
    //        mBuilder.setSmallIcon(R.drawable.common_google_signin_btn_icon_light);
    //        mBuilder.setContentTitle("Alert");
     //       mBuilder.setContentText("Phone has been fallen with x coordinate acceleration "+sensorEvent.values[0]+" y coordinate acceleration "+sensorEvent.values[1]+" z coordinate acceleration "+sensorEvent.values[2]);
    //        Intent intent=new Intent(this,Detection.class);
    //        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
     //       mBuilder.setContentIntent(pendingIntent);
     //       NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
     //       nm.notify(45612,mBuilder.build());

            Timestamp r=new Timestamp(System.currentTimeMillis());
            String time=" "+r;
            status.setText("Pothole has been detected at "+time+"at the location shown on the map with \n x coordinate acceleration "+sensorEvent.values[0]+"\n y coordinate acceleration "+sensorEvent.values[1]+"\n z coordinate acceleration "+sensorEvent.values[2]+"\nOur Cloud has been updated.");
            Child x=new Child();
            x.setPriority(Thread.MIN_PRIORITY);
            x.start();
            String email_id=FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String id=myRef.push().getKey();
            Geocoder geocoder= new Geocoder(getApplicationContext(),Locale.getDefault());
            String address= "";
            try
            {
                List<Address> addressList=geocoder.getFromLocation(latitude,longitude,1);
                if(addressList!=null && addressList.size()>0)
                {
                    if(addressList.get(0).getSubLocality()!=null)
                        address+=addressList.get(0).getSubLocality()+" ";
                    if(addressList.get(0).getSubThoroughfare()!=null)
                        address+=addressList.get(0).getSubThoroughfare()+" ";
                    if(addressList.get(0).getThoroughfare()!=null)
                        address+=addressList.get(0).getThoroughfare()+" ";
                    if(addressList.get(0).getLocality()!=null)
                        address+=addressList.get(0).getLocality()+" ";
                    if(addressList.get(0).getPostalCode()!=null)
                        address+=addressList.get(0).getPostalCode()+" ";
                    if(addressList.get(0).getCountryName()!=null)
                        address+=addressList.get(0).getCountryName()+" ";
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            acceleration p=new acceleration(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2],time,email_id,address,latitude,longitude);
            myRef.child(id).setValue(p);
            mapView.setVisibility(View.VISIBLE);

            TTS("A pothole has just been detected at "+address+".\nOur database has been updated. Thank You. ");
    }}
}

}
