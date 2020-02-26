package net.danteh.dantehviewer.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import net.danteh.dantehviewer.MainActivity;
import net.danteh.dantehviewer.R;

public class SignUpActivity extends AppCompatActivity {

    EditText email_input, password_input, name_input, phone_input;
    CheckBox checkBox_input;
    MaterialButton signup, signin;
    String name, email, pass, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        email_input = findViewById(R.id.email);
        password_input = findViewById(R.id.password);
        name_input = findViewById(R.id.name);
        phone_input = findViewById(R.id.phone);
        checkBox_input = findViewById(R.id.checkbox);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "Sign Up Button Clicked", Toast.LENGTH_LONG).show();
                name = name_input.getText().toString().toLowerCase().trim();
                email = email_input.getText().toString().toLowerCase().trim();
                pass = password_input.getText().toString().trim();
                phone = phone_input.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty())
                    Toast.makeText(SignUpActivity.this, "لطفا فرم را کامل پر کنید.", Toast.LENGTH_LONG).show();
                else {
                    ParseUser user = new ParseUser();
                    user.setUsername(name);
                    user.setPassword(pass);
                    user.setEmail(email);
                    user.put("phone", phone);

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                if (e.getCode() == 202)
                                    Toast.makeText(SignUpActivity.this, "این نام کاربری از قبل انتخاب شده.", Toast.LENGTH_SHORT).show();
                                else if (e.getCode() == 203)
                                    Toast.makeText(SignUpActivity.this, "این ایمیل قبلا ثبت شده.", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(SignUpActivity.this, e.getCode() + " : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}