package net.danteh.dantehviewer.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import net.danteh.dantehviewer.MainActivity;
import net.danteh.dantehviewer.R;


public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    CheckBox checkBox;
    Button signin;
    ImageButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fb = findViewById(R.id.fb);
        email = findViewById(R.id.email_input_login);
        password = findViewById(R.id.password_input_login);
//
//        checkBox = (CheckBox)findViewById(R.id.checkbox);
//
        signin = findViewById(R.id.loginbtn);

        //signup = findViewById(R.id.signup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if (mail.isEmpty() || pass.isEmpty())
                    Toast.makeText(LoginActivity.this, "فیلد ها را کامل پر کنید", Toast.LENGTH_SHORT).show();
                else {
                    ParseUser.logInInBackground(mail, pass, new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(LoginActivity.this, "SignIn:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);

            }
        });
    }
}