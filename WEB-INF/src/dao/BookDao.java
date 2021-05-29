package dao;

import session.ConnectionManager;
import entities.Book;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class BookDao implements Dao<Book>
{
	Connection con;

	public BookDao()
	{
		con = ConnectionManager.getConnection();
	}

	private Book bookFromRset(ResultSet rset)
	{
		Book res = null;
		try
		{
			int id = rset.getInt("Id");
			String name = rset.getString("Name");
			String author = rset.getString("Author");
			String genre = rset.getString("Genre");
			int qty = rset.getInt("Available");

			res = new Book(id, name, author, genre, qty);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}

		return res;
	}

	@Override
	public List<Book> getAll()
	{
		String select_string = "select * from BOOKS";
		Statement stmt = null;
		ResultSet rset = null;
		List<Book> res = new ArrayList<Book>();
		try
		{
			stmt = con.createStatement();
			rset = stmt.executeQuery(select_string);
			while(rset.next())
				res.add(bookFromRset(rset));
		}
		catch(Exception ex)
		{
			System.err.println("Error while selecting * from BOOKS");
			System.err.println(ex);
			// throw ex;
		}
		return res;
	}

	@Override
	public void save(Book b)
	{
		String query_str = "insert into BOOKS(Name, Author, Genre, Available) values(?, ?, ?, ?)";
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setString(1, b.getName());
			stmt.setString(2, b.getAuthor());
			stmt.setString(3, b.getGenre());
			stmt.setInt(4, b.getAvailable());
			System.out.println(stmt);
			stmt.executeUpdate();
		}
		catch(Exception ex)
		{
			System.err.println("Error while inserting to BOOKS");
			System.err.println(ex);
		}

	}

	@Override
	public Book get(int id)
	{
		String query_str = "select * from BOOKS where Id = ?";
		Book res = null;
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setInt(1, id);
			System.out.println(stmt);
			ResultSet rset = stmt.executeQuery();
			rset.next();

			res = bookFromRset(rset);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}
		return res;
	}

	public List<Book> getByAuthor(String author)
	{
		// String query_str = "select * from BOOKS where Author='?'";
		String query_str = "select * from BOOKS where Author = ?";
		List<Book> res = new ArrayList<Book>();
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setString(1, author);
			System.err.println(stmt);
			ResultSet rset = stmt.executeQuery();
			while(rset.next())
				res.add(bookFromRset(rset));
		}
		catch(Exception ex)
		{
			System.err.println("Error while selecting * from BOOKS by author");
			System.err.println(ex);
			// throw ex;
		}
		return res;
	}

	@Override
	public void delete(Book b)
	{
		String query_str = "delete from BOOKS where Id = ?";
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setInt(1, b.getId());
			System.out.println(stmt);
			stmt.executeUpdate();

		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}

	}

	@Override
	public void update(Book b)
	{
		String query_str = "update BOOKS set Name = ?, Author = ?, Genre = ?, Available = ? where Id = ?";
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setString(1, b.getName());
			stmt.setString(2, b.getAuthor());
			stmt.setString(3, b.getGenre());
			stmt.setInt(4, b.getAvailable());
			stmt.setInt(5, b.getId());
			System.out.println(stmt);
			stmt.executeUpdate();
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}

	}

}
