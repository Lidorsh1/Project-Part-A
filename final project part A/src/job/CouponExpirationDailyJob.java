package job;

import dbdao.CouponsDBDao;
import exeptions.couponExeptions;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Bean.Coupon;
import dao.CouponsDao;

public class CouponExpirationDailyJob implements Runnable {

	private CouponsDao couponsDao = new CouponsDBDao();

	private volatile Boolean quit=false;

	public CouponExpirationDailyJob() {
	}

	/*
	 * this method runs the thread
	 *     and check if there coupons thats are expired
	 *         and delete them from the coupons, and coupons vs customer
	 * */
	@Override
	public void run() {
		try {
		while (!quit) {
			ArrayList<Coupon>coupons = couponsDao.getAllExpiredCoupon();
				for (Coupon coupon : coupons) {
				couponsDao.deleteCouponPurchaseCouponId(coupon.getId());
				couponsDao.deleteCoupon(coupon.getId());
				}
				TimeUnit.DAYS.sleep(1);
				//Thread.sleep(86400000);
		}
			} catch ( couponExeptions |InterruptedException e) {
				stop();
			}
	}

	public void stop() {
		quit = true;
	}
}
