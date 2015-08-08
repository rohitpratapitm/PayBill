package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sikar.web.HttpRequest;
import com.example.sikar.web.ResponseHandler;
import com.example.sikar.web.MPCZConstants;
import com.example.sikar.web.utils.MySession;

import java.util.HashMap;
import java.util.Map;


public class ShowBill extends Activity {

    private Account mAccount;
    private Context mContext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill);

        mContext = this.getApplicationContext();

        mAccount = (Account)getIntent().getExtras().get("Account");

        TextView customerNameView = (TextView)findViewById(R.id.customer_name);
        if(mAccount == null){
            customerNameView.setText("Account object is NULL");
            return;
        }
        BillInfo billInfo = mAccount.getBillInfo();

        customerNameView.setText(mAccount.getCustomerName());

        TextView accountNumberView = (TextView)findViewById(R.id.account_number);
        accountNumberView.setText(mAccount.getAccountId());

        TextView billAmountView = (TextView)findViewById(R.id.bill_amount);
        billAmountView.setText(billInfo.getAmtToBePaid());

        TextView billDueDatetView = (TextView)findViewById(R.id.due_date);
        billDueDatetView.setText(billInfo.getBillDueDate());

        TextView mobileNumberView = (TextView)findViewById(R.id.mobile_number);
        mobileNumberView.setText(mAccount.getMobileNumber());

        final Button payBillButton = (Button)findViewById(R.id.button_pay_bill);
        payBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PayBillTask payBillTask = new PayBillTask(mAccount);
                payBillTask.execute();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_bill, menu);
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

            ResponseHandler handler = new ResponseHandler();

            mTransactionInfo = handler.handleTransactionResponse(httpGETResponse);
            mTransactionInfo.setCustomerName(mAccount.getCustomerName());
            mTransactionInfo.setBillDueDate(mAccount.getBillInfo().getBillDueDate());
            return mTransactionInfo;
        }

        @Override
        protected void onPostExecute(TransactionInfo aTransactionInfo) {

            Intent payBillIntent = new Intent(ShowBill.this,PayBill.class);
            payBillIntent.putExtra("TransactionInfo",aTransactionInfo);
            startActivity(payBillIntent);
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
            queryParameters.put(MPCZConstants.POST_ACCOUNT_ID,mAccount.getAccountId());
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
