package com.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class LibGlobal {
	RequestSpecification rqst;
	Response response;
	static WebDriver driver;
public void baseAuth(String Username , String Password) {
		rqst=rqst.auth().preemptive().basic(Username, Password);

	}
	public String getPropertyValue(String Key ) throws IOException {
     Properties p = new Properties();
     FileInputStream stream  = new FileInputStream("C:\\Users\\kisho\\eclipse-workspace3\\ApiChaining\\src\\test\\resources\\config.properties");
     p.load(stream);
     Object object = p.get(Key);
     
     String value = (String)object;
     
	return value;
     
		
		
	}

	public void header(String key, String value) {
		rqst = RestAssured.given().header(key, value);
	}
	public void headers(Headers header) {
		rqst = RestAssured.given().headers(header);
	}

	public void pathParam(String key, String value) {
		rqst = rqst.queryParam(key, value);
	}

	public void queryParam(String key, String value) {
		rqst = rqst.pathParam(key, value);
	}

	public void body(Object body) {
        rqst.body(body);
	}
	public void body(String body) {
		rqst.body(body);
	}

	public Response methodType(String type, String endpoint) {
		switch (type) {
		case "GET":
			response = rqst.log().all().get(endpoint);
			break;
		case "PUT":
			response = rqst.log().all().put(endpoint);
			break;
		case "POST":
			response = rqst.log().all().post(endpoint);
			break;
		case "DELETE":
			response = rqst.log().all().delete(endpoint);
			break;
	
			default:
			break;

		}
		return response;
	}

	public ResponseBody getbody(Response response) {
		ResponseBody body = response.getBody();
		return body;
	}

	public String getBodyAsString(Response response) {
		String asString = getbody(response).asString();
		return asString;
	}

	public String getBodyAsPrettyString(Response response) {
		String asPrettyString = getbody(response).asPrettyString();
		return asPrettyString;
	}

	public int getStatusCode(Response response) {
		int statusCode = response.getStatusCode();
		return statusCode;
	}
}
