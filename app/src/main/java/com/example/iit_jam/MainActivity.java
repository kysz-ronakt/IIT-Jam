package com.example.iit_jam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName= findViewById(R.id.firstName);
        lastName =  findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pwd);
        register = findViewById(R.id.idBtnRegister);

        //onclick event
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered(); //this method is to be created later for the
            }
        });

    }

    boolean isEmailEmpty(EditText editText){
        CharSequence  email = editText.getText().toString();
        return(!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText editText){
        CharSequence sequence = editText.getText().toString();
        return TextUtils.isEmpty(sequence);
    }

    void checkDataEntered(){
        if (isEmpty(firstName)){
            Toast toast = Toast.makeText(this, "please enter the firstName", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (isEmpty(lastName)){
            Toast toast = Toast.makeText(this, "please enter the lastName", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (isEmailEmpty(email)==false){
            email.setError("Enter valid email.");
        }
    }
}