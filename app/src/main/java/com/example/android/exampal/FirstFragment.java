package com.example.android.exampal;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class FirstFragment extends Fragment {

    SessionManagement sessionManagement;
    EditText emailText;
    EditText passText;
    Button loginButton;
    TextView registerView;
    int counter = 0;

    private int sizeInDp(int size) {
        int dpSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, size, getResources()
                        .getDisplayMetrics());
        return dpSize;

    }


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        sessionManagement = new SessionManagement(getContext());

        if(!sessionManagement.isLoggedIn()) {

            emailText = (EditText) view.findViewById(R.id.emailText);
            passText = (EditText) view.findViewById(R.id.passwordText);
            loginButton = (Button) view.findViewById(R.id.LoginButton);
            registerView = (TextView) view.findViewById(R.id.RegisterView);

        /*programmatically created editviews,layout and button*/

            final EditText nameText = new EditText(getActivity());
            final EditText DeptText = new EditText(getActivity());
            final EditText phoneNumber = new EditText(getActivity());
            final Button registerButton = new Button(getActivity());
            final LinearLayout layout = (LinearLayout) view.findViewById(R.id.fragmentLayout);

            LinearLayout.LayoutParams parameters1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams parameters2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parameters2.setMargins(0, sizeInDp(15), 0, 0);
            parameters2.width = sizeInDp(250);
            parameters2.height = sizeInDp(40);
            parameters2.gravity = Gravity.CENTER_HORIZONTAL;
            phoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);
            phoneNumber.setLayoutParams(parameters1);
            phoneNumber.setHint("Enter Phone Number");

            nameText.setLayoutParams(parameters1);
            nameText.setHint("Enter Name");

            DeptText.setLayoutParams(parameters1);
            DeptText.setHint("Enter Department");


            registerButton.setLayoutParams(parameters2);
            registerButton.setBackgroundResource(R.drawable.rect);
            registerButton.setTextColor(getResources().getColor(R.color.textColorPrimary));
            registerButton.setText("Register");


            registerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    counter++;
                    if (counter == 1) {
                        layout.addView(nameText, 1);
                        layout.addView(DeptText, 2);
                        layout.addView(phoneNumber, 3);
                        layout.removeView(loginButton);
                        layout.addView(registerButton, 5);
                        registerView.setText("Already signed Up? login");
                    } else {
                        layout.removeView(nameText);
                        layout.removeView(DeptText);
                        layout.removeView(phoneNumber);
                        layout.removeView(registerButton);
                        layout.addView(loginButton, 2);
                        registerView.setText("New User? Sign Up");
                        counter = 0;

                    }


                }
            });

            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog pDialog = new ProgressDialog(getActivity());
                    pDialog.setMessage("Loading...");
                    pDialog.show();
                    pDialog.setCancelable(false); //disable touch outside
                    final String name = nameText.getText().toString();
                    final String department = DeptText.getText().toString();
                    final String ph_no = phoneNumber.getText().toString();
                    final String mail_id = emailText.getText().toString();
                    final String password = passText.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.i("tagconvertstr", "[" + response + "]");
                                pDialog.dismiss();
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                boolean noValue = jsonObject.getBoolean("Value");
                                if (noValue) {
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
                                                        layout.addView(loginButton, 2);
                                                        registerView.setText("New User? Sign Up");
                                                        counter = 0;

                                                    }
                                                })
                                                .setNegativeButton("Cancel", null)
                                                .create()
                                                .show();

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setMessage("You have already registered")
                                                .setPositiveButton("retry", null)
                                                .setNegativeButton("cancel", null)
                                                .create()
                                                .show();

                                    }
                                } else {
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

                    RegisterRequest registerRequest = new RegisterRequest(name, mail_id, department, ph_no, password, responseListener);
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    requestQueue.add(registerRequest);

                }
            });

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog pDialog = new ProgressDialog(getActivity());
                    pDialog.setMessage("Loading...");
                    pDialog.show();
                    pDialog.setCancelable(false);
                    final String mail_id = emailText.getText().toString();
                    final String password = passText.getText().toString();
                    Log.i("cred", "[" + password + "]");
                    Log.i("cred", "[" + mail_id + "]");

                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.i("tagconvertstr", "[" + response + "]");
                                pDialog.dismiss();
                                JSONObject jsonObject = new JSONObject(response);
                                boolean noValue = jsonObject.getBoolean("Value");
                                if (noValue) {
                                    boolean userExists = jsonObject.getBoolean("User");
                                    if (userExists) {
                                        boolean success = jsonObject.getBoolean("success");

                                        if (success) {
                                            String F_name = jsonObject.getString("name");//as in php file
                                            String F_dept = jsonObject.getString("dept");//as in php file
                                            String F_Rno = jsonObject.getString("dept");//as in php file
                                            String F_mail = jsonObject.getString("mail_id");//as in php file
                                            sessionManagement.loginSession(F_name, F_Rno, F_dept, F_mail);
                                            Intent intent = new Intent(getActivity(), UserArea.class);
                                            startActivity(intent);

                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            builder.setMessage("Incorrect Password")
                                                    .setNegativeButton("retry", null)
                                                    .create()
                                                    .show();
                                        }
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setMessage("Incorrect Username/New User")
                                                .setNegativeButton("retry", null).setPositiveButton("Register", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                layout.addView(nameText, 1);
                                                layout.addView(DeptText, 2);
                                                layout.addView(phoneNumber, 3);
                                                layout.removeView(loginButton);
                                                layout.addView(registerButton, 5);
                                                registerView.setText("Already signed Up? login");
                                                counter++;

                                            }
                                        })
                                                .create()
                                                .show();

                                    }
                                } else {
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
                    LoginRequest loginRequest = new LoginRequest(mail_id, password, listener);
                    RequestQueue loginQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    loginQueue.add(loginRequest);
                }
            });
        }
        else {
            Intent intent = new Intent(getActivity(), UserArea.class);
            startActivity(intent);
        }
        // Inflate the layout for this fragment
        return view;
    }


}
