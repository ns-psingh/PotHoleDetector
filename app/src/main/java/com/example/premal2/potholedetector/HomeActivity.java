package com.example.premal2.potholedetector;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button mLogout;
    private FirebaseAuth mAuth;
    private TextView email;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth=FirebaseAuth.getInstance();
        email=(TextView) findViewById(R.id.email);
        email.setText("Welcome "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+" to PotHole Detector. Please Select a lanuage to proceed");
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(HomeActivity.this,MainActivity.class));
                }
            }
        };
        mLogout=(Button) findViewById(R.id.logoutbtn);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });
        button1=(Button) findViewById(R.id.englishbtn);
        button2=(Button) findViewById(R.id.hindibtn);
        button3=(Button) findViewById(R.id.gujratibtn);
        button4=(Button) findViewById(R.id.punjabibtn);
        button5=(Button) findViewById(R.id.marathibtn);
        button6=(Button) findViewById(R.id.bengalibtn);
        button7=(Button) findViewById(R.id.telugubtn);
        button8=(Button) findViewById(R.id.kannadabtn);
        button9=(Button) findViewById(R.id.tamilbtn);
        button10=(Button) findViewById(R.id.malayalambtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MainMenu.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Sorry, Hindi is currently unavailable.क्षमा करें, हिंदी वर्तमान में अनुपलब्ध है।", Toast.LENGTH_LONG).show();
                // Toast.makeText(MainActivity.this,"You got an error.",Toast.LENGTH_LONG).show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Sorry, Gujarati is currently unavailable.માફ કરશો, ગુજરાતી હાલમાં અનુપલબ્ધ છે.", Toast.LENGTH_LONG).show();
                // Toast.makeText(MainActivity.this,"You got an error.",Toast.LENGTH_LONG).show();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Sorry, Punjabi is currently unavailable.ਮੁਆਫ ਕਰਨਾ, ਪੰਜਾਬੀ ਫਿਲਹਾਲ ਅਣਉਪਲਬਧ ਹੈ.", Toast.LENGTH_LONG).show();
                // Toast.makeText(MainActivity.this,"You got an error.",Toast.LENGTH_LONG).show();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Sorry, Marathi is currently unavailable.क्षमस्व, मराठी सध्या अनुपलब्ध आहे.", Toast.LENGTH_LONG).show();
                // Toast.makeText(MainActivity.this,"You got an error.",Toast.LENGTH_LONG).show();
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Sorry, Bengali is currently unavailable.দুঃখিত, বাংলা বর্তমানে অনুপলব্ধ।", Toast.LENGTH_LONG).show();
                // Toast.makeText(MainActivity.this,"You got an error.",Toast.LENGTH_LONG).show();
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Sorry, Telugu is currently unavailable.\n" +
                        "క్షమించండి, తెలుగు ప్రస్తుతం అందుబాటులో లేదు.", Toast.LENGTH_LONG).show();
                // Toast.makeText(MainActivity.this,"You got an error.",Toast.LENGTH_LONG).show();
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Sorry, Kannada is currently unavailable.ಕ್ಷಮಿಸಿ, ಕನ್ನಡವು ಪ್ರಸ್ತುತ ಲಭ್ಯವಿಲ್ಲ.", Toast.LENGTH_LONG).show();
                // Toast.makeText(MainActivity.this,"You got an error.",Toast.LENGTH_LONG).show();
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Sorry, Tamil is currently unavailable.\n" +
                        "மன்னிக்கவும், தமிழ் தற்போது கிடைக்கவில்லை.", Toast.LENGTH_LONG).show();
                // Toast.makeText(MainActivity.this,"You got an error.",Toast.LENGTH_LONG).show();
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Sorry, Malayalam is currently unavailable.ಕക്ഷമിക്കണം, മലയാളം നിലവിൽ ലഭ്യമല്ല", Toast.LENGTH_LONG).show();
                // Toast.makeText(MainActivity.this,"You got an error.",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
