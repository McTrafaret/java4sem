package dao;

import session.ConnectionManager;
import entities.User;
import entities.Roles;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class UserDao implements Dao<User>
{
	Connection con;

	public UserDao()
	{
		con = ConnectionManager.getConnection();
	}

	private User userFromRset(ResultSet rset)
	{
		User res = null;
		try
		{
			int id = rset.getInt("Id");
			String get_username = rset.getString("Username");
			String get_password = rset.getString("Password");
			String name = rset.getString("Name");
			Roles role = Roles.RoleFromInt(rset.getInt("Role_id"));
			res = new User(id, get_username, get_password, name, role);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}

		return res;
	}

	@Override
	public List<User> getAll()
	{
		String select_string = "select * from USERS";
		Statement stmt = null;
		ResultSet rset = null;
		List<User> res = new ArrayList<User>();
		try
		{
			stmt = con.createStatement();
			rset = stmt.executeQuery(select_string);
			while(rset.next())
				res.add(userFromRset(rset));
		}
		catch(Exception ex)
		{
			System.err.println("Error while selecting * from USERS");
			System.err.println(ex);
			// throw ex;
		}
		return res;
	}

	@Override
	public void save(User u)
	{
		String query_str = "insert into USERS(Username, Password, Role_id, Name) values(?, ?, ?, ?)";
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setInt(3, u.getRole().ToInt());
			stmt.setString(4, u.getName());
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
	public User get(int id)
	{
		String query_str = "select * from USERS where Id = ?";
		User res = null;
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setInt(1, id);
			System.out.println(stmt);
			ResultSet rset = stmt.executeQuery();
			rset.next();

			res = userFromRset(rset);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}
		return res;
	}

	public User get(String username, String password)
	{
		String query_str = "select * from USERS where Username = ? and Password = ?";
		User res = null;
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setString(1, username);
			stmt.setString(2, password);

			System.out.println(stmt);
			ResultSet rset = stmt.executeQuery();
            rset.next();

            res = userFromRset(rset);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}
		return res;
	}

	@Override
	public void delete(User u)
	{
		String query_str = "delete from USERS where Id = ?";
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setInt(1, u.getId());
			System.out.println(stmt);
			stmt.executeUpdate();

		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}

	}

	@Override
	public void update(User u)
	{
		String query_str = "update USERS set Username = ?, Password = ?, Name = ?, Role_id = ? where Id = ?";
		try
		{
			PreparedStatement stmt = con.prepareStatement(query_str);
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getName());
			stmt.setInt(4, u.getRole().ToInt());
			stmt.setInt(5, u.getId());
			System.out.println(stmt);
			stmt.executeUpdate();
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}
	}

}
