package com.example.android.exampal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserArea extends AppCompatActivity {

    HashMap<String, String> userDetails;
    private static final String REQUEST_URL = "	http://exampal.site88.net/getDutyTable.php";
    private static final String F_NAME = "F_name";
    private static final String F_DEPT = "F_dept";
    private static final String F_RNO = "F_room";
    private static final String F_PH_NO = "F_phoneno";
    private static final String FACULTY_INFO = "faculty_info";
    private JSONArray jsonArray;
    private ListView listView;
    private CustomAdapter customAdapter;
    private List<FacultyInfo> infoList = new ArrayList<FacultyInfo>();
    SessionManagement sessionManagement;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                sessionManagement.LogOut();
                finish();
            case R.id.Refresh:

                item.setActionView(R.layout.progressbar);
                refresh();
                item.setActionView(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);


        final TextView nameText = (TextView) findViewById(R.id.get_name_text);
        final TextView roomText = (TextView) findViewById(R.id.get_room_text);
        final Button ShowList = (Button) findViewById(R.id.showListButton);
        listView = (ListView) findViewById(R.id.facultyList);

        Log.i("tagconvertstr", "[" + infoList + "]");

        sessionManagement = new SessionManagement(this);
        customAdapter = new CustomAdapter(this, infoList);
        listView.setAdapter(customAdapter);
        userDetails = sessionManagement.getUserDetails();

        nameText.setText("hi " + userDetails.get("name"));
        String room = userDetails.get("RoomNo");
        if (room.equals("null"))
            room = "Not Assigned";
        roomText.setText("your roomno : " + room);

        ShowList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final ProgressDialog pDialog = new ProgressDialog(UserArea.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                pDialog.setCancelable(false);
                infoList.clear();//clears the list to avoid list duplication
                StringRequest stringRequest = new StringRequest(REQUEST_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pDialog.dismiss();

                        Log.i("tagconvertstr", "[" + response + "]");
                        try {
                            JSONObject JObj = new JSONObject(response);
                            jsonArray = JObj.getJSONArray(FACULTY_INFO);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                FacultyInfo f_info = new FacultyInfo();
                                if (!jsonObject.getString(F_NAME).equals(userDetails.get("name"))) {
                                    f_info.setFacultyName(jsonObject.getString(F_NAME));
                                    f_info.setFacultyDept(jsonObject.getString(F_DEPT));
                                    f_info.setFacultyRno(jsonObject.getString(F_RNO));
                                    f_info.setFacultyNum(jsonObject.getString(F_PH_NO));
                                    infoList.add(f_info);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        customAdapter.notifyDataSetChanged();
                    }

                }, null);

                RequestQueue requestQueue = Volley.newRequestQueue(UserArea.this);
                requestQueue.add(stringRequest);
            }

        });

    }
    private void refresh(){

    }
}
