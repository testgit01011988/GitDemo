import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class GettingDataFromFiles {

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

		}
}
