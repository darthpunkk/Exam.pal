package com.example.android.exampal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FirstFragment extends Fragment {


    private  EditText emailText;
    private EditText passText;
    private Button loginButton;
    private TextView registerView;
    private  int counter=0;


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
                    registerView.setText("Already Logged in? login");
                }
                else{
                    layout.removeView(nameText);
                    layout.removeView(DeptText);
                    layout.removeView(phoneNumber);
                    layout.removeView(registerButton);
                    layout.addView(loginButton,2);
                    registerView.setText("New User? Register");
                    counter=0;

                }


            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
