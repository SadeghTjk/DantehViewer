package net.danteh.dantehviewer;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;
import com.parse.livequery.ParseLiveQueryClient;

import ir.tapsell.sdk.Tapsell;

public class DantehApplication extends Application {
   // public SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences();
   private static final String TAG = "DANTEHAPP";
    public static ParseLiveQueryClient parseLiveQueryClient;
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("NotTodaySatanNotToday")
                .clientKey("DantehView")
                .server("http://178.63.245.234:1337/parse/")
                .build()
        );

//        Parse.checkInit();
//        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        ParseInstallation installation =ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId","336238375701");
        installation.put("FCMSenderId","336238375701");
        installation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully installed.");
                } else {
                    Log.e("com.parse.push", "failed to install", e);
                }
            }
        });
        Tapsell.initialize(this, "ightjqhercgjesqefpfmmlinharmaihnfpiinccjthsljdaqhoplsktretjbglkhcbblsh");

//        ParsePush.subscribeInBackground("", new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
//                } else {
//                    Log.e("com.parse.push", "failed to subscribe for push", e);
//                }
//            }
//        });
    }
}
