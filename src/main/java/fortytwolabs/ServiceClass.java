//CONTAINS jUNIT TEST CASES!

package fortytwolabs;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import fortytwolabs.BookClass.BookStatus;

public class ServiceClass {
	Map<Integer, BookClass> bookMap = new HashMap<Integer, BookClass>();
	private static int bookIdCounter = 1;
//	public void addBook(Scanner sc) {
//		
//		System.out.println("Enter the Name of the book : ");
//		String enteredBookName = sc.nextLine();
//		//sc.nextLine();
//		
//		System.out.println("Enter the Authors name : ");
//		String enteredAuthorName = sc.nextLine();
//		//sc.nextLine();
//		
//		System.out.println("Enter the Quantity of the book entered : ");
//		int quantity = sc.nextInt();
//		sc.nextLine();
//		
//		for(BookClass existingBook : bookMap.values()) {
//			if(existingBook.getBookName().equalsIgnoreCase(enteredBookName) && existingBook.getAuthor().equalsIgnoreCase(enteredAuthorName)) {
//				existingBook.setQuantity(existingBook.getQuantity() + quantity );
//				System.out.println("The book already Exists. Quantity increamented to " + existingBook.getQuantity());
//				return;
//			}
//		} 
//		int generatedBookID = bookIdCounter++;
//		BookClass newBook = new BookClass(generatedBookID, enteredBookName, enteredAuthorName, BookClass.BookStatus.AVAILABLE, quantity, generatedBookID, generatedBookID);
//			bookMap.put(generatedBookID, newBook);
//			System.out.println("Book with quantity " + quantity +" And BookName -> " + enteredBookName +" Added Successfully with Id : "+generatedBookID);
//	}
	
	public String addBook(String bookName, String author, int quantity) {
		if(bookName == null || bookName.trim().isEmpty()) {
			return "BookName Cannot be Empty : ";
		}
		for(BookClass existingBook : bookMap.values()) {
			if(existingBook.getBookName().equalsIgnoreCase(bookName) && existingBook.getAuthor().equalsIgnoreCase(author)) {
				existingBook.setQuantity(existingBook.getQuantity() + quantity);
				return "Book Already Exists. Quantity Increamented to " + existingBook.getQuantity();
			}
		}
		int generatedBookId = bookIdCounter++;
		BookClass newBook = new BookClass(generatedBookId, bookName, author, BookClass.BookStatus.AVAILABLE , quantity, quantity, generatedBookId);
		bookMap.put(generatedBookId, newBook);
		return "Book Added Successfully with Id : " + generatedBookId;
	}
	
//	public void issueBook(Scanner sc) {
//		System.out.println("Enter the BookId you want to Issue : ");
//		int bookId = sc.nextInt();
//		sc.nextLine();
//		
//		BookClass book = bookMap.get(bookId);
//		if(book != null) {
//			if(book.getAvailableQuantity() > 0) {
//				book.setIssuedCount(book.getIssuedCount() + 1);
//				book.setStatus(BookStatus.ISSUED);
//				System.out.println("Book with ID " + bookId + " issued Successfully.");
//			}else {
//				System.out.println("No Available copies to issue.");
//			} 
//		}else {
//			System.out.println("Book with given ID not Found.");
//			} 
//	}

	public String issueBook(int bookId) {
		BookClass book = bookMap.get(bookId);
		if(book != null) {
			if(book.getAvailableQuantity() > 0) {
				book.setIssuedCount(book.getIssuedCount() + 1);
				book.setStatus(BookClass.BookStatus.ISSUED);
				return "Book with given Book Id " + bookId + " issued Successfully.";
			} else {
				return "No Available Copies to Issue.";
				} 
			}else {
				return "Book With Given BookId Not Found.";
			}
	}
	
	
	
