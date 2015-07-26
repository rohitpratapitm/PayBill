package com.example.sikar.web;

import com.example.sikar.paybill.Account;
import com.example.sikar.paybill.BillInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Created by sikar on 7/22/2015.
 */
public class JSONResponseHandler  {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String DATA = "results";


    public Account handleResponse(InputStream aResponse) throws IOException {

        Account account = null;
        //String JSONResponse = new BasicResponseHandler().handleResponse(response);
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(aResponse, StandardCharsets.UTF_8.name()));
        StringBuffer responseBuffer = new StringBuffer();
        String inputLine;
        try{
            while ((inputLine = responseReader.readLine()) != null)
                responseBuffer.append(inputLine);
        }catch (IOException aIOException){
            aIOException.printStackTrace();
            throw aIOException;
        }finally {
            responseReader.close();
        }

        final String duplicateKey = "amtToBePaid";
        int index = responseBuffer.indexOf(duplicateKey);
        responseBuffer = responseBuffer.delete(index, index+duplicateKey.length());
        String JSONResponse = responseBuffer.toString();
        try {

            // Get top-level JSON Object - a Map
            JSONObject responseObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();


            Boolean isSuccessful = responseObject.getBoolean(SUCCESS);
            JSONArray accountInfoArray = responseObject.getJSONArray(DATA);

            // It will ALWAYS be ONE, iterating is USELESS
            for (int idx = 0; idx < accountInfoArray.length(); idx++) {

                // Get single earthquake data - a Map
                JSONObject accountInfo = (JSONObject) accountInfoArray.get(idx);

                //Create Account object
                account = new Account();
                //populate it.
                account.setAccountId(accountInfo.getString(Account.ACCOUNT_ID));
                account.setConnectionId(accountInfo.getString(Account.CONNECTION_NO));
                account.setCustomerName(accountInfo.getString(Account.CUSTOMER_NAME));
                account.setCity(accountInfo.getString(Account.CITY));
                account.setAddress(accountInfo.getString(Account.ADDRESS));
                //account.setEmailId(accountInfo.getString(Account.EMAIL_ID));
                //account.setMobileNumber(accountInfo.getString(Account.MOBILE_NO));

                //Create BillInfo Object
                BillInfo billInfo = new BillInfo();
                //populate it
                billInfo.setBillId(accountInfo.getString(BillInfo.BILL_ID));
                billInfo.setBillMonth(accountInfo.getString(BillInfo.MONTH));
                billInfo.setBillIssueDate(accountInfo.getString(BillInfo.ISSUE_DATE));
                billInfo.setBillDueDate(accountInfo.getString(BillInfo.DUE_DATE));
                billInfo.setLastBillAmt(accountInfo.getString(BillInfo.LAST_BILL_AMT));
                billInfo.setCurrentBillAmt(accountInfo.getString(BillInfo.CURRENT_BILL_AMT));
                billInfo.setOutStandingAmt(accountInfo.getString(BillInfo.OUTSTANDING_AMT));
                billInfo.setAmtToBePaid(accountInfo.getString(BillInfo.AMT_TO_BE_PAID));

                account.setBillInfo(billInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return account;
    }
}