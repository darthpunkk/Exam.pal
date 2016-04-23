package com.example.android.exampal;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vedant on 4/21/2016.
 */


public class LoginRequest extends StringRequest{
    private static final String REQUEST_URL = "	http://exampal.site88.net/Login2.php";
    private Map<String,String> parameters;

    public LoginRequest(String mail_id, String password, Response.Listener<String> listener){

        super(Method.POST,REQUEST_URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("mail_id",mail_id);
        parameters.put("password",password);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
