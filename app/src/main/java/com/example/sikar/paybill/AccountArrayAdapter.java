package com.example.sikar.paybill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sikar on 8/6/2015.
 */
public class AccountArrayAdapter extends ArrayAdapter<Account> {

    private final Context mContext;
    private final List<Account> mAccounts;

    /**
     * Constructor
     *
     * @param aContext  The current context.
     * @param aAccounts List of accounts
     */
    public AccountArrayAdapter(Context aContext, List<Account> aAccounts) {

        super(aContext,-1, aAccounts);
        this.mContext = aContext;
        this.mAccounts = aAccounts;
    }

    /**
     * {@inheritDoc}
     *
     * @param aPosition
     * @param aConvertView
     * @param aParent
     */
    @Override
    public View getView(int aPosition, View aConvertView, ViewGroup aParent) {

        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.account_row_layout,aParent,false);
        TextView customerNameView = (TextView)rowView.findViewById(R.id.mycustomerName);
        customerNameView.setText(mAccounts.get(aPosition).getCustomerName());
        return rowView;
    }


}
