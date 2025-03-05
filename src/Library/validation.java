package Library;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import Files.Payload;
import Files.ReusuableCodes;

public class validation {
	
	String bookID;
	@Test(dataProvider= "BooksData")	
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI= "http://216.10.245.166";
		
	String response= given().header("Content-Type", "application/json")
			//	.body(Payload.addBookDetails())
			.body(Payload.addBookDetails(isbn,aisle)) //when passing isbn, aisle as arguments to the method
	.when().post("Library/Addbook.php")
	.then().assertThat().log().all().statusCode(200).extract().response().asString();
	
	JsonPath js= ReusuableCodes.rawToJson(response);
	 bookID = js.get("ID");
	String message= js.getString("Msg");
	System.out.println(bookID);
	System.out.println(message);
	}
	
		
	//Adding lot of book details using data provider
	
	@DataProvider(name="BooksData")
	
	public Object[][] getData()
	{
		//array = collections of elements
		//multi dimentional array - collection of arrays
		
		return new Object[][] {{"hyujgh", "89656"}, {"rdfgtb", "34322"}, {"erftgh", "45667"}};
		
	}
	
	@Test(dataProvider= "BooksData")
	public void deleteBook(String isbn1, String aisle1)
	{
		RestAssured.baseURI= "http://216.10.245.166";
		
	String response= given().header("Content-Type", "application/json")
			//	.body(Payload.addBookDetails())
			.body(Payload.deleteBookDetails(isbn1,aisle1)) //when passing isbn, aisle as arguments to the method
	.when().post("Library/DeleteBook.php")
	.then().assertThat().log().all().statusCode(200).extract().response().asString();
	
	JsonPath js= ReusuableCodes.rawToJson(response);
//	String bookID = js.get("ID");
	String message= js.getString("msg");
	//System.out.println(bookID);
	System.out.println(message);
	}
	
	
}


