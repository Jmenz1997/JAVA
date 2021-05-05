package com.example.projet_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeInfo extends AppCompatActivity {
    String providerID = "not changed";
    final ArrayList<String> providerServiceList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);
        final Intent intent = getIntent();
        final String providerUsername = intent.getStringExtra(MainActivity.EXTRA_TEXT2);
        Spinner myServiceList = (Spinner)findViewById(R.id.myproviderServices);



        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(    // get the ID of the provider in firebase
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            if(providerUsername.equals(String.valueOf(dsp.child("username").getValue().toString())) && dsp.child("roleType").getValue().toString().equals("Service Provider")) {  //find the provider in firebase and get ID
                                providerID = dsp.getKey();
                            }
                        }
                        for (DataSnapshot dsp : dataSnapshot.child(providerID).child("myServices").getChildren()) {
                            Service temp = new Service("a", 0);
                            temp.setServiceName(String.valueOf(dsp.child("serviceName").getValue()));
                            providerServiceList.add(temp.toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(                // fill the spinner in the page with contents
                this, android.R.layout.simple_spinner_item, providerServiceList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myServiceList.setAdapter(adapter);
}
}

