package fortytwolabs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



public class TestServiceClass {
	
	@Test
	public void testAddNewBook() {
		ServiceClass service = new ServiceClass();
		
		String result = service.addBook("Clean Code", "Robert C. Martin", 3);
		assertTrue(result.contains("Book Added Successfully with Id : "), "Book Should be Added Successfully!");
		assertEquals(3, service.countAvailableBooks(), "Available Books Count Should be Shown as 3.");
	}
	
	@Test
	public void testInvalidTitle() {
		ServiceClass service = new ServiceClass();
		
		String result = service.addBook("", "Robert C. Martin", 3);
		assertEquals("BookName Cannot be Empty : ",result);
	}
	
	@Test
	public void testAddExistingBook() {
		ServiceClass service = new ServiceClass();
		service.addBook("Clean Code", "Robert C Martin", 3);
		String result = service.addBook("Clean Code", "Robert C Martin", 3);
		assertTrue(result.contains("Book Already Exists."));
	}
}
