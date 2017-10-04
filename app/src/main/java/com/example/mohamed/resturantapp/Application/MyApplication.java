package com.example.mohamed.resturantapp.Application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mohamed on 04/10/2017.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;
    public static final String TAG = MyApplication.class
            .getSimpleName();
    private RequestQueue requestQueue;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public static synchronized MyApplication getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request,String tag){
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void CancelRequest(Object tag){
        if (requestQueue!=null){
            requestQueue.cancelAll(tag);
        }
    }
}
