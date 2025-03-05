package EcomE2EAPIAuth;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class E2EAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
			
			//Login API
			POJOLoginRequest lreq= new POJOLoginRequest();
			lreq.setUserEmail("te01011988st@gmail.com");
			lreq.setUserPassword("Te01011988st@");
			
			//If SSL valisation is blocking testing give relaxedHTTPSValidation()
			RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(lreq);
			
			POJOLoginResponse loginResponse= reqLogin.when().post("/api/ecom/auth/login")
			.then().log().all().extract().response().as(POJOLoginResponse.class);
			System.out.println(loginResponse.getToken());
			String tokenValue=loginResponse.getToken();
			System.out.println(loginResponse.getUserId());
			String userId= loginResponse.getUserId();
			System.out.println(loginResponse.getMessage());
	
			
			//Create Product(form data payload)
			
			RequestSpecification createProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
														addHeader("Authorization", tokenValue).build();
			
			
			RequestSpecification productReq= given().log().all().spec(createProductBaseReq)
										.param("productName", "Laptop")
										.param("productAddedBy", userId)
										.param("productCategory","Work")
										.param("productSubCategory", "Necessity")
										.param("productPrice", "50000")
										.param("productDescription", "Lenovo")
										.param("productFor", "All")
										.multiPart("productImage", new File("C:\\Users\\admin\\Desktop\\flower.jpg")); //attaching image file
			
			String createProductResponse= productReq.when().post("/api/ecom/product/add-product")
			.then().log().all().extract().response().asString();
			
			JsonPath js= new JsonPath(createProductResponse);
			String productId= js.get("productId");
			System.out.println(productId);
			
			
			//create order
			
			RequestSpecification createOrderBaseReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
													setContentType(ContentType.JSON).addHeader("Authorization",tokenValue).build();
			
			POJOOrdersRequest orderReq = new POJOOrdersRequest();
			orderReq.setCountry("India");
			orderReq.setProductOrderedId(productId);
			
			List<POJOOrdersRequest> createOrder = new ArrayList<POJOOrdersRequest>();
			createOrder.add(orderReq);
			POJOCreateOrderRequest createOrderReqBody= new  POJOCreateOrderRequest();
			createOrderReqBody.setOrders(createOrder);
			
			
			RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(createOrderReqBody);
				
			String createOrderResponse= createOrderReq.when().post("/api/ecom/order/create-order")
			.then().log().all().extract().response().asString();
			
			System.out.println(createOrderResponse);
			
			JsonPath js2= new JsonPath(createOrderResponse);
			List<String> orderId= js2.get("orders");
		//	String id= orderId[0];
			System.out.println(orderId);
			String Id="";
			for(int i=0;i<orderId.size();i++)
			{
				 Id= orderId.get(i);
			}
			System.out.println(Id);
			
			//Delete product - using path parameter in url
			
			RequestSpecification deleteProductBaseReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
												.setContentType(ContentType.JSON)
												.addHeader("Authorization", tokenValue).build();
			
			RequestSpecification deleteProductReq= given().log().all().spec(deleteProductBaseReq).pathParam("productId", productId);
			
			//give "productId" field in past resource path to keep it dynamic
			String deleteProductresponse= deleteProductReq.when().delete("/api/ecom/product/delete-product/{productId}")
					.then().log().all().extract().response().asString();
			System.out.println(deleteProductresponse);
			
			JsonPath js1= new JsonPath(deleteProductresponse);
			String deleteMessage = js1.getString("message");
			System.out.println(deleteMessage);
			Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
			
			//Delete Order
			
			RequestSpecification deleteOrderBaseReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
										.addHeader("Authorization", tokenValue).build();
			
			RequestSpecification deleteOrderRequest = given().log().all().spec(deleteOrderBaseReq).pathParam("id", Id);
			
			String deleteOrderResponse= deleteOrderRequest.when()
					.delete("https://rahulshettyacademy.com/api/ecom/order/delete-order/{id}")
					.then().log().all().extract().response().asString();
			
			System.out.println(deleteOrderResponse);
}
}
