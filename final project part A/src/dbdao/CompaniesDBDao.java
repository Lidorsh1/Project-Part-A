package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import connection.ConnectionPool;
import dao.CompaniesDao;
import exeptions.couponExeptions;
import Bean.Company;

public class CompaniesDBDao implements CompaniesDao {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	/*
	 * the method checking if the company is exists in the database by email and
	 * password
	 */
	@Override
	public boolean isCompanyExists(String email, String pass)throws couponExeptions {

		String sql = "SELECT * FROM companies WHERE email = ? and password = ?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);){

		stm.setString(1, email);
			stm.setString(2, pass);
			ResultSet rst = stm.executeQuery();
			return rst.next();
		} catch (SQLException e) {
			throw new couponExeptions("- error - isCompanyExists faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * Business method the method checking if the company is exists in the database
	 * by email and name
	 */
	@Override
	public boolean isCompanyExistsByNameEmail(String email, String name)throws couponExeptions {

		Connection con = connectionPool.getConnection();
		boolean Exists = false;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM companies WHERE email = ? and name = ?");
			ps.setString(1, email);
			ps.setString(2, name);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				Exists = (rst.getString("email").equals(email)) && ((rst.getString("name").equals(name)));
			}

			return Exists;
		} catch (SQLException e) {
			throw new couponExeptions("- error - isCompanyExistsByNameEmail -faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * this method gets the company id by existing email 
	 */
	@Override
	public int getCompanyId(String email)throws couponExeptions {

		String sql = "SELECT id from companies WHERE email=?";
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

			throw new couponExeptions("the email not exist");
		} finally {
			connectionPool.restoreConnection(con);

		}
	}

	/*
	 * this method send to the dataBase information on company and save it there
	 */
	@Override
	public void addCompany(Company company)throws couponExeptions {

		String sql = "insert into companies values(0,?,?,?)";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, company.getName());
			stm.setString(2, company.getEmail());
			stm.setString(3, company.getPassword());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("create a new Company failed T_T", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	/*
	 * this method update the information from the dataBase of company
	 */
	@Override
	public void updateCompany(Company company)throws couponExeptions {

		String sql = "update companies set name= ?,  email= ? , password= ? where id = ?";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, company.getName());
			stm.setString(2, company.getEmail());
			stm.setString(3, company.getPassword());
			stm.setInt(4, company.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("update Company faild", e);
		} finally {
			connectionPool.restoreConnection(con);

		}

	}

	/*
	 * Business method this method update the information from the dataBase of
	 * company without update the name
	 */
	@Override
	public void updateCompanyNoName(Company company)throws couponExeptions {

		String sql = "update companies set email= ? , password= ? where id = ?";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, company.getEmail());
			stm.setString(2, company.getPassword());
			stm.setInt(3, company.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("update Company faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * this method deletes the company from the DatabBase by the number of the
	 * company id
	 */
	@Override
	public void deleteCompany(int companyId)throws couponExeptions {

		String sql = "delete from companies where id = ?";
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, companyId);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("delete Company faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * this method gets the list of all the companies from the dataBase and print is
	 * information
	 */
	@Override
	public ArrayList<Company> getAllCompanies()throws couponExeptions {
		String sql = "Select * from companies";
		ArrayList<Company> companies = null;
		Connection con = connectionPool.getConnection();
		try {
			Statement stm = con.createStatement();
			ResultSet rst = stm.executeQuery(sql);
			companies = new ArrayList<>();
			while (rst.next()) {
				Company company = new Company();
				company.setId(rst.getInt("id"));
				company.setName(rst.getString("name"));
				company.setEmail(rst.getString("email"));
				company.setPassword(rst.getString("password"));
				companies.add(company);
			}
			return companies;
		} catch (SQLException e) {
			throw new couponExeptions("getAllCompanies faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	/*
	 * this method gets the specific company by is id from the dataBase and print is
	 * information
	 */
	@Override
	public Company getOneCompany(int companyId) throws couponExeptions{
		String sql = "select * from companies where id= ?";
		try {
			Connection con = connectionPool.getConnection();
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, companyId);
			ResultSet rSet = stm.executeQuery();
			if (rSet.next()) {
				Company company = new Company();
				company.setId(companyId);
				company.setName(rSet.getString("Name"));
				company.setEmail(rSet.getString("email"));
				company.setPassword(rSet.getString("password"));
				connectionPool.restoreConnection(con);
				return company;
			} else {
				throw new couponExeptions("finding Company with id " + companyId + "failed - not found");
			}
		} catch (SQLException e) {
			throw new couponExeptions("getOneCompany Failed", e);
		}

	}

}
