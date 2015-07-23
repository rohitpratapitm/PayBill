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

import java.io.IOException;

/**
 * Created by sikar on 7/22/2015.
 */
public class JSONResponseHandler implements ResponseHandler<Account> {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String DATA = "results";

    @Override
    public Account handleResponse(HttpResponse response)
            throws ClientProtocolException, IOException {

        Account account = null;
        String JSONResponse = new BasicResponseHandler().handleResponse(response);
        try {

            // Get top-level JSON Object - a Map
            JSONObject responseObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();

            // Extract value of "earthquakes" key -- a List
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
                account.setEmailId(accountInfo.getString(Account.EMAIL_ID));
                account.setMobileNumber(accountInfo.getString(Account.MOBILE_NO));

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