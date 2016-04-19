package com.example.android.exampal;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegisterRequest extends StringRequest {

    private static final String REQUEST_URL = "	http://exampal.site88.net/register.php";
    private Map<String,String> parameters;

    public  RegisterRequest(String name, String mail_id, String department, String ph_no, String password , Response.Listener<String> listener){
        super(Method.POST,REQUEST_URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("name",name);
        parameters.put("department",department);
        parameters.put("mail_id",mail_id);
        parameters.put("password",password);
        parameters.put("phone_no",ph_no);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
