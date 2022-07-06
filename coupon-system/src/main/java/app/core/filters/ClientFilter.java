package app.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import app.core.jwt.util.JwtUtil;
import app.core.login.ClientType;

// not component, otherwise filter will be valid for the whole application and login would be impossible
public class ClientFilter implements Filter {

	private JwtUtil jwtUtil;

	public ClientFilter(JwtUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println(">>> FILTER");
		// cast the req/resp to http
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String token = req.getHeader("token"); // can be shortened
		System.out.println("===== LOGIN FILTER TOKEN: " + token);
		
		// browser check - preflight (doesn't happen with Swagger)
		if (token == null && req.getMethod().equals("OPTIONS")) {
			System.out.println("this is a preflight request: " + req.getMethod());
			chain.doFilter(request, response); // go ahead with preflight and return
			return;
		}

		try {
			String uri = req.getRequestURI();
			ClientType client = jwtUtil.extractClient(token).getClientType();
			System.out.println("======================================");
			System.out.println(uri);
			System.out.println("======================================");
			// check token validity
			// if valid forward the request to the end point
			if (!jwtUtil.isTokenExpired(token)) {
				System.out.println(">>> FILTER - valid token");
				if (uri.contains(client.toString())) {
					chain.doFilter(request, response);
					return;
				} else {
					System.out.println(">>> FILTER - wrong client type");
					resp.sendError(HttpStatus.UNAUTHORIZED.value(), "wrong client type - go to login");
					return;
				}
			}
		} catch (Exception e) {
			System.out.println(">>> FILTER - invalid token, expired");
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.sendError(HttpStatus.UNAUTHORIZED.value(), "invalid token - go to login");
			return;
		}
	}

}
