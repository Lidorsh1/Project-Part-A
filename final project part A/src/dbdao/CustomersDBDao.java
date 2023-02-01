package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectionPool;
import dao.CustomersDao;
import exeptions.couponExeptions;
import Bean.Customer;

public class CustomersDBDao implements CustomersDao {
	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	/*
	 * this method check if the customer is Exists by email and password
	 */
	@Override
	public boolean isCustomerExists(String email, String pass) throws couponExeptions {
		boolean Exists = false;
		String sql = "SELECT * FROM customers WHERE email = ? and password = ?";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, pass);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				Exists = (rst.getString("email").equals(email)) && ((rst.getString("password").equals(pass)));
			}
			return Exists;
		} catch (SQLException e) {
			throw new couponExeptions("isCustomerExists faild", e);
		} finally {

			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * Business method this method check if the customer is Exists by email only
	 */
	@Override
	public boolean isCustomerExistsByEmail(String email)throws couponExeptions {

		boolean Exists = false;
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM customers WHERE email = ?");
			ps.setString(1, email);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				Exists = (rst.getString("email").equals(email));
			}
			return Exists;
		} catch (SQLException e) {
			throw new couponExeptions("isCustomerExistsByEmail faild", e);
		} finally {

			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * this method adding values of customer to the DataBase
	 */
	@Override
	public void addCustomer(Customer customer)throws couponExeptions {
		String sql = "insert into customers values(0,?,?,?,?)";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, customer.getFirstName());
			stm.setString(2, customer.getLastName());
			stm.setString(3, customer.getEmail());
			stm.setString(4, customer.getPassword());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("create a new Customer failed T_T", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * this method update the customers in the DataBase
	 */
	@Override
	public void updateCustomer(Customer customer)throws couponExeptions {

		String sql = "update customers set firstName= ? , lastName= ? , email= ?,password= ? where id = ?";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, customer.getFirstName());
			stm.setString(2, customer.getLastName());
			stm.setString(3, customer.getEmail());
			stm.setString(4, customer.getPassword());
			stm.setInt(5, customer.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("update Customer faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * this method delete a customer from the the DataBase by customer id
	 */
	@Override
	public void deleteCustomer(int customerId)throws couponExeptions {
		String sql = "delete from customers where id = ?";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, customerId);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("delete Customer faild", e);
		} finally {
			connectionPool.restoreConnection(con);

		}
	}

	/*
	 * this method print a list of all the customers in the DataBase
	 */
	@Override
	public ArrayList<Customer> getAllCustomeres() throws couponExeptions {
		String sql = "Select * from customers";
		ArrayList<Customer> customers = null;
		Connection con = connectionPool.getConnection();
		try {
			Statement stm = con.createStatement();
			ResultSet rst = stm.executeQuery(sql);
			customers = new ArrayList<>();
			while (rst.next()) {
				customers.add(new Customer(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
						rst.getString(5), null));
			}

			return customers;
		} catch (SQLException e) {

			throw new couponExeptions("getAllCustomeres faild", e);
		} finally {
			connectionPool.restoreConnection(con);

		}
	}

	/*
	 * this method print a one customer and is information by the id from the
	 * DataBase
	 */
	@Override
	public Customer getOneCustomer(int customerId) throws couponExeptions{
		String sql = "select * from customers where id= ?";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, customerId);
			ResultSet rSet = stm.executeQuery();
			if (rSet.next()) {
				Customer customer = new Customer();
				customer.setId(customerId);
				customer.setFirstName(rSet.getString("firstName"));
				customer.setLastName(rSet.getString("lastName"));
				customer.setEmail(rSet.getString("email"));
				customer.setPassword(rSet.getString("password"));
				return customer;
			} else {
				throw new couponExeptions("finding Customer with id " + customerId + "failed - not found");
			}

		} catch (SQLException e) {
			throw new couponExeptions("getOneCustomer Failed", e);
		} finally {
			connectionPool.restoreConnection(con);

		}
	}

	/*
	 * Business method this method delete the purchase history of a customer by is
	 * id
	 */
	@Override
	public void deleteCustomersPurchaseId(int customerId) throws couponExeptions{

		String sql = "delete from customers_vs_coupons where customerId = ?";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, customerId);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("delete Coupon Purchase faild", e);
		} finally {
			connectionPool.restoreConnection(con);

		}
	}

	/*
	 * Business method this method returns the customer id if is email is exists
	 */
	@Override
	public int getCustomerId(String email) throws couponExeptions{

		String sql = "SELECT id from customers WHERE email=?";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rst = ps.executeQuery();
			if (rst.next()) {
				int id = rst.getInt("id");
				return id;

			} else {
				throw new couponExeptions("error");
			}
		} catch (SQLException e) {

			throw new couponExeptions("-error- get customer faild ", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}
}
