package com.wheelabs.wheecheck.wheecheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nikola on 10/16/14.
 */
public class FragmentUserHomeContent extends Fragment implements View.OnClickListener {

    Button bScan;
    TextView tvResult;

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
        String result = data.getStringExtra("result");
        tvResult.setText("Your classrom: " + result);
    }

}
