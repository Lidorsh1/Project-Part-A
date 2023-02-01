package testAll;

import java.sql.Connection;
import java.time.LocalDate;

import connection.ConnectionPool;
import exeptions.couponExeptions;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import Bean.Category;
import Bean.Company;
import Bean.Coupon;
import Bean.Customer;
import job.CouponExpirationDailyJob;
import logger.ClientType;
import logger.LoginManager;

public class Test {

	public static void main(String[] args) throws couponExeptions {
		testAll();
	}

	public static void testAll() throws couponExeptions {
		CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
		Thread thread = new Thread(couponExpirationDailyJob);

		try {
			// JOB METHOD that delete all the expired coupon
			thread.start();

			// LOGIN -> LoginManager <-(press f3 for explanation)
			LoginManager log = LoginManager.getInstance();

			// Admin
//			AdminFacade adminFacade = (AdminFacade) log.login("admin@admin.com", "admin", ClientType.Administrator);
//			adminFacade.addCompany(new Company(0, "B", "B@gmail.com", "BF12"));
//			 adminFacade.updateCompany(new Company(1, "BBB", "bbb@gmail.com",
//			 "bbburger"));
//			 adminFacade.deleteCompany(3);
//			System.out.println(adminFacade.getAllCompanies());
//			System.out.println(adminFacade.getOneCompany(1));

//            adminFacade.addCustomer(new Customer(0, "lior", "sharabi", "lidor1212@gmail.com", "lidor1"));
//            adminFacade.updateCustomer(new Customer(1, "lidor", "sharabi","lidor@gmail.com","lidor1"));
//            adminFacade.deleteCustomer(2);
//            System.out.println(adminFacade.getAllCuostomers());
//            System.out.println(adminFacade.getOneCustomer(1));

			// Company
//          CompanyFacade companyFacade = (CompanyFacade) log.login("bbb@gmail.com", "bbburger", ClientType.Company);
//          companyFacade.addCoupon(new Coupon(0, 1, Category.VACATION, "fre cruise", "get afree cruise",
//                  LocalDate.of(2022, 9, 12), LocalDate.of(2022, 12, 16), 20, 5, ""));
//	        companyFacade.updateCoupon(new Coupon(2, 0, Category.VACATION, "free cruise", "get afree cruise",
//	                LocalDate.of(2022, 9, 12), LocalDate.of(2022, 1, 16), 120, 150, ""));
//          companyFacade.deleateCoupon(1);
//	        System.out.println(companyFacade.getCompanyCouponID());
//	        System.out.println(companyFacade.getCompanyCouponCAT(Category.VACATION));
//	        System.out.println(companyFacade.getCompanyCoupon(6.0));
//          System.out.println(companyFacade.getCompanyDeatils());

			// Customer
//			CustomerFacade customerFacade = (CustomerFacade) log.login("lidor@gmail.com", "lidor1",	ClientType.Customer);
//			customerFacade.purchaseCoupon((new Coupon(3, 1, Category.VACATION, "freecruise", "get afree cruise",
//					LocalDate.of(2022, 9, 12), LocalDate.of(2022, 10, 16), 20, 5, "")));
//			System.out.println(customerFacade.getCustomerCoupons());
//			System.out.println(customerFacade.getCustomerCoupons(Category.VACATION));
//			System.out.println(customerFacade.getCustomerCoupons(150.0));
//			System.out.println(customerFacade.getCustomerDetails());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			thread.interrupt();
			;
			try {
				thread.join();
			} catch (InterruptedException e) {
				throw new couponExeptions("join error error");
			}
			try {
				ConnectionPool.getInstance().closeAllConnections();
				System.out.println("end");
			} catch (couponExeptions e) {

				throw new couponExeptions("close all connection error");
			}
		}
	}
}
