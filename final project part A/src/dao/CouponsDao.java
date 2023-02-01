package dao;

import java.util.ArrayList;

import Bean.Category;
import Bean.Coupon;
import exeptions.couponExeptions;

public interface CouponsDao {

	void addCoupon(Coupon Coupon)throws couponExeptions;

	void updateCoupon(Coupon coupon)throws couponExeptions;
	void updateCouponLogic(Coupon coupon)throws couponExeptions;

	void deleteCoupon(int couponId)throws couponExeptions;
	void deleteCouponCom(int couponId)throws couponExeptions;

	ArrayList<Coupon> getAllCoupons()throws couponExeptions;
	ArrayList<Coupon> getAllCouponsLogicID(int companyId)throws couponExeptions;
	ArrayList<Coupon> getAllCouponsLogicCat(Category category)throws couponExeptions;
	ArrayList<Coupon> getAllCoponsByMaxPrice(double MaxPrice)throws couponExeptions;

	Coupon getOneCoupon(int couponId)throws couponExeptions;

    void addCouponPurchase(int customerId,int couponId)throws couponExeptions;
    void amountdrop(int customerId)throws couponExeptions;
    
    void deleteCouponPurchase(int customerId,int couponId)throws couponExeptions;
    void deleteCouponPurchaseId(int customerId)throws couponExeptions;
    void deleteCouponPurchaseCouponId(int couponId)throws couponExeptions;
    
    boolean isTitleExists(String title)throws couponExeptions;
    
    boolean isCouponExists(int customerId, int couponId)throws couponExeptions;
    
    ArrayList<Coupon> getAllExpiredCoupon()throws couponExeptions;
}
