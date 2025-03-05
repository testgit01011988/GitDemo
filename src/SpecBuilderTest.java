import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import Files.Payload;

public class SpecBuilderTest {

	public static void main(String[] args) throws IOException {
	
		//creating request specification builder for generic parameters, here base uri, query paramateres, content type are common for Addplace, getplace api
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
								.addQueryParam("key","qaclick123")
								.setContentType(ContentType.JSON)
								.build();
		
		ResponseSpecification resspec= new ResponseSpecBuilder().expectStatusCode(200)
										.expectContentType(ContentType.JSON).build();
		
	//	RestAssured.baseURI= "https://rahulshettyacademy.com";
		RequestSpecification res= given().spec(req)   		//assigning above created builder using spec()S
					.body("{\r\n"
							+ "    \"location\": {\r\n"
							+ "        \"lat\": -38.383494,\r\n"
							+ "        \"lng\": 33.427362\r\n"
							+ "    },\r\n"
							+ "    \"accuracy\": 50,\r\n"
							+ "    \"name\": \"Frontline house\",\r\n"
							+ "    \"phone_number\": \"(+91) 983 893 3937\",\r\n"
							+ "    \"address\": \"29, side layout, cohen 09\",\r\n"
							+ "    \"types\": [\r\n"
							+ "        \"shoe park\",\r\n"
							+ "        \"shop\"\r\n"
							+ "    ],\r\n"
							+ "    \"website\": \"http://google.com\",\r\n"
							+ "    \"language\": \"French-IN\"\r\n"
							+ "}");
		
		Response response= res.when().post("maps/api/place/add/json")
		.then().spec(resspec).extract().response();
		
		String responseString= response.asString();
		System.out.println(responseString); //extract().response().asString() will extract the response and save in response variable
		
		JsonPath js= new JsonPath(responseString); //for parsing Json. It takes String as input
		String placeId= js.getString("place_id");//in response, place_id has no parent so it can be accessed directly, if parent is present then it should be parent.placedID()
	//	System.out.println(placeId);
		
		//update place
	String newAddress="New Street, Africa";
		
	RequestSpecification res1= given().log().all().spec(req)
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}");
		Response response1= res1.when().put("maps/api/place/update/json")
		.then().spec(resspec).extract().response();
		String responseString1= response1.asString();
		System.out.println(responseString1);
		
		//get place - to validate if the updated place is displayed for the place id
		
		RequestSpecification res2= given().log().all().spec(req).queryParam("place_id",placeId);
		Response response2 = res2.when().get("maps/api/place/get/json")
		.then().spec(resspec).extract().response();
		
		String responseString2= response2.asString();
		System.out.println(responseString2);
		
		
		
	}

}
