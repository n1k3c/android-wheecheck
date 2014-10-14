package com.wheelabs.wheecheck.wheecheck;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseAnalytics;

/**
 * Created by nikola on 10/14/14.
 */
public class ActivitySignUp extends Activity implements View.OnClickListener {

    EditText etName, etSurname, etEmail, etPassword;
    Button bSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Parse.com
        Parse.initialize(this, "DS51bI5NWK50Juxj66qNrumszEOdEHmzUopU3mRK", "FCvutL8DQR7DfmdOdVuEgiex9VRtcsWwvcX8QU4e");
        // XML Bridge
        XMLBridge();
    }

    public void XMLBridge() {
        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        bSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bSignUp:
                Toast.makeText(this, "Sign up was clicked.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
