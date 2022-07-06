package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;

@CrossOrigin
@RestController
@RequestMapping("/api/ADMINISTRATOR")
public class AdminController {

	@Autowired
	private AdminService adminService;
//	@Autowired
//	private JwtUtil jwtUtil;
	
	// to do: client login, token (object) from server, client saves token
	// http protocol is stateless. server gets header with token from client
	/**
	 * Login to AdminService
	 * @return adminService through token
	 */
	/*
	@PutMapping("/login")
	public String login(@RequestHeader String token) {
		if(jwtUtil.extractClient(token).getClientType() == ClientType.ADMINISTRATOR) {
			return "You are logged in as Admin";
		} else {
			return "You are not logged in as Admin";
		}
	}*/
	
	@PostMapping("/add-company")
	public ResponseEntity<?> addCompany(@RequestBody Company company, @RequestHeader String token) throws CouponSystemException {
		try {
			int id = adminService.addCompany(company);
//			return ResponseEntity.ok("company added, id: " + id);
			ResponseEntity<?> re = new ResponseEntity<>("company added, id: " + id, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update-company")
	public ResponseEntity<?> updateCompany(@RequestBody Company company, @RequestHeader String token) throws CouponSystemException {
		try {
			adminService.updateCompany(company);
			return ResponseEntity.ok("company updated: " + company);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete-company/{companyId}")
	public ResponseEntity<?> deleteCompany(@PathVariable int companyId, @RequestHeader String token) throws CouponSystemException {
		try {
			adminService.deleteCompany(companyId);
			return ResponseEntity.ok("company deleted: " + companyId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/get-all-companies")
	public ResponseEntity<?> getAllCompanies(@RequestHeader String token) throws CouponSystemException {
		try {
			List<Company> companies = adminService.getAllCompanies();
			ResponseEntity<?> re = new ResponseEntity<>(companies, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/get-company/{companyId}")
	public ResponseEntity<?> getOneCompany(@PathVariable int companyId, @RequestHeader String token) throws CouponSystemException{
		try {
			Company company = adminService.getOneCompany(companyId);
			ResponseEntity<?> re = new ResponseEntity<>(company, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping("/add-customer")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer, @RequestHeader String token) throws CouponSystemException {
		try {
			int id = adminService.addCustomer(customer);
			return ResponseEntity.ok("customer added, id: " + id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update-customer")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @RequestHeader String token) throws CouponSystemException {
		try {
			adminService.updateCustomer(customer);
			return ResponseEntity.ok("customer updated: " + customer);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete-customer/{customerId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int customerId, @RequestHeader String token) throws CouponSystemException {
		try {
			adminService.deleteCustomer(customerId);
			return ResponseEntity.ok("customer deleted: " + customerId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/get-all-customers")
	public ResponseEntity<?> getAllCustomers(@RequestHeader String token) throws CouponSystemException {
		try {
			List<Customer> customers = adminService.getAllCustomers();
			ResponseEntity<?> re = new ResponseEntity<>(customers, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/get-customer/{customerId}")
	public ResponseEntity<?> getOneCustomer(@PathVariable int customerId, @RequestHeader String token) throws CouponSystemException{
		try {
			Customer customer = adminService.getOneCustomer(customerId);
			ResponseEntity<?> re = new ResponseEntity<>(customer, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
