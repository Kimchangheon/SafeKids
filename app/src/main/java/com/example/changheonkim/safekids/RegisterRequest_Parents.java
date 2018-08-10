package com.example.changheonkim.safekids;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class RegisterRequest_Parents extends StringRequest {
    final static private String URL = "http://diablo827.cafe24.com /Register.php";
    private Map<String,String> parameters;

    public RegisterRequest_Parents(String userID, String userPassword, String userName, String userEmail, int userPhoneNum, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userEmail", userEmail);
        parameters.put("userPhoneNum",userPhoneNum+"");
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
