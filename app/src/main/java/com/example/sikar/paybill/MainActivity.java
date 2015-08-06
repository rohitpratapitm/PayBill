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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Context mContext;
       @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this.getApplicationContext();

        Button showAccountsButton = (Button)findViewById(R.id.show_accounts);
        showAccountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReadAccountsFromDiskTask readAccountsFromDiskTask = new ReadAccountsFromDiskTask(mContext);
                readAccountsFromDiskTask.execute();
            }
        });
        /*//1. Fetch existing records
        Account existingRecord = readRecord();

        //2. Show this on layout
        if(existingRecord != null){
            Intent showBillIntent = new Intent(MainActivity.this,ShowBill.class);
            showBillIntent.putExtra("Account",existingRecord);
            startActivity(showBillIntent);
        }/*else{
            Intent searchBillIntent = new Intent(MainActivity.this,SearchBill.class);
            startActivity(searchBillIntent);
        }*/

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
    public class ReadAccountsFromDiskTask extends AsyncTask<Void,Void,List<Account>> {



        private ArrayList<Account> mAccounts;
        private final Context mContext;

        public ReadAccountsFromDiskTask(Context aContext){
            this.mContext = aContext;
            this.mAccounts = new ArrayList<Account>();
        }
        @Override
        protected List<Account> doInBackground(Void... params) {
            {
                FileInputStream fis = null;
                ObjectInputStream ois = null;
                try {
                    fis = mContext.openFileInput(Account.FILENAME);
                    ois = new ObjectInputStream(fis);
                    mAccounts = (ArrayList<Account>)ois.readObject();
                    //Integer noOfRecords = ois.readInt();

                    /*Account account = null;
                    while((account = (Account)ois.readObject()) != null){
                        //
                        mAccounts.add(account);
                    }*/

                } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally{
                    try {
                        if(ois !=null)ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return mAccounts;
        }

        @Override
        protected void onPostExecute(List<Account> aAccounts) {

            Intent showAccountsIntent = new Intent(mContext,ShowAccounts.class);
            showAccountsIntent.putExtra("Accounts", mAccounts);
            showAccountsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(showAccountsIntent);
        }
    }
}
