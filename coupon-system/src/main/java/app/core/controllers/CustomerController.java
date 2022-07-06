package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.jwt.util.JwtUtil;
import app.core.services.CustomerService;

@CrossOrigin
@RestController
@RequestMapping("/api/CUSTOMER")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private JwtUtil jwtUtil;
	
	/*
	// to do (id is not unique, email is!)
	@PutMapping("/login")
	public String login(@RequestHeader String token) {
		if(jwtUtil.extractClient(token).getClientType() == ClientType.CUSTOMER) {
			customerService.setCustomerId(jwtUtil.extractClient(token).getClientId());
			return "You are logged in as customer " + jwtUtil.extractClient(token).toString();
		} else {
			return "Wrong client type";
		}
	}
	*/
	
	@PutMapping("/purchase-coupon/{couponId}")
	public ResponseEntity<?> purchaseCoupon(@PathVariable int couponId, @RequestHeader String token) throws CouponSystemException {
		try {
			int customerId = jwtUtil.extractClient(token).getClientId();
			customerService.purchaseCoupon(couponId, customerId);
			return ResponseEntity.ok("coupon id " + couponId + " purchased");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(e.getMessage());
		}
	}
	
	@GetMapping("/get-customer-coupons")
	public ResponseEntity<?> getCustomerCoupons(@RequestHeader String token) throws CouponSystemException {
		try {
			int customerId = jwtUtil.extractClient(token).getClientId();
			List<Coupon> coupons = customerService.getCustomerCoupons(customerId);
			ResponseEntity<?> re = new ResponseEntity<>(coupons, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/get-customer-coupons/{category}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable String categoryString, @RequestHeader String token) throws CouponSystemException {
		try {
			Category category = Category.valueOf(categoryString);
			int customerId = jwtUtil.extractClient(token).getClientId();
			List<Coupon> coupons = customerService.getCustomerCoupons(category, customerId);
			ResponseEntity<?> re = new ResponseEntity<>(coupons, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
	
	@GetMapping("/get-customer-coupons/{maxPrice}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable double maxPrice, @RequestHeader String token) throws CouponSystemException {
		try {
			int customerId = jwtUtil.extractClient(token).getClientId();
			List<Coupon> coupons = customerService.getCustomerCoupons(maxPrice, customerId);
			ResponseEntity<?> re = new ResponseEntity<>(coupons, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/get-customer-details")
	public ResponseEntity<?> getCustomerDetails(@RequestHeader String token) throws CouponSystemException{
		try {
			int customerId = jwtUtil.extractClient(token).getClientId();
			Customer customer = customerService.getCustomerDetails(customerId);
			ResponseEntity<?> re = new ResponseEntity<>(customer, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
