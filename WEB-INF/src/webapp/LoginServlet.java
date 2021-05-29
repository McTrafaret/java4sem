package webapp;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import dao.UserDao;
import entities.User;

public class LoginServlet extends HttpServlet
{
	private void loginPage(PrintWriter out)
	{
		try
		{
			out.println("<form action='login' method='GET'>");
			out.println("<label for='login'>Login:</label><br>");
			out.println("<input type='text' id='login' name='login'><br>");
			out.println("<label for='password'>Password:</label><br>");
			out.println("<input type='password' id='password' name='password'><br>");
			out.println("<input type='submit' id='submit' name='submit' value='Submit'><br>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
		}
		finally
		{
			out.close();
		}
	}

	private void invalidCredentials(PrintWriter out)
	{
		try
		{
			out.println("<p style='color:red'>Invalid login or password</p>");
			out.println("<form action='login' method='GET'>");
			out.println("<label for='login'>Login:</label><br>");
			out.println("<input type='text' id='login' name='login'><br>");
			out.println("<label for='password'>Password:</label><br>");
			out.println("<input type='password' id='password' name='password'><br>");
			out.println("<input type='submit' id='submit' name='submit' value='Submit'><br>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
		}
		finally
		{
			out.close();
		}
	}

	private void alreadyLogged(PrintWriter out)
	{
		try
		{
			out.println("<p>You are already logged</p>");
			out.println("<form action='login' method='GET'>");
			out.println("<input type='submit' id='logout' name='logout' value='Log out'>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
		}
		finally
		{
			out.close();
		}
	}

	private void testPage(PrintWriter out, User u)
	{
		try
		{
			out.println("<p>" + u + "</p");
			out.println("</body>");
			out.println("</html>");
		}
		finally
		{
			out.close();
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		User u = null;
		UserDao userHandler = new UserDao();
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		boolean logout = request.getParameter("logout") != null;
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		if(logout)
			session.setAttribute("user", null);

		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			out.println("<title>Login page</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Login page</h1>");
		}
		catch(Exception ex)
		{
			out.close();
			return;
		}
		u = (User) session.getAttribute("user");
		if(u != null)
		{
			alreadyLogged(out);
			return;
		}

		if(login != null && password != null)
		{
			u = userHandler.get(login, password);
			if(u != null)
			{
				session.setAttribute("user", u);
				String path = request.getContextPath() + "/main";
				response.sendRedirect(path);
				return;
			}
			else
			{
				invalidCredentials(out);
				return;
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		loginPage(out);
	}
}
