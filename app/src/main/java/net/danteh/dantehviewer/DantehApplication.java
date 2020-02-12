package net.danteh.dantehviewer;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class DantehApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("DantehView")
                .clientKey("DantehView27561")
                .server("http://188.40.170.72:1337/parse/")
                .build()
        );
        Parse.checkInit();
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}
