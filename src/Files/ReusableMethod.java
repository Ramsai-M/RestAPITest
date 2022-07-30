package Files;

import io.restassured.path.json.JsonPath;

public class ReusableMethod {

	public static JsonPath rowtoJson(String response) {
		JsonPath js2=new JsonPath(response);
		return js2;
	}
}
