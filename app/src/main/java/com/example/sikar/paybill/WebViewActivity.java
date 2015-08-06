package com.example.sikar.paybill;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sikar.web.HttpRequest;
import com.example.sikar.web.MPCZConstants;
import com.example.sikar.web.utils.MySession;

import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends Activity {

	WebView mWebView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mWebView = (WebView) findViewById(R.id.webview);

		// Set a kind of listener on the WebView so the WebView can intercept
		// URL loading requests if it wants to

		mWebView.setWebViewClient(new HelloWebViewClient());

		mWebView.getSettings().setJavaScriptEnabled(true);
		//mWebView.loadUrl("http://www.google.com");
		String url = "http://www.mpcz.co.in/PaymentServlet?selectname=PaymentUpdation&_dc=1437991416092&accntId=9493692000&billerid=MPMKBHORAP&RU=http%3A%2F%2Fwww.mpcz.co.in%2FpaymentAck&chooseIdentifier=Account%20ID&amtToBePaid=0&outstandingAmt=0&customerName=SWAMI%20SARAN%20SHARMA&billId=184255954&lastBillAmt=0.00&currentBillAmt=408&billmon=JUL-2015&billissuedate=07-JUL-2015&billdueDate=20-JUL-2015&consAddres=Q.NO.30GOSPURA%20COLONY%2030%2FA%20TAN&city=TANSEN&mblNum=9346584202&payGateway=BILLDESK&emailId=rohitpratapitm%40gmail.com";
		Map<String,String> headerParameters = new HashMap<String,String>();
		headerParameters = initializeWithDefaults(headerParameters);
		mWebView.loadUrl(url,headerParameters);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class HelloWebViewClient extends WebViewClient {
		private static final String TAG = "HelloWebViewClient";;

		// Give application a chance to catch additional URL loading requests
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i(TAG, "About to load:" + url);
			view.loadUrl(url);
			return true;
		}
	}
	private Map<String,String> initializeWithDefaults(Map<String,String> aHeaderParameters){

		aHeaderParameters.put(HttpRequest.HEADER_ACCEPT, "*/*");
		aHeaderParameters.put(HttpRequest.HEADER_ACCEPT_ENCODING, "gzip, deflate");
		aHeaderParameters.put(HttpRequest.HEADER_COOKIE, MySession.getSessionCookie());
		aHeaderParameters.put(HttpRequest.HEADER_USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
		aHeaderParameters.put(HttpRequest.HEADER_ACCEPT_LANGUAGE, "en-US,en;q=0.8,hi;q=0.6");
		aHeaderParameters.put(HttpRequest.HEADER_X_REQUESTED_WITH, "XMLHttpRequest");
		aHeaderParameters.put(HttpRequest.HEADER_CONNECTION, "keep-alive");
		aHeaderParameters.put(HttpRequest.HEADER_REFERER, MPCZConstants.HOME_SCREEN);
		aHeaderParameters.put(HttpRequest.HEADER_HOST, "www.mpcz.co.in");
		aHeaderParameters.put(HttpRequest.HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
		return aHeaderParameters;
	}
}