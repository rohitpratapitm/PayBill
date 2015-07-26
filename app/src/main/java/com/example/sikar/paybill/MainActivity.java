package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Context;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

        Button newAccountButton = (Button)findViewById(R.id.new_account);
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent createNewAccount = new Intent(MainActivity.this,CreateAccount.class);
                startActivity(createNewAccount);
            }
        });
        //1. Fetch existing records
        Account existingRecord = readRecord();

        //2. Show this on layout
        if(existingRecord != null){
            Intent showBillIntent = new Intent(MainActivity.this,ShowBill.class);
            showBillIntent.putExtra("Account",existingRecord);
            startActivity(showBillIntent);
        }else{
            Intent searchBillIntent = new Intent(MainActivity.this,SearchBill.class);
            startActivity(searchBillIntent);
        }

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

    private Account readRecord()  {
        String FILENAME = "account_info";
        String string = "hello world!";
        Account account = null;

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {

            fis = openFileInput(FILENAME);
            ois = new ObjectInputStream(fis);
            account = (Account)ois.readObject();

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
        return account;
    }
}
