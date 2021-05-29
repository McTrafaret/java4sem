package entities;

public enum Roles
{
	admin(1),
	librarian(2),
	user(3);

	private final int value;

	private Roles(int v)
	{
		this.value = v;
	}

	public int ToInt()
	{
		return value;
	}

	public static Roles RoleFromInt(int v)
	{
		Roles role = null;
		switch(v)
		{
			case 1: role = admin; break;
			case 2: role = librarian; break;
			case 3: role = user; break;
		}
		return role;
	}
};
