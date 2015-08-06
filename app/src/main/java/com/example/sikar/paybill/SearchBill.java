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
import android.widget.EditText;

import com.example.sikar.web.HttpRequest;
import com.example.sikar.web.ResponseHandler;
import com.example.sikar.web.MPCZConstants;
import com.example.sikar.web.utils.MySession;

import java.io.IOException;
import java.util.HashMap;


public class SearchBill extends Activity {

    private EditText mAccountIdView;
    private Context mContext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bill);

        mContext = this.getApplicationContext();

        mAccountIdView = (EditText)findViewById(R.id.account_id);

        Button viewBill = (Button)findViewById(R.id.view_bill);
        viewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = mAccountIdView.getText().toString();
                //Execute in back ground
                SearchBillTask searchBillTask = new SearchBillTask(mContext);
                searchBillTask.execute(accountId);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_bill, menu);
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

     public class SearchBillTask extends AsyncTask<String,Void,Void> {

         public static final String POST_ACCOUNT_ID = "accntId";
         public static final String POST_CHOOSE_IDENTIFIER = "chooseIdentifier";
         public static final String POST_CHOOSE_IDENTIFIER_VALUE = "Account ID";

         private String mSessionCookie;
         private Account mAccount;
         private final Context mContext;

         public SearchBillTask(Context aContext){
                mContext = aContext;
         }

         @Override
         protected Void doInBackground(String... params) {

             mSessionCookie = MySession.getSessionCookie();

             //1.Make a POST Request and send account Id as parameter
             HashMap<String, String> postQueryParameters = new HashMap<String, String>();
             postQueryParameters.put(POST_CHOOSE_IDENTIFIER, POST_CHOOSE_IDENTIFIER_VALUE);
             postQueryParameters.put(POST_ACCOUNT_ID, params[0]);

             HttpRequest httpPOSTRequest = new HttpRequest(MPCZConstants.LOGIN_SCREEN, HttpRequest.HTTP_REQUEST_TYPE.POST, postQueryParameters);
             httpPOSTRequest.setCookie(mSessionCookie);
             String httpPOSTResponse = httpPOSTRequest.sendPOSTRequest(postQueryParameters);

             ResponseHandler responseHandler = new ResponseHandler();
             try {
                 mAccount = responseHandler.handleAccountResponse(httpPOSTResponse);
             } catch (IOException aIOException) {
                 aIOException.printStackTrace();
             }
             return null;
         }

         @Override
         protected void onPostExecute(Void aNull) {

             Intent showBillIntent = new Intent(SearchBill.this, ShowBill.class);
             showBillIntent.putExtra("Account", mAccount);
             showBillIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             mContext.startActivity(showBillIntent);
         }

         public Account getResult(){
             return mAccount;
         }

     }
}
