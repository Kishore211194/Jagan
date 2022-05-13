package com.pojo;

import java.util.ArrayList;

public class OutputRoot {
	private int status;
	private String message;
	private ArrayList<GetAddress_output_pojo> data;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<GetAddress_output_pojo> getData() {
		return data;
	}
	public void setData(ArrayList<GetAddress_output_pojo> data) {
		this.data = data;
	}
	

}
