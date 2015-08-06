package com.example.sikar.web;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.sikar.paybill.Account;
import com.example.sikar.paybill.ShowBill;
import com.example.sikar.web.utils.MySession;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by sikar on 7/22/2015.
 */

public class HttpPostTask extends AsyncTask<String,Void,Account> {

    private String mSessionCookie;
    Activity mActivity;

    public HttpPostTask(Activity aActivity){
        super();
        mActivity = aActivity;
    }

    @Override
    protected Account doInBackground(String... params) {

        mSessionCookie = MySession.getSessionCookie();
        Account account = null;

        //1.Make a POST Request and send account Id as parameter
        HashMap<String,String> postQueryParameters = new HashMap<String,String>();
        postQueryParameters.put(MPCZConstants.POST_CHOOSE_IDENTIFIER, MPCZConstants.POST_CHOOSE_IDENTIFIER_VALUE);
        postQueryParameters.put(MPCZConstants.POST_ACCOUNT_ID, params[0]);

        HttpRequest httpPOSTRequest = new HttpRequest(MPCZConstants.LOGIN_SCREEN, HttpRequest.HTTP_REQUEST_TYPE.POST,postQueryParameters);
        httpPOSTRequest.setCookie(mSessionCookie);
        String httpPOSTResponse = httpPOSTRequest.sendPOSTRequest(postQueryParameters);

        ResponseHandler responseHandler = new ResponseHandler();
        try{
            account = responseHandler.handleAccountResponse(httpPOSTResponse);
        }catch (IOException aIOException){
            aIOException.printStackTrace();
        }
        return account;
    }


    @Override
    protected void onPostExecute(Account aAccount) {

        Intent showBillIntent = new Intent(mActivity,ShowBill.class);
        showBillIntent.putExtra("Account",aAccount);
        showBillIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.getApplicationContext().startActivity(showBillIntent);

    }


}