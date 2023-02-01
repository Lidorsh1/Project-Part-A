package logger;

import exeptions.couponExeptions;
import facade.AdminFacade;
import facade.ClientFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

public final class LoginManager {

	private static LoginManager instance = new LoginManager();

	private LoginManager() {
	}

	public static LoginManager getInstance() {
		return instance;
	}

	/**
	 * this method checks the type of client and then give the option to log in with
	 * the password and name that are right to this client type
	 * 
	 * @param email
	 * @param password
	 * @param clientType
	 * @return null if all options are not good
	 */
	public ClientFacade login(String email, String password, ClientType clientType) throws couponExeptions{
		if (clientType == ClientType.Administrator) {
			AdminFacade a = new AdminFacade();
			if (a.login(email, password)) {
				return a;
			}
		} else if (clientType == ClientType.Company) {
			CompanyFacade b = new CompanyFacade();
			if (b.login(email, password)) {
				return b;
			}
		} else if (clientType == ClientType.Customer) {
			CustomerFacade c = new CustomerFacade();
			if(c.login(email, password)) {
				return c;				
			}
		}
		return null;
	}

}
