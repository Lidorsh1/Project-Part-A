package facade;

import dbdao.CompaniesDBDao;
import dao.CompaniesDao;
import dbdao.CouponsDBDao;
import dao.CouponsDao;
import dbdao.CustomersDBDao;
import exeptions.couponExeptions;
import dao.CustomersDao;

public abstract class ClientFacade {

	protected CompaniesDao companiesDao=new CompaniesDBDao();
	protected CustomersDao customersDao=new CustomersDBDao();
	protected CouponsDao couponsDao=new CouponsDBDao();

	
	
	public boolean login(String email, String password)throws couponExeptions {
		return false;
	}
}
