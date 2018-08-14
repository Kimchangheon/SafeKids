package com.example.changheonkim.safekids;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest_Parents extends StringRequest {
    final static private String URL = "http://diablo827.cafe24.com/Login_Parents.php";
    private Map<String,String>parameters;

    public LoginRequest_Parents(String userID, String userPw, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPw", userPw);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
