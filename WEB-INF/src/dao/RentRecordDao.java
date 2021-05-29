package dao;

import session.ConnectionManager;
import entities.RentRecord;
import entities.Book;
import entities.User;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class RentRecordDao implements Dao<RentRecord>
{
	Connection con;

	public RentRecordDao()
	{
		con = ConnectionManager.getConnection();
	}

	private java.sql.Date utilToSqlDate(java.util.Date d)
	{
		return new java.sql.Date(d.getTime());
	}

	private RentRecord rentRecordFromRset(ResultSet rset)
	{
		RentRecord res = null;
		try
		{
			int id = rset.getInt("Id");
			int book_id = rset.getInt("Book_id");
			int user_id = rset.getInt("User_id");
			Date rented_on = new Date(rset.getDate("Rented_on").getTime());
			Date due_to = new Date(rset.getDate("Due_to").getTime());

			res = new RentRecord(id, book_id, user_id, rented_on, due_to);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}

		return res;
	}

	@Override
	public List<RentRecord> getAll()
	{
		String select_string = "select * from RENT_RECORDS";
		Statement stmt = null;
		ResultSet rset = null;
		List<RentRecord> res = new ArrayList<RentRecord>();
		try
		{
			stmt = con.createStatement();
			rset = stmt.executeQuery(select_string);
			while(rset.next())
				res.add(rentRecordFromRset(rset));
		}
		catch(Exception ex)
		{
			System.err.println("Error while selecting * from RENT_RECORDS");
			System.err.println(ex);
			// throw ex;
		}
		return res;
	}

	@Override
	public void save(RentRecord r)
	{
		String query_str = "insert into RENT_RECORDS(Book_id, User_id, Rented_on, Due_to) values(?, ?, ?, ?)";
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setInt(1, r.getBookId());
			stmt.setInt(2, r.getUserId());
			stmt.setDate(3, utilToSqlDate(r.getRentDate()));
			stmt.setDate(4, utilToSqlDate(r.getDueDate()));
			System.out.println(stmt);
			stmt.executeUpdate();
		}
		catch(Exception ex)
		{
			System.err.println("Error while inserting to RENT_RECORDS");
			System.err.println(ex);
		}

	}

	@Override
	public RentRecord get(int id)
	{
		String query_str = "select * from RENT_RECORDS where Id = ?";
		RentRecord res = null;
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setInt(1, id);
			System.out.println(stmt);
			ResultSet rset = stmt.executeQuery();
			rset.next();

			res = rentRecordFromRset(rset);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}
		return res;
	}

	public List<RentRecord> getByUser(User u)
	{
		String query_str = "select * from RENT_RECORDS where User_id = ?";
		List<RentRecord> res = new ArrayList<RentRecord>();
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setInt(1, u.getId());
			System.out.println(stmt);
			ResultSet rset = stmt.executeQuery();
			while(rset.next())
				res.add(rentRecordFromRset(rset));
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}
		return res;
	}

	public List<RentRecord> getByBook(Book b)
	{
		String query_str = "select * from RENT_RECORDS where Book_id = ?";
		List<RentRecord> res = new ArrayList<RentRecord>();
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setInt(1, b.getId());
			System.out.println(stmt);
			ResultSet rset = stmt.executeQuery();
			while(rset.next())
				res.add(rentRecordFromRset(rset));
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}
		return res;
	}

	@Override
	public void delete(RentRecord r)
	{
		String query_str = "delete from RENT_RECORDS where Id = ?";
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setInt(1, r.getId());
			System.out.println(stmt);
			stmt.executeUpdate();

		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}

	}

	@Override
	public void update(RentRecord r)
	{
	}

}
