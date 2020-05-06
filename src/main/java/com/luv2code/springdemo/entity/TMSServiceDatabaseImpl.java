package com.luv2code.springdemo.entity;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.luv2code.springdemo.entity.tms.error.DuplicateCategoryException;
import com.luv2code.springdemo.entity.tms.error.InvalidCategoryException;
import com.luv2code.springdemo.entity.tms.error.InvalidDataException;
import com.luv2code.springdemo.entity.tms.error.InvalidDateException;
import com.luv2code.springdemo.entity.tms.error.InvalidPaymentmethodException;
import com.luv2code.springdemo.entity.tms.error.MissingArgumentException;
import com.luv2code.springdemo.entity.tms.error.NegativAmountException;
import com.luv2code.springdemo.entity.tms.error.NullAmountException;

public class TMSServiceDatabaseImpl implements TMSService {
	private Connection connection;
	private String databaseName;
	private String userName;
	private String password;
	private PreparedStatement pstmt;
	private ResultSet result;

	public TMSServiceDatabaseImpl() throws ClassNotFoundException, SQLException, IOException {

//		DataInputStream reader = new DataInputStream(new FileInputStream("configFile.txt"));
//		this.databaseName = reader.readLine();
//		this.userName = reader.readLine();
//		this.password = reader.readLine();

		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "tms", "root", "deema");
	}

	@Override
	public Category getCategory(int id) throws SQLException {
		try {
			String sqlStatment = "select dkey,value,icon from DictionaryEntries where id=? and enable='1'";
			this.pstmt = this.connection.prepareStatement(sqlStatment);
			this.pstmt.setInt(1, id);

			ResultSet categortData = this.pstmt.executeQuery();
			categortData.next();

			return new Category(id, categortData.getInt("dkey"), categortData.getString("value"),
					categortData.getString("icon"));
		} catch (Exception e) {

			return null;
		}

	}

	@Override
	public List<TransactionBase> getTransatcions(TransactionFilters filters) throws Exception {
		String sqlStatment = "";
		if (filters.getType() == null) {
			// get all

			sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,COALESCE(d.id,0) as \"paymentMethod\",COALESCE(t.comment,'no comment') as \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\" \n"
					+ "FROM Transaction as t\n" + "LEFT JOIN Expense as e\n" + "ON t.id=e.id\n"
					+ "LEFT JOIN Income as i\n" + "ON t.id=i.id\n" + "LEFT JOIN DictionaryEntries as d\n"
					+ "ON e.paymentMethod=d.id\n";

		} else if (filters.getType() == 15) {
			// get Income

			sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,COALESCE(t.comment,'no comment') as \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\"\n"
					+ "FROM Transaction as t\n" + "INNER JOIN Income as i\n" + "ON t.id=i.id\n";

		} else if (filters.getType() == 16) {
			// get Expense

			sqlStatment = "SELECT t.id,t.type,t.amount,t.date,t.category,d.id as \"paymentMethod\",COALESCE(t.comment,'no comment') \"comment\",t.id in(select id from FrequentTransaction) as \"frequent\"\n"
					+ "FROM Transaction as t\n" + "INNER JOIN Expense as e\n" + "ON t.id=e.id\n"
					+ "INNER JOIN DictionaryEntries as d\n" + "ON e.paymentMethod=d.id\n";
		}

		if (filters.getFrom() != null && filters.getTo() != null) {
			sqlStatment += " WHERE t.date>=DATE(\"" + filters.getFrom() + "\") and t.date<=DATE(\"" + filters.getTo()
					+ "\")";
		} else if (filters.getFrom() != null) {
			sqlStatment += " WHERE t.date>=DATE(\"" + filters.getFrom() + "\")";
		} else if (filters.getTo() != null) {
			sqlStatment += " WHERE t.date<=DATE(\"" + filters.getTo() + "\")";
		}

		if (filters.getcategoryId() != null) {
			if (filters.getFrom() != null || filters.getTo() != null) {
				sqlStatment += " AND t.category=" + filters.getcategoryId();
			} else {
				sqlStatment += " WHERE t.category=" + filters.getcategoryId();
			}
		}
		sqlStatment += " ORDER BY t.date";
		List<TransactionBase> transactions = new ArrayList<TransactionBase>();
		this.pstmt = this.connection.prepareStatement(sqlStatment);
		this.result = this.pstmt.executeQuery();
		while (this.result.next()) {
			Category category = getCategory(this.result.getInt("category"));
			// System.out.println(this.result.getInt("category"));
			TransactionArgument transactionArgument;

			if (this.result.getInt("type") == 15) {
				transactionArgument = new TransactionArgument(this.result.getInt("id"), this.result.getInt("type"),
						this.result.getDouble("amount"), this.result.getInt("category"),
						this.result.getString("comment"), this.result.getDate("date").toLocalDate(), 0, null);
				TransactionBase income = Factory.createTransaction("Income", transactionArgument);

				transactions.add(income);
			} else if (this.result.getInt("type") == 16) {
				transactionArgument = new TransactionArgument(this.result.getInt("id"), this.result.getInt("type"),
						this.result.getDouble("amount"), this.result.getInt("category"),
						this.result.getString("comment"), this.result.getDate("date").toLocalDate(),
						this.result.getInt("paymentMethod"), null);
				TransactionBase expense = Factory.createTransaction("Expense", transactionArgument);
				transactions.add(expense);
			}
		}

		return transactions;
	}

	@Override
	public List<Category> getCategories(Integer type) throws SQLException {
		// type===dkey income if dkey=1 expense if dkey=2 String
		if (type == null) {
			throw new MissingArgumentException("Error : category type can't be null!! ");
		}
		 if (type ==0) {
         	String sqlStatment = "select * from DictionaryEntries where  enable=true";
 			this.pstmt = this.connection.prepareStatement(sqlStatment);
 			
 			this.result = this.pstmt.executeQuery();
 			List<Category> category = new ArrayList<Category>();
 			while (this.result.next()) {
 				category.add(new Category(this.result.getInt("id"), this.result.getInt("dkey"),
 						this.result.getString("value"), this.result.getString("icon")));
 			}
 			return category;
         }
		PreparedStatement ps;
		ResultSet r;
		ps = connection.prepareStatement("select exists (select dkey from dictionaryentries where " + type
				+ " in (select dkey from dictionaryentries))");
		r = ps.executeQuery();
		r.next();
		if (r.getBoolean(1) == false)
			throw new InvalidDataException("Error:category type Not Found!!!");
		else {
           
			String sqlStatment = "select * from DictionaryEntries where dkey=? and enable=true";
			this.pstmt = this.connection.prepareStatement(sqlStatment);
			this.pstmt.setInt(1, type);
			this.result = this.pstmt.executeQuery();
			List<Category> category = new ArrayList<Category>();
			while (this.result.next()) {
				category.add(new Category(this.result.getInt("id"), this.result.getInt("dkey"),
						this.result.getString("value"), this.result.getString("icon")));
			}
			return category;
		}
	}

	@Override
	public void addIncome(Income income) throws SQLException {

		String addTransactionQuery = "insert into transaction (type, amount, category, comment, date)"
				+ "values (?, ?, ?, ?, ?)";

		pstmt = connection.prepareStatement(addTransactionQuery);
		pstmt.setInt(1, income.getType()); // 15
		pstmt.setDouble(2, income.getAmount());
		pstmt.setInt(3, income.getIcategory()); // category in tran table is int //id or dkey
		pstmt.setString(4, income.getComment());
		pstmt.setDate(5, Date.valueOf(income.getDate()));
		pstmt.executeUpdate();

		int transId = 0;
		String idQuery = "select transaction.id from transaction \r\n"
				+ "  where type=15 AND transaction.id NOT IN (select income.id from income)";
		pstmt = connection.prepareStatement(idQuery);
		result = pstmt.executeQuery(idQuery);

		while (result.next()) {
			System.out.println("id= " + result.getInt(1));
			transId = result.getInt(1);
			System.out.println(transId);
		}

		String addIncomeQuery = "insert into income (id)  \r\n" + " values( ? ) ";
		pstmt = connection.prepareStatement(addIncomeQuery);
		pstmt.setInt(1, transId);
		pstmt.executeUpdate();

		if (income instanceof FrequentIncome) {

			String addIncomeFrequentQuery = " insert into frequenttransaction (id, monthFrequent) \r\n"
					+ "  values (?, ?)";

			pstmt = connection.prepareStatement(addIncomeFrequentQuery);
			pstmt.setInt(1, transId);
			pstmt.setInt(2, ((FrequentIncome) income).getMonthFrequent());

			pstmt.executeUpdate();
		}
	}

	@Override
	public void addExpense(Expense expense) throws SQLException, InvalidCategoryException, InvalidDataException {
		String chekCategory = "select id from dictionaryentries where dkey=2";
		pstmt = connection.prepareStatement(chekCategory);
		ResultSet r = pstmt.executeQuery(chekCategory);
		int cid = 0;
		boolean flagcategory = false, flagdate = true;
		while (r.next()) {
			cid = r.getInt(1);
			if (expense.getIcategory() == cid)
				flagcategory = true;
			// System.out.println(cid);
		}
		if (expense.getDate().compareTo(LocalDate.now()) > 0) {
			throw new InvalidDateException("Date You inserted in future");
		}
		if (expense.getAmount() == 0) {
			throw new NullAmountException("You try to insert 0 amount value!");
		}
		if (expense.getPymentMethod() != 13 && expense.getPymentMethod() != 14) {
			throw new InvalidPaymentmethodException("wrong paymant method!\n13:cash,14:visa");
		}
		if (expense.getAmount() < 0) {
			throw new NegativAmountException("You try to insert negative amount value!");
		}
		if (flagcategory == true) {
			String addTransactionQuery = "insert into Transaction (type, amount, category, comment, date) values (?, ?, ?, ?, ?) ";
			// System.out.println(expense.getIcategory());
			pstmt = connection.prepareStatement(addTransactionQuery);
			pstmt.setInt(1, expense.getType());
			pstmt.setDouble(2, -1 * expense.getAmount());
			pstmt.setInt(3, expense.getIcategory());
			pstmt.setString(4, expense.getComment());
			pstmt.setDate(5, Date.valueOf(expense.getDate()));

			pstmt.executeUpdate();
			// System.out.println("executeAdd to transaction");

			String query = "select Transaction.id \r\n" + "		from Transaction\r\n"
					+ "		where type=16 AND Transaction.id NOT IN (select Expense.id from Expense)";

			pstmt = connection.prepareStatement(query);
			r = pstmt.executeQuery(query);
			int id = 0;
			while (r.next()) {
				id = r.getInt(1);
			}
			// System.out.println("id :" + id);
			String addExpenseQuery = "insert into Expense (id,paymentMethod) values (?,?)";

			pstmt = connection.prepareStatement(addExpenseQuery);
			pstmt.setInt(1, id);
			pstmt.setDouble(2, expense.getPymentMethod());

			pstmt.executeUpdate();
			System.out.println("Your Transaction Successfully Add to Expense");
			if (expense instanceof FrequentExpense)

			{

				String addFrequantQuery = "insert into FrequentTransaction (id, monthFrequent) values (?,?)";

				pstmt = connection.prepareStatement(addFrequantQuery);
				pstmt.setInt(1, 0);
				pstmt.setInt(2, ((FrequentExpense) expense).getMonthFrequent());
				pstmt.executeUpdate();

			}
		} else if (flagcategory == false) {
			throw new InvalidCategoryException("Change category to Expense category");
		}
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		if (category.getValue() == null) {
			throw new MissingArgumentException("Error:Argument 'value' cannot be null");
		} else {
			pstmt = connection.prepareStatement(
					"SELECT EXISTS(SELECT * from DictionaryEntries WHERE dkey=? and value=? and enable='1')");
			pstmt.setInt(1, category.getDkey());
			pstmt.setString(2, category.getValue());
			result = pstmt.executeQuery();
			result.next();
			if (result.getBoolean(1) == false) {// not repeated
				pstmt = connection.prepareStatement(
						"select exists (SELECT dd.dkey from dictionaryentries as dd where ? in(SELECT d.dkey from dictionary as d))");
				pstmt.setInt(1, category.getDkey());
				result = pstmt.executeQuery();
				result.next();
				if (result.getBoolean(1) == true) {
					pstmt = connection.prepareStatement("INSERT INTO DictionaryEntries(dkey,value,icon) VALUES(?,?,?)");

					pstmt.setInt(1, category.getDkey());
					pstmt.setString(2, category.getValue());
					pstmt.setString(3, category.getIconPath());
					pstmt.executeUpdate();
				} else {
					// Type not in the dkey of the dictionary table ;
					throw new InvalidDataException(
							"Try again!! You Entered Invalid Category Type . The value Should be in this rang {1,2,3,4}");
				}
			} else { // the category already exists (repeatred!!)
				throw new DuplicateCategoryException("Error:This category is already Found!!");
			}
		}
		System.out.println("*****Successful*****");
	}

	@Override
	public void removeCategory(Integer id) throws SQLException {

		String removeCategoryQuery = "update  DictionaryEntries set enable = '0' where id = ? ";
		pstmt = connection.prepareStatement(removeCategoryQuery);
		pstmt.setInt(1, id);
		pstmt.executeUpdate();

	}

	@Override
	public void updateCategory(Category category) throws SQLException {

		PreparedStatement ps;
		ResultSet r;
		if (category.getId() == null) {
			throw new MissingArgumentException("Error:Argument 'id' cannot be null");
		}

		if (category.getValue() == null && category.getIconPath() == null && category.getDkey() == null) {
			throw new MissingArgumentException("Error:Arguments 'value' 'icon' 'type' cannot be  all null togather !!");
		}

		if (category.getValue() == null) {
			ps = connection.prepareStatement("select value from dictionaryentries where id=" + category.getId());
			r = ps.executeQuery();
			r.next();

			if (r.getString(1) == null)
				throw new MissingArgumentException("Error:'Value' can't be null");
		}

		if (category.getDkey() == null)// if user doesn't provide dkey //just update based on id
		{
			ps = connection.prepareStatement("select dkey from dictionaryentries where id =" + category.getId());
			r = ps.executeQuery();
			r.next();
			category.setDkey(r.getInt(1));
		}
		if (category.getValue() == null)// if user doesn't provide dkey just update based on id
		{
			ps = connection.prepareStatement("select value from dictionaryentries where id =" + category.getId());
			r = ps.executeQuery();
			r.next();
			category.setValue(r.getString(1));
		}
		pstmt = connection.prepareStatement(
				"select exists (SELECT id from dictionaryentries where ? in(SELECT id from dictionaryentries ))");
		pstmt.setInt(1, category.getId());
		result = pstmt.executeQuery();
		result.next();
		if (result.getBoolean(1) == true) {
			pstmt = connection.prepareStatement(
					"select exists (SELECT dd.dkey from dictionaryentries as dd where ? in(SELECT d.dkey from dictionary as d))");
			pstmt.setInt(1, category.getDkey());
			result = pstmt.executeQuery();
			result.next();
			if (result.getBoolean(1) == true) {
				pstmt = connection.prepareStatement(
						"SELECT EXISTS(SELECT * from DictionaryEntries WHERE dkey=? and value=? and icon= ?)");
				pstmt.setInt(1, category.getDkey());
				pstmt.setString(2, category.getValue());
				pstmt.setString(3, category.getIconPath());
				result = pstmt.executeQuery();
				result.next();
				if (result.getBoolean(1) == false) {
					pstmt = connection.prepareStatement(
							"UPDATE  DictionaryEntries set dkey=?,value=?,icon=? where id=? and enable='1'");

					pstmt.setInt(1, category.getDkey());
					pstmt.setString(2, category.getValue());
					pstmt.setString(3, category.getIconPath());
					pstmt.setInt(4, category.getId());

					pstmt.executeUpdate();
				} else {
					throw new DuplicateCategoryException(
							"Error:This category is already Found!!,your updated values is repeated");
				}
			} else {
				// Type not in the dkey of the dictionary table ;
				throw new InvalidDataException(
						"Try again!! You Entered Invalid Category Type . The value Should be in this rang {1,2,3,4}");
			}

		} else {
			// id notfound
			throw new InvalidDataException("Error:id not found");
		}
		System.out.println("*****Successful*****");
	}

	@Override
	public double getBalance(TransactionFilters filters) throws SQLException {
		String sqlStatment = "";
		String s = "";
		PreparedStatement ps;
		ResultSet r;

		ResultSet total = null;
		if (filters.getType() == null) {
			// get all

			sqlStatment = ("SELECT sum(amount) from transaction as t");

		} else if (filters.getType() == 15) {
			// get Income

			sqlStatment = ("SELECT sum(amount) from transaction as t  where  t.type=15");

		} else if (filters.getType() == 16) {
			// get Expense

			sqlStatment = ("SELECT sum(amount) from transaction as t  where  t.type=16");

		} else
			throw new InvalidDataException("Error:Invalid type!! Hint: valid values of type are {15,16}");

		if (filters.getcategoryId() != null) {
			ps = connection.prepareStatement("select exists (select category from transaction where "
					+ filters.getcategoryId() + " in (select category  from transaction))");

			r = ps.executeQuery();
			r.next();

			if (r.getBoolean(1) == false) {
				throw new InvalidDataException("There is NO transaction of this category!!!");
			}
			else {
				if (filters.getType() != null)
					sqlStatment += " and t.category=" + filters.getcategoryId();
				else
					sqlStatment += " where t.category=" + filters.getcategoryId();
			}
		}

		if (filters.getFrom() != null) {
			if (filters.getFrom().compareTo(LocalDate.now()) > 0)
				throw new InvalidDateException("Erorr:Term date cannot be in the future!!!!");
		}
		if (filters.getTo() != null) {
			if (filters.getTo().compareTo(LocalDate.now()) > 0)
				throw new InvalidDateException("Erorr:Term date cannot be in the future!!!!");
		}

		if (filters.getFrom() != null && filters.getTo() != null) {
			if (filters.getFrom().compareTo(filters.getTo()) > 0)
				throw new InvalidDateException("Error:Start-date 'From' greater than End-date 'To' !!");
			if (filters.getFrom().compareTo(LocalDate.now()) > 0 || filters.getTo().compareTo(LocalDate.now()) > 0) {
				// dates > current date
				throw new InvalidDateException("Erorr:Term date cannot be in the future!!!!");
			}
		}

		if (filters.getFrom() != null && filters.getTo() != null
				&& (filters.getType() != null || filters.getcategoryId() != null)) {
			{
				sqlStatment += " and t.date>=DATE(\"" + filters.getFrom() + "\") and t.date<=DATE(\"" + filters.getTo()
						+ "\")";
			}
		} else if (filters.getFrom() != null && (filters.getType() != null || filters.getcategoryId() != null)) {
			sqlStatment += " and t.date>=DATE(\"" + filters.getFrom() + "\")";
		} else if (filters.getTo() != null && (filters.getType() != null || filters.getcategoryId() != null)) {
			sqlStatment += " and t.date<=DATE(\"" + filters.getTo() + "\")";
		} else if (filters.getFrom() != null && (filters.getType() == null || filters.getcategoryId() == null))
			sqlStatment += " where t.date>=DATE(\"" + filters.getFrom() + "\")";
		else if (filters.getTo() != null && (filters.getType() == null || filters.getcategoryId() == null))
			sqlStatment += " where t.date<=DATE(\"" + filters.getTo() + "\")";
		this.pstmt = this.connection.prepareStatement(sqlStatment);

		// System.out.println(sqlStatment);
		total = this.pstmt.executeQuery();
		if (total.next())
			return total.getDouble(1);
		return 0;
	}
}
