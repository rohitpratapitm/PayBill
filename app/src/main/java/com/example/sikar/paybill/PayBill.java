package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.sikar.web.HttpPostTask;
import com.example.sikar.web.HttpRequest;
import com.example.sikar.web.JSONResponseHandler;
import com.example.sikar.web.MPCZConstants;
import com.example.sikar.web.utils.MySession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class PayBill extends Activity {

    private Account mAccount;
    private Context mContext ;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);

        mContext = this.getApplicationContext();

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

    class PayBillTask extends AsyncTask<String,Void,Object> {

        private Map<String,String> mQueryParameters;
        private Object mResult;
        private Account mAccount;

        public PayBillTask(Account aAccount){
            mAccount = aAccount;
        }

        @Override
        protected Object doInBackground(String... params) {

            mQueryParameters = initializeQueryParameters();
            HttpRequest httpGETRequest = new HttpRequest(MPCZConstants.PAYMENT_SCREEN,HttpRequest.HTTP_REQUEST_TYPE.GET,mQueryParameters);
            httpGETRequest.setCookie(MySession.getSessionCookie());
            String httpGETResponse = httpGETRequest.sendGETRequest();
            JSONResponseHandler handler = new JSONResponseHandler();
            try {
                mResult = handler.handleResponse(httpGETResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return mResult;
        }

        @Override
        protected void onPostExecute(Object aResult) {
            //mWebView.loadUrl(MPCZConstants.PAYMENT_SCREEN,mQueryParameters);
        }

        private Map<String,String> initializeQueryParameters(){
            // selectname=PaymentUpdation&_dc=1437991416092&accntId=9493692000&billerid=MPMKBHORAP&RU=http%3A%2F%2Fwww.mpcz.co.in%2FpaymentAck&chooseIdentifier=Account%20ID&
            // amtToBePaid=0&outstandingAmt=0&customerName=SWAMI%20SARAN%20SHARMA&billId=184255954&lastBillAmt=0.00&currentBillAmt=408&billmon=JUL-2015&billissuedate=07-JUL-2015&
            // billdueDate=20-JUL-2015&consAddres=Q.NO.30GOSPURA%20COLONY%2030%2FA%20TAN&city=TANSEN&mblNum=9346584202&payGateway=BILLDESK&emailId=rohitpratapitm%40gmail.com
            BillInfo billInfo = mAccount.getBillInfo();

            Map<String,String> queryParameters = new HashMap<String,String>();
            //Constant properties
            queryParameters.put(HttpPostTask.POST_CHOOSE_IDENTIFIER, HttpPostTask.POST_CHOOSE_IDENTIFIER_VALUE);
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
            queryParameters.put(BillInfo.DUE_DATE,billInfo.getBillDueDate());

            return queryParameters;
        }
    }
}
