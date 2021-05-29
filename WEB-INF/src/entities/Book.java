package entities;

public class Book
{
	String name, author, genre;
	int available, id;

	public Book(int id, String name, String author, String genre, int available)
	{
		this.id = id;
		this.name = name;
		this.author = author;
		this.genre = genre;
		this.available = available;
	}

	public Book(String name, String author, String genre, int available)
	{
		this.name = name;
		this.author = author;
		this.genre = genre;
		this.available = available;
	}

	@Override
	public String toString()
	{
		String fmt = "Id: %d\n%s by %s\nGenre: %s\nAvailable: %d\n";
		return String.format(fmt, id, name, author, genre, available);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public int getAvailable()
	{
		return available;
	}

	public void setAvailable(int available)
	{
		this.available = available;
	}

	public void decrementAvailable()
	{
		available--;
	}

	public void incrementAvailable()
	{
		available++;
	}

	public int getId()
	{
		return id;
	}

}
