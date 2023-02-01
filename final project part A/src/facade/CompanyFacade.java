package facade;

import java.util.ArrayList;

import exeptions.couponExeptions;
import Bean.Category;
import Bean.Company;
import Bean.Coupon;

public class CompanyFacade extends ClientFacade {

	private int companyId;

	public CompanyFacade() {
	}

	public CompanyFacade(String email, String password) {
	}

	/**
	 * 
	 */
	public boolean login(String email, String password)throws couponExeptions {

		if (companiesDao.isCompanyExists(email, password)) {
			companyId = companiesDao.getCompanyId(email);
			System.out.println("you are logedIN");
			return true;
		}
		System.out.println("the email or password is incorrect please try agine ");

		return false;
	}

	/**
	 * 
	 * @param coupon
	 */
	public void addCoupon(Coupon coupon)throws couponExeptions {
		if (couponsDao.isTitleExists(coupon.getTitle())) {
			throw new couponExeptions("this title is already exist please change it");
		}
		couponsDao.addCoupon(coupon);
	}

	/**
	 * 
	 * @param coupon
	 */
	public void updateCoupon(Coupon coupon) throws couponExeptions{
		couponsDao.updateCouponLogic(coupon);
	}

	/**
	 * 
	 * @param couponID
	 */
	public void deleateCoupon(int couponID)throws couponExeptions {
		couponsDao.deleteCouponPurchaseCouponId(couponID);
		couponsDao.deleteCoupon(couponID);
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Coupon> getCompanyCouponID()throws couponExeptions {
		if (companyId != 0) {
			return couponsDao.getAllCouponsLogicID(companyId);
		}
		throw new couponExeptions("the Company is not loggedIN");

	}

	/**
	 * 
	 * @param category
	 * @return
	 */
	public ArrayList<Coupon> getCompanyCouponCAT(Category category)throws couponExeptions {
		ArrayList<Coupon> list = new ArrayList<>();
		for (Coupon coupon : getCompanyCouponID()) {
			if (coupon.getCategory().equals(category)) {
				list.add(coupon);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param maxPrice
	 * @return
	 */
	public ArrayList<Coupon> getCompanyCoupon(double maxPrice)throws couponExeptions {
		ArrayList<Coupon> list = new ArrayList<>();
		for (Coupon coupon : getCompanyCouponID()) {
			if (coupon.getPrice() <= maxPrice) {
				list.add(coupon);
			}
		}
		return list;
	}

	/**
	 * 
	 * @return
	 */
	public Company getCompanyDeatils()throws couponExeptions {
		if (companyId != 0) {
			return companiesDao.getOneCompany(companyId);
		}
		throw new couponExeptions("the Company is not loggedIN");
	}
}
