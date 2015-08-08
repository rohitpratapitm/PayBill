package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sikar.web.HttpRequest;
import com.example.sikar.web.MPCZConstants;

import java.util.HashMap;
import java.util.Map;


public class PayBill extends Activity {

    private TransactionInfo mTransactionInfo;
    private Context mContext ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);

        mContext = this.getApplicationContext();

        mTransactionInfo = (TransactionInfo)getIntent().getExtras().get("TransactionInfo");

        TextView customerNameView = (TextView)findViewById(R.id.customer_name);
        customerNameView.setText(mTransactionInfo.getCustomerName());

        TextView accountNumberView = (TextView)findViewById(R.id.account_number);
        accountNumberView.setText(mTransactionInfo.getTxtCustomerID());

        TextView billAmountView = (TextView)findViewById(R.id.bill_amount);
        billAmountView.setText(mTransactionInfo.getTxnAmount());

        TextView billDueDateView = (TextView)findViewById(R.id.due_date);
        billDueDateView.setText(mTransactionInfo.getBillDueDate());

        TextView transactionTypeView = (TextView)findViewById(R.id.transaction_type);
        transactionTypeView.setText(mTransactionInfo.getTransactionType());

        TextView transactionStatusView = (TextView)findViewById(R.id.transaction_status);
        transactionStatusView.setText(mTransactionInfo.getTransactionStatus());

        Button confirmButton = (Button)findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                ProcessPaymentTask processPaymentTask = new ProcessPaymentTask(mTransactionInfo);
                processPaymentTask.execute();
                */
                Intent webViewIntent = new Intent();
                webViewIntent.setAction(Intent.ACTION_VIEW);
                webViewIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                webViewIntent.setData(Uri.parse(MPCZConstants.BILLDESK_PAYMENT_URL));
                //webViewIntent.putExtra("TransactionInfo",mTransactionInfo);
                startActivity(webViewIntent);
            }
        });
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
    public class ProcessPaymentTask extends AsyncTask<Void,Void,String>{

        public static final String PAYMENT_GATEWAY_URL = "https://www.billdesk.com/pgidsk/PGIMerchantPayment";
        private final TransactionInfo mTransactionInfo;
        private final Map<String,String> mQueryParameters;


        public ProcessPaymentTask(TransactionInfo aTransactionInfo){
            mTransactionInfo = aTransactionInfo;
            mQueryParameters = new HashMap<String,String>();
            initializeQueryParameters();
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpRequest postRequest = new HttpRequest(PAYMENT_GATEWAY_URL, HttpRequest.HTTP_REQUEST_TYPE.POST,mQueryParameters);
            String response = postRequest.sendPOSTRequest(mQueryParameters);
            String location = postRequest.getHeaderField("Location");
            String cookie = postRequest.getHeaderField("Set-Cookie");
            return location;
        }

        @Override
        protected void onPostExecute(String aLocation) {
            System.out.println(aLocation);
        }

        private Map<String,String> initializeQueryParameters(){

            mQueryParameters.put(MPCZConstants.RU,MPCZConstants.RU_ACKNOWLEDGMENT_VALUE);
            mQueryParameters.put(TransactionInfo.BILLER_ID,mTransactionInfo.getBillerId());
            mQueryParameters.put(TransactionInfo.TXT_CUSTOMER_ID,mTransactionInfo.getTxtCustomerID());
            mQueryParameters.put(TransactionInfo.TXT_AMOUNT,mTransactionInfo.getTxnAmount());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_1,mTransactionInfo.getTxtAdditionalInfo1());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_2,mTransactionInfo.getTxtAdditionalInfo2());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_3,mTransactionInfo.getTxtAdditionalInfo3());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_4,mTransactionInfo.getTxtAdditionalInfo4());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_5,mTransactionInfo.getTransactionType());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_6,mTransactionInfo.getTransactionStatus());
            mQueryParameters.put(TransactionInfo.MESSAGE,mTransactionInfo.getMessage());

            return mQueryParameters;
        }
    }
}
