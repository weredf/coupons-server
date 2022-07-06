package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import app.core.exceptions.CouponSystemException;
import app.core.filters.ClientFilter;
import app.core.jwt.util.JwtUtil;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@EnableScheduling
public class Application {

	public static void main(String[] args) throws CouponSystemException, InterruptedException {
		SpringApplication.run(Application.class, args);
	}
	
	// register and map the filter
	// container for registering filters
	// register our filter - set the filter
	// map the filter to route (a url pattern)
	// return the set and routed FilterRegistrationBean
	
		@Bean
		public FilterRegistrationBean<ClientFilter> clientFilter(JwtUtil jwtUtil) {
			FilterRegistrationBean<ClientFilter> filterRegistrationBean = new FilterRegistrationBean<>();
			ClientFilter clientFilter = new ClientFilter(jwtUtil);
			filterRegistrationBean.setFilter(clientFilter);
			filterRegistrationBean.addUrlPatterns("/api/*");
			return filterRegistrationBean;
		}
}
