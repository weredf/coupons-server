package app.core.job;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;

//@Component
@Scope("singleton")
public class CouponExpirationDailyJob implements Runnable {

	@Autowired
	private AdminService adminService;

//	@Scheduled(cron = " 0 0 * * * *")
//	@Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
//	public void dailyJob() throws CouponSystemException {
//		System.out.println("Daily job deleting coupons performed");
//		couponRepo.deleteByEndDateBefore(LocalDate.now());
//	}
	private boolean quit;
	private Thread thread = new Thread(this, "daily_job");

	@Override
	public void run(){
		System.out.println(">>> Daily job running");
		while(!quit) {
			try {
				TimeUnit.SECONDS.sleep(5);
				adminService.deleteExpiredCoupons();
				System.out.println(">>> All expired coupons deleted");
				TimeUnit.DAYS.sleep(1);
			} catch (InterruptedException e) {
				System.out.println("Daily job: " + e.getMessage());
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Start Daily Job Thread, boolean
	 */
	@PostConstruct
	public void startDailyJob() {
		quit = false;
		this.thread.start();
		System.out.println(">>> Daily job started");
	}
	
	/**
	 * Stop Daily Job Thread, boolean
	 */
	@PreDestroy
	public void stopDailyJob() {
		quit = true;
		this.thread.interrupt();
		System.out.println(">>> Daily job stopped");
	}
}
