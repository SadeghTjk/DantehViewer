package net.danteh.dantehviewer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import net.danteh.dantehviewer.fragments.LinkFragment;

import java.util.Locale;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements LinkFragment.OnFragmentInteractionListener {
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView pointCounter;
    WebView webView;
    EditText editText;
    MaterialToolbar toolbar;
    MaterialButton sync_btn;
    Record user = new Record();

    String number;
    int point = 1;
    int i = 0;
    public Retrofit retrofit = null;
    public final static String TAG = "webview";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        webView = findViewById(R.id.wv);
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

        webView.getSettings().setJavaScriptEnabled(true);

        sync_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        user = ApiCaller.userInfo(context,retrofit,1);
                        editText.setText(user.getEmail());
                        updateNav();
                    }
                },3000);
            }
        });

        //Navigation View
        View headerView = navigationView.getHeaderView(0);
        pointCounter = headerView.findViewById(R.id.point_counter);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.opend,R.string.closed);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.submit_link:
                        Fragment fragment = new LinkFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

                    case R.id.home_page:
                        Log.e(TAG, "onNavigationIlected: ");
                    case R.id.point_collector:
                        Log.e(TAG, "onNavigatiotemSelected: ");
                    case R.id.logout:
                        Log.e(TAG, "onNavigmSelected: ");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });



         //webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                Log.e(TAG, "onPageFinished: LOADED");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        point++;
                        if (i < urls.length) {
                            ApiCaller.updatePoints(getApplicationContext(),retrofit,1,point);
                            Toast.makeText(MainActivity.this, "یک امتیاز اضافه شد!", Toast.LENGTH_SHORT).show();
                            pointCounter.setText(point+ " امتیاز ");
                            webView.loadUrl(urls[i]);
                            i++;
                        }
                    }

                }, 20000);
            }
        });


        number = pointCounter.getText().toString().replaceAll("\\D+","");
        point = Integer.parseInt(number)+1;
        webView.loadUrl(urls[i]);
        i++;

    }

    private void updateNav() {
        Log.e(TAG, "updateNav: "+user.getPoint() );
        pointCounter.setText(String.valueOf(user.getPoint())+ " امتیاز ");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
//        switch (item.getItemId()) {
////            case android.R.id.home:
////                drawerLayout.openDrawer(GravityCompat.START);
////                return true;
////        }
        if (toggle.onOptionsItemSelected(item)) {
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
}
