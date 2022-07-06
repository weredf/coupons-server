package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;

import app.core.exceptions.CouponSystemException;
import app.core.repos.CompanyRepo;
import app.core.repos.CouponRepo;
import app.core.repos.CustomerRepo;

public abstract class ClientService {

	@Autowired
	protected CompanyRepo companyRepo;
	@Autowired
	protected CustomerRepo customerRepo;
	@Autowired
	protected CouponRepo couponRepo;
	
	/**
	 * @param email
	 * @param password
	 * @return Login to ClientService
	 * @throws CouponSystemException
	 */
	public abstract boolean login(String email, String password) throws CouponSystemException;
}
