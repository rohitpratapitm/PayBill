package com.example.sikar.paybill;


import java.util.HashMap;
import java.util.List;


public class BillInfo {
	
	private static final String ACCOUNTID     = "accountId";
	private static final String CONNECTION_NO = "connectionNo";
	private static final String BILLID  = "billId";
	private static final String CustomerName  = "customerName";
	private static final String CITY = "consCity";
	private static final String ADDRESS = "consAddr";
	
	private static final String MONTH = "billMonth";
	private static final String ISSUE_DATE = "billIssueDate";
	private static final String DUE_DATE = "billDueDate";
	
	private static final String OUTSTANDINGAMT = "outstandingAmt";
	private static final String LASTBILLAMT = "lastBillAmt";
	private static final String CURRENTBILLAMT = "currentBillAmt";
	private static final String AMTTOBEPAID = "amtToBePaid";
	

	private String mAccountId;
	private String mConnectionNo;
	private String mBillId;
	private String mCustomerName;
	private String mConsCity;
	private String mConsAddr;
	
	private String mBillDueDate;
	private String mBillIssueDate;
	private String mBillMonth;
	
	private String mAmtToBePaid;
	private String mLastBillAmt;
	private String mOutStandingAmt;
	private String mCurrentBillAmt;
	
	public BillInfo(List<HashMap> aList){

		HashMap<String,String> map = aList.get(0);

		mAccountId = (String) map.get(ACCOUNTID);
		mConnectionNo = (String) map.get(CONNECTION_NO);
		mBillId = (String) map.get(BILLID);
		mCustomerName = (String) map.get(CustomerName);
		mConsCity = (String) map.get(CITY);
		mConsAddr = (String) map.get(ADDRESS);
			
		mBillIssueDate = map.get(ISSUE_DATE);
		mBillDueDate   = map.get(DUE_DATE);
		mBillMonth =   map.get(MONTH);
		
		mAmtToBePaid = map.get(AMTTOBEPAID);
		mLastBillAmt = map.get(LASTBILLAMT);
		mOutStandingAmt = map.get(OUTSTANDINGAMT);
		mCurrentBillAmt = map.get(CURRENTBILLAMT);
	}
	public String getmAccountId() {
		return mAccountId;
	}
	public void setmAccountId(String mAccountId) {
		this.mAccountId = mAccountId;
	}
	public String getmConnectionNo() {
		return mConnectionNo;
	}
	public void setmConnectionNo(String mConnectionNo) {
		this.mConnectionNo = mConnectionNo;
	}
	public String getmBillId() {
		return mBillId;
	}
	public void setmBillId(String mBillId) {
		this.mBillId = mBillId;
	}
	public String getmCustomerName() {
		return mCustomerName;
	}
	public void setmCustomerName(String mCustomerName) {
		this.mCustomerName = mCustomerName;
	}
	public String getmConsCity() {
		return mConsCity;
	}
	public void setmConsCity(String mConsCity) {
		this.mConsCity = mConsCity;
	}
	public String getmConsAddr() {
		return mConsAddr;
	}
	public void setmConsAddr(String mConsAddr) {
		this.mConsAddr = mConsAddr;
	}
	public String getmBillDueDate() {
		return mBillDueDate;
	}
	public void setmBillDueDate(String mBillDueDate) {
		this.mBillDueDate = mBillDueDate;
	}
	public String getmBillIssueDate() {
		return mBillIssueDate;
	}
	public void setmBillIssueDate(String mBillIssueDate) {
		this.mBillIssueDate = mBillIssueDate;
	}
	public String getmBillMonth() {
		return mBillMonth;
	}
	public void setmBillMonth(String mBillMonth) {
		this.mBillMonth = mBillMonth;
	}
	public String getmAmtToBePaid() {
		return mAmtToBePaid;
	}
	public void setmAmtToBePaid(String mAmtToBePaid) {
		this.mAmtToBePaid = mAmtToBePaid;
	}
	public String getmLastBillAmt() {
		return mLastBillAmt;
	}
	public void setmLastBillAmt(String mLastBillAmt) {
		this.mLastBillAmt = mLastBillAmt;
	}
	public String getmOutStandingAmt() {
		return mOutStandingAmt;
	}
	public void setmOutStandingAmt(String mOutStandingAmt) {
		this.mOutStandingAmt = mOutStandingAmt;
	}
	public String getmCurrentBillAmt() {
		return mCurrentBillAmt;
	}
	public void setmCurrentBillAmt(String mCurrentBillAmt) {
		this.mCurrentBillAmt = mCurrentBillAmt;
	}

	
	
}
