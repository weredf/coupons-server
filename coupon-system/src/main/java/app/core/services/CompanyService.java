package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
//@Scope("prototype")
public class CompanyService extends ClientService{

//	@Setter @Getter
//	private int companyId;
	
	/**
	 * Check if company exists by email and password, sets companyId for usage in LoginManager, LoginController to put in token
	 * @param email, password
	 * @return boolean
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		Optional<Company> opt = companyRepo.findByEmailAndPassword(email, password);
		if (opt.isPresent()) {
//			this.companyId = opt.get().getId();
			return true;
		} else {
			return false;
		}
			
	}
	
	/**
	 * Check if coupon exists for this company, and if title not already in use
	 * @param coupon
	 * @return coupon id
	 * @throws CouponSystemException
	 */
	public int addCoupon (Coupon coupon, int companyId) throws CouponSystemException {
		Optional<Coupon> opt = couponRepo.findByCompanyIdAndTitle(companyId, coupon.getTitle());
		if (opt.isEmpty()) {
			getCompanyDetails(companyId).addCoupon(coupon);
			return couponRepo.save(coupon).getId();
		} else {
			throw new CouponSystemException("addCoupon failed - coupon title " + coupon.getTitle() + " already in use");
		}
	}
	
	/**
	 * Check if coupon exists by id, set company to logged in company
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void updateCoupon (Coupon coupon, int companyId) throws CouponSystemException {
		Optional<Coupon> opt = couponRepo.findById(coupon.getId());
		if(opt.isPresent()) {
			// instead of set, check if coupon belongs to company?
			coupon.setCompany(getCompanyDetails(companyId));
			couponRepo.save(coupon);
		} else {
			throw new CouponSystemException("updateCoupon failed - coupon " + coupon.getId() + " doesn't exist");
		}
	}
	
	/**
	 * Delete coupon by id
	 * @param couponId
	 * @throws CouponSystemException
	 */
	public void deleteCoupon (int couponId, int companyId) throws CouponSystemException {
		Optional<Coupon> opt = couponRepo.findById(couponId);
		if(opt.isPresent()) {
			// check if coupon belongs to company
			couponRepo.deleteById(couponId);
		} else {
			throw new CouponSystemException("deleteCoupon failed - coupon " + couponId + " doesn't exist");
		}
	}
	
	/**
	 * Get all coupons by logged in company
	 * @return List of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException {
		List<Coupon> coupons = couponRepo.findByCompany(getCompanyDetails(companyId));
		return coupons;
		
	}

	/**
	 * Get all coupons by logged in company in specific category
	 * @param category
	 * @return List of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCompanyCoupons(Category category, int companyId) throws CouponSystemException {
		List<Coupon> coupons = couponRepo.findByCompanyAndCategory(getCompanyDetails(companyId), category);
		return coupons;
	}

	/**
	 * Get all coupons by logged in company up to max price
	 * @param maxPrice
	 * @return List of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCompanyCoupons(double maxPrice, int companyId) throws CouponSystemException {
		List<Coupon> coupons = couponRepo.findByPriceLessThanEqualAndCompany(maxPrice, getCompanyDetails(companyId));
		return coupons;
	}
	
	/**
	 * Get logged in company
	 * @return company
	 * @throws CouponSystemException
	 */
	public Company getCompanyDetails(int companyId) throws CouponSystemException {
		return companyRepo.findById(companyId).get();
	}
}
