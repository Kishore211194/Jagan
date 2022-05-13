package com.chain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.Endpoints;
import com.base.LibGlobal;
import com.pojo.DeleteAddress_input_pojo;
import com.pojo.GetAddress_output_pojo;
import com.pojo.OutputRoot;
import com.pojo.Root;
import com.pojo.UpdateAddress_output_Pojo;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class DeleteAddress extends LibGlobal {
	static String jwt;
	static int address_id;
	
	@Test(priority = 1)
	private void login() throws IOException {
		header("Content-Type", "application/json");
		baseAuth(getPropertyValue("Username"), getPropertyValue("Password"));
		Response response = methodType("POST", Endpoints.LOGIN);
		System.out.println(getStatusCode(response));
		System.out.println(getBodyAsPrettyString(response));
		Root as = response.as(Root.class);
		jwt = as.getData().getLogtoken();
		System.out.println(jwt);
		Assert.assertEquals(as.getData().getLogtoken(), jwt, "Verify jwt key is correct");
	}
	
	@Test(priority = 3)
	private void getAddress() {
		header("Authorization", "Bearer " + jwt);
		Response response = methodType("GET", Endpoints.GET_ADDRESS);
		System.out.println(getStatusCode(response));
		System.out.println(getBodyAsPrettyString(response));
		OutputRoot as = response.as(OutputRoot.class);

		ArrayList<GetAddress_output_pojo> data = as.getData();
		GetAddress_output_pojo getAddress_output_pojo = data.get(0);
		address_id = getAddress_output_pojo.getId();


	}
	
	@Test( priority  = 4 )
	private void deleteAddress() {
		List<Header> listOfHeaders = new ArrayList<Header>();
		Header h2 = new Header("accept", "application/json");
		Header h3 = new Header("Content-Type", "application/json");
		Header h4 = new Header("Authorization", "Bearer " + jwt);
		listOfHeaders.add(h2);
		listOfHeaders.add(h3);
		listOfHeaders.add(h4);
		Headers headers = new Headers(listOfHeaders);
		headers(headers);
		DeleteAddress_input_pojo deleteAddress = new DeleteAddress_input_pojo(String.valueOf(address_id));
		body(deleteAddress);
		Response response = methodType("DELETE", Endpoints.DELETE_ADDRESS);
		System.out.println(getStatusCode(response));
		System.out.println(getBodyAsPrettyString(response));
		UpdateAddress_output_Pojo as = response.as(UpdateAddress_output_Pojo.class);
		System.out.println(as.getMessage());
		Assert.assertEquals(as.getStatus(), 200, "Verify the status");
		Assert.assertEquals(as.getMessage(), "Address deleted successfully", "Verify Address has been deleted");
	}
	
	
	
	
	
	

}
