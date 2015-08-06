package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sikar.web.HttpPostTask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class CreateAccount extends Activity {

    private Context mContext;
    private static int mNumberOfRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mContext = this.getApplicationContext();
        //Get Accound Id
        final EditText accountIdView = (EditText)findViewById(R.id.account_id);

        //Get Mobile Number
        final EditText mobileNumberView = (EditText)findViewById(R.id.mobile_number);

        //Get Accound Id
        final EditText emailIdView = (EditText)findViewById(R.id.email_Id);

        Button saveButton = (Button)findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String accountId = accountIdView.getText().toString();
                final String mobileNumber = mobileNumberView.getText().toString();
                final String emailId = emailIdView.getText().toString();

                Account newAccount = null;
                try{
                    //verify and Save Info
                    Intent searchBillIntent = new Intent(mContext,SearchBill.class);

                    HttpPostTask asyncTask = new HttpPostTask(CreateAccount.this);
                    //execute the task
                    asyncTask.execute(accountId);
                    //fetch results on completion
                    newAccount = asyncTask.get();
                    if(newAccount != null){
                        newAccount.setMobileNumber(mobileNumber);
                        newAccount.setEmailId(emailId);
                    }else{
                        //show dialog box
                    }
                    //Save account info
                    saveInfo(newAccount);

                }catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveInfo(Account aAccount)  {

        FileOutputStream fos = null;
        FileInputStream fis = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ArrayList<Account> accounts = null;


        try {
            fis = openFileInput(Account.FILENAME);
            ois = new ObjectInputStream(fis);
            accounts = (ArrayList<Account>)ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(accounts == null){
                    accounts = new ArrayList<Account>();
                }
                accounts.add(aAccount);
                fos = openFileOutput(Account.FILENAME, Context.MODE_PRIVATE);
                oos = new ObjectOutputStream(fos);

                oos.writeObject(accounts);
                oos.flush();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
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
