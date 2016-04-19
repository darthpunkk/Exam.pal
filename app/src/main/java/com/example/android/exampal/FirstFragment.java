package com.example.android.exampal;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class FirstFragment extends Fragment {


      EditText emailText;
      EditText passText;
      Button loginButton;
      TextView registerView;
      int counter=0;


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_first, container, false);
        emailText  = (EditText) view.findViewById(R.id.emailText);
        passText = (EditText) view.findViewById(R.id.passwordText);
        loginButton = (Button) view.findViewById(R.id.LoginButton);
        registerView = (TextView) view.findViewById(R.id.RegisterView);



        final EditText nameText = new EditText(getActivity());
        final EditText DeptText = new EditText(getActivity());
        final EditText phoneNumber = new EditText(getActivity());
        final Button registerButton = new Button(getActivity());
        final LinearLayout layout = (LinearLayout)view.findViewById(R.id.fragmentLayout);
        phoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);
        LinearLayout.LayoutParams parameters1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) ;
        nameText.setLayoutParams(parameters1);
        DeptText.setLayoutParams(parameters1);
        phoneNumber.setLayoutParams(parameters1);
        LinearLayout.LayoutParams parameters2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) ;
        parameters2.gravity = Gravity.CENTER;
        registerButton.setLayoutParams(parameters2);
        nameText.setHint("Enter Name");
        DeptText.setHint("Enter Department");
        phoneNumber.setHint("Enter Phone Number");
        registerButton.setText("Register");
        registerView.setTextColor(Color.BLUE);
        registerView.setTextSize(17);



        registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                counter++;
                if(counter==1) {
                    layout.addView(nameText,1 );
                    layout.addView(DeptText, 2);
                    layout.addView(phoneNumber,3);
                    layout.removeView(loginButton);
                    layout.addView(registerButton,5);
                    registerView.setText("Already signed Up? login");
                }
                else{
                    layout.removeView(nameText);
                    layout.removeView(DeptText);
                    layout.removeView(phoneNumber);
                    layout.removeView(registerButton);
                    layout.addView(loginButton,2);
                    registerView.setText("New User? Sign Up");
                    counter=0;

                }


            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = nameText.getText().toString();
                final String department = DeptText.getText().toString();
                final String ph_no = phoneNumber.getText().toString();
                final String mail_id = emailText.getText().toString();
                final String password = passText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            boolean noValue = jsonObject.getBoolean("Value");
                            if(noValue) {
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("You have successfully Registered")
                                            .setPositiveButton("login", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    layout.removeView(nameText);
                                                    layout.removeView(DeptText);
                                                    layout.removeView(phoneNumber);
                                                    layout.removeView(registerButton);
                                                    layout.addView(loginButton,2);
                                                    registerView.setText("New User? Sign Up");
                                                    counter=0;

                                                }
                                            })
                                            .setNegativeButton("Cancel",null)
                                            .create()
                                            .show();

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("You have already registered")
                                            .setPositiveButton("retry",null)
                                            .setNegativeButton("cancel", null)
                                            .create()
                                            .show();

                                }
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Fields are empty..")
                                        .setNegativeButton("retry", null)
                                        .create()
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name,mail_id,department,ph_no,password,responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(registerRequest);

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
