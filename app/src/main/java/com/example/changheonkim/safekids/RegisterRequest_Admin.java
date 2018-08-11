package com.example.changheonkim.safekids;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest_Admin extends StringRequest {
    final static private String URL = "http://diablo827.cafe24.com/Register_Admin.php";
    private Map<String,String>parameters;

    public RegisterRequest_Admin(String userID, String userPw, String userAdminName, String userEmail, int userPhoneNum, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPw", userPw);
        parameters.put("userAdminName", userAdminName);
        parameters.put("userEmail", userEmail);
        parameters.put("userPhoneNum",userPhoneNum+"");
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
