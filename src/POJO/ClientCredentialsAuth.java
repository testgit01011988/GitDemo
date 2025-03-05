package POJO;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class ClientCredentialsAuth {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//get access_token
				String response1= given()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		System.out.println(response1);
		
		JsonPath js = new JsonPath(response1);
		String accessToken= js.getString("access_token");
		
		//get course details
		getCourseDetailsDesrialization gcd= given()
		.queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(getCourseDetailsDesrialization.class);
		
		//System.out.println(response2);
		
		//below fields are simple key value type, so we can display their values directly
		System.out.println(gcd.getLinkedIn());
		System.out.println(gcd.getInstructor());
		System.out.println(gcd.getExpertise());
		System.out.println(gcd.getUrl());
		System.out.println(gcd.getServices());
		
		//for courses, array is present for its different fields:
		
		System.out.println(gcd.getCourses().getWebAutomation().get(1).getCourseTitle()); //to display the value at index 1 for webAutomation
		
		//to get the price of course SoapUI Webservices testing - Deserialization
		
		List<Api> apiCourse = gcd.getCourses().getApi();
		
		for(int i=1; i<apiCourse.size(); i++)
		{
			if(apiCourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCourse.get(i).getPrice());
			}
		}
		
		// to get list of all coursetitles of course webautomation and compare against the expected list
		
		String[] expectedList1= {"Selenium Webdriver Java","Cypress","Protractor"};
		ArrayList<String> actualList = new ArrayList(); //as we are not aware how many elements will be present, we use arrayList
		
		List<WebAutomation> webAutomationCourses = gcd.getCourses().getWebAutomation();
		
		for(int i=0; i<webAutomationCourses.size(); i++)
		{
		//	System.out.println(webAutomationCourses.get(i).getCourseTitle());
			actualList.add(webAutomationCourses.get(i).getCourseTitle());
		}
		
		//it is easy to coompare arraylist vs arraylist than array vs arraylist. so we will convert expectedList1 array to array list
		
		List<String> expectedList2 = Arrays.asList(expectedList1);
		
		Assert.assertTrue(actualList.equals(expectedList2)); //testng assertion
		
		
		
		
		
	}

}
