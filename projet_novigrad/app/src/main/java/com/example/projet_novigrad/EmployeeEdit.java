package com.example.projet_novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeEdit extends AppCompatActivity {


    private EditText Address, phoneNumber, Company, profileDescription;
    private Button addingProfileButt;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_edit);

        mAuth = FirebaseAuth.getInstance();

        final Intent intent = getIntent();
        final String providerUser = intent.getStringExtra(MainActivity.EXTRA_TEXT2);  // retrieve provider name from welcome page intent

        Address = findViewById(R.id.providerAddressText);
        phoneNumber = findViewById(R.id.providerPhoneText);
        Company = findViewById(R.id.providerNameText);
        profileDescription = findViewById(R.id.providerDescriptText);
        addingProfileButt = findViewById(R.id.SetInfoButton);

        addingProfileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statusValidate()) {
                    final String Add = Address.getText().toString().trim();
                    final String phone = phoneNumber.getText().toString().trim();
                    final String comp = Company.getText().toString().trim();
                    final String desc = profileDescription.getText().toString().trim();



                    EmployeeProfile profile = new EmployeeProfile(
                            Add,
                            Integer.parseInt(phone),
                            comp,
                            desc
                    );

                    FirebaseUser user = mAuth.getCurrentUser();
                    FirebaseDatabase.getInstance().getReference("ProviderProfileInfo").child(user.getUid())
                            .setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EmployeeEdit.this, "Profile Creation successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EmployeeEdit.this, EmployeeEdit.class));
                            } else {
                                Toast.makeText(EmployeeEdit.this, "Profile did not create properly", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        Button addService = (Button)findViewById(R.id.providerAddServiceButt);
        Button changeTimes = (Button)findViewById(R.id.changeTimesButt);

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EmployeeEdit.this, EmployeeServices.class);
                intent.putExtra(MainActivity.EXTRA_TEXT2, providerUser);
                startActivity(intent);
            }
        });

        changeTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
               Intent intent = new Intent(EmployeeEdit.this, Calender.class);
                intent.putExtra(MainActivity.EXTRA_TEXT2, providerUser);
                startActivity(intent);
            }
        });
    }

    public boolean statusValidate() {
        if (Company.getText().toString().trim().equals(""))  {
            Toast.makeText(EmployeeEdit.this, "Company Name is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNumber.getText().toString().trim().equals("")){
            Toast.makeText(EmployeeEdit.this, "Phone Number is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Address.getText().toString().equals("")) {
            Toast.makeText(EmployeeEdit.this, "Address is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}



