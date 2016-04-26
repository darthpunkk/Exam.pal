package com.example.android.exampal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class CustomAdminAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<FacultyInfo> facultyInfoList;
    private String[] array;
    private String roomNo;
    private String phNumber;
    private String fname;

    public CustomAdminAdapter(Activity activity, ArrayList<FacultyInfo> facultyInfoList, int length) {
        this.activity = activity;
        this.facultyInfoList = facultyInfoList;
         array = new  String[length];

    }

    @Override
    public int getCount() {
        return facultyInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return facultyInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //final int temp = position;
       final ViewHolder holder;
        final FacultyInfo facultyInfo= facultyInfoList.get(position);

        if (convertView == null) {

            holder = new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_admin_view, null);
            holder.facultynameText = (TextView) convertView.findViewById(R.id.facultyName_admin);
            holder.facultydeptText = (TextView) convertView.findViewById(R.id.dept_admin);
            holder.facultyRoomText = (EditText) convertView.findViewById(R.id.room_no_admin);
            holder.facultyRoomButton = (Button) convertView.findViewById(R.id.room_no_button);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.reference = position;

        holder.facultynameText.setText(facultyInfo.getFacultyName());
        holder.facultydeptText.setText(facultyInfo.getFacultyDept());
        holder.facultyRoomText.setHint(facultyInfo.getFacultyRno());
       holder.facultyRoomText.setText(array[holder.reference]);
       holder.facultyRoomText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                array[holder.reference] = s.toString();


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.facultyRoomText.clearFocus();

        holder.facultyRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNo =  holder.facultyRoomText.getText().toString();
                fname = facultyInfo.getFacultyName();
                Log.i("tagconvertstr", "[" + fname + "]");
                phNumber = facultyInfo.getFacultyNum();
                Log.i("tagconvertstr", "[" + phNumber + "]");
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("tagconvertstr", "[" + response + "]");
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                facultyInfo.setFacultyRno(roomNo);
                                if(!roomNo.equals("")){
                                    holder.facultyRoomText.setHint(roomNo);
                                }
                                else
                                    holder.facultyRoomText.setHint("000");

                                Toast.makeText(activity, "Saved successfully",
                                        Toast.LENGTH_SHORT).show();


                                    } else {Toast.makeText(activity, "something went Wrong!",
                                    Toast.LENGTH_SHORT).show();

                                    }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                RoomRequest roomRequest = new RoomRequest(phNumber,fname,roomNo,listener);
                RequestQueue loginQueue = Volley.newRequestQueue(activity.getApplicationContext());
                loginQueue.add(roomRequest);

            }
        });





        return convertView;
    }
    private static class ViewHolder {
        TextView facultynameText;
        TextView facultydeptText;
        EditText facultyRoomText;
        Button facultyRoomButton;
        private int reference;
    }

}
