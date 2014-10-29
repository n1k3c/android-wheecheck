package com.wheelabs.wheecheck.wheecheck;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;


/**
 * Created by nikola on 10/16/14.
 */
public class FragmentUserHomeContent extends Fragment implements View.OnClickListener {

    Button bScan;
    TextView tvResult;
    String result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home_content, container, false);
        tvResult = (TextView) view.findViewById(R.id.tvResult);
        bScan = (Button) view.findViewById(R.id.bScan);
        bScan.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent s = new Intent(getActivity(), ActivityScanner.class);
        startActivityForResult(s, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        result = data.getStringExtra("result");
        if (result != null){
            tvResult.setText("Your classrom: " + result);
            new SendResultToParse().execute(result);
        }else {
            Toast.makeText(getActivity(), "Fail! Please scan QR code again.", Toast.LENGTH_SHORT).show();
        }
    }

    class SendResultToParse extends AsyncTask<String, Integer, String>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading...");
            dialog.setMessage("Please wait.");
            dialog.show();
        }


        @Override
        protected String doInBackground(String... arg0) {

            // Get parameter from execute()
            final String classroom = arg0[0];

            // Get current user
            ParseUser currentUser = ParseUser.getCurrentUser();
            final String username = (String) currentUser.get("username");
            final String name = (String) currentUser.get("name");
            final String surname = (String) currentUser.get("surname");
            final boolean presence = true;

            // Get current date
            final Calendar cd = Calendar.getInstance();
            int year = cd.get(Calendar.YEAR);
            int month = cd.get(Calendar.MONTH) + 1;
            int day = cd.get(Calendar.DAY_OF_MONTH);
            String date = day + "." + month + "." + year;

            // Get current time
            int hour = cd.get(Calendar.HOUR_OF_DAY);
            int minute = cd.get(Calendar.MINUTE);
            String time = hour + ":" + minute;

            // Save data to Parse.com -> DateTime class for statistics
            final ParseObject statistics = new ParseObject("DateTime");
            statistics.put("username", username);
            statistics.put("classroom", classroom);
            statistics.put("date", date);
            statistics.put("time", time);
            statistics.saveInBackground();


            final ParseQuery<ParseObject> query = ParseQuery.getQuery("UserActivity");
            query.whereEqualTo("username", username);
            query.whereEqualTo("classroom", classroom);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    if (parseObject == null){
                        ParseObject userActivity = new ParseObject("UserActivity");
                        userActivity.put("username", username);
                        userActivity.put("name", name);
                        userActivity.put("surname", surname);
                        userActivity.put("classroom", classroom);
                        userActivity.put("presence", presence);
                        userActivity.saveInBackground();
                    }else {
                        boolean presenceNew = parseObject.getBoolean("presence");
                        if(presenceNew == false){
                            presenceNew = true;
                            parseObject.put("presence", presenceNew);
                            statistics.put("presence", presenceNew);
                            statistics.saveInBackground();
                            parseObject.saveInBackground();
                        }else {
                            presenceNew = false;
                            parseObject.put("presence", presenceNew);
                            statistics.put("presence", presenceNew);
                            statistics.saveInBackground();
                            parseObject.saveInBackground();
                        }
                    }
                }
            });




            dialog.dismiss();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

    }

}
