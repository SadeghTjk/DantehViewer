package net.danteh.dantehviewer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import net.danteh.dantehviewer.fragments.EditLinksFragment;
import net.danteh.dantehviewer.fragments.LinkFragment;
import net.danteh.dantehviewer.fragments.LinkHomeFragment;
import net.danteh.dantehviewer.fragments.WebViewFragment;

import java.security.Provider;
import java.util.List;

import okhttp3.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements LinkFragment.OnFragmentInteractionListener, WebViewFragment.OnFragmentInteractionListener, EditLinksFragment.OnFragmentInteractionListener {
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView pointCounter;
    int has = 0;
    EditText editText;
    MaterialToolbar toolbar;
    MaterialButton sync_btn;
    User user = new User();
    ParseObject gameScore;
    String number;
    Fragment webViewFragment,linkHomeFragment;
    int i = 0;
    public Retrofit retrofit = null;
    public final static String TAG = "webview";
    Context context;
    private int clickedNavItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//
//                        // Log and toast
//                        String msg = token;
//                        Log.e(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

        editText = findViewById(R.id.et);
        sync_btn = findViewById(R.id.button);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawer_layout);

        ApiCaller.adminloger(context, retrofit);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_humberger);

        webViewFragment = new WebViewFragment();
        linkHomeFragment = new LinkHomeFragment();

        final String[] urls = {"https://www.all.ir/", "https://www.all.ir/%d8%b3%d8%a7%d9%86%d8%af%d8%a8%d8%a7%d8%b1-%d8%b3%d8%a7%d9%85%d8%b3%d9%88%d9%86%da%af-hw-j7591/", "https://www.google.com/"};

        sync_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(has==0) {
                //                  gameScore = new ParseObject("GameTest");
//                    gameScore.put("name", "mamadok");
//                    gameScore.put("playerName", "shayan");
//                    gameScore.put("link", 10);
//                    gameScore.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            if (e == null) {
//                                // object will be your game score
//                                Toast.makeText(context, "" + gameScore.getObjectId(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                // something went wrong
//                            }
//                        }
//                    });
//                    has =1;
//                }
//                else if (has == 1){

//               ParseQuery<ParseObject> query = ParseQuery.getQuery("GameTest");
//                query.getInBackground(gameScore.getObjectId(), new GetCallback<ParseObject>() {
//                    public void done(ParseObject object, ParseException e) {
//                        if (e == null) {
//                            Log.e(TAG, "done: "+gameScore.getString("playerName"));
//                        } else {
//                            Log.e(TAG, "went wrong ",e );
//                        }
//                    }
//                });
//                gameScore.fetchInBackground(new GetCallback<ParseObject>() {
//                    public void done(ParseObject object, ParseException e) {
//                        if (e == null) {
//                            Toast.makeText(context, "SUCCCCCCCCCCCCC", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "you SUC"+e.getMessage() +"\n"+e.getCode() +"\n" +e.getCause(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        user = ApiCaller.userInfo(context, retrofit, 1);
                        editText.setText(user.getEmail());
//                        updateNav();
                    }
                }, 3000);
            }
        });

        //Navigation View


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.submit_link:
                        clickedNavItem = R.id.submit_link;
                        sync_btn.setVisibility(View.GONE);
                        editText.setVisibility(View.GONE);
                        break;
                    case R.id.home_page:
                        clickedNavItem = R.id.home_page;
                        sync_btn.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.VISIBLE);
                        break;
                    case R.id.point_collector:
                        clickedNavItem = R.id.point_collector;
                        sync_btn.setVisibility(View.GONE);
                        editText.setVisibility(View.GONE);
                        break;
                    case R.id.logout:
                        clickedNavItem = R.id.logout;
                        Log.e(TAG, "onNavigmSelected: ");
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        //hack to prevent drawer lag
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerClosed(View drawerView) {
                switch (clickedNavItem) {
                    case R.id.home_page:

                        break;
                    case R.id.submit_link:
                        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fragment_frame, linkHomeFragment, linkHomeFragment.getClass().getSimpleName()).addToBackStack(null).commit();
                        break;
                    case R.id.point_collector:
                        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fragment_frame, webViewFragment, webViewFragment.getClass().getSimpleName()).addToBackStack(null).commit();
                        break;
                }
                super.onDrawerClosed(drawerView);
            }
        });

        //webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);


    }

    private void updateNav() {
        Log.e(TAG, "updateNav: " + user.getPoint());
        pointCounter.setText(String.valueOf(user.getPoint()) + " امتیاز ");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }


    @Override
    public void onFragmentInteraction(String urlname, String url) {

        int i = ApiCaller.sendLink(context, retrofit, urlname, url);
        Log.e(TAG, "onFragmentInteraction: " + i);
    }

    @Override
    public void onCoinUpdates(int coin) {

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
