package dbdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connection.ConnectionPool;
import dao.CouponsDao;
import exeptions.couponExeptions;
import Bean.Category;
import Bean.Coupon;

public class CouponsDBDao implements CouponsDao {
	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	/*
	 * a method that add coupons to the data base
	 */
	@Override
	public void addCoupon(Coupon coupon)throws couponExeptions {
		String sql = "insert into coupons values(0,?,?,?,?,?,?,?,?,?)";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, coupon.getCompanyId());
			stm.setInt(2, coupon.getCategory().getId());
			stm.setString(3, coupon.getTitle());
			stm.setString(4, coupon.getDescription());
			stm.setDate(5, Date.valueOf(coupon.getStartDate()));
			stm.setDate(6, Date.valueOf(coupon.getEndDate()));
			stm.setInt(7, coupon.getAmount());
			stm.setDouble(8, coupon.getPrice());
			stm.setString(9, coupon.getImage());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("create a new Coupon failed (T_T)", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * a method that update coupon from the data base
	 */
	@Override
	public void updateCoupon(Coupon coupon)throws couponExeptions {
		String sql = "update COUPONS set companyId= ? ,category= ? ,title= ? ,description= ? ,startDate= ? "
				+ ",endDate=? ,amount=? ,price=? ,image=? where id =? ";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, coupon.getCompanyId());
			stm.setInt(2, coupon.getCategory().getId());
			stm.setString(3, coupon.getTitle());
			stm.setString(4, coupon.getDescription());
			stm.setDate(5, Date.valueOf(coupon.getStartDate()));
			stm.setDate(6, Date.valueOf(coupon.getEndDate()));
			stm.setInt(7, coupon.getAmount());
			stm.setDouble(8, coupon.getPrice());
			stm.setString(9, coupon.getImage());
			stm.setInt(10, coupon.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("update Coupon faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * a method that update coupon from the data base and not allow to update the
	 * companyId and the coupons id
	 */
	@Override
	public void updateCouponLogic(Coupon coupon)throws couponExeptions {
		String sql = "update COUPONS set category= ? " + ",title= ? ,description= ? ,startDate= ? "
				+ ",endDate=? ,amount=? ,price=? ,image=? where id =? ";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, coupon.getCategory().getId());
			stm.setString(2, coupon.getTitle());
			stm.setString(3, coupon.getDescription());
			stm.setDate(4, Date.valueOf(coupon.getStartDate()));
			stm.setDate(5, Date.valueOf(coupon.getEndDate()));
			stm.setInt(6, coupon.getAmount());
			stm.setDouble(7, coupon.getPrice());
			stm.setString(8, coupon.getImage());
			stm.setInt(9, coupon.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("update Coupon faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * a method that delete the coupon from the data base by the couponId
	 */
	@Override
	public void deleteCoupon(int couponId) throws couponExeptions{

		String sql = "delete from COUPONS where id = ?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, couponId);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("delete Coupon faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * a method that delete the coupon from the data base by the companyId
	 */
	@Override
	public void deleteCouponCom(int companyId) throws couponExeptions{

		String sql = "delete from COUPONS where companyId = ?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, companyId);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("delete Coupon faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * a method that gets all the coupons from the data base and print it
	 */
	@Override
	public ArrayList<Coupon> getAllCoupons()throws couponExeptions {
		String sql = "Select * from coupons";
		ArrayList<Coupon> coupons = null;
		Connection con = connectionPool.getConnection();
		try (Statement stm = con.createStatement()) {
			ResultSet rst = stm.executeQuery(sql);
			coupons = new ArrayList<>();
			while (rst.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rst.getInt("id"));
				coupon.setCompanyId(rst.getInt("companyId"));
				coupon.setCategory(Category.valueOf(rst.getString("category")));
				coupon.setTitle(rst.getString("title"));
				coupon.setDescription(rst.getString("description"));
				coupon.setStartDate(rst.getDate("startDate").toLocalDate());
				coupon.setEndDate(rst.getDate("endDate").toLocalDate());
				coupon.setAmount(rst.getInt("amount"));
				coupon.setPrice(rst.getDouble("price"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {

			throw new couponExeptions("getAllCompanies faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * a method that gets all the coupons from the data base by the companyId
	 */
	@Override
	public ArrayList<Coupon> getAllCouponsLogicID(int companyId) throws couponExeptions{
		String sql = "Select * from coupons where companyId =" + companyId;
		ArrayList<Coupon> coupons = null;
		Connection con = connectionPool.getConnection();
		try (Statement stm = con.createStatement()) {
			ResultSet rst = stm.executeQuery(sql);
			coupons = new ArrayList<>();
			while (rst.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rst.getInt("id"));
				coupon.setCompanyId(rst.getInt("companyId"));
				coupon.setCategory(Category.valueOf(rst.getString("category")));
				coupon.setTitle(rst.getString("title"));
				coupon.setDescription(rst.getString("description"));
				coupon.setStartDate(rst.getDate("startDate").toLocalDate());
				coupon.setEndDate(rst.getDate("endDate").toLocalDate());
				coupon.setAmount(rst.getInt("amount"));
				coupon.setPrice(rst.getDouble("price"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new couponExeptions("getAllCompanies faild", e);
		} finally {

			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * a method that gets all the coupons from the data base by the category
	 */
	@Override
	public ArrayList<Coupon> getAllCouponsLogicCat(Category category)throws couponExeptions {
		String sql = "Select * from coupons where category = ?";
		ArrayList<Coupon> coupons = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, category.getId());
			ResultSet rst = stm.executeQuery();
			coupons = new ArrayList<>();
			while (rst.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rst.getInt("id"));
				coupon.setCompanyId(rst.getInt("companyId"));
				coupon.setCategory(Category.valueOf(rst.getString("category")));
				coupon.setTitle(rst.getString("title"));
				coupon.setDescription(rst.getString("description"));
				coupon.setStartDate(rst.getDate("startDate").toLocalDate());
				coupon.setEndDate(rst.getDate("endDate").toLocalDate());
				coupon.setAmount(rst.getInt("amount"));
				coupon.setPrice(rst.getDouble("price"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {

			throw new couponExeptions("getAllCompanies faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * a method that gets one coupon from the data base by is companyId
	 */
	@Override
	public Coupon getOneCoupon(int couponId)throws couponExeptions {

		String sql = "select * from COUPONS where id= ?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, couponId);
			ResultSet rSet = stm.executeQuery();
			if (rSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(couponId);
				coupon.setCompanyId(rSet.getInt("companyId"));
				coupon.setCategory(Category.valueOf(rSet.getString("category")));
				coupon.setTitle(rSet.getString("title"));
				coupon.setDescription(rSet.getString("description"));
				coupon.setStartDate(rSet.getDate("startDate").toLocalDate());
				coupon.setEndDate(rSet.getDate("endDate").toLocalDate());
				coupon.setAmount(rSet.getInt("amount"));
				coupon.setPrice(rSet.getDouble("price"));
				coupon.setImage(rSet.getString("image"));
				return coupon;
			} else {
				throw new couponExeptions("getOneCoupon fail-not found");
			}
		} catch (SQLException e) {
			throw new couponExeptions("get One Coupon failed", e);
		} finally {
			connectionPool.restoreConnection(con);

		}
	}

	/*
	 * a method thats add customerId and couponId to customerVScoupon in the data
	 * base
	 */
	@Override
	public void addCouponPurchase(int customerId, int couponId)throws couponExeptions {

		String sql = "insert into customers_vs_coupons values (?,?)";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, customerId);
			stm.setInt(2, couponId);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("add Coupon Purchase faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	/**
	 * a method thats drop the amount by 1 by the coupon id
	 */
	@Override
	public void amountdrop(int couponid)throws couponExeptions {
		String sql = "update coupons set amount = amount-1 where id= ?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, couponid);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("the amount didnt drop - error-");
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	/**
	 * a method that delete the customerId and couponId from customerVScoupon
	 */
	@Override
	public void deleteCouponPurchase(int customerId, int couponId) throws couponExeptions{

		String sql = "delete from customers_vs_coupons where customerId= ? and couponId= ?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, customerId);
			stm.setInt(2, couponId);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("delete Coupon Purchase faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	/*
	 * method that delete the coupon purchase by couponId only
	 */
	@Override
	public void deleteCouponPurchaseCouponId(int couponId)throws couponExeptions {

		String sql = "delete from customers_vs_coupons where couponId= ?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, couponId);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("delete Coupon Purchase faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	/*
	 * method that delete the coupon purchase by companyId only
	 */
	@Override
	public void deleteCouponPurchaseId(int companyId)throws couponExeptions {

		String sql = "delete from customers_vs_coupons where couponId in (select id from coupons where companyId =?)";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setInt(1, companyId);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new couponExeptions("delete Coupon Purchase faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	/*
	 * a method that checks if a coupon has this title already in the data base
	 */
	@Override
	public boolean isTitleExists(String title)throws couponExeptions {

		Connection con = connectionPool.getConnection();
		boolean Exists = false;
		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM coupons WHERE title = ? ")) {
			ps.setString(1, title);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				Exists = (rst.getString("title").equals(title));
			}
			return Exists;
		} catch (SQLException e) {
			Exists = false;
			throw new couponExeptions(" -ERROR-  ");
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	/*
	 * a method that checks if a coupon is exists in the CouponPurchase
	 */
	@Override
	public boolean isCouponExists(int customerId, int couponId)throws couponExeptions {

		String sql = "select * from customers_vs_coupons where customerId= ? and couponId= ?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {

			stm.setInt(1, customerId);
			stm.setInt(2, couponId);
			ResultSet rSet = stm.executeQuery();
			return rSet.next();
		} catch (SQLException e) {
			throw new couponExeptions("isCouponExists faild", e);
		} finally {
			connectionPool.restoreConnection(con);

		}

	}

	/**
	 * this method gets all the coupons with endDate that pass the date of now
	 */
	@Override
	public ArrayList<Coupon> getAllExpiredCoupon()throws couponExeptions {
		String sql = "select * from coupons where endDate <=?";
		ArrayList<Coupon> coupons = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setDate(1, Date.valueOf(LocalDate.now()));
			ResultSet rst = stm.executeQuery();
			coupons = new ArrayList<>();
			while (rst.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rst.getInt("id"));
				coupon.setCompanyId(rst.getInt("companyId"));
				coupon.setCategory(Category.valueOf(rst.getString("category")));
				coupon.setTitle(rst.getString("title"));
				coupon.setDescription(rst.getString("description"));
				coupon.setStartDate(rst.getDate("startDate").toLocalDate());
				coupon.setEndDate(rst.getDate("endDate").toLocalDate());
				coupon.setAmount(rst.getInt("amount"));
				coupon.setPrice(rst.getDouble("price"));
				coupon.setImage(rst.getString("image"));
				coupons.add(coupon);
			}
			return coupons;

		} catch (SQLException e) {
			throw new couponExeptions("delete All Expired Coupon faild", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	/**
	 * a method that gets all the coupons by a max price that limit what it brings
	 */
	@Override
	public ArrayList<Coupon> getAllCoponsByMaxPrice(double MaxPrice)throws couponExeptions {
		String sql = "select * from coupons where price <= ?";
		ArrayList<Coupon> coupons = null;
		Connection con = connectionPool.getConnection();
		try (PreparedStatement stm = con.prepareStatement(sql);) {
			stm.setDouble(1, MaxPrice);
			ResultSet rst = stm.executeQuery();
			coupons = new ArrayList<>();
			while (rst.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rst.getInt("id"));
				coupon.setCompanyId(rst.getInt("companyId"));
				coupon.setCategory(Category.valueOf(rst.getString("category")));
				coupon.setTitle(rst.getString("title"));
				coupon.setDescription(rst.getString("description"));
				coupon.setStartDate(rst.getDate("startDate").toLocalDate());
				coupon.setEndDate(rst.getDate("endDate").toLocalDate());
				coupon.setAmount(rst.getInt("amount"));
				coupon.setPrice(rst.getDouble("price"));
				coupons.add(coupon);

			}
			return coupons;
		} catch (SQLException e) {

			throw new couponExeptions("getAllCoponsByMaxPrice faild", e);
		} finally {
			connectionPool.restoreConnection(con);

		}
	}
}
