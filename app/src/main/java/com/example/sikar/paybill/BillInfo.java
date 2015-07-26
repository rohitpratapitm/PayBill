package com.example.sikar.paybill;

import java.io.Serializable;

/**
 * Created by sikar on 7/22/2015.
 */
public class BillInfo implements Serializable{

    public static final String BILL_ID = "billId";

    public static final String MONTH = "billMonth";
    public static final String ISSUE_DATE = "billIssueDate";
    public static final String DUE_DATE = "billDueDate";

    public static final String OUTSTANDING_AMT = "outstandingAmt";
    public static final String LAST_BILL_AMT = "lastBillAmt";
    public static final String CURRENT_BILL_AMT = "currentBillAmt";
    public static final String AMT_TO_BE_PAID = "amtToBePaid";


    private String mBillId;
    private String mBillDueDate;
    private String mBillIssueDate;
    private String mBillMonth;

    private String mAmtToBePaid;
    private String mLastBillAmt;
    private String mOutStandingAmt;
    private String mCurrentBillAmt;

    public String getBillId() {
        return mBillId;
    }

    public void setBillId(String aBillId) {
        this.mBillId = aBillId;
    }

    public String getBillDueDate() {
        return mBillDueDate;
    }

    public void setBillDueDate(String aBillDueDate) {
        this.mBillDueDate = aBillDueDate;
    }

    public String getBillIssueDate() {
        return mBillIssueDate;
    }

    public void setBillIssueDate(String aBillIssueDate) {
        this.mBillIssueDate = aBillIssueDate;
    }

    public String getBillMonth() {
        return mBillMonth;
    }

    public void setBillMonth(String aBillMonth) {
        this.mBillMonth = aBillMonth;
    }

    public String getAmtToBePaid() {
        return mAmtToBePaid;
    }

    public void setAmtToBePaid(String aAmtToBePaid) {
        this.mAmtToBePaid = aAmtToBePaid;
    }

    public String getLastBillAmt() {
        return mLastBillAmt;
    }

    public void setLastBillAmt(String aLastBillAmt) {
        this.mLastBillAmt = aLastBillAmt;
    }

    public String getOutStandingAmt() {
        return mOutStandingAmt;
    }

    public void setOutStandingAmt(String aOutStandingAmt) {
        this.mOutStandingAmt = aOutStandingAmt;
    }

    public String getCurrentBillAmt() {
        return mCurrentBillAmt;
    }

    public void setCurrentBillAmt(String aCurrentBillAmt) {
        this.mCurrentBillAmt = aCurrentBillAmt;
    }
}