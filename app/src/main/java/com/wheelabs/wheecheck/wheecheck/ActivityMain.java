package com.wheelabs.wheecheck.wheecheck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class ActivityMain extends Activity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button bSignUp, bLogIn;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Parse.com
        Parse.initialize(this, "DS51bI5NWK50Juxj66qNrumszEOdEHmzUopU3mRK", "FCvutL8DQR7DfmdOdVuEgiex9VRtcsWwvcX8QU4e");
        // XML Bridge
        XMLBrigde();

        try {
            // Get Current User and check if login details exist
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                final String user = currentUser.getString("userType");
                Intent auh = new Intent(ActivityMain.this, ActivityUserHome.class);
                startActivity(auh);
            }
        } catch(Exception e){
            Toast.makeText(ActivityMain.this, "Please login", Toast.LENGTH_LONG).show();
        }

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
                new ParseLogIn().execute();
                break;
        }
    }

    //New Thread for Log In action
    class ParseLogIn extends AsyncTask<String, Integer, String>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ActivityMain.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading...");
            dialog.setMessage("Please wait.");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            // Get input variables
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            ParseUser.logInInBackground(email, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null){
                        dialog.dismiss();
                        Intent userHome = new Intent(ActivityMain.this, ActivityUserHome.class);
                        startActivity(userHome);
                    }else {
                        dialog.dismiss();
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

// End of ActivityMain.class
}
