import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import Files.Payload;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//Rest assured protocol depends on:
		//given- all input details
		//when- submit the API
		//Then- validate the 
		//Add place ->Update place with new address -> get place to validate is new address is present in response
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response= given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
	//	.body(Payload.AddPlace()).when().post("maps/api/place/add/json")
				//to add content data of jason file in body()
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\admin\\Desktop\\automation\\RestAssured\\JavaSamples\\Library\\AddPlaceDetails.json")))).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
		System.out.println(response); //extract().response().asString() will extract the response and save in response variable
		
		JsonPath js= new JsonPath(response); //for parsing Json. It takes String as input
		String placeId= js.getString("place_id");//in response, place_id has no parent so it can be accessed directly, if parent is present then it should be parent.placedID()
		System.out.println(placeId);
		
		//update place
	String newAddress="New Street, Africa";
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//get place - to validate if the updated place is displayed for the place id
		
	 String getPlaceResponse= given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId)       //header is not required when no body is sent
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).body("address", equalTo(newAddress))
		.extract().response().asString();
		System.out.println(getPlaceResponse);
		
		JsonPath js1= new JsonPath(getPlaceResponse);
		String actualAddress= js1.getString("address");
		System.out.println(actualAddress);
		
//-		testng
		Assert.assertEquals(actualAddress,newAddress);
		
	}

}
