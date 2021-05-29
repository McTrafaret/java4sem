package session;

import entities.*;
import dao.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class Session
{
	User u;
	UserDao userHandler;
	BookDao bookHandler;
	RentRecordDao rentRecordHandler;
	boolean quit = false;

	public Session()
	{
		u = null;
		userHandler = new UserDao();
		bookHandler = new BookDao();
		rentRecordHandler = new RentRecordDao();
	}

	private int getChoice(int min_choice, int max_choice)
	{
		int choice = 0;
		while(true)
		{
			boolean invalid = false;
			System.out.println("");
			System.out.print("Choice: ");
			try
			{
				choice = Integer.parseInt(System.console().readLine());
			}
			catch(NumberFormatException e)
			{
				invalid = true;
			}
			if(invalid || choice < min_choice || choice > max_choice)
			{
				continue;
			}
			break;
		}

		return choice;
	}

	private void startScreenInfo()
	{
		System.out.println("1. Log in");
		System.out.println("2. Register");
		System.out.println("3. Quit");
	}

	private void startScreenForm()
	{
		final int MIN_CHOICE = 1, MAX_CHOICE = 3;
		startScreenInfo();
		int choice = getChoice(MIN_CHOICE, MAX_CHOICE);
		switch(choice)
		{
			case 1: logInForm(); break;
			case 2: registerForm(); break;
			case 3: quit = true; break;
		}
	}

	private void registerForm()
	{
		User temp = null;
		String username, password, name;
		while(true)
		{
			System.out.println("Username: ");
			username = System.console().readLine();
			if(Validator.IsValidUsername(username))
				break;
			System.out.println("Invalid input");
		}
		System.out.println("Password: ");
		password = System.console().readLine();
		while(true)
		{
			System.out.println("Full name: ");
			name = System.console().readLine();
			if(Validator.IsValidName(name))
				break;
			System.out.println("Invalid input");
		}
		temp = new User(username, password, name, Roles.user);
		userHandler.save(temp);
		System.out.print("Registered!");
	}

	private void logInForm()
	{
		System.out.println("Username: ");
		String username  = System.console().readLine();
		System.out.println("Password: ");
		String password = System.console().readLine();
		User user = userHandler.get(username, password);
		if(user != null)
		{
			u = user;
			System.out.println(String.format("Welcome, %s!", u.getName()));
			switch(u.getRole())
			{
				case admin: adminMenu(); break;
				case librarian: librarianMenu(); break;
				case user: userMenu(); break;
			}
		}
		else
		{
			System.out.println("Wrong username or password");
		}
	}

	private void promoteUser()
	{
		int id;
		System.out.print("Input id of the user you wish to promote: ");
		while(true)
		{
			try
			{
				id = Integer.parseInt(System.console().readLine());
			}
			catch(NumberFormatException e)
			{
				System.out.println("Invalid input. Try again.");
				System.out.print("Id: ");
				continue;
			}
			break;
		}

		User to_promote = userHandler.get(id);
		if(to_promote == null)
		{
			System.out.println("There are no users with such id.");
			return;
		}
		to_promote.setRole(Roles.librarian);
		userHandler.update(to_promote);
		System.out.println("User promoted to librarian!");
	}

	private void deleteUser()
	{
		int id;
		System.out.print("Input id of the user you wish to delete: ");
		while(true)
		{
			try
			{
				id = Integer.parseInt(System.console().readLine());
			}
			catch(NumberFormatException e)
			{
				System.out.println("Invalid input. Try again.");
				System.out.print("Id: ");
				continue;
			}
			break;
		}

		User to_delete = userHandler.get(id);
		if(to_delete == null)
		{
			System.out.println("There are no users with such id.");
			return;
		}
		List<RentRecord> loans = rentRecordHandler.getByUser(to_delete); // deleting loans
		for(RentRecord rr : loans)                                       // maybe just cascade it in the db
		{
			rentRecordHandler.delete(rr);
		}
		userHandler.delete(to_delete);
		System.out.println("User deleted");
	}

	private void addBook()
	{
		System.out.print("Input name of the book: ");
		String name = System.console().readLine();
		System.out.print("Input book's author: ");
		String author = System.console().readLine();
		System.out.print("Input genre of the book: ");
		String genre = System.console().readLine();
		System.out.print("Input number of available copies: ");
		int available = Integer.parseInt(System.console().readLine());

		Book b = new Book(name, author, genre, available);
		bookHandler.save(b);
		System.out.println("Book added!");
	}

	private void deleteBook()
	{
		int id;
		System.out.print("Input id of the book you wish to delete: ");
		while(true)
		{
			try
			{
				id = Integer.parseInt(System.console().readLine());
			}
			catch(NumberFormatException e)
			{
				System.out.println("Invalid input. Try again.");
				System.out.print("Id: ");
				continue;
			}
			break;
		}

		Book to_delete = bookHandler.get(id);
		if(to_delete == null)
		{
			System.out.println("There are no books with such id.");
			return;
		}
		List<RentRecord> loans = rentRecordHandler.getByBook(to_delete); // deleting loans
		for(RentRecord rr : loans)                                       // maybe just cascade it in the db
		{
			rentRecordHandler.delete(rr);
		}
		bookHandler.delete(to_delete);
		System.out.println("Book deleted!");
	}

	private void returnBook()
	{
		int id;
		System.out.print("Input record id: ");
		while(true)
		{
			try
			{
				id = Integer.parseInt(System.console().readLine());
			}
			catch(NumberFormatException e)
			{
				System.out.println("Invalid input. Try again.");
				System.out.print("Id: ");
				continue;
			}
			break;
		}

		RentRecord to_delete = rentRecordHandler.get(id);
		Book b = bookHandler.get(to_delete.getId());
		b.incrementAvailable();
		bookHandler.update(b);
		rentRecordHandler.delete(to_delete);
		System.out.println("Done!");
	}

	private void adminMenuInfo()
	{
		System.out.println("1. Promote user");
		System.out.println("2. Delete user");
		System.out.println("3. Add book");
		System.out.println("4. Delete book");
		System.out.println("5. Return book");
		System.out.println("6. Quit");
	}

	private void adminMenu()
	{
		final int MIN_CHOICE = 1, MAX_CHOICE = 6;
		boolean quit = false;
		while(!quit)
		{
			adminMenuInfo();
			int choice = getChoice(MIN_CHOICE, MAX_CHOICE);
			switch(choice)
			{
				case 1: promoteUser(); break;
				case 2: deleteUser(); break;
				case 3: addBook(); break;
				case 4: deleteBook(); break;
				case 5: returnBook(); break;
				case 6: u = null; quit = true; break;
			}
		}
	}

	private void librarianMenuInfo()
	{
		System.out.println("1. Delete user");
		System.out.println("2. Add book");
		System.out.println("3. Delete book");
		System.out.println("4. Return book");
		System.out.println("5. Quit");
	}

	private void librarianMenu()
	{
		final int MIN_CHOICE = 1, MAX_CHOICE = 5;
		boolean quit = false;
		while(!quit)
		{
			librarianMenuInfo();
			int choice = getChoice(MIN_CHOICE, MAX_CHOICE);
			switch(choice)
			{
				case 1: deleteUser(); break;
				case 2: addBook(); break;
				case 3: deleteBook(); break;
				case 4: returnBook(); break;
				case 5: u = null; quit = true; break;
			}
		}
	}

	private void userMenuInfo()
	{
		System.out.println("1. List available books");
		System.out.println("2. Rent a book");
		System.out.println("3. Show rented books");
		System.out.println("4. Quit");
	}

	private void listBooks()
	{
		List<Book> books = bookHandler.getAll();
		for(Book b : books)
		{
			System.out.println(b);
		}
	}

	private void rentBook()
	{
		int id;
		System.out.print("Input id of the book you wish to rent: ");
		try
		{
			id = Integer.parseInt(System.console().readLine());
		}
		catch(NumberFormatException e)
		{
			System.out.println("Man");
			return;
		}
		Book b = bookHandler.get(id);
		if(b == null)
		{
			System.out.println("There is no book with such id");
			return;
		}
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_YEAR, 1);

		Date tomorrow = calendar.getTime();
		RentRecord rr = new RentRecord(b, u, tomorrow);
		rentRecordHandler.save(rr);
		b.decrementAvailable();
		bookHandler.update(b);
		System.out.println("Book rent!");
	}

	private void showRented()
	{
		List<RentRecord> rented = rentRecordHandler.getByUser(u);
		for(RentRecord rr : rented)
		{
			Book b = bookHandler.get(rr.getBookId());
			System.out.println(String.format("%s by %s", b.getName(), b.getAuthor()));
			System.out.print("Rented on: ");
			System.out.println(rr.getRentDate());
			System.out.print("Due to: ");
			System.out.println(rr.getDueDate());
			System.out.println("");
		}

	}

	private void userMenu()
	{
		final int MIN_CHOICE = 1, MAX_CHOICE = 4;
		boolean quit = false;
		while(!quit)
		{
			userMenuInfo();
			int choice = getChoice(MIN_CHOICE, MAX_CHOICE);
			switch(choice)
			{
				case 1: listBooks(); break;
				case 2: rentBook(); break;
				case 3: showRented(); break;
				case 4: u = null; quit = true; break;
			}
		}
	}

	public void start()
	{
		while(!quit)
		{
			startScreenForm();
		}
	}
}
