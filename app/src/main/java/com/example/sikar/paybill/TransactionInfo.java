package com.example.sikar.paybill;

import com.example.sikar.web.MPCZConstants;

import java.io.Serializable;

/**
 * Created by sikar on 8/1/2015.
 */
public class TransactionInfo implements Serializable {

    public static final String TXT_ADDITIONAL_INFO_1 = "txtAdditionalInfo1";
    public static final String TXT_ADDITIONAL_INFO_2 = "txtAdditionalInfo2";
    public static final String TXT_ADDITIONAL_INFO_3 = "txtAdditionalInfo3";
    public static final String TXT_ADDITIONAL_INFO_4 = "txtAdditionalInfo4";
    public static final String TXT_ADDITIONAL_INFO_5 = "txtAdditionalInfo5";
    public static final String TXT_ADDITIONAL_INFO_5_VALUE = "Normal";
    public static final String TXT_ADDITIONAL_INFO_6 = "txtAdditionalInfo6";
    public static final String TXT_ADDITIONAL_INFO_6_VALUE = "Pending";
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
    private String mRU = MPCZConstants.RU_ACKNOWLEDGMENT_VALUE;
    private String mBillerId = BillInfo.BILLER_ID_VALUE;
    private String mTxtCustomerID ;
    private String mTxnAmount;
    private String mTxtAdditionalInfo1;
    private String mTxtAdditionalInfo2;
    private String mTxtAdditionalInfo3;
    private String mTxtAdditionalInfo4;
    private String mTxtAdditionalInfo5;
    private String mTxtAdditionalInfo6;
}
