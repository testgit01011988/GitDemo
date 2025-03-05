
import Files.Payload;
import io.restassured.path.json.JsonPath;

public class Parsejson {

	public static void main(String[] args) {
	
		JsonPath js= new JsonPath(Payload.courseDetails());
		
		//1. Print No of courses returned by API
		int countCourses= js.getInt("courses.size()");
		System.out.println(countCourses);

		//2.Print Purchase Amount
		int purchaseAmt= js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmt);
	
		//3.Print Title of the first course
		String courseFirstTitle = js.get("courses[0].title");
		System.out.println(courseFirstTitle);
		
		//4.Print Title of the last course
		String courseLastTitle = js.get("courses["+(countCourses-1)+"].title"); //if output is string, we can just write get()
		System.out.println(courseLastTitle);
		
		//5. Print All course titles and their respective Prices
		for(int i=0; i<countCourses; i++)
		{
			String courseTitle = js.get("courses["+i+"].title");
			System.out.println("Title: "+ courseTitle);
		//	System.out.println(js.get("courses["+i+"].title").toString()); 
//to directly display output from system.out.println instead of saving it in a different variable and then printing it, covert the output to tostring() as above, then only it will print
			int coursePrice= js.getInt("courses["+i+"].price");
			System.out.println("Price: "+ coursePrice);
		}
		
		//6. Print no of copies sold by RPA Course
		for(int i=0; i<countCourses; i++)
		{
			 String actualCourse = js.get("courses["+i+"].title");
			//System.out.println(actualCourse);
			Boolean result = actualCourse.equals("RPA"); //we can also use equalsIgnorecase()
			
			if(result)
			{
				int numberCopies= js.getInt("courses["+i+"].copies");
				System.out.println("Number of copies sold for RPA are:"+ numberCopies);
				break; //suppose, we got the result and now want to stop the search, we can give break
			}
		}
		
		
	}

}
