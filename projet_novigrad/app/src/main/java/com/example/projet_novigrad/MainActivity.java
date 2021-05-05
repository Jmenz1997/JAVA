package com.example.projet_novigrad;


import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private RadioButton radButton;
    private RadioGroup radGroup;
    private TextView errorMessage;
    private FirebaseAuth firebaseAuth;
    public static final String EXTRA_TEXT2 = "com.example.projet_novigrad.EXTRA_TEXT2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        errorMessage = findViewById(R.id.errorMessageLogin);
        Button loginButton = (Button) findViewById(R.id.login);
        Button register =(Button) findViewById(R.id.button);
        radGroup = findViewById(R.id.groupBox);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                configure();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity (intent);
            }
        });
    }

    public void configure(){
        //@Override
        if(sValidate()){
            EditText editText1 = (EditText) findViewById(R.id.username);
            String user = editText1.getText().toString();

            EditText editText2 = (EditText) findViewById(R.id.password);
            String pass = editText2.getText().toString();

            int selected = radGroup.getCheckedRadioButtonId();
            RadioButton role = (RadioButton)findViewById(selected);
            String Type = role.getText().toString();

            validate(username.getText().toString(), password.getText().toString(),Type);
        }
    }


    public boolean sValidate(){
        boolean result=true;
        if(username.getText().toString().equals("")){
            errorMessage.setText("! Username is empty");
            result= false;
        }
        if(password.getText().toString().equals("")){
            errorMessage.setText("! Password is empty");
            result= false;
        }
        if (radGroup.getCheckedRadioButtonId() == -1){
            errorMessage.setText("! Account type not selected");
            return false;
        }
        return result;
    }

    public void validate(final String userName, String userPassword, final String userType){

        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Welcome.class);
                    intent.putExtra(EXTRA_TEXT2, userType); // send the username and type to welcome page
                    startActivity (intent);

                }else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Registration.class);
                    startActivity (intent);
                }

            }
        });
    }
}