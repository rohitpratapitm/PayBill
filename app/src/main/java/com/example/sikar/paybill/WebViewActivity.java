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
import com.example.sikar.web.utils.HttpUtils;
import com.example.sikar.web.utils.MySession;

import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends Activity {

	private WebView mWebView;
	private Account mAccount;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mAccount = (Account)getIntent().getExtras().get("Account");
		mWebView = (WebView) findViewById(R.id.webview);

		mWebView.setWebViewClient(new HelloWebViewClient());

		mWebView.getSettings().setJavaScriptEnabled(true);

		Map<String,String> queryParameters = initializeQueryParameters();
		String url = HttpUtils.addQueryParametersToURL(MPCZConstants.PAYMENT_SCREEN,queryParameters);
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
	private Map<String,String> initializeQueryParameters(){


		BillInfo billInfo = mAccount.getBillInfo();

		Map<String,String> queryParameters = new HashMap<String,String>();
		//Constant properties
		queryParameters.put(MPCZConstants.POST_CHOOSE_IDENTIFIER, MPCZConstants.POST_CHOOSE_IDENTIFIER_VALUE);
		queryParameters.put(BillInfo.BILLER_ID,BillInfo.BILLER_ID_VALUE);
		queryParameters.put(MPCZConstants.RU,MPCZConstants.RU_ACKNOWLEDGMENT_VALUE);
		queryParameters.put(MPCZConstants.PAYMENT_GATEWAY,MPCZConstants.PAYMENT_GATEWAY_VALUE);
		queryParameters.put(MPCZConstants.SELECT_NAME,MPCZConstants.SELET_NAME_VALUE);

		//Account properties
		queryParameters.put(MPCZConstants.POST_ACCOUNT_ID,mAccount.getAccountId());
		queryParameters.put(Account.CUSTOMER_NAME,mAccount.getCustomerName());
		//queryParameters.put(Account.ADDRESS,mAccount.getAddress());
		queryParameters.put("consAddres",mAccount.getAddress());
		//queryParameters.put(Account.CITY,mAccount.getCity());
		queryParameters.put("city",mAccount.getCity());
		queryParameters.put(Account.MOBILE_NO,mAccount.getMobileNumber());
		queryParameters.put(Account.EMAIL_ID,mAccount.getEmailId());
		//Bill properties
		queryParameters.put(BillInfo.BILL_ID,billInfo.getBillId());
		queryParameters.put(BillInfo.AMT_TO_BE_PAID,billInfo.getAmtToBePaid());
		queryParameters.put(BillInfo.OUTSTANDING_AMT,billInfo.getOutStandingAmt());
		queryParameters.put(BillInfo.LAST_BILL_AMT,billInfo.getLastBillAmt());
		queryParameters.put(BillInfo.CURRENT_BILL_AMT,billInfo.getCurrentBillAmt());
		//queryParameters.put(BillInfo.MONTH,billInfo.getBillMonth());
		queryParameters.put("billMon",billInfo.getBillMonth());
		queryParameters.put(BillInfo.ISSUE_DATE,billInfo.getBillIssueDate());
		queryParameters.put("billdueDate",billInfo.getBillDueDate());

		return queryParameters;

	}
}