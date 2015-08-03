package com.example.sikar.web.utils;

import com.example.sikar.web.HttpRequest;
import com.example.sikar.web.MPCZConstants;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by sikar on 7/29/2015.
 */
public class MySession {

    private static String mCookie;
    private static Map<String,String> mHeaderParameters;

    public static String getSessionCookie(){
        if(mCookie == null){
            //1. Create mCookie
            HttpRequest httpGETRequest = new HttpRequest(MPCZConstants.HOME_SCREEN, HttpRequest.HTTP_REQUEST_TYPE.GET,null);
            httpGETRequest.sendGETRequest();
            mCookie = httpGETRequest.getHeaderField("Set-Cookie");
        }
        return mCookie;
    }
}
