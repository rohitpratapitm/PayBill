package com.example.sikar.paybill;

/**
 * Created by sikar on 7/19/2015.
 */
public class Account {

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
