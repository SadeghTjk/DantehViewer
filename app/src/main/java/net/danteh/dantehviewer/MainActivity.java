package net.danteh.dantehviewer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.livequery.LiveQueryException;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.ParseLiveQueryClientCallbacks;

import net.danteh.dantehviewer.fragments.AboutFragment;
import net.danteh.dantehviewer.fragments.EditLinksFragment;
import net.danteh.dantehviewer.fragments.GuideFragment;
import net.danteh.dantehviewer.fragments.HomeFragment;
import net.danteh.dantehviewer.fragments.LinkFragment;
import net.danteh.dantehviewer.fragments.LinkHomeFragment;
import net.danteh.dantehviewer.fragments.WebViewFragment;
import net.danteh.dantehviewer.login.LoginActivity;
import net.danteh.dantehviewer.viewmodels.MainViewModel;

import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements LinkFragment.OnFragmentInteractionListener, WebViewFragment.OnFragmentInteractionListener, EditLinksFragment.OnFragmentInteractionListener {
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView pointCounter;
    int has = 0;
    MaterialToolbar toolbar;
    User user = new User();
    ParseObject gameScore;
    String number;
    Fragment webViewFragment, linkHomeFragment,guideFragment,aboutFragment, homeFragment;
    int i = 0;
    Intent loginIntent;
    public Retrofit retrofit = null;
    public final static String TAG = "DANTEH VIEW";
    Context context;
    private int clickedNavItem = 0;
    public static ParseLiveQueryClient parseLiveQueryClient;
    // public SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawer_layout);

        // ApiCaller.adminloger(context, retrofit);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_humberger);

        ParseUser currentUser = ParseUser.getCurrentUser();
        loginIntent = new Intent(MainActivity.this, LoginActivity.class);

        MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);
        parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
        parseLiveQueryClient.registerListener(new ParseLiveQueryClientCallbacks() {
            @Override
            public void onLiveQueryClientConnected(ParseLiveQueryClient client) {
                Log.e(TAG, "onLiveQueryClientConnected: CONNECTED");
            }

            @Override
            public void onLiveQueryClientDisconnected(ParseLiveQueryClient client, boolean userInitiated) {
                Log.e(TAG, "onLiveQueryClientDisconnected: " + userInitiated);
            }

            @Override
            public void onLiveQueryError(ParseLiveQueryClient client, LiveQueryException reason) {
                Log.e(TAG, "onLiveQueryError: " + reason.getMessage() + "\n" + client.toString());
                reason.printStackTrace();
            }

            @Override
            public void onSocketError(ParseLiveQueryClient client, Throwable reason) {
                Log.e(TAG, "onSocketError: " + reason.getMessage());
                reason.printStackTrace();
            }
        });

        if (currentUser != null) {
            Toast.makeText(context, "Welcome " + currentUser.getUsername(), Toast.LENGTH_SHORT).show();
        } else {
            startActivity(loginIntent);
        }

        webViewFragment = new WebViewFragment();
        linkHomeFragment = new LinkHomeFragment();
        homeFragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment_frame, homeFragment, homeFragment.getClass().getSimpleName()).addToBackStack(null).commit();

        //Navigation View
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.submit_link:
                        clickedNavItem = R.id.submit_link;
                        break;
                    case R.id.home_page:
                        clickedNavItem = R.id.home_page;
                        break;
                    case R.id.point_collector:
                        clickedNavItem = R.id.point_collector;
                        break;
                    case R.id.about_us:
                        aboutFragment = new AboutFragment();
                        clickedNavItem = R.id.about_us;
                        break;
                    case R.id.guide:
                        guideFragment = new GuideFragment();
                        clickedNavItem = R.id.guide;
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
                        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fragment_frame, homeFragment, homeFragment.getClass().getSimpleName()).addToBackStack(homeFragment.getTag()).commit();
                        break;
                    case R.id.submit_link:
                        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fragment_frame, linkHomeFragment, linkHomeFragment.getClass().getSimpleName()).addToBackStack(linkHomeFragment.getTag()).commit();
                        break;
                    case R.id.point_collector:
                        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fragment_frame, webViewFragment, webViewFragment.getClass().getSimpleName()).addToBackStack(null).commit();
                        break;
                    case R.id.about_us:
                        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fragment_frame, aboutFragment, aboutFragment.getClass().getSimpleName()).addToBackStack(aboutFragment.getTag()).commit();
                        break;
                    case R.id.guide:
                        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fragment_frame, guideFragment, guideFragment.getClass().getSimpleName()).addToBackStack(guideFragment.getTag()).commit();
                        break;
                    case R.id.logout:
                        ParseUser.logOutInBackground();
                        startActivity(loginIntent);
                        finish();
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
