package net.danteh.dantehviewer.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import net.danteh.dantehviewer.MainActivity;
import net.danteh.dantehviewer.R;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;


public class LoginActivity extends AppCompatActivity{

    Toolbar toolbar;
    EditText email,password;
    CheckBox checkBox;
    Button signin;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

//        email = (EditText)findViewById(R.id.email);
//        password = (EditText)findViewById(R.id.password);
//
//        checkBox = (CheckBox)findViewById(R.id.checkbox);
//
        signin =findViewById(R.id.loginbtn);

        //signup = findViewById(R.id.signup);

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
//        signup.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
//                startActivity(i);
//            }
//        });
    }
}