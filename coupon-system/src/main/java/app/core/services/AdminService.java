package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class AdminService extends ClientService{

	@Value("${admin.email}")
	private String email;
	@Value("${admin.password}")
	private String password;
	
	/**
	 * Login to AdminService
	 * Returns boolean
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		return email.equals(this.email) && password.equals(this.password);
	}
	
	/**
	 * Check if company exists by email, add company
	 * @param company
	 * @return company id
	 * @throws CouponSystemException
	 */
	public int addCompany(Company company) throws CouponSystemException{
		if (!companyRepo.existsByEmail(company.getEmail())) {
			return companyRepo.save(company).getId();
		} else {
			throw new CouponSystemException("addCompany failed - email already in use");
		}
	}
	
	/**
	 * Update company, find by id, set name according to existing company
	 * @param company
	 * @throws CouponSystemException
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		Optional<Company> opt = companyRepo.findById(company.getId());
		if (opt.isPresent()) {
			company.setName(opt.get().getName());
			companyRepo.save(company);
		} else {
			throw new CouponSystemException("updateCompany failed - company with id " + company.getId() + " doesn't exist");
		}
	}
	
	/**
	 * Delete company, find by id
	 * @param companyId
	 * @throws CouponSystemException
	 */
	public void deleteCompany(int companyId) throws CouponSystemException {
		Optional<Company> opt = companyRepo.findById(companyId);
		if(opt.isPresent()) {
			companyRepo.deleteById(companyId);
		} else {
			throw new CouponSystemException("deleteCompany failed - company with id " + companyId + " doesn't exist");
		}
	}
	
	/**
	 * Find all companies
	 * @return List of companies
	 * @throws CouponSystemException
	 */
	public List<Company> getAllCompanies() throws CouponSystemException{
		List<Company> companies = companyRepo.findAll();
		return companies;
	}
	
	/**
	 * Find one company according to id
	 * @param companyId
	 * @return one company
	 * @throws CouponSystemException
	 */
	public Company getOneCompany(int companyId) throws CouponSystemException {
		Optional<Company> opt = companyRepo.findById(companyId);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			 throw new CouponSystemException("getOneCompany failed - company with id " + companyId + " doesn't exist");
		}
	}
	
	/**
	 * Check if customer exists by email, add customer
	 * @param customer
	 * @return customer id
	 * @throws CouponSystemException
	 */
	public int addCustomer (Customer customer) throws CouponSystemException {
		if (!customerRepo.existsByEmail(customer.getEmail())) {
			return customerRepo.save(customer).getId();
		} else {
			throw new CouponSystemException("addCustomer failed - email already in use");
		}
	}
	
	/**
	 * Update customer, find by id, set name according to existing customer
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void updateCustomer (Customer customer) throws CouponSystemException {
		Optional<Customer> opt = customerRepo.findById(customer.getId());
		if (opt.isPresent()) {
			customerRepo.save(customer);
		} else {
			throw new CouponSystemException("updateCustomer failed - customer with id " + customer.getId() + " doesn't exist");
		}
	}
	
	/**
	 * Delete customer, find by id
	 * @param customerId
	 * @throws CouponSystemException
	 */
	public void deleteCustomer (int customerId) throws CouponSystemException {
		if (customerRepo.existsById(customerId)) {
			customerRepo.deleteById(customerId);
		} else {
			throw new CouponSystemException("deleteCustomer failed - customer doesn't exist");
		}
	}
	
	/**
	 * Find all customers
	 * @return
	 * @throws CouponSystemException
	 */
	public List<Customer> getAllCustomers() throws CouponSystemException {
		List<Customer> customers = customerRepo.findAll();
		return customers;
	}
	
	/**
	 * Find one customer according to id
	 * @param customerId
	 * @return once customer
	 * @throws CouponSystemException
	 */
	public Customer getOneCustomer(int customerId) throws CouponSystemException {
		Optional<Customer> opt = customerRepo.findById(customerId);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			 throw new CouponSystemException("getOneCustomer failed - customer doesn't exist");
		}
	}
	
	/**
	 * Delete all expired coupons once a day
	 * @throws CouponSystemException
	 */
	public void deleteExpiredCoupons() throws CouponSystemException {
		this.couponRepo.deleteByEndDateBefore(LocalDate.now());
	}

}
