package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URI;


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

                new MyTask().execute();
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

    public class MyTask extends AsyncTask<Void,Void,BillInfo>{

        @Override
        protected BillInfo doInBackground(Void... params) {

            MyHttpAPI myHttpAPI = new MyHttpAPI("");
            MyJSONObject jsonObject = myHttpAPI.getJsonAsMap();
            BillInfo billInfo = jsonObject.getBillInfo();
            return billInfo;
        }
        @Override
        protected void onPostExecute(BillInfo aBillInfo) {
            mTextView.setText(aBillInfo.getmCustomerName());
        }
    }
}
