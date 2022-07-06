package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.exceptions.CouponSystemException;
import app.core.jwt.util.JwtUtil;
import app.core.jwt.util.JwtUtil.ClientDetails;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.repos.CompanyRepo;
import app.core.repos.CustomerRepo;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

//	@Autowired
//	@Qualifier("adminService")
//	private AdminService adminService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private CustomerRepo customerRepo;
//	@Autowired
//	private CompanyService companyService;
	
	
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String login(@RequestParam String email, @RequestParam String password, @RequestParam String clientTypeString) throws CouponSystemException {
		ClientType clientType = ClientType.valueOf(clientTypeString);
		System.out.println(clientType);
		if(this.loginManager.login(email, password, clientType) != null) {
			// generate token and return
			ClientDetails client = new ClientDetails(email, clientType, 0);
			if (clientType == ClientType.ADMINISTRATOR) {
//				adminService = (AdminService) loginManager.login(email, password, clientType);
			} else if (clientType == ClientType.COMPANY) {
//				companyService = (CompanyService) loginManager.login(email, password, clientType);
//				companyService.getCompanyId();
				client.setClientId(companyRepo.findByEmailAndPassword(email, password).get().getId());
			} else if (clientType == ClientType.CUSTOMER) {
//				customerService = (CustomerService) loginManager.login(email, password, clientType);
				client.setClientId(customerRepo.findByEmailAndPassword(email, password).get().getId());
			}
			System.out.println("Client id: " + client.getClientId());
			return jwtUtil.generateToken(client);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "bad credentials");
		}
	}
	
}
