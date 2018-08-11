package com.example.changheonkim.safekids;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest_Parents extends StringRequest {
    final static private String URL = "http://diablo827.cafe24.com/Register_Parents.php";
    private Map<String,String> parameters;

    public RegisterRequest_Parents(String userID, String userPw, String userParentsName, String userEmail, int userPhoneNum, String userChildName, String schoolCode, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPw", userPw);
        parameters.put("userParentsName", userParentsName);
        parameters.put("userEmail", userEmail);
        parameters.put("userPhoneNum",userPhoneNum+"");
        parameters.put("userChildName",userChildName);
        parameters.put("schoolCode",schoolCode);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
