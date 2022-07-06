package app.core.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Company;
import app.core.exceptions.CouponSystemException;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

	/**
	 * Check if company exists by email
	 * @param email
	 * @return boolean
	 * @throws CouponSystemException
	 */
	boolean existsByEmail(String email) throws CouponSystemException;

	/**
	 * Find one company by email and password
	 * @param email
	 * @param password
	 * @return company with given email and password or Optional#empty() if none found
	 * @throws CouponSystemException
	 */
	Optional<Company> findByEmailAndPassword(String email, String password) throws CouponSystemException;

}
