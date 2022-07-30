import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.Payload;
import Files.ReusableMethod;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParams("key","qaclick123").header("Content-Type","application/json")
		.body(Payload.addPlace()).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server","Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js=new JsonPath(response);
		String PlaceId= js.getString("place_id");
		System.out.println(PlaceId);
		
		String NewAddress="45, Stright layout, Raju Street, Banglore 09, India";
		
		//update Address
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+PlaceId+"\",\r\n"
				+ "\"address\":\""+NewAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("/maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//get place
		
		String Updatedaddress= given().log().all().queryParam("key", "qaclick123").queryParam("place_id", PlaceId)
		.when().get("/maps/api/place/get/json").then().assertThat().log().all().statusCode(200)
		.extract().response().asString();
		
		JsonPath js1=ReusableMethod.rowtoJson(Updatedaddress);
		String ActualAddress= js1.getString("address");
		Assert.assertEquals(ActualAddress, NewAddress);
		
		

	}

}
