package com.wheelabs.wheecheck.wheecheck;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikola on 10/16/14.
 */
public class FragmentUserHomeClassroom extends Fragment {

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ListViewAdapter adapter;
    private List<UserList> userList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home_classroom, container, false);
        listview = (ListView) view.findViewById(R.id.listview);
        new RemoteDataTask().execute();
        return view;
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Loading...");
            // Set progressdialog message
            mProgressDialog.setMessage("Please wait.");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            userList = new ArrayList<UserList>();
            try {
                // Locate the class table named "UserActivity" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "UserActivity");
                query.whereEqualTo("presence", true);
                // Locate the column named "username" in Parse.com and order list
                // by ascending
                query.orderByAscending("username");
                ob = query.find();
                for (ParseObject users : ob) {
                    UserList list = new UserList();
                    list.setUsername((String) users.get("username"));
                  //  list.setPresence((String) users.get("presence"));
                    userList.add(list);
                }
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(getActivity(),
                    userList);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    public class UserList {
        private String username;
        private String presence;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPresence() {
            return presence;
        }

        public void setPresence(String presence) {
            this.presence = presence;
        }
    }

    public class ListViewAdapter extends BaseAdapter {

        // Declare Variables
        Context mContext;
        LayoutInflater inflater;
        private List<UserList> userList = null;
        private ArrayList<UserList> arraylist;

        public ListViewAdapter(Context context,
                               List<UserList> userList) {
            mContext = context;
            this.userList = userList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<UserList>();
            this.arraylist.addAll(userList);
        }

        public class ViewHolder {
            TextView username;
            TextView presence;
        }

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public UserList getItem(int position) {
            return userList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.single_row_user_home_classroom_listview, null);
                // Locate the TextViews in listview_item.xml
                holder.username = (TextView) view.findViewById(R.id.tvUsername);
                holder.presence = (TextView) view.findViewById(R.id.tvPresence);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            holder.username.setText(userList.get(position).getUsername());
            holder.presence.setText(userList.get(position).getPresence());

            return view;
        }
    }

} // End of FragmentUserHomeClassroom
