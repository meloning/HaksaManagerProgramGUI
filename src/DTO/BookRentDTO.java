package DTO;

public class BookRentDTO {
	String no;
	String id;
	String bookno;
	String rDate;
	BooksDTO booksDto=new BooksDTO();
	StudentDTO stdDto=new StudentDTO();
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBookno() {
		return bookno;
	}
	public void setBookno(String bookno) {
		this.bookno = bookno;
	}
	public String getrDate() {
		return rDate;
	}
	public void setrDate(String rDate) {
		this.rDate = rDate;
	}
	public BooksDTO getBooksDto() {
		return booksDto;
	}
	public void setBooksDto(BooksDTO booksDto) {
		this.booksDto = booksDto;
	}
	public StudentDTO getStdDto() {
		return stdDto;
	}
	public void setStdDto(StudentDTO stdDto) {
		this.stdDto = stdDto;
	}
	
}
