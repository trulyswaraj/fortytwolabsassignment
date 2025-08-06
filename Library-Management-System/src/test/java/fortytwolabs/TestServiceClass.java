package fortytwolabs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestServiceClass {
    //Test Cases for Add Book Method.
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
    public void testNullTitle(){
        ServiceClass service = new ServiceClass();

        String result = service.addBook(null,"Robert C Martin",10);
        assertEquals("BookName Cannot be Empty : ", result);
    }

    @Test
    public void testAddExistingBook() {
        ServiceClass service = new ServiceClass();
        service.addBook("Clean Code", "Robert C Martin", 3);
        String result = service.addBook("Clean Code", "Robert C Martin", 3);
        assertTrue(result.contains("Book Already Exists."));
    }

    @Test
    public void sameIdGeneration(){
        ServiceClass service = new ServiceClass();
        String res1 = service.addBook("Clean Code", "Robert C Martin", 10);
        String res2 = service.addBook("Clean Code 2", "Robert C Martin", 10);
        assertNotEquals(res1, res2);
    }

    @Test
    public void checkInputCases(){
        ServiceClass service = new ServiceClass();
        service.addBook("Java", "John",10);
        String result = service.addBook("JAVA", "JOHN", 1);
        assertTrue(result.contains("Book Already Exists. Quantity Incremented to 11"));
       // assertTrue(result.contains(""));
    }

    @Test
    public void bookNameMismatchAddBook(){
        ServiceClass service = new ServiceClass();
        service.addBook("Clean Code","John",10);
        String result = service.addBook("Java","John",10);
        assertTrue(result.contains("Book Added Successfully with Id : "), "New Book Should be Added Due to Book Name mismatch.");
    }

    @Test
    public void authorNameMismatchAddBook(){
        ServiceClass service = new ServiceClass();
        service.addBook("Clean Code","John",10);
        String result = service.addBook("Clean Code","Robert C Martin", 10);
        assertTrue(result.contains("Book Added Successfully with Id : "), "New Book Should be Added Due to Author Name mismatch. ");
    }

    @Test
    public void bookNameMismatch(){
        ServiceClass service = new ServiceClass();
        service.addBook("Clean Code", "John", 10);
        BookClass result = service.getBookAndAuthorName("Java","John");
        assertNull(result);
    }

    @Test
    public void authorNameMismatch(){
        ServiceClass service = new ServiceClass();
        service.addBook("Java","John",10);
        BookClass result = service.getBookAndAuthorName("Java","James");
        assertNull(result);
    }

    @Test
    public void bothMismatch(){
        ServiceClass service = new ServiceClass();
        service.addBook("Java","John",10);
        BookClass result = service.getBookAndAuthorName("java","python");
        assertNull(result);
    }

    //white-box-testing
    @Test
    public void checkStateOfObject(){
        ServiceClass service = new ServiceClass();
        service.addBook("Java", "John", 10);
        service.addBook("JAVA", "JOHN", 1);
        BookClass book = service.getBookAndAuthorName("Java", "John");
        assertNotNull(book);
        assertEquals(11, book.getQuantity());
    }
    //add Book test Cases ends here.

    @Test
    public void checkForIssuingBookNotNullId(){
        ServiceClass service = new ServiceClass();
        service.addBook("Clean Code","Robert C Martin",10);
        String book = service.issueBook(null);
        assertNotNull("Book with given Book Id issued Successfully.",book);
    }

    @Test
    public void checkForIssuingBookNullId(){
        ServiceClass service = new ServiceClass();
        String result = service.issueBook(null);
        assertEquals("Book Id Required.", result);
    }

}
