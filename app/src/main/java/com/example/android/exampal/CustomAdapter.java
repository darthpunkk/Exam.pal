package com.example.android.exampal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vedant on 4/23/2016.
 */
public class CustomAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<FacultyInfo> facultyInfoList;

    public CustomAdapter(Activity activity,List<FacultyInfo> facultyInfoList)
    {
        this.activity=activity;
        this.facultyInfoList=facultyInfoList;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater == null)
            layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        convertView = layoutInflater.inflate(R.layout.custom_view,null);

        TextView facultynameText = (TextView) convertView.findViewById(R.id.facultyName);
        TextView facultydeptText = (TextView) convertView.findViewById(R.id.dept);
        TextView facultyRoomText = (TextView) convertView.findViewById(R.id.room_no);
        TextView facultyphoneText = (TextView) convertView.findViewById(R.id.Phone_no);

        final FacultyInfo facultyInfo = facultyInfoList.get(position);
        facultynameText.setText(facultyInfo.getFacultyName());
        facultyRoomText.setText(facultyInfo.getFacultyRno());
        facultydeptText.setText(facultyInfo.getFacultyDept());
        facultyphoneText.setText(facultyInfo.getFacultyNum());

        facultyphoneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("You are about to call a faculty for swapping your room Duty")
                        .setNegativeButton("cancel",null)
                        .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+facultyInfo.getFacultyNum()));
                        activity.startActivity(intent);


                    }
                })
                        .create()
                        .show();

            }
        });

        return convertView;
    }
}
