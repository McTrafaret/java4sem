package entities;

import java.util.List;
import java.util.ArrayList;

public class User
{
	int id;
	String username, password, name;
	Roles role;
	List<RentRecord> rented_books;

	public User(String username, String password, String name,
	     Roles role)
	{
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
		rented_books = new ArrayList<RentRecord>();
	}

	public User(int id, String username, String password, String name,
	     Roles role)
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
		rented_books = new ArrayList<RentRecord>();
	}

	public Roles getRole()
	{
		return role;
	}

	public void setRole(Roles role)
	{
		this.role = role;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getName()
	{
		return name;
	}

	public String toString()
	{
		String fmt = "Id: %d\nUsername: %s\nPassword: %s\nName: %s\n Role: %s\n";
		return String.format(fmt, id, username, password, name, role);
	}

	public int getId()
	{
		return id;
	}
}
