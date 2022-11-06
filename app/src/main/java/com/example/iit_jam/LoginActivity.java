package com.example.iit_jam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

     EditText email;
     EditText password;
     Button login;
     Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.pwd);
        login = findViewById(R.id.idBtnLogin);
        register = findViewById(R.id.idBtnRegister);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmail();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

        void checkEmail(){
                boolean isValid = true;
                if (!isEmailEmpty(email)){
                    email.setError("You must enter email to login");
                    isValid = false;
                }

                if (!isEmpty(password)){
                    password.setError("you must enter password to login");
                    isValid = false;
                }else{
                        if (password.getText().toString().length()<8){
                            password.setError("password must be at least 8 characters long");
                        }
                }

                if (isValid){
                    String emailValue = email.getText().toString();
                    String passwordValue = password.getText().toString();
                    if (emailValue.equals("hub.itjam@gmail.com")&& passwordValue.equals("password1234")){
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        this.finish();
                    }else{
                        Toast t = Toast.makeText(this, "wrong email or password", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }
        }


    boolean isEmailEmpty(EditText editText){
        CharSequence  email = editText.getText().toString();
        return(!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText editText){
        CharSequence sequence = editText.getText().toString();
        return TextUtils.isEmpty(sequence);
    }




}