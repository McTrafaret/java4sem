package webapp;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.Enumeration;

import entities.*;
import dao.*;

public class RentedBooksServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		PrintWriter out = response.getWriter();
		RentRecordDao rr_handler = new RentRecordDao();
		UserDao user_handler = new UserDao();
		BookDao book_handler = new BookDao();
		HttpSession session = request.getSession();
		User cur_user = (User) session.getAttribute("user");
		String root = request.getContextPath();
		if(cur_user == null)
		{
			response.sendRedirect(root+"/login");
			return;
		}
		if(cur_user.getRole() != Roles.user)
		{
			String rr_id = request.getParameter("return");
			if(rr_id != null)
			{
				RentRecord rr = rr_handler.get(Integer.parseInt(rr_id));
				Book b = book_handler.get(rr.getBookId());
				b.incrementAvailable();
				book_handler.update(b);
				rr_handler.delete(rr);
			}
		}

		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			out.println("<title>Rented books</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Rented books</h1>");
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>Book</th>");
			out.println("<th>Rented by</th>");
			out.println("<th>Rented on</th>");
			out.println("<th>Due to</th>");
			out.println("</tr>");

			List<RentRecord> rrs = null;
			if(cur_user.getRole() == Roles.user)
			{
				rrs = rr_handler.getByUser(cur_user);
			}
			else
			{
				String user_id = request.getParameter("user_id");
				if(user_id == null)
				{
					rrs = rr_handler.getAll();
				}
				else
				{
					User to_show = user_handler.get(Integer.parseInt(user_id));
					rrs = rr_handler.getByUser(to_show);
				}
			}
			if(rrs.isEmpty())
			{
				out.write("</table>");
				out.write("<b>Nothing found</b>");
			}
			else
			{
				for(RentRecord rr : rrs)
				{
					User u = user_handler.get(rr.getUserId());
					Book b = book_handler.get(rr.getBookId());
					out.println("<tr>");
					out.print("<td>"); out.print(b.getName()); out.println("</td>");
					out.print("<td>"); out.print(u.getName()); out.println("</td>");
					out.print("<td>"); out.print(rr.getRentDate()); out.println("</td>");
					out.print("<td>"); out.print(rr.getDueDate()); out.println("</td>");
					if(cur_user.getRole() != Roles.user)
					{
						out.println("<td>");
						// out.println("<form method='GET' action='/library/rented'>");
						// out.print("<input type='submit' id='return' name='return" + rr.getId() + "' ");
						// out.println("value='Return'>");
						// out.println("</form>");
						out.println("<a href='" + root + "/rented?return=" + rr.getId() +"'>Return</a>");
						out.println("</td>");
					}
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
