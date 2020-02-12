package net.danteh.dantehviewer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import net.danteh.dantehviewer.fragments.LinkFragment;
import net.danteh.dantehviewer.fragments.WebViewFragment;

import java.security.Provider;
import java.util.List;

import okhttp3.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements LinkFragment.OnFragmentInteractionListener,WebViewFragment.OnFragmentInteractionListener {
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView pointCounter;

    EditText editText;
    MaterialToolbar toolbar;
    MaterialButton sync_btn;
    User user = new User();

    String number;

    int i = 0;
    public Retrofit retrofit = null;
    public final static String TAG = "webview";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();


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

        final String[] urls = {"https://www.all.ir/", "https://www.all.ir/%d8%b3%d8%a7%d9%86%d8%af%d8%a8%d8%a7%d8%b1-%d8%b3%d8%a7%d9%85%d8%b3%d9%88%d9%86%da%af-hw-j7591/", "https://www.google.com/"};



        sync_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        user = ApiCaller.userInfo(context,retrofit,1);
                        editText.setText(user.getEmail());
//                        updateNav();
                    }
                },3000);
            }
        });

        //Navigation View


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.submit_link:
                        Fragment fragment = new LinkFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
                        break;
                    case R.id.home_page:

                        break;
                    case R.id.point_collector:
                        Fragment webFragment = new WebViewFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_frame, webFragment, webFragment.getClass().getSimpleName()).addToBackStack(null).commit();
                        break;
                    case R.id.logout:
                        Log.e(TAG, "onNavigmSelected: ");
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });



         //webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);



    }

    private void updateNav() {
        Log.e(TAG, "updateNav: "+user.getPoint() );
        pointCounter.setText(String.valueOf(user.getPoint())+ " امتیاز ");
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
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }


    @Override
    public void onFragmentInteraction(String urlname, String url) {

        int i = ApiCaller.sendLink(context,retrofit,urlname,url);
        Log.e(TAG, "onFragmentInteraction: "+i );
    }

    @Override
    public void onCoinUpdates(int coin) {

    }
}
