package com.example.premal2.potholedetector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class retrievedb extends AppCompatActivity {

    ListView acc;
    DatabaseReference accr;
    List<acceleration> o;
    @Override
    protected void onStart() {
        super.onStart();
        accr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                o.clear();
                for(DataSnapshot accval: dataSnapshot.getChildren())
                {

                    acceleration q= accval.getValue(acceleration.class);
                    o.add(q);
                }
                ArtistList adapter= new ArtistList(retrievedb.this,o);
                acc.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrievedb);
        o=new ArrayList<acceleration>();
        accr= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        acc=(ListView) findViewById(R.id.listview_acc);
    }
}
