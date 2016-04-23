package com.example.android.exampal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import java.util.List;

public class UserArea extends AppCompatActivity {

    private static final String REQUEST_URL = "	http://exampal.site88.net/getDutyTable.php";
    private static final String F_NAME = "F_name";
    private static final String F_DEPT = "F_dept";
    private static final String F_RNO = "F_dept";
    private static final String F_PH_NO = "F_phoneno";
    private static final String FACULTY_INFO = "faculty_info";
    private JSONArray jsonArray;
    private String name;
    private ListView listView;
    private CustomAdapter customAdapter;
    private List<FacultyInfo> infoList = new ArrayList<FacultyInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_area);
        final TextView textView = (TextView)findViewById(R.id.extraText) ;
        final Button ShowList = (Button)findViewById(R.id.showListButton);
        listView = (ListView)findViewById(R.id.facultyList) ;
        customAdapter = new CustomAdapter(this,infoList);
        listView.setAdapter(customAdapter);
        Intent intent=getIntent();
        name = intent.getStringExtra("F_name");

        textView.setText("hi "+name);

        ShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final ProgressDialog pDialog = new ProgressDialog(UserArea.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                pDialog.setCancelable(false);
                StringRequest stringRequest = new StringRequest(REQUEST_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pDialog.dismiss();

                        Log.i("tagconvertstr", "["+response+"]");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            jsonArray= jsonObject.getJSONArray(FACULTY_INFO);
                            getFaculty(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        customAdapter.notifyDataSetChanged();
                    }

                },null);

                RequestQueue requestQueue = Volley.newRequestQueue(UserArea.this);
                requestQueue.add(stringRequest);
            }

        });

    }
    private void getFaculty(JSONArray JArray) {


        for (int i = 0; i < JArray.length(); i++) {
            try {
                JSONObject jsonObject = JArray.getJSONObject(i);
                FacultyInfo f_info = new FacultyInfo();
                if(!jsonObject.getString(F_NAME).equals(name)){
                    f_info.setFacultyName(jsonObject.getString(F_NAME));
                    f_info.setFacultyDept(jsonObject.getString(F_DEPT));
                    f_info.setFacultyRno(" ");
                    f_info.setFacultyNum(jsonObject.getString(F_PH_NO));
                    infoList.add(f_info);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
