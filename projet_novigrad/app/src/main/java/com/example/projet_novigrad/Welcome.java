package com.example.projet_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Welcome extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Intent intent = getIntent();

        TextView welcomeText = (TextView) findViewById(R.id.textView);


        Button addServiceButton = (Button) findViewById(R.id.addServiceButt);
        addServiceButton.setVisibility(View.GONE);

        Button deleteServiceButton = (Button) findViewById(R.id.deleteServiceButt);
        deleteServiceButton.setVisibility(View.GONE);

        Button editServiceButton = (Button) findViewById(R.id.editServiceButt);
        editServiceButton.setVisibility(View.GONE);

        final TextView userName = (TextView) findViewById(R.id.user);
        TextView userType = (TextView) findViewById(R.id.role);

        //userName.setText(intent.getStringExtra(EXTRA_TEXT1));         //ENVOYER LE USERNAME
        userType.setText(intent.getStringExtra(MainActivity.EXTRA_TEXT2));


        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent( // get the ID of the provider in firebase
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            if (intent.getStringExtra(MainActivity.EXTRA_TEXT2).equals(String.valueOf(dsp.child("username").getValue().toString())) && dsp.child("roleType").getValue().toString().equals("Service Provider")) {  //find the provider in firebase and get ID
                                uid = dsp.getKey();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

        if (userType.getText().equals("Admin")) {

            addServiceButton.setVisibility(View.VISIBLE);

            addServiceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAddServices();
                }
            });

            deleteServiceButton.setVisibility(View.VISIBLE);
            deleteServiceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDeleteServices();
                }
            });

            editServiceButton.setVisibility(View.VISIBLE);
            editServiceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEditServices();
                }
            });
        }

        if (userType.getText().equals("Client")) {
            welcomeText.setText(welcomeText.getText() + "vous êtes enregistré en tant que " );

        }


        if (userType.getText().equals("Employee")) {                             // provider tools show when provider is logged in

            welcomeText.setText(welcomeText.getText() + " vous êtes enregistré en tant que " );
        }
    }

        public void openAddServices(){
            Intent intent = new Intent(this, AddService.class);
            startActivity(intent);
        }
        public void openDeleteServices(){
            Intent intent = new Intent(this, DeleteService.class);
            startActivity(intent);
        }
        public void openEditServices(){
            Intent intent = new Intent(this, EditService.class);
            startActivity(intent);
        }
}

