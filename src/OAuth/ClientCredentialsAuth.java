package OAuth;
import static io.restassured.RestAssured.given;

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
		String response2= given()
		.queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
		
		System.out.println(response2);
	}

}
