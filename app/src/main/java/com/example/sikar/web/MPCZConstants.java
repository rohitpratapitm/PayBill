package com.example.sikar.web;

/**
 * Created by sikar on 7/29/2015.
 */
public class MPCZConstants {

    public static final String HOST_URL = "http://www.mpcz.co.in/portal/Bhopal_home.portal";
    public static final String HOME_SCREEN = "http://www.mpcz.co.in/portal/Bhopal_home.portal?_nfpb=true&_pageLabel=custCentre_viewBill_bpl";
    public static final String LOGIN_SCREEN = "http://www.mpcz.co.in/onlineBillPayment?do=onlineBillPaymentUnregValidate";

    public static final String PAYMENT_SCREEN = "http://www.mpcz.co.in/PaymentServlet";

    public static final String POST_CHOOSE_IDENTIFIER = "chooseIdentifier";
    public static final String POST_CHOOSE_IDENTIFIER_VALUE = "Account ID";

    public static final String RU = "RU";
    public static final String RU_ACKNOWLEDGMENT_VALUE = "http://www.mpcz.co.in/paymentAck";

    public static final String BILLDESK_PAYMENT_URL = "https://www.billdesk.com/pgidsk/PGIMerchantPayment";
    public static final String PAYMENT_GATEWAY = "payGateway";
    public static final String PAYMENT_GATEWAY_VALUE = "BILLDESK";

    public static final String SELECT_NAME = "selectname";
    public static final String SELET_NAME_VALUE = "PaymentUpdation";

    /** On click of Pay Now, it will take you to billdesk.
     * <form method="post" name="mysubmitform" id="mysubmitform" action="https://www.billdesk.com/pgidsk/PGIMerchantPayment">
     <input type="hidden" id="RU"  name="RU" value="http://www.mpcz.co.in/paymentAck"/>



     <!--  form action="/paymentAck" method="post"> -->
     <input type="hidden" id="billerid" name="billerid" value="MPMKBHORAP"/>


     <input type="hidden" id="txtCustomerID" name="txtCustomerID" value="9493692000" />
     <input type="hidden" id="txtTxnAmount" name="txtTxnAmount" value="0" />

     <input type="hidden" id="txtAdditionalInfo1"  name="txtAdditionalInfo1" value="11593777"/>
     <input type="hidden" id="txtAdditionalInfo2"  name="txtAdditionalInfo2" value="2424200" />

     <input type="hidden" id="txtAdditionalInfo3"  name="txtAdditionalInfo3"  value="2429500"/>
     <input type="hidden" id="txtAdditionalInfo4"  name="txtAdditionalInfo4" value="01-08-2015"/>

     <input type="hidden" id="txtAdditionalInfo5"  name="txtAdditionalInfo5" value="Normal"/>
     <input type="hidden" id="txtAdditionalInfo6"  name="txtAdditionalInfo6" value="Pending" />
     <input type="hidden" id="msg"  name="msg" value="MPMKBHOPAL|11593777|NA|0|NA|NA|NA|INR|NA|R|mpmkbhopal|NA|NA|F|9493692000|2424200|2429500|20-07-2015|2424205|SWAMI SARAN SHARMA|TANSEN|http://www.mpcz.co.in/paymentAck|2280209535" />

     */
}
