package dao;

import java.util.ArrayList;

import Bean.Company;
import exeptions.couponExeptions;


public interface CompaniesDao {

	boolean isCompanyExists(String email, String password)throws couponExeptions;
	boolean isCompanyExistsByNameEmail(String email, String name) throws couponExeptions;

	int getCompanyId(String email)throws couponExeptions;

	void addCompany(Company company)throws couponExeptions;

	void updateCompany(Company company)throws couponExeptions;
	void updateCompanyNoName(Company company)throws couponExeptions;

	void deleteCompany(int companyId)throws couponExeptions;

	ArrayList<Company> getAllCompanies()throws couponExeptions;

	Company getOneCompany(int companyId)throws couponExeptions;

}
