package com.example.sikar.web.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sikar on 7/22/2015.
 */
public class HttpUtils {


    public static String addQueryParametersToURL(String aURLWithoutParameters,HashMap<String,String> aParameters){

        if(!aURLWithoutParameters.endsWith("?"))
            aURLWithoutParameters += "?";

        StringBuffer urlWithParams = new StringBuffer(aURLWithoutParameters);

        String paramString = convertQueryParametersToString(aParameters);
        urlWithParams.append(paramString);

        return urlWithParams.toString();
    }

    public static String convertQueryParametersToString(HashMap<String,String> aParameters){
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        Set<Map.Entry<String,String>> entrySet = aParameters.entrySet();
        for(Map.Entry<String,String> entry: entrySet){
            params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }

        String paramString = URLEncodedUtils.format(params, "UTF-8");
        return paramString;
    }

}
