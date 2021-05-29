package webapp;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

import entities.*;
import dao.*;

public class UsersPageServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		PrintWriter out = response.getWriter();
		UserDao user_handler = new UserDao();
		HttpSession session = request.getSession();
		User cur_user = (User) session.getAttribute("user");
		String root = request.getContextPath();

		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			out.println("<title>Users list</title>");
			out.println("</head>");
			if(cur_user == null || cur_user.getRole() == Roles.user)
			{
				out.println("<body>");
				out.println("<b>You can't view this page</b>");
				out.println("</body>");
				out.println("</html>");
				return;
			}
			out.println("<body>");
			out.println("<h1>Users list</h1>");
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>Id</th>");
			out.println("<th>Username</th>");
			out.println("<th>Name</th>");
			out.println("<th>Role</th>");
			out.println("</tr>");

			List<User> users = user_handler.getAll();
			if(users.isEmpty())
			{
				out.write("</table>");
				out.write("<b>Nothing found</b>");
			}
			else
			{
				for(User u : users)
				{
					out.println("<tr>");
					out.print("<td>"); out.print(u.getId()); out.println("</td>");
					out.print("<td>"); out.print(u.getUsername()); out.println("</td>");
					out.print("<td>"); out.print(u.getName()); out.println("</td>");
					out.print("<td>"); out.print(u.getRole()); out.println("</td>");
					out.println("<td>");
					out.println("<a href='" + root + "/rented?user_id=" + u.getId() + "'>Rented books</a>");
					// out.println("<form method='GET' action='/library/main'>");
					// out.print("<input type='submit' id='rent-button' name='rent-button" + b.getId() + "' ");
					// out.println("value='Rent'>");
					// out.println("</form>");
					out.println("</td>");
					out.println("</tr>");
				}
				out.println("</table>");
			}

			out.println("</body>");
			out.println("</html>");
		}
		finally
		{
			out.close();
		}
	}
}
