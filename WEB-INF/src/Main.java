import session.Session;
import entities.*;
import dao.*;

import java.util.List;
import java.sql.*;

public class Main
{
	public static void main(String[] args)
	{

		BookDao bookHandler = new BookDao();
		UserDao userHandler = new UserDao();
		RentRecordDao rr_handler = new RentRecordDao();

		List<RentRecord> rrs = rr_handler.getAll();
		for(RentRecord rr : rrs)
		{
			System.out.println(rr);
		}
		System.out.print("input id of an item you want to delete: ");
		String rr_id = System.console().readLine();
		RentRecord rrc = rr_handler.get(Integer.parseInt(rr_id));
		rr_handler.delete(rrc);
		rrs = rr_handler.getAll();
		for(RentRecord rr : rrs)
		{
			System.out.println(rr);
		}

	}
}
