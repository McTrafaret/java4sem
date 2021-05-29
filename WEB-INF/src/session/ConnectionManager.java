package session;

import java.sql.*;
public class ConnectionManager
{
	static String driver = "org.mariadb.jdbc.Driver";
	static String url = "jdbc:mariadb://localhost:3306/library";
	static String login = "library";
	static String password = "DFKkV+'@BC)#PFX(U_o0Ph0,v";
	private static ConnectionManager instance = new ConnectionManager();
	private Connection connection;

	private ConnectionManager()
	{
		System.out.println("connection manager instantiated");
		Connection connection = null;
		try
		{
			this.connection = DriverManager.getConnection(url, login, password);
		}
		catch(Exception ex)
		{
			System.err.println("Couldn't establish a connection to db");
			System.err.println(ex);
		}
	}

	public static Connection getConnection()
	{
		return instance.connection;
	}
}
