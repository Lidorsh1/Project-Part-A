package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.Customer;
import exeptions.couponExeptions;

public interface CustomersDao {
	
	boolean isCustomerExists(String email, String password) throws couponExeptions;
    
	boolean isCustomerExistsByEmail(String email)throws couponExeptions;

	void addCustomer(Customer Customer)throws couponExeptions;

	void updateCustomer(Customer Customer)throws couponExeptions;

	void deleteCustomer(int CustomerId)throws couponExeptions;

	ArrayList<Customer> getAllCustomeres()throws couponExeptions;

	Customer getOneCustomer(int CustomerId)throws couponExeptions;
	
	void deleteCustomersPurchaseId(int customerId)throws couponExeptions;
	
	int getCustomerId(String email)throws couponExeptions;

}
