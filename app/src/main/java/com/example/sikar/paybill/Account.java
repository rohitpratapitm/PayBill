package com.example.sikar.paybill;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sikar on 7/19/2015.
 */
public class Account implements Serializable{

    public  static final String ACCOUNT_ID     = "accountId";
    public static final String CONNECTION_NO = "connectionNo";
    public static final String CUSTOMER_NAME  = "customerName";
    public static final String CITY = "consCity";
    public static final String ADDRESS = "consAddr";
    public static final String MOBILE_NO = "mblNum";
    public static final String EMAIL_ID = "emailId";

    private  String mAccountId;
    private  String mConnectionNo;
    private  String mCustomerName;
    private  String mCity;
    private  String mAddress;
    private  String mMobileNumber;
    private  String mEmailId;
    private  BillInfo mBillInfo;

    public Account(){

    }
    public Account(String aAccountId,String aConnectionNo,String aCustomerName,String aCity,String aMobileNumber,String aEmailId,String aAddress){
        mAccountId    = aAccountId;
        mConnectionNo = aConnectionNo;
        mCustomerName = aCustomerName;
        mCity         = aCity;
        mAddress      = aAddress;
        mMobileNumber = aMobileNumber;
        mEmailId      = aEmailId;
    }


    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String aCustomerName) {
        this.mCustomerName = aCustomerName;
    }

    public String getConnectionNo() {
        return mConnectionNo;
    }

    public void setConnectionId(String aConnectionNo) {
        this.mConnectionNo = aConnectionNo;
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public void setMobileNumber(String aMobileNumber) {
        this.mMobileNumber = aMobileNumber;
    }

    public String getEmailId() {
        return mEmailId;
    }

    public void setEmailId(String aEmailId) {
        this.mEmailId = aEmailId;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String aAddress) {
        this.mAddress = aAddress;
    }
    public String getAccountId() {
        return mAccountId;
    }

    public void setAccountId(String aAccountId) {
        this.mAccountId = aAccountId;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String aCity) {
        this.mCity = aCity;
    }

    public void setBillInfo(BillInfo aBillInfo){
        this.mBillInfo = aBillInfo;
    }

    public BillInfo getBillInfo(){
        return mBillInfo;
    }
}
 class BillInfo {

    public static final String BILL_ID  = "billId";

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
