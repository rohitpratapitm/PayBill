package com.example.sikar.web;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.sikar.paybill.Account;
import com.example.sikar.paybill.ShowBill;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by sikar on 7/22/2015.
 */

public class HttpPostTask extends AsyncTask<String,Void,Account> {

    public final static String USER_AGENT = "Mozilla/5.0";
    public static final String HOME_SCREEN = "http://www.mpcz.co.in/portal/Bhopal_home.portal?_nfpb=true&_pageLabel=custCentre_viewBill_bpl";
    public static final String LOGIN_SCREEN = "http://www.mpcz.co.in/onlineBillPayment?do=onlineBillPaymentUnregValidate";
    public static final String HOST = "http://www.mpcz.co.in/portal/Bhopal_home.portal";

    public static final int OK = 200;
    //URL Parameters
    private static final String CHOOSE_IDENTIFIER = "chooseIdentifier";
    private String cookie ;
    //instantiates httpclient to make request
    DefaultHttpClient mClient = new DefaultHttpClient();

    Activity mActivity;

    public HttpPostTask(Activity aActivity){
        super();
        mActivity = aActivity;
    }

    @Override
    protected Account doInBackground(String... params) {

//        HashMap<String,String> queryParametes = new HashMap<String,String>();
//        HttpRequest httpGETRequest = new HttpRequest(HOST, HttpRequest.HTTP_REQUEST_TYPE.GET,queryParametes);

        //String cookie = null;
        String accountId = params[0];
        try {
            URL obj = new URL(HOME_SCREEN);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            cookie = con.getHeaderField("Set-Cookie");
            cookie = cookie.substring(0, cookie.indexOf(";"));
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String urlParameters = "chooseIdentifier=Account%20ID&chooseIdentifier=Account%20ID&accntId=9493692000&chooseGateway=BillDesk&chooseGateway=Bill%20Desk%20Payment&mblNum=&emailId=&gridValues=";
        //url with the post data
        HttpPost request = new HttpPost(LOGIN_SCREEN);

        //sets a request header so the page receving the request
        //will know what to do with it
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Encoding", "gzip, deflate");
        request.setHeader("Cookie", cookie);
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
        request.setHeader("Accept-Language","en-US,en;q=0.8,hi;q=0.6");
        request.setHeader("X-Requested-With", "XMLHttpRequest");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Referer", HOME_SCREEN);
        request.setHeader("Host", "www.mpcz.co.in");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        try {
            //sets the post request as the resulting string
            request.setEntity(new StringEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONResponseHandler responseHandler = new JSONResponseHandler();

        try {
            return mClient.execute(request,responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Account aAccount) {

        Intent showBillIntent = new Intent(mActivity,ShowBill.class);
        showBillIntent.putExtra("Account",aAccount);
        showBillIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.getApplicationContext().startActivity(showBillIntent);

    }
}