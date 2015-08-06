package com.example.sikar.web;

import com.example.sikar.paybill.Account;
import com.example.sikar.paybill.BillInfo;
import com.example.sikar.paybill.TransactionInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

/**
 * Created by sikar on 7/22/2015.
 */
public class JSONResponseHandler  {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String DATA = "results";


    public Account handleAccountResponse(String aResponse) throws IOException {

        if(aResponse == null || aResponse.isEmpty()){
            return null;
        }
        Account account = null;

        final String duplicateKey = "amtToBePaid";
        StringBuffer responseBuffer = new StringBuffer(aResponse);
        int index = responseBuffer.indexOf(duplicateKey);
        if(index > -1){
            responseBuffer = responseBuffer.delete(index, index+duplicateKey.length());
        }
        String JSONResponse = responseBuffer.toString();
        try {

            // Get top-level JSON Object - a Map
            JSONObject responseObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();

            Boolean isFailure = !responseObject.isNull(FAILURE);
            if(isFailure){
                return null;
            }

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
                account.setEmailId("rohitpratapitm@gmail.com");
                //account.setMobileNumber(accountInfo.getString(Account.MOBILE_NO));
                account.setMobileNumber("9346584202");

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

    public TransactionInfo handleTransactionResponse(String aResponse){

        if(aResponse == null || aResponse.isEmpty()){
            return null;
        }
        TransactionInfo transactionInfo = new TransactionInfo();

        String[] transactionValues = aResponse.split(",");

        if(transactionValues !=null && transactionValues.length > 0 ){

            transactionInfo.setTxtAdditionalInfo1(transactionValues[0]);
            transactionInfo.setBillerId(transactionValues[1]);
            transactionInfo.setTxtCustomerID(transactionValues[2]);
            transactionInfo.setRU(transactionValues[3]);
            transactionInfo.setTxtAdditionalInfo2(transactionValues[4]);
            transactionInfo.setTxtAdditionalInfo3(transactionValues[5]);
            transactionInfo.setTxtAdditionalInfo4(transactionValues[6]);
            transactionInfo.setTxtAdditionalInfo5(transactionValues[7]);
            transactionInfo.setTxtAdditionalInfo6(transactionValues[8]);
            transactionInfo.setTxnAmount(transactionValues[9]);
        }

        return transactionInfo;
    }

}