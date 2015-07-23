package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ShowBill extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill);

        Account account = (Account)getIntent().getExtras().get("Account");

        TextView customerNameView = (TextView)findViewById(R.id.customer_name);
        customerNameView.setText(account.getCustomerName());

        Button payBillButton = (Button)findViewById(R.id.pay_bill);
        payBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent payBillIntent = new Intent(Intent.ACTION_VIEW);
                //payBillIntent.
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
}