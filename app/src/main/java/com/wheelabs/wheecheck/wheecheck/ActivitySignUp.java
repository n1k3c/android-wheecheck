package com.wheelabs.wheecheck.wheecheck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by nikola on 10/14/14.
 */
public class ActivitySignUp extends Activity implements View.OnClickListener {

    EditText etName, etSurname, etEmail, etPassword;
    Button bSignUp;
    String name, surname, email, password;

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
        switch (view.getId()) {
            case R.id.bSignUp:
                // Execute action in new Thread -> ParseSignUp
                new ParseSignUp().execute();
                break;
        }
    }


    //New Thread for Sign Up action
    class ParseSignUp extends AsyncTask<String, Integer, String> {

        ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ActivitySignUp.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading...");
            dialog.setMessage("Please wait.");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            // Get input variables
            name = etName.getText().toString();
            surname = etSurname.getText().toString();
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            // Set input variables
            ParseUser user = new ParseUser();
            user.setUsername(email);
            user.setPassword(password);
            user.setEmail(email);
            user.put("name", name);
            user.put("surname", surname);

            // Push to Parse.com
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        dialog.dismiss();
                    }else {

                    }
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }

// End of ActivitySignUp.class
}