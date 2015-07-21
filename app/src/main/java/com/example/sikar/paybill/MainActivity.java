package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mTextView = (TextView)findViewById(R.id.customer_name);
        Button viewBillButton = (Button)findViewById(R.id.view_Bill);
        viewBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MyHttpPostTask().execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public class MyHttpPostTask extends AsyncTask<Void,Void,Account>{

        private final static String USER_AGENT = "Mozilla/5.0";
        private static final String HOME_SCREEN = "http://www.mpcz.co.in/portal/Bhopal_home.portal?_nfpb=true&_pageLabel=custCentre_viewBill_bpl";
        private static final String LOGIN_SCREEN = "http://www.mpcz.co.in/onlineBillPayment?do=onlineBillPaymentUnregValidate";
        //instantiates httpclient to make request
        DefaultHttpClient mClient = new DefaultHttpClient();

        @Override
        protected Account doInBackground(Void... params) {
            String cookie = null;
            try {
                URL obj = new URL(HOME_SCREEN);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                // add request header
                con.setRequestProperty("User-Agent", USER_AGENT);
                //int responseCode = con.getResponseCode();
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
                return mClient.execute(request, responseHandler);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Account aAccount) {
            mTextView.setText(aAccount.getCustomerName());
        }
    }
    private class JSONResponseHandler implements ResponseHandler<Account> {

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
}
