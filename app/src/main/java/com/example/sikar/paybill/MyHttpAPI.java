package com.example.sikar.paybill;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class MyHttpAPI {

	private final static String USER_AGENT = "Mozilla/5.0";
	private static final String SEE_BILL = "http://www.mpcz.co.in/portal/Bhopal_home.portal?_nfpb=true&_pageLabel=custCentre_viewBill_bpl";
	private static final String ENTER_CREDENTIALS = "http://www.mpcz.co.in/onlineBillPayment?do=onlineBillPaymentUnregValidate";
	private HttpURLConnection mConnection;
	private URL mURL;
	private String mJSONResult;

	public MyHttpAPI(String aAccountId) {
		try {
			mURL = new URL(ENTER_CREDENTIALS);
			mConnection = (HttpURLConnection) mURL.openConnection();
			initializeHeader(mConnection);
			execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initializeHeader(final HttpURLConnection aConnection)
			throws ProtocolException {
		// add request header
		String cookie = createCookie();
		aConnection.setRequestMethod("POST");
		aConnection.setRequestProperty("Accept", "*/*");
		aConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
		aConnection.setRequestProperty("Cookie", cookie);
		aConnection
				.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
		aConnection.setRequestProperty("Accept-Language",
				"en-US,en;q=0.8,hi;q=0.6");
		aConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
		aConnection.setRequestProperty("Connection", "keep-alive");
		aConnection.setRequestProperty("Referer", SEE_BILL);
		aConnection.setRequestProperty("Host", "www.mpcz.co.in");
		aConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
	}

	private void execute() throws IOException {

		String urlParameters = "chooseIdentifier=Account%20ID&chooseIdentifier=Account%20ID&accntId=9493692000&chooseGateway=BillDesk&chooseGateway=Bill%20Desk%20Payment&mblNum=&emailId=&gridValues=";

		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		mConnection.setRequestProperty("Content-Length",
				Integer.toString(postData.length));
		// Send post request
		mConnection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(
				mConnection.getOutputStream());
		wr.write(postData);
		wr.flush();
		wr.close();
	}

	private static String createCookie() {
		String cookie = "";

		try {
			URL obj = new URL(SEE_BILL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			//int responseCode = con.getResponseCode();
			cookie = con.getHeaderField("Set-Cookie");
			cookie = cookie.substring(0, cookie.indexOf(";"));
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cookie;
	}
/*
	public MyJSONObject getJsonAsMap() {

		MyJSONObject obj=null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			// convert JSON string to Map
			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			obj = mapper.readValue(mConnection.getInputStream(),	new TypeReference<MyJSONObject>(){});
			System.out.println(obj.getResults());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}*/
/*
	public static void main(String args[]) {
		MyHttpAPI request = new MyHttpAPI(null);
		MyJSONObject jsonResult = request.getJsonAsMap();
		System.out.println("Response:"+jsonResult.getSuccess());
		System.out.println("Bill Info:"+"\n"+jsonResult.getBillInfo());
	}*/
}
