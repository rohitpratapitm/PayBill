package com.example.sikar.paybill;

import com.example.sikar.web.MPCZConstants;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by sikar on 8/1/2015.
 */
public class TransactionInfo implements Serializable {

    public static final String BILLER_ID = "billerid";
    public static final String TXT_CUSTOMER_ID = "txtCustomerID";
    public static final String TXT_AMOUNT = "txtTxnAmount";
    public static final String TXT_ADDITIONAL_INFO_1 = "txtAdditionalInfo1";
    public static final String TXT_ADDITIONAL_INFO_2 = "txtAdditionalInfo2";
    public static final String TXT_ADDITIONAL_INFO_3 = "txtAdditionalInfo3";
    public static final String TXT_ADDITIONAL_INFO_4 = "txtAdditionalInfo4";
    public static final String TXT_ADDITIONAL_INFO_5 = "txtAdditionalInfo5";
    public static final String TXT_ADDITIONAL_INFO_6 = "txtAdditionalInfo6";
    public static final String MESSAGE = "msg";
    //public static final String TXT_ADDITIONAL_INFO_5_VALUE = "Normal";
     // public static final String TXT_ADDITIONAL_INFO_6_VALUE = "Pending";
    /*['11594354','MPMKBHORAP','0355403000','http://www.mpcz.co.in/paymentAck','2424200','2429500','01-08-2015','Normal','Pending','0']
    *

    RU	http://www.mpcz.co.in/paymentAck
    billerid	MPMKBHORAP
    txtCustomerID	0355403000
    txtTxnAmount	0
    txtAdditionalInfo1	11594354
    txtAdditionalInfo2	2424200
    txtAdditionalInfo3	2429500
    txtAdditionalInfo4	01-08-2015
    txtAdditionalInfo5	Normal
    txtAdditionalInfo6	Pending
    */
    private String mCustomerName;
    private String mRU = MPCZConstants.RU_ACKNOWLEDGMENT_VALUE;
    private String mBillerId = BillInfo.BILLER_ID_VALUE;
    private String mTxtCustomerID ;
    private String mTxnAmount;
    private String mTxtAdditionalInfo1;
    private String mTxtAdditionalInfo2;
    private String mTxtAdditionalInfo3;
    private String mTxtAdditionalInfo4;
    private String mTransactionType;
    private String mTransactionStatus;
    private String mMessage;
    private String mBillDueDate;

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String aCustomerName) {
        this.mCustomerName = aCustomerName;
    }
    public String getRU() {
        return mRU;
    }
    public void setRU(String aRU) {
        this.mRU = aRU;
    }
    public String getBillerId() {
        return mBillerId;
    }
    public void setBillerId(String aBillerId) {
        this.mBillerId = aBillerId;
    }
    public String getTxtCustomerID() {
        return mTxtCustomerID;
    }
    public void setTxtCustomerID(String aTxtCustomerID) {
        this.mTxtCustomerID = aTxtCustomerID;
    }
    public String getTxnAmount() {
        Double txtAmountDouble = Double.parseDouble(mTxnAmount);
        Integer txtAmountInteger = txtAmountDouble.intValue();
        return txtAmountInteger.toString();
    }
    public void setTxnAmount(String aTxnAmount) {
        this.mTxnAmount = aTxnAmount;
    }
    public String getTxtAdditionalInfo1() {
        return mTxtAdditionalInfo1;
    }
    public void setTxtAdditionalInfo1(String aTxtAdditionalInfo1) {
        this.mTxtAdditionalInfo1 = aTxtAdditionalInfo1;
    }
    public String getTxtAdditionalInfo2() {
        return mTxtAdditionalInfo2;
    }
    public void setTxtAdditionalInfo2(String aTxtAdditionalInfo2) {
        this.mTxtAdditionalInfo2 = aTxtAdditionalInfo2;
    }
    public String getTxtAdditionalInfo3() {
        return mTxtAdditionalInfo3;
    }
    public void setTxtAdditionalInfo3(String aTxtAdditionalInfo3) {
        this.mTxtAdditionalInfo3 = aTxtAdditionalInfo3;
    }
    public String getTxtAdditionalInfo4() {
        return mTxtAdditionalInfo4;
    }
    public void setTxtAdditionalInfo4(String aTxtAdditionalInfo4) {
        this.mTxtAdditionalInfo4 = aTxtAdditionalInfo4;
    }
    public String getTransactionType() {
        return mTransactionType;
    }
    public void setTxtAdditionalInfo5(String aTxtAdditionalInfo5) {
        this.mTransactionType = aTxtAdditionalInfo5;
    }
    public String getTransactionStatus() {
        return mTransactionStatus;
    }
    public void setTxtAdditionalInfo6(String aTxtAdditionalInfo6) {
        this.mTransactionStatus = aTxtAdditionalInfo6;
    }
    public String getBillDueDate() {
        return mBillDueDate;
    }
    public void setBillDueDate(String aBillDueDate) {
        if(aBillDueDate != null){
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new SimpleDateFormat("dd-MMM-yyyy").parse(aBillDueDate));
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                mBillDueDate = sdf.format(cal.getTime());

            } catch (ParseException e)
            { e.printStackTrace(); }
        }
    }
   /*
    MPMKBHOPAL|11610905|NA|0|NA|NA|NA|INR|NA|R|mpmkbhopal|NA|NA|F|9493692000|2424200|2429500|20-07-2015|2424205|
    SWAMI SARAN SHARMA|TANSEN|http://www.mpcz.co.in/paymentAck|4068340746
    */
    public String getMessage(){
        String SEPARATOR = "|";
        String NOT_APPLICABLE = "NA";
        String CURRENCY = "INR";
        String billerId = "MPMKBHOPAL";
        StringBuffer message = new StringBuffer();
        message.append(billerId);message.append(SEPARATOR);
        message.append(mTxtAdditionalInfo1);message.append(SEPARATOR);
        message.append(NOT_APPLICABLE);message.append(SEPARATOR);
        message.append(mTxnAmount);message.append(SEPARATOR);
        message.append(NOT_APPLICABLE);message.append(SEPARATOR);
        message.append(NOT_APPLICABLE);message.append(SEPARATOR);
        message.append(NOT_APPLICABLE);message.append(SEPARATOR);
        message.append(CURRENCY);message.append(SEPARATOR);
        message.append(NOT_APPLICABLE);message.append(SEPARATOR);
        message.append("R");message.append(SEPARATOR);
        message.append(billerId.toLowerCase());message.append(SEPARATOR);
        message.append(NOT_APPLICABLE);message.append(SEPARATOR);
        message.append(NOT_APPLICABLE);message.append(SEPARATOR);
        message.append("F");message.append(SEPARATOR);
        message.append(mTxtCustomerID);message.append(SEPARATOR);
        message.append(mTxtAdditionalInfo2);message.append(SEPARATOR);
        message.append(mBillDueDate);message.append(SEPARATOR);
        message.append("2424205");message.append(SEPARATOR);
        message.append(mCustomerName);message.append(SEPARATOR);
        message.append(mRU);message.append(SEPARATOR);
        message.append("4068340746");
        mMessage = message.toString();

        return mMessage;
    }
    @Override
    public String toString() {


        return super.toString();
    }


}
