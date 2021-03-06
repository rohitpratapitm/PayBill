package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sikar.web.HttpRequest;
import com.example.sikar.web.MPCZConstants;

import java.net.HttpURLConnection;
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

                ProcessPaymentTask processPaymentTask = new ProcessPaymentTask(mTransactionInfo);
                processPaymentTask.execute();
              /*
                Intent webViewIntent = new Intent();
                webViewIntent.setAction(Intent.ACTION_VIEW);
                webViewIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                webViewIntent.setData(Uri.parse(MPCZConstants.BILLDESK_PAYMENT_URL));
                //webViewIntent.putExtra("TransactionInfo",mTransactionInfo);
                startActivity(webViewIntent);
                */
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
            postRequest.initializeHeader(initializeHeaderParameters());
            String response = postRequest.sendPOSTRequest(mQueryParameters, false);
            int responseCode = postRequest.getResponseCode();
            if(responseCode== HttpURLConnection.HTTP_MOVED_TEMP){
                System.out.println("Its a temporary redirection request 302");
            }
            String location = postRequest.getHeaderField("Location");
            String cookie = postRequest.getHeaderField("Set-Cookie");
            return location;
        }

        @Override
        protected void onPostExecute(String aLocation) {
            System.out.println(aLocation);
        }

        private Map<String,String> initializeHeaderParameters(){

            Map<String,String> headerParameters = new HashMap<String,String>();
            headerParameters.put(MPCZConstants.HEADER_ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            headerParameters.put(MPCZConstants.HEADER_ACCEPT_ENCODING, "gzip, deflate");
            headerParameters.put(MPCZConstants.HEADER_COOKIE, null);
            headerParameters.put(MPCZConstants.HEADER_USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
            headerParameters.put(MPCZConstants.HEADER_ACCEPT_LANGUAGE, "en-US,en;q=0.8,hi;q=0.6");
            headerParameters.put("Upgrade-Insecure-Requests","1");
            headerParameters.put(MPCZConstants.HEADER_CONNECTION, "keep-alive");
            headerParameters.put(MPCZConstants.HEADER_REFERER, "http://www.mpcz.co.in/portal/Bhopal_home.portal?_nfpb=true&_pageLabel=custCentre_OBP_bpl");
            headerParameters.put(MPCZConstants.HEADER_HOST, MPCZConstants.HOST_URL_BILLDESK);
            headerParameters.put(MPCZConstants.HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded");
            headerParameters.put("Cache-Control","max-age=0");
            headerParameters.put("Origin",MPCZConstants.HOST_URL_MPCZ);

            return headerParameters;
        }
        private Map<String,String> initializeQueryParameters(){

            mQueryParameters.put(MPCZConstants.RU, MPCZConstants.RU_ACKNOWLEDGMENT_VALUE);
            mQueryParameters.put(TransactionInfo.BILLER_ID, mTransactionInfo.getBillerId());
            mQueryParameters.put(TransactionInfo.TXT_CUSTOMER_ID, mTransactionInfo.getTxtCustomerID());
            mQueryParameters.put(TransactionInfo.TXT_AMOUNT, mTransactionInfo.getTxnAmount());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_1, mTransactionInfo.getTxtAdditionalInfo1());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_2, mTransactionInfo.getTxtAdditionalInfo2());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_3, mTransactionInfo.getTxtAdditionalInfo3());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_4, mTransactionInfo.getTxtAdditionalInfo4());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_5, mTransactionInfo.getTransactionType());
            mQueryParameters.put(TransactionInfo.TXT_ADDITIONAL_INFO_6, mTransactionInfo.getTransactionStatus());
            mQueryParameters.put(TransactionInfo.MESSAGE, mTransactionInfo.getMessage());

            return mQueryParameters;
        }
    }
}
