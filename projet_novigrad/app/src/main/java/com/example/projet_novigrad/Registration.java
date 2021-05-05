package com.example.projet_novigrad;
//ajouter progressbar
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Registration extends AppCompatActivity {


    private EditText username, password, passwordConfirm,name;
    private Button registerButton;
    private TextView errorMessage;
    private FirebaseAuth mAuth;
    private RadioGroup radGroup;
    final String roleType=" ";
    private  RadioButton role;
    public static final String EXTRA_TEXT2 = "com.example.projet_novigrad.EXTRA_TEXT2";
    public static final String EXTRA_TEXT1 = "com.example.projet_novigrad.EXTRA_TEXT1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.user);
        name=findViewById(R.id.username);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.repeat_password);
        registerButton = findViewById(R.id.register_btn);
        radGroup= findViewById(R.id.groupBox);
        errorMessage = findViewById(R.id.error);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (statusValidate()){
                    final String Username = username.getText().toString().trim();
                    final String Password = password.getText().toString().trim();
                    //final String Name = name.getText().toString().trim();
                    int selected=radGroup.getCheckedRadioButtonId();
                    role= findViewById(selected);
                    final String roleType = role.getText().toString().trim();


                    mAuth.createUserWithEmailAndPassword(Username,Password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                User user = new User(
                                        Username,
                                        roleType
                                );

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(Registration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Registration.this, Welcome.class);
                                            //intent.putExtra(EXTRA_TEXT1, Name);
                                            intent.putExtra(EXTRA_TEXT2, roleType);
                                            startActivity (intent);
                                        } else {
                                            Toast.makeText(Registration.this, "Registration f", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });
    }

    public boolean statusValidate() {

        if (password.getText().toString().equals(passwordConfirm.getText().toString()) != true) {
            errorMessage.setText("! Passwords do not match");
            return false;
        }
/*        if (role.getText().toString().equals("")) {
            errorMessage.setText("! RoleType is empty");
            return false;
        }*/
        if (username.getText().toString().equals("")) {
            errorMessage.setText("! Username is empty");
            return false;
        }
        if (password.getText().toString().trim().equals("")) {
            errorMessage.setText("! Password is empty");
            return false;
        }
        if (password.getText().toString().trim().length() < 6) {
            errorMessage.setText("Password too short, enter minimum 6 character");
            return false;
        }if (radGroup.getCheckedRadioButtonId() == -1){
            errorMessage.setText("! Account type not selected");
            return false;
        }


        return true;
    }
}











