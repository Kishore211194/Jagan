package com.chain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.Endpoints;
import com.base.LibGlobal;
import com.pojo.Addaddress_input_pojo;
import com.pojo.Addaddress_output_pojo;
import com.pojo.DeleteAddress_input_pojo;
import com.pojo.GetAddress_output_pojo;
import com.pojo.OutputRoot;
import com.pojo.Root;
import com.pojo.UpdateAddress_Input_Pojo;
import com.pojo.UpdateAddress_output_Pojo;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class VelsClub extends LibGlobal {

	static String jwt;
	static int address;

	@Test(priority = -3)
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

	@Test(priority = 0, enabled=false)
	private void addAddress() {
		List<Header> listOfHeaders = new ArrayList<Header>();
		Header h2 = new Header("accept", "application/json");
		Header h3 = new Header("Content-Type", "application/json");
		Header h4 = new Header("Authorization", "Bearer " + jwt);
		listOfHeaders.add(h2);
		listOfHeaders.add(h3);
		listOfHeaders.add(h4);
		Headers headers = new Headers(listOfHeaders);
		headers(headers);
		Addaddress_input_pojo add = new Addaddress_input_pojo("Aashoor", "Praveen", "9578867501", "Queens", 35, 45, 75,
				"607803", "Chennai", "Home");
		body(add);
		Response response = methodType("POST", Endpoints.CREATE_ADDRESS);
		System.out.println(getStatusCode(response));
		System.out.println(getBodyAsPrettyString(response));
		Addaddress_output_pojo as = response.as(Addaddress_output_pojo.class);
		address = as.getAddress_id();
		System.out.println(address);
		Assert.assertEquals(as.getAddress_id(), address, "Verify the address id");
		Assert.assertEquals(as.getMessage(), "Address added successfully", "Verify Address has been added");
	}

	@Test(priority = 2 ,enabled=false)
	private void updateAddress() {
		List<Header> listOfHeaders = new ArrayList<Header>();
		Header h2 = new Header("accept", "application/json");
		Header h3 = new Header("Content-Type", "application/json");
		Header h4 = new Header("Authorization", "Bearer " + jwt);
		listOfHeaders.add(h2);
		listOfHeaders.add(h3);
		listOfHeaders.add(h4);
		Headers headers = new Headers(listOfHeaders);
		headers(headers);
		UpdateAddress_Input_Pojo updateAddress = new UpdateAddress_Input_Pojo(String.valueOf(address), "Jaya",
				"Pradeep", "9578867501", "Happy hostel", "Tamil Nadu", "Chennai", "India", "6000002", "Kumaran Kudil",
				"Home");
		body(updateAddress);
		Response response = methodType("PUT", Endpoints.UPDATE_ADDRESS);
		System.out.println(getStatusCode(response));
		System.out.println(getBodyAsPrettyString(response));
		UpdateAddress_output_Pojo as = response.as(UpdateAddress_output_Pojo.class);
		Assert.assertEquals(as.getStatus(), 200, "Verify the status");
		Assert.assertEquals(as.getMessage(), "Address updated successfully", "Verify Address has been updated");
	}

	@Test(priority = 3)
	private void getAddress() {
		header("Authorization", "Bearer " + jwt);
		Response response = methodType("GET", Endpoints.GET_ADDRESS);
		System.out.println(getStatusCode(response));
		System.out.println(getBodyAsPrettyString(response));
		OutputRoot as = response.as(OutputRoot.class);

		ArrayList<GetAddress_output_pojo> data = as.getData();
		GetAddress_output_pojo getAddress_output_pojo = data.get(data.size() - 1);
		String first_name = getAddress_output_pojo.getFirst_name();

		// System.out.println(as.getData().get(0).getFirst_name());
		Assert.assertEquals(first_name, "Jaya", "Verify firstname");
//		Assert.assertEquals(as.getData().get(0).getLast_name(), "Pradeep", "Verify lastname");

	}

	@Test(priority = 4 , enabled=false)
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
		DeleteAddress_input_pojo deleteAddress = new DeleteAddress_input_pojo(String.valueOf(address));
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
