package app.core.repos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

	/**
	 * Find all coupons by one company id
	 * @param company
	 * @return list of coupons of only one company
	 * @throws CouponSystemException
	 */
	List<Coupon> findByCompany(Company company) throws CouponSystemException;

	/**
	 * Find all coupons by one company id in specific category
	 * @param company
	 * @param category
	 * @return list of coupons of only one company and category
	 * @throws CouponSystemException
	 */
	List<Coupon> findByCompanyAndCategory(Company company, Category category) throws CouponSystemException;

	/**
	 * Find all coupons by one company id up to maxPrice
	 * @param maxPrice
	 * @param company
	 * @return list of coupons of only one company and up to maxPrice
	 * @throws CouponSystemException
	 */
	List<Coupon> findByPriceLessThanEqualAndCompany(Double maxPrice, Company company) throws CouponSystemException;

	/**
	 * Find coupon of company with specific title
	 * @param companyId
	 * @param title
	 * @return coupon with given company id and title or Optional#empty() if none found
	 * @throws CouponSystemException
	 */
	Optional<Coupon> findByCompanyIdAndTitle(Integer companyId, String title) throws CouponSystemException;

	/**
	 * Find all coupons for this customer
	 * @param customerId
	 * @return list of coupons of one customer
	 * @throws CouponSystemException
	 */
	List<Coupon> findByCustomersId(int customerId) throws CouponSystemException;

	/**
	 * Find coupon purchase for this customer
	 * @param id
	 * @param customerId
	 * @return coupon with given customer id if purchased or Optional#empty() if none found
	 * @throws CouponSystemException
	 */
	Optional<Coupon> findByIdAndCustomersId(int id, int customerId) throws CouponSystemException;

	/**
	 * Find all coupons for one customer in specific category
	 * @param category
	 * @param customerId
	 * @return list of coupons of one customer and category
	 * @throws CouponSystemException
	 */
	List<Coupon> findByCategoryAndCustomersId(Category category, int customerId) throws CouponSystemException;

	/**
	 * Find all coupons for one customer up to maxPrice
	 * @param maxPrice
	 * @param customerId
	 * @return list of coupons of only one customer and up to maxPrice
	 * @throws CouponSystemException
	 */
	List<Coupon> findByPriceLessThanEqualAndCustomersId(double maxPrice, int customerId) throws CouponSystemException;

	/**
	 * Delete all coupons that have expired
	 * @param date
	 * @throws CouponSystemException
	 */
	void deleteByEndDateBefore(LocalDate date) throws CouponSystemException;
}
