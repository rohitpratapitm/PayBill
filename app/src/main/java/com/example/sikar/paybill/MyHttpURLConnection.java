package com.example.sikar.paybill;

/**
 * Created by sikar on 7/17/2015.
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpURLConnection {

    private final String USER_AGENT = "Mozilla/5.0";
    private static final String SEE_BILL = "http://www.mpcz.co.in/portal/Bhopal_home.portal?_nfpb=true&_pageLabel=custCentre_viewBill_bpl";
    private static final String ENTER_CREDENTIALS = "https://www.mpcz.co.in/onlineBillPayment?do=onlineBillPaymentUnregValidate";

    public static void main(String[] args) throws Exception {

        MyHttpURLConnection http = new MyHttpURLConnection();

        System.out.println("Testing 1 - Send Http GET request");
        String cookie = "";
        http.sendGet(SEE_BILL,cookie);

        System.out.println("\nTesting 2 - Send Http POST request with Cookie : "+cookie);
        http.sendPost(ENTER_CREDENTIALS,cookie);

    }

    // HTTP GET request
    private void sendGet(String aURL,String aCookie) throws Exception {

        String url = aURL;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("Set cookie value is  :"+con.getHeaderField("Set-Cookie"));
        aCookie = con.getHeaderField("Set-Cookie");
        aCookie = aCookie.substring(0,aCookie.indexOf(";"));
        System.out.println("Header Fields "+"\n"+con.getHeaderFields());
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP POST request
    private void sendPost(String aURL,String aCookie) throws Exception {

        String url = aURL;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "*/*");
        con.setRequestProperty("Accept-Encoding","gzip, deflate");
        con.setRequestProperty("Cookie",aCookie);
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.8,hi;q=0.6");
        con.setRequestProperty("X-Requested-With","XMLHttpRequest");
        con.setRequestProperty("Connection","keep-alive");
        //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        String urlParameters = "chooseIdentifier=Account%20ID&chooseIdentifier=Account%20ID&accntId=9493692000&chooseGateway=BillDesk&chooseGateway=Bill%20Desk%20Payment&mblNum=&emailId=&gridValues=";
        //urlParameters = "chooseIdentifier=Account ID&accntId=9493692000&chooseGateway=BillDesk";
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

}