package Files;

import io.restassured.path.json.JsonPath;

public class ReusuableCodes {
	
	public static JsonPath rawToJson(String response)
	{	
	JsonPath js= new JsonPath(response);
	return js;
	}
}