	public void totalCount() {
		for(BookClass books : bookMap.values()) {
			System.out.println(books);
		}
	}
	
//	public void borrowBook(Scanner sc) {
//		System.out.println("Enter the BookId you want to borrow : ");
//		int bookId = sc.nextInt();
//		
//		BookClass book = bookMap.get(bookId);
//		if(book != null) {
//			if(book.getAvailableQuantity() > 0) {
//				book.setBorrowedCount(book.getBorrowedCount() + 1);
//				book.setStatus(BookStatus.BORROWED);
//				System.out.println("Book with BookId" + bookId + " Borrowed Successfully.");
//			} else {
//				System.out.println("NO Available Copies to Borrow!");
//				} 
//			}else {
//			System.out.println("Book with the given bookId "+ bookId + " not found.");
//			}
//		//System.out.println(book);
//		//System.out.println("Available Books  : " + book.getAvailableQuantity());
//	}
	
	public String borrowBook(int bookId) {
		BookClass book = bookMap.get(bookId);
		if(book != null) {
			if(book.getAvailableQuantity() > 0) {
				book.setStatus(BookClass.BookStatus.BORROWED);
				return "Book With Given BookId " + bookId + "Borrowed Successfully.";
			} else {
				return "No Available Copies to Borrow";
			}
		} else {
			return "Book With Given BookId not Found";
		}
	}
	
	public String returnBorrowedBook(int bookId) {
		BookClass book = bookMap.get(bookId);
		if(book != null) {
			if(book.getBorrowedCount() > 0) {
				book.setBorrowedCount(book.getBorrowedCount() + 1);
				if(book.getIssuedCount() == 0) {
					book.setStatus(BookStatus.AVAILABLE);
				}
				return "Book With BookId " + bookId + " Returned.";
			}else {
				return "This was not the Book Borrowed Priorly.";
			}
		} else {
		return "No Available Copies to Return";
		}
		
	}
	
//	public void returnBorrowedBook(Scanner sc) {
//		System.out.println("Enter the bookId to be returned : ");
//		int bookId = sc.nextInt();
//		
//		BookClass book = bookMap.get(bookId);
//		if(book != null) {
//			if(book.getBorrowedCount() > 0) {
//				book.setBorrowedCount(book.getBorrowedCount()-1);
//				if(book.getIssuedCount() == 0) {
//					book.setStatus(BookStatus.AVAILABLE);
//				}
//				System.out.println("Book with BookId "+ bookId + " Returned.");
//			}else {
//				System.out.println("This was not the Book Borrowed Priorly.");
//			}
//		} else {
//			System.out.println("Book with BookId "+bookId + " not found.");
//		}
//	}

	public String submitBook(int bookId) {
		BookClass book = bookMap.get(bookId);
		if(book != null) {
			if(book.getIssuedCount() > 0) {
				book.setIssuedCount(book.getIssuedCount() - 1);
				if(book.getIssuedCount() == 0) {
					book.setStatus(BookStatus.AVAILABLE);
				}
				return "Book With BookID " + bookId + " Submitted.";
			} else {
				return "This was not the book Issued priorly.";
			}
		} else {
			return "Book with BookId " + bookId + " not Not available to Submit.";
		}
	}
	
//	public void submitBook(Scanner sc) {
//		System.out.println("Enter the book Id to be submitted : ");
//		int bookId = sc.nextInt();
//	
//		BookClass book = bookMap.get(bookId);
//		if(book != null) {
//			if(book.getIssuedCount() > 0) {
//				book.setIssuedCount(book.getIssuedCount() -1);
//				if(book.getIssuedCount() == 0) {
//					book.setStatus(BookStatus.AVAILABLE);
//				}
//				System.out.println("Book with BookId " + bookId + " Submitted.");
//			} else {
//				System.out.println("This was not the book issued priorly.");
//			}
//		} else {
//			System.out.println("Book with ID "+ bookId + " not found.");
//		}
//		//System.out.println(book);
//	}
//	
	public int countAvailableBooks() {
		int totalAvailable = 0;
		for(BookClass book : bookMap.values()) {
			totalAvailable += book.getAvailableQuantity();	
		}
		return totalAvailable;
	}
}
