import org.testng.Assert;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void sumOfCourses()
	{
		//7. Verify if Sum of all Course prices matches with Purchase Amount
		
		JsonPath js= new JsonPath(Payload.courseDetails());
		
				int expectedAmount= js.getInt("dashboard.purchaseAmount");
				System.out.println(expectedAmount);
				
				int countCourses= js.getInt("courses.size()");
				int totalAmount=0;
				for(int i=0; i<countCourses; i++)
				{
					
					totalAmount = totalAmount+ ((js.getInt("courses["+i+"].price"))*(js.getInt("courses["+i+"].copies")));
					
				}
				System.out.println("Total Price: "+ totalAmount);
				
				Assert.assertEquals(totalAmount,expectedAmount); // assertions validations of jestng/junit 
			
							//Assert.assertEquals(actualvalue, expectedvalue)
		
	}

}
