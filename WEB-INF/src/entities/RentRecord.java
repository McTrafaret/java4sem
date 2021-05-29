package entities;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RentRecord
{
	int id, book_id, user_id;
	Date rented_on, due_to;

	public RentRecord(int id, int book_id, int user_id, Date rented_on, Date due_to)
	{
		this.id = id;
		this.book_id = book_id;
		this.user_id = user_id;
		this.rented_on = rented_on;
		this.due_to = due_to;
	}

	public RentRecord(int book_id, int user_id, Date due_to)
	{
		this.book_id = book_id;
		this.user_id = user_id;
		this.due_to = due_to;
		this.rented_on = new Date();
	}

	public RentRecord(Book b, User u, Date due_to)
	{
		this.book_id = b.getId();
		this.user_id = u.getId();
		this.rented_on = new Date();
		this.due_to = due_to;
	}

	public int getId()
	{
		return id;
	}

	public int getBookId()
	{
		return book_id;
	}

	public int getUserId()
	{
		return user_id;
	}

	public Date getRentDate()
	{
		return rented_on;
	}

	public Date getDueDate()
	{
		return due_to;
	}

	public String toString()
	{
		String fmt = "Id: %d\nBook id: %d\nUser id: %d\nRented on: %s\nDue to: %s\n";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String on = dateFormat.format(rented_on);
		String due = dateFormat.format(due_to);
		return String.format(fmt, id, book_id, user_id, on, due);
	}

}
