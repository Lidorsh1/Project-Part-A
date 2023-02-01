package facade;

import java.util.ArrayList;

import exeptions.couponExeptions;
import Bean.Company;
import Bean.Customer;

public class AdminFacade extends ClientFacade {

	public AdminFacade() {
	}

	public AdminFacade(String email, String password) {

	}

	//this method is for login to the server by email and password that is defined
	public boolean login(String email, String password)throws couponExeptions {
		try {
		if (email == "admin@admin.com" && password == "admin") {
			System.out.println("you are logedIn");
			return true;
		}
		}catch (Exception e) {
			throw new couponExeptions("the email or password is incorrect please try again",e);
		}
		return false;
	}

	/*
	 * this method adding company
	 *      and forbidden the possibility to add company
	 *                            that is name or email already exists
	 */
	public void addCompany(Company company) throws couponExeptions{
		try {
			if (this.companiesDao.isCompanyExistsByNameEmail(company.getEmail(), company.getName())) {
				throw new couponExeptions("company already exists");
			}
			companiesDao.addCompany(company);
		} catch (Exception e) {

			throw new couponExeptions("add company failed-the email or name allready exists", e);
		}
	}

	/*
	 * this method update company in the data base but not allow to update the name or id of the company
	 * */
	public void updateCompany(Company company)throws couponExeptions {
		try {
			companiesDao.updateCompanyNoName(company);

		} catch (Exception e) {
			throw new couponExeptions("update comapny failed-you can not update id or name ", e);
		}
	}

	/*
	 * this method delete company from the date base and remove all of is coupons and is purchase history
	 * */
	public void deleteCompany(int companyId)throws couponExeptions {
		try {
			couponsDao.deleteCouponPurchaseId(companyId);
			couponsDao.deleteCouponCom(companyId);
			companiesDao.deleteCompany(companyId);

		} catch (Exception e) {
			throw new couponExeptions("delete comapny failed", e);
		}
	}

	/*
	 * this method gets all the companies that on the database
	 */
	public ArrayList<Company> getAllCompanies()throws couponExeptions {
		try {
			return companiesDao.getAllCompanies();
		} catch (Exception e) {
			throw new couponExeptions("getAllCompanies failed", e);
		}
	}

	
	/*
	 * this method gets only one company by the company id
	 * */
	public Company getOneCompany(int CompanyId)throws couponExeptions {
		try {
			return companiesDao.getOneCompany(CompanyId);
		} catch (Exception e) {
			throw new couponExeptions("getAllCompanies failed", e);
		}
	}

	/*
	 * this method adding customer
	 *      to the data base and forbidden the possibility
	 *                         to add a customer with same email
	 * */
	public void addCustomer(Customer customer)throws couponExeptions {
		try {

			if (customersDao.isCustomerExistsByEmail(customer.getEmail())) {
				throw new couponExeptions("customer already exists");
			}
			customersDao.addCustomer(customer);
		} catch (Exception e) {
			throw new couponExeptions("addCustomer failed - this email already exists",e);
		}
	}

	/*
	 * this method update a customer in the data base but not allow to update is id
	 * */
	public void updateCustomer(Customer customer)throws couponExeptions {
		try {
			customersDao.updateCustomer(customer);

		} catch (Exception e) {
			throw new couponExeptions("updateCustomer failed- you can not update id", e);
		}
	}

	
	/*
	 * this method delete a customer by is id and in addition delete is purchase history
	 * */
	public void deleteCustomer(int customerId) throws couponExeptions{
		try {
			customersDao.deleteCustomersPurchaseId(customerId);
			customersDao.deleteCustomer(customerId);

		} catch (Exception e) {
			throw new couponExeptions("deleateCustomer failed", e);
		}
	}

	/*
	 * this method gets all the customers from the data base
	 * */
	public ArrayList<Customer> getAllCuostomers()throws couponExeptions {
		try {
			return customersDao.getAllCustomeres();

		} catch (Exception e) {
			throw new couponExeptions("getAllCuostomers faild", e);
		}
	}

	/*
	 * this method gets only one customer by is id 
	 * */
	public Customer getOneCustomer(int customerId)throws couponExeptions {
		try {
			return customersDao.getOneCustomer(customerId);

		} catch (Exception e) {
			throw new couponExeptions("getOneCustomer faild", e);
		}
	}
}
