package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.EditText;

import com.example.sikar.web.HttpPostTask;
import com.example.sikar.web.HttpRequest;
import com.example.sikar.web.JSONResponseHandler;
import com.example.sikar.web.MPCZConstants;
import com.example.sikar.web.utils.MySession;

import java.util.HashMap;
import java.util.Map;


public class PayBill extends Activity {

    private Account mAccount;
    private Context mContext ;
    private WebView mWebView;
    private EditText mStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);

        mContext = this.getApplicationContext();

        mStatusView = (EditText)findViewById(R.id.status);
        mWebView = (WebView)findViewById(R.id.webview);

        mAccount = (Account)getIntent().getExtras().get("Account");

        PayBillTask payBillTask = new PayBillTask(mAccount);
        payBillTask.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pay_bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class PayBillTask extends AsyncTask<String,Void,TransactionInfo> {

        private Map<String,String> mQueryParameters;
        private TransactionInfo mTransactionInfo;
        private Account mAccount;

        public PayBillTask(Account aAccount){
            mAccount = aAccount;
        }

        @Override
        protected TransactionInfo doInBackground(String... params) {

            mQueryParameters = initializeQueryParameters();
            HttpRequest httpGETRequest = new HttpRequest(MPCZConstants.PAYMENT_SCREEN,HttpRequest.HTTP_REQUEST_TYPE.GET,mQueryParameters);
            httpGETRequest.setCookie(MySession.getSessionCookie());
            String httpGETResponse = httpGETRequest.sendGETRequest();

            JSONResponseHandler handler = new JSONResponseHandler();

            mTransactionInfo = handler.handleTransactionResponse(httpGETResponse);

            return mTransactionInfo;
        }

        @Override
        protected void onPostExecute(TransactionInfo aTransactionInfo) {
            //mWebView.loadUrl(MPCZConstants.PAYMENT_SCREEN,mQueryParameters);
            mStatusView.setText(mTransactionInfo.getTxtAdditionalInfo6());
        }

        private Map<String,String> initializeQueryParameters(){


            BillInfo billInfo = mAccount.getBillInfo();

            Map<String,String> queryParameters = new HashMap<String,String>();
            //Constant properties
            queryParameters.put(MPCZConstants.POST_CHOOSE_IDENTIFIER, MPCZConstants.POST_CHOOSE_IDENTIFIER_VALUE);
            queryParameters.put(BillInfo.BILLER_ID,BillInfo.BILLER_ID_VALUE);
            queryParameters.put(MPCZConstants.RU,MPCZConstants.RU_ACKNOWLEDGMENT_VALUE);
            queryParameters.put(MPCZConstants.PAYMENT_GATEWAY,MPCZConstants.PAYMENT_GATEWAY_VALUE);
            queryParameters.put(MPCZConstants.SELECT_NAME,MPCZConstants.SELET_NAME_VALUE);

            //Account properties
            queryParameters.put(HttpPostTask.POST_ACCOUNT_ID,mAccount.getAccountId());
            queryParameters.put(Account.CUSTOMER_NAME,mAccount.getCustomerName());
            //queryParameters.put(Account.ADDRESS,mAccount.getAddress());
            queryParameters.put("consAddres",mAccount.getAddress());
            //queryParameters.put(Account.CITY,mAccount.getCity());
            queryParameters.put("city",mAccount.getCity());
            queryParameters.put(Account.MOBILE_NO,mAccount.getMobileNumber());
            queryParameters.put(Account.EMAIL_ID,mAccount.getEmailId());
            //Bill properties
            queryParameters.put(BillInfo.BILL_ID,billInfo.getBillId());
            queryParameters.put(BillInfo.AMT_TO_BE_PAID,billInfo.getAmtToBePaid());
            queryParameters.put(BillInfo.OUTSTANDING_AMT,billInfo.getOutStandingAmt());
            queryParameters.put(BillInfo.LAST_BILL_AMT,billInfo.getLastBillAmt());
            queryParameters.put(BillInfo.CURRENT_BILL_AMT,billInfo.getCurrentBillAmt());
            //queryParameters.put(BillInfo.MONTH,billInfo.getBillMonth());
            queryParameters.put("billMon",billInfo.getBillMonth());
            queryParameters.put(BillInfo.ISSUE_DATE,billInfo.getBillIssueDate());
            queryParameters.put("billdueDate",billInfo.getBillDueDate());

            return queryParameters;

        }
    }
}
