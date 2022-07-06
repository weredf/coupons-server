package app.core.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	/**
	 * Check if customer exists by email and password
	 * @param email
	 * @param password
	 * @return boolean
	 * @throws CouponSystemException
	 */
	boolean existsByEmailAndPassword(String email, String password) throws CouponSystemException;

	/**
	 * Check if customer exists by email
	 * @param email
	 * @return boolean
	 * @throws CouponSystemException
	 */
	boolean existsByEmail(String email) throws CouponSystemException;

	/**
	 * Get one customer by email and password
	 * @param email
	 * @param password
	 * @return customer
	 * @throws CouponSystemException
	 */
	Customer getByEmailAndPassword(String email, String password) throws CouponSystemException;

	/**
	 * Find one customer by email and password
	 * @param email
	 * @param password
	 * @return Optional of customer
	 * @throws CouponSystemException
	 */
	Optional<Customer> findByEmailAndPassword(String email, String password) throws CouponSystemException;

}
