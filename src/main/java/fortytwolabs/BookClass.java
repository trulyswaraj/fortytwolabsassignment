//CONTAINS jUNIT TEST CASES!

package fortytwolabs;

import fortytwolabs.BookClass.BookStatus;

public class BookClass {
	int bookId;
	String bookName;
	String author;
	BookStatus status;
	int quantity;
	int issuedCount;
	int borrowedCount;
	
	public BookClass(int bookId, String bookName, String author, BookStatus status, int quantity, int issuedCount,
			int borrowedCount) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.author = author;
		this.status = status;
		this.quantity = quantity;
		this.issuedCount = 0;
		this.borrowedCount = 0;
	}

	public enum BookStatus{ISSUED, AVAILABLE, BORROWED};

	public int getBookId() {
		return bookId;
	}
	private void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public BookStatus getStatus() {
		return status;
	}
	public void setStatus(BookStatus status) {
		this.status = status;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getAvailableQuantity() {
		return quantity - (issuedCount + borrowedCount);
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getIssuedCount() {
		return issuedCount;
	}

	public void setIssuedCount(int issuedCount) {
		this.issuedCount = issuedCount;
	}
	public int getBorrowedCount() {
		return borrowedCount;
	}

	public void setBorrowedCount(int borrowedCount) {
		this.borrowedCount = borrowedCount;
	}
	@Override
	public String toString() {
		return "BookClass [bookId=" + bookId + ", bookName=" + bookName + ", author=" + author + ", status=" + status
				+ ", quantity=" + quantity + ", issuedCount=" + issuedCount + ", borrowedCount=" + borrowedCount + "]";
	}
}
