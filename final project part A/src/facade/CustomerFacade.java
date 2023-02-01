package facade;

import java.time.LocalDate;
import java.util.ArrayList;
import exeptions.couponExeptions;
import Bean.Category;
import Bean.Coupon;
import Bean.Customer;

public class CustomerFacade extends ClientFacade {

	private int customerId;

	public CustomerFacade() {
	}

	public CustomerFacade(String email, String password) {

	}

	/**
	 * 
	 */
	public boolean login(String email, String password)throws couponExeptions {
		try {
			if (customersDao.isCustomerExists(email, password)) {
				customerId = customersDao.getCustomerId(email);
				System.out.println("you are logedIN");
				return true;
			}
		} catch (Exception e) {
			throw new couponExeptions("the email or password is incorrect please try agine ", e);
		}
		return false;
	}

	/**
	 * 
	 * @param coupon
	 */
	public void purchaseCoupon(Coupon coupon)throws couponExeptions {
		try {

			if (couponsDao.isCouponExists(customerId, coupon.getId())) {
				throw new couponExeptions("this customer has already this coupon");
			}
			if (coupon.getAmount() == 0) {
				throw new couponExeptions("this coupon is sold out");
			}
			if (coupon.getEndDate().isAfter(LocalDate.now())) {
				throw new couponExeptions("This coupon has expired");
			}
			couponsDao.addCouponPurchase(customerId, coupon.getId());
			couponsDao.amountdrop(coupon.getId());
		} catch (Exception e) {

			throw new couponExeptions("purchaseCoupon faild", e);
		}

	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Coupon> getCustomerCoupons()throws couponExeptions {
		return couponsDao.getAllCouponsLogicID(customerId);
	}

	/**
	 * 
	 * @param category
	 * @return
	 */
	public ArrayList<Coupon> getCustomerCoupons(Category category)throws couponExeptions {

		ArrayList<Coupon> listOfCopons = new ArrayList<>();
		for (Coupon coupons : couponsDao.getAllCouponsLogicCat(category)) {
			if (couponsDao.isCouponExists(customerId, coupons.getId())) {
				listOfCopons.add(coupons);
			}
		}
		return listOfCopons;
	}

	/**
	 * 
	 * @param maxPrice
	 * @return
	 */
	public ArrayList<Coupon> getCustomerCoupons(Double maxPrice)throws couponExeptions {
		ArrayList<Coupon> listOfCopons = new ArrayList<>();
		for (Coupon coupons : couponsDao.getAllCoponsByMaxPrice(maxPrice)) {
			if (couponsDao.isCouponExists(customerId, coupons.getId())) {
				listOfCopons.add(coupons);
			}

		}
		return listOfCopons;

	}

	/**
	 * 
	 * @return
	 */
	public Customer getCustomerDetails()throws couponExeptions {
		return customersDao.getOneCustomer(customerId);
	}

}
