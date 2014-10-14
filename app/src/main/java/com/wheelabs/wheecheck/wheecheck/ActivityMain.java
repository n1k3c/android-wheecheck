package com.wheelabs.wheecheck.wheecheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;


public class ActivityMain extends Activity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button bSignUp, bLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Parse.com
        Parse.initialize(this, "DS51bI5NWK50Juxj66qNrumszEOdEHmzUopU3mRK", "FCvutL8DQR7DfmdOdVuEgiex9VRtcsWwvcX8QU4e");
        // XML Bridge
        XMLBrigde();

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }

    public void XMLBrigde() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        bLogIn = (Button) findViewById(R.id.bLogIn);
        bSignUp.setOnClickListener(this);
        bLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bSignUp:
                Intent s = new Intent(this, ActivitySignUp.class);
                startActivity(s);
                break;
            case R.id.bLogIn:
                Intent l = new Intent(this, ActivityUserHome.class);
                startActivity(l);
                break;
        }
    }
}
