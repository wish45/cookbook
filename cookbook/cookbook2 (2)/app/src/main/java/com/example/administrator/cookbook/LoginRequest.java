package com.example.administrator.cookbook;


        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;

        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by newhyodong on 2017-12-01.
 */

public class LoginRequest extends StringRequest{
    final static private String URL = "http://192.168.1.51/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String>listener ){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
