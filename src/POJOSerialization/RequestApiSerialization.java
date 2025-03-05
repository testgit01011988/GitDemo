package POJOSerialization;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;



public class RequestApiSerialization 
{

	public static void main(String[] args) 
	{
	
	
	AddPlace p= new AddPlace();
	p.setAccuracy(50);
	p.setAddress("29, street layout, mumbai");
	p.setLanguage("English");
	
	Location locationValues= new Location();
	locationValues.setLat(-39.383494);
	locationValues.setLng(30.427362);
	p.setLocation(locationValues);
	
	p.setName("Frontline House");
	p.setPhone_number("(+91) 983 893 3937");
	
	List<String> typesList= new ArrayList<String>();
	typesList.add("shoe park");
	typesList.add("shop");	
	p.setTypes(typesList);

	p.setWebsite("http://google.com");
	
	RestAssured.baseURI="https://rahulshettyacademy.com";
	Response res1= given().log().all().queryParam("key", "qaclick123")   //import class for restassured Response 
	.body(p)			//passing object p created for AddPlace class
	.when().post("maps/api/place/add/json")
	.then().assertThat().statusCode(200).extract().response();
	
	//System.out.println(res1);
	
	String res2= res1.asString();
	System.out.println(res2);
	}
	
	
}
