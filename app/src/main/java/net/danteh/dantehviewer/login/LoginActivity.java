package net.danteh.dantehviewer.login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.florent37.shapeofview.shapes.ArcView;
import com.google.android.material.button.MaterialButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import net.danteh.dantehviewer.MainActivity;
import net.danteh.dantehviewer.R;


public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    CheckBox checkBox;
    Button signIn;
    MaterialButton signUp;
    LinearLayout linearlayout;
    ArcView arcView;
    ImageView dantehView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signUp = findViewById(R.id.signupbtn);
        email = findViewById(R.id.user_input_login);
        password = findViewById(R.id.password_input_login);
        linearlayout = findViewById(R.id.linearlayout);
        arcView = findViewById(R.id.arcview);
//      checkBox = (CheckBox)findViewById(R.id.checkbox);
        signIn = findViewById(R.id.loginbtn);
        dantehView = findViewById(R.id.danteh);

        signIn.setOnClickListener(new View.OnClickListener() {
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
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "SignIn:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,
                        Pair.create(signIn, "loginbtn"),
                        Pair.create(email, "usernametrans"),
                        Pair.create(signUp,"signupbtn"),
                        Pair.create(dantehView,"danteh"),
                        Pair.create(password, "passtrans"));
                startActivity(i, transitionActivityOptions.toBundle());
            }
        });
    }
}