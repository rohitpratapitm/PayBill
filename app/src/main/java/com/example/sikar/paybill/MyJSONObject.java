package com.example.sikar.paybill;

import java.util.HashMap;
import java.util.List;


public class MyJSONObject {

	private String success;
	private List<HashMap> results;
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public List<HashMap> getResults() {
		return results;
	}
	public void setResults(List<HashMap> results) {
		this.results = results;
	}
	/*
	public BillInfo getBillInfo(){
		return new BillInfo(results);
	}*/
}
