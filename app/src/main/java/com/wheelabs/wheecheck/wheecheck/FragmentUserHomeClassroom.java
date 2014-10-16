package com.wheelabs.wheecheck.wheecheck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nikola on 10/16/14.
 */
public class FragmentUserHomeClassroom extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home_classroom, container, false);
        return view;
    }
}
