package com.example.android.exampal;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vedant on 4/26/2016.
 */
public class RoomRequest extends StringRequest {
    private static final String REQUEST_URL = "	http://exampal.site88.net/setroom.php";
    private Map<String,String> parameters;

    public RoomRequest(String number, String name,String room, Response.Listener<String> listener){

        super(Method.POST,REQUEST_URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("name",name);
        parameters.put("room",room);
        parameters.put("phone_no",number);

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}