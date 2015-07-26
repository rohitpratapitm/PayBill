package com.example.sikar.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.sikar.paybill.Account;
import com.example.sikar.paybill.ShowBill;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by sikar on 7/22/2015.
 */

public class HttpPostTask extends AsyncTask<String,Void,Account> {

    public final static String USER_AGENT = "Mozilla/5.0";
    public static final String HOME_SCREEN = "http://www.mpcz.co.in/portal/Bhopal_home.portal?_nfpb=true&_pageLabel=custCentre_viewBill_bpl";
    public static final String LOGIN_SCREEN = "http://www.mpcz.co.in/onlineBillPayment?do=onlineBillPaymentUnregValidate";
    public static final String HOST = "http://www.mpcz.co.in/portal/Bhopal_home.portal";
    public static final String POST_ACCOUNT_ID = "accntId";
    public static final String POST_CHOOSE_IDENTIFIER = "chooseIdentifier";

    public static final int OK = 200;
    //URL Parameters
    private static final String CHOOSE_IDENTIFIER = "chooseIdentifier";
    private String mSessionCookie ;
    Activity mActivity;

    public HttpPostTask(Activity aActivity){
        super();
        mActivity = aActivity;
    }

    @Override
    protected Account doInBackground(String... params) {

        Account account = null;
        HashMap<String,String> queryParametes = new HashMap<String,String>();
        queryParametes.put("_pageLabel","custCentre_viewBill_bpl");
        queryParametes.put("_nfpb", "true");

        //1. Send a GET request to create a Session Cookie
        HttpRequest httpGETRequest = new HttpRequest(HOST, HttpRequest.HTTP_REQUEST_TYPE.GET,queryParametes);
        HttpURLConnection httpGETConnection = httpGETRequest.sendGETRequest();
        mSessionCookie = httpGETRequest.getCookie();
        int responseCode = 0;
        try {
            responseCode = httpGETConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Response code is : "+responseCode);
        httpGETConnection.disconnect();

        //2.Make a POST Request and send account Id as parameter
        HashMap<String,String> postQueryParameters = new HashMap<String,String>();
        postQueryParameters = initializeDefaultParameters(postQueryParameters);
        postQueryParameters.put(POST_ACCOUNT_ID, params[0]);

        HttpRequest httpPOSTRequest = new HttpRequest(LOGIN_SCREEN, HttpRequest.HTTP_REQUEST_TYPE.POST,postQueryParameters);
        httpPOSTRequest.setCookie(mSessionCookie);
        HttpURLConnection connection = httpPOSTRequest.sendPOSTRequest(postQueryParameters);

        JSONResponseHandler responseHandler = new JSONResponseHandler();
        InputStream stream = null;
        try{
            stream = connection.getInputStream();
            if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                System.out.println("Response is good");
            }
            account = responseHandler.handleResponse(stream);
        }catch (IOException aIOException){
            aIOException.printStackTrace();
        }
        System.out.println("Name is : "+account.getCustomerName());

        return account;

    }


    @Override
    protected void onPostExecute(Account aAccount) {

        Intent showBillIntent = new Intent(mActivity,ShowBill.class);
        showBillIntent.putExtra("Account",aAccount);
        showBillIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.getApplicationContext().startActivity(showBillIntent);

    }
    private static HashMap<String,String> initializeDefaultParameters(HashMap<String,String> aParameterMap){
    //String urlParameters = "chooseIdentifier=Account%20ID&chooseIdentifier=Account%20ID&accntId=9493692000&chooseGateway=BillDesk&chooseGateway=Bill%20Desk%20Payment&mblNum=&emailId=&gridValues=";
    //aParameterMap.put("accntId","9493692000");
    aParameterMap.put(POST_CHOOSE_IDENTIFIER,"Account ID");
    //aParameterMap.put("chooseIdentifier","Account ID");

    //aParameterMap.put("chooseGateway","BillDesk");
    //aParameterMap.put("chooseGateway","Bill Desk Payment");
    //aParameterMap.put("mblNum","9346584202");
    //aParameterMap.put("emailId","rohitpratapitm@gmail.com");
    //aParameterMap.put("gridValues","");

    return aParameterMap;
    }

}