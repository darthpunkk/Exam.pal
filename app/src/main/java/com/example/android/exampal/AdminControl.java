package com.example.android.exampal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminControl extends AppCompatActivity {


    private static final String REQUEST_URL = "	http://exampal.site88.net/getDutyTable.php";
    private JSONArray jsonArray;
    private static final String FACULTY_INFO = "faculty_info";
    private ListView listView;
    private CustomAdminAdapter customAdapter;
    private static final String F_NAME = "F_name";
    private static final String F_DEPT = "F_dept";
    private static final String F_PHNO = "F_phoneno";
    private static final String F_RNO= "F_room";
    private Button SubmitAll;
    private ArrayList<FacultyInfo> infoList = new ArrayList<FacultyInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);

        listView = (ListView) findViewById(R.id.faculty_admin_list);

        Log.i("tagconvertstr", "[" + infoList + "]");


        StringRequest stringRequest = new StringRequest(REQUEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.i("tagconvertstr", "[" + response + "]");
                try {
                    JSONObject jobject = new JSONObject(response);
                    jsonArray = jobject.getJSONArray(FACULTY_INFO);
                    customAdapter = new CustomAdminAdapter(AdminControl.this, infoList,jsonArray.length());
                    listView.setAdapter(customAdapter);
                    listView.setItemsCanFocus(true);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        FacultyInfo f_info = new FacultyInfo();
                        f_info.setFacultyName(jsonObject.getString(F_NAME));
                        f_info.setFacultyDept(jsonObject.getString(F_DEPT));
                        f_info.setFacultyRno(jsonObject.getString(F_RNO));
                        f_info.setFacultyNum(jsonObject.getString(F_PHNO));
                        infoList.add(f_info);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                customAdapter.notifyDataSetChanged();
            }

        }, null);

        RequestQueue requestQueue = Volley.newRequestQueue(AdminControl.this);
        requestQueue.add(stringRequest);
    }


}
