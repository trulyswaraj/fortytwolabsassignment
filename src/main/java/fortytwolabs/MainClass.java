//CONTAINS jUNIT TEST CASES!

package fortytwolabs;

import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) {
		ServiceClass service  = new ServiceClass();
		Scanner sc = new Scanner(System.in);
		int choice;
		while(true) {
			System.out.println("Library Management System");
			System.out.println();
			System.out.println("1. Add Book ");
			System.out.println("2. Issue Book ");
			System.out.println("3. Borrow Book ");
			System.out.println("4. Total Book Count ");
			System.out.println("5. Submit Book ");
			System.out.println("6. Return Borrowed Book ");
			System.out.println("7. Count Available Book ");
			System.out.println("8. System Exit. ");
			System.out.println();
			System.out.println("Enter Your Choice : ");
			choice = sc.nextInt();
			sc.nextLine();
			switch(choice) {
			case 1: 
//				service.addBook(sc);
				System.out.println("Enter Book Name : ");
				String bookName = sc.nextLine();
				
				System.out.println("Enter Author Name : ");
				String author = sc.nextLine();
				
				System.out.println("Enter the Quantity Of Books to be Entered : ");
				int quantity = sc.nextInt();
				sc.nextLine();
				
				String result = service.addBook(bookName, author, quantity);
				System.out.println(result);
				break;
			
			case 2 : 
				//service.issueBook(sc);
				System.out.println("Enter BookId you want to Issue : ");
				int bookId = sc.nextInt();
				sc.nextLine();
				String resultIssueBook = service.issueBook(bookId);
				System.out.println(resultIssueBook);
				break;
			
			case 3 : 
				//service.borrowBook(sc);
				System.out.println("Enter BookId you want to Borrow : ");
				int bookIdOfBorrow = sc.nextInt();
				sc.nextLine();
				String resultBorrowBook = service.borrowBook(bookIdOfBorrow);
				System.out.println(resultBorrowBook);
				break;
				
			case 4 : 
				service.totalCount();
				break;
			
			case 5 : 
				//service.submitBook(sc);
				System.out.println("Enter the BookId you want to Submit : ");
				int bookIdToSubmit = sc.nextInt();
				sc.nextLine();
				String resultOfSubmitBook = service.submitBook(bookIdToSubmit);
				System.out.println(resultOfSubmitBook);
				break;
				
			case 6:
				//service.returnBorrowedBook(sc);
				System.out.println("Enter the bookId you want to return : ");
				int bookIdToReturn = sc.nextInt();
				sc.nextLine();
				String resultOfReturnBook = service.returnBorrowedBook(bookIdToReturn);
				System.out.println(resultOfReturnBook);
				break;
				
			case 7:
				//service.countAvailableBooks(sc);
				System.out.println(service.countAvailableBooks());
				break;
				
			case 8: 
				System.out.println("System Exit");
				System.exit(0);
				break;
			}
		}
	}
}
