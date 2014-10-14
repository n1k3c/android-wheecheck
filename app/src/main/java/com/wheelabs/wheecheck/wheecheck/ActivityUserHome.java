package com.wheelabs.wheecheck.wheecheck;

import android.app.Activity;
import android.os.Bundle;
import com.parse.Parse;
import com.parse.ParseAnalytics;

/**
 * Created by nikola on 10/14/14.
 */
public class ActivityUserHome extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        // Parse.com
        Parse.initialize(this, "DS51bI5NWK50Juxj66qNrumszEOdEHmzUopU3mRK", "FCvutL8DQR7DfmdOdVuEgiex9VRtcsWwvcX8QU4e");
    }
}
