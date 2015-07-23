package com.example.sikar.web;

import com.example.sikar.web.utils.HttpUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by sikar on 7/22/2015.
 */
public class HttpRequest {

    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_CONTENT_TYPE = "Content-type";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_COOKIE = "Cookie";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEADER_X_REQUESTED_WITH = "X-Requested-With";
    public static final String HEADER_CONNECTION = "Connection";
    public static final String HEADER_REFERER = "Referer";
    public static final String HEADER_HOST = "Host";
    public static final String DEFAULT_USER_AGENT = "Mozilla/5.0";

    public enum  HTTP_REQUEST_TYPE{GET,POST}


    private  HttpURLConnection mConnection;
    private  URL mURL;
    private String mCookie;
    private CookieManager mCookieManager;


    public HttpRequest(String aURL){
        try{
            mURL = new URL(aURL);
            mConnection = (HttpURLConnection)mURL.openConnection();
            mConnection.setRequestProperty(HEADER_USER_AGENT,DEFAULT_USER_AGENT);
            mCookieManager = new CookieManager();
            mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(mCookieManager);

        }catch (MalformedURLException aMalformedURLException){
            aMalformedURLException.printStackTrace();
        }catch (IOException aIOException){
            aIOException.printStackTrace();
        }
    }

    public HttpRequest(URL aURL){
        try{
            mURL = aURL;
            mConnection = (HttpURLConnection)mURL.openConnection();
        }catch (IOException aIOException) {
            aIOException.printStackTrace();
        }
    }
    public HttpRequest(String aHost,HTTP_REQUEST_TYPE aRequestType,HashMap<String,String> aQueryParameters){
        try{
            String url;
            if(aRequestType==HTTP_REQUEST_TYPE.GET){
                url = HttpUtils.addQueryParametersToURL(aHost,aQueryParameters);
                mURL = new URL(url);
                mConnection = (HttpURLConnection)mURL.openConnection();
                mConnection.setRequestMethod(HTTP_REQUEST_TYPE.GET.name());
            }
            else{//its a post request
                url = aHost;
                mURL = new URL(url);
                mConnection = (HttpURLConnection)mURL.openConnection();
                mConnection.setRequestMethod(HTTP_REQUEST_TYPE.POST.name());
            }

        }catch (MalformedURLException aMalformedURLException){
            aMalformedURLException.printStackTrace();
        }catch (IOException aIOException){
            aIOException.printStackTrace();
        }
    }
    public String getCookie(){
        if(mCookie == null){
            mCookie = mConnection.getHeaderField("Set-Cookie");
            mCookie = mCookie.substring(0, mCookie.indexOf(";"));
        }
        return mCookie;
    }
    public HttpURLConnection sendGETRequest(){
        //only setting cookie here
        getCookie();
        //mConnection.setRequestMethod(HTTP_REQUEST_TYPE.GET.name());
        return mConnection;
    }

    public HttpURLConnection sendPOSTRequest(HashMap<String,String> aQueryParameters) {

        try {
            mConnection.setRequestMethod(HTTP_REQUEST_TYPE.POST.name());
            initializeWithDefaults();
            //Send parameters
            mConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(mConnection.getOutputStream());
            String urlParameters = HttpUtils.convertQueryParametersToString(aQueryParameters);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
        } catch (ProtocolException aProtocolException) {
            aProtocolException.printStackTrace();
        } catch (IOException aIOException) {
            aIOException.printStackTrace();
        }
        return mConnection;
    }

    public void initializeHeader(HashMap<String,String> aHeaderParameters){
        if(aHeaderParameters == null || aHeaderParameters.isEmpty()){
            initializeWithDefaults();
        }
    }

    private void initializeWithDefaults(){

        mConnection.setRequestProperty(HEADER_ACCEPT, "application/json; */*");
        mConnection.setRequestProperty(HEADER_CONTENT_TYPE, "application/json;application/x-www-form-urlencoded; charset=UTF-8");
        mConnection.setRequestProperty(HEADER_ACCEPT_ENCODING, "gzip, deflate");
        mConnection.setRequestProperty(HEADER_COOKIE, mCookie);
        mConnection.setRequestProperty(HEADER_USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
        mConnection.setRequestProperty(HEADER_ACCEPT_LANGUAGE, "en-US,en;q=0.8,hi;q=0.6");
        mConnection.setRequestProperty(HEADER_X_REQUESTED_WITH, "XMLHttpRequest");
        mConnection.setRequestProperty(HEADER_CONNECTION, "keep-alive");
        mConnection.setRequestProperty(HEADER_REFERER, HttpPostTask.HOME_SCREEN);
        mConnection.setRequestProperty(HEADER_HOST, "www.mpcz.co.in");

    }

}
