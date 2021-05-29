package webapp;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import entities.*;
import dao.*;

public class MainPageServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		PrintWriter out = response.getWriter();
		BookDao book_handler = new BookDao();
		RentRecordDao rr_handler = new RentRecordDao();
		HttpSession session = request.getSession();
		User cur_user = (User) session.getAttribute("user");
		String root = request.getContextPath();
		if(cur_user == null)
		{
			response.sendRedirect(root+"/login");
			return;
		}
		String rent_id = request.getParameter("rent");
		if(rent_id != null)
		{
			Book b = book_handler.get(Integer.parseInt(rent_id));
			Calendar calendar = Calendar.getInstance();

			calendar.add(Calendar.DAY_OF_YEAR, 1);

			Date tomorrow = calendar.getTime();
			RentRecord rr = new RentRecord(b, cur_user, tomorrow);
			rr_handler.save(rr);
			b.decrementAvailable();
			book_handler.update(b);
		}

		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			out.println("<title>Login page</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Main page</h1>");
			if(cur_user.getRole() != Roles.user)
			{
				out.println("<a href='" + root + "/users'>Users moderation</a>");
			}
			out.println("<a href='" + root + "/rented'>Rented books</a>");
			out.println("<form method='GET' action='/library/main'>");
			out.println("<input type='text' id='search-request' name='search-request'>");
			out.println("<input type='submit' id='search-button' name='search-button'");
			out.println("value='Search'><br>");
			out.println("<input type='checkbox' id='by-author' name='by-author'>");
			out.println("<label for='by-author'>Search by author</label>");
			out.println("<input type='checkbox' id='by-name' name='by-name'>");
			out.println("<label for='by-name'>Search by name</label>");
			out.println("<input type='checkbox' id='by-genre' name='by-genre'>");
			out.println("<label for='by-genre'>Search by genre</label>");
			out.println("</form>");
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>Id</th>");
			out.println("<th>Name</th>");
			out.println("<th>Author</th>");
			out.println("<th>Genre</th>");
			out.println("<th>Available</th>");
			out.println("</tr>");

			boolean by_author = request.getParameter("by-author") != null;
			String search_request = request.getParameter("search-request");
			List<Book> books = null;
			if(by_author)
				books = book_handler.getByAuthor(search_request);
			else
				books = book_handler.getAll();
			if(books.isEmpty())
			{
				out.write("</table>");
				out.write("<b>Nothing found</b>");
			}
			else
			{
				for(Book b : books)
				{
					out.println("<tr>");
					out.print("<td>"); out.print(b.getId()); out.println("</td>");
					out.print("<td>"); out.print(b.getName()); out.println("</td>");
					out.print("<td>"); out.print(b.getAuthor()); out.println("</td>");
					out.print("<td>"); out.print(b.getGenre()); out.println("</td>");
					out.print("<td>"); out.print(b.getAvailable()); out.println("</td>");
					out.println("<td>");
					// out.println("<form method='GET' action='/library/main'>");
					// out.print("<input type='submit' id='rent-button' name='rent-button" + b.getId() + "' ");
					// out.println("value='Rent'>");
					// out.println("</form>");
					out.println("<a href='" + root + "/main?rent=" + b.getId() + "' >Rent</a>");
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
