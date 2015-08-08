package com.example.sikar.paybill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;


public class ShowAccounts extends Activity {

    private Context mContext;
    private ListView mListView;
    private Button mNewAccountButton;
    private AccountArrayAdapter mAdapter;
    private List<Account> mAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_accounts);

        mContext = this.getApplicationContext();

        mAccounts = (List<Account>)getIntent().getExtras().get("Accounts");

        mNewAccountButton = (Button)findViewById(R.id.newAccount);
        mNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newAccountIntent = new Intent(mContext,CreateAccount.class);
                startActivity(newAccountIntent);
            }
        });
        mListView = (ListView)findViewById(R.id.listview);

        final AccountArrayAdapter adapter = new AccountArrayAdapter(mContext,mAccounts);
        mListView.setAdapter(adapter);
        mListView.setVisibility(View.VISIBLE);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Account account = (Account) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {

                                Intent showBillIntent = new Intent(mContext, ShowBill.class);
                                showBillIntent.putExtra("Account", account);
                                showBillIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(showBillIntent);

                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_accounts, menu);
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
