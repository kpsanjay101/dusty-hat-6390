package com.masai.BookingService;

import java.util.List;
import java.util.Scanner;

import com.masai.BookingDAO.CabBookingDao;
import com.masai.BookingDAO.CabBookingDaoImpl;
import com.masai.BookingDAO.EMUtils;
import com.masai.BookingEntity.Admin;
import com.masai.BookingEntity.Cab;
import com.masai.BookingEntity.Customer;
import com.masai.BookingUI.UIMain;
import com.masai.Exception.NoCabAvailableAtThisTime;
import com.masai.Exception.NoRecordFoundException;
import com.masai.Exception.SomethingWentWrongException;
import com.masai.Exception.UserNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class BookingServices {
	static Scanner sc = new Scanner(System.in);
	
	public static String loginAdminPage(String user_name, String password) {
		
		CabBookingDao cabBDao = new CabBookingDaoImpl();
		
		try {
			Admin admin = cabBDao.findUsernameAndPasswordForAdmin(user_name);
			String userName = admin.getUserName();
			String passwordA = admin.getPassword();
			if(user_name.equals(userName) && password.equals(passwordA)) {
				return "WELCOME ADMIN !";
			}else {
				System.out.println("Please Inter Correct password");
				UIMain.logingToAdmin(sc);
			}
		}catch(PersistenceException ex) {
			throw new UserNotFoundException("User doesn't Exist. Please SignIn First Or Enter Correct UserName");
		}
		return "Please Enter Correct Password";
	}
	
	public static String loginCustomerPage(String user_name, String password) {
			
			CabBookingDao cabBDao = new CabBookingDaoImpl();
			
			try {
				Customer cust = cabBDao.findUsernameAndPasswordForCustomer(user_name);
				String userName = cust.getUserName();
				String passwordA = cust.getPassword();
				if(user_name.equals(userName) && password.equals(passwordA)) {
					String str = "Hello "+userName;
					return str;
				}else {
					System.out.println("Please Check Your User Name And Password");
					UIMain.loginToCustomer(sc);
				}
			}catch(PersistenceException ex) {
				throw new UserNotFoundException("User doesn't Exist. Please Sign in first");
			}
			return "Please check your Password";
	}
	
	public static void viewListOfCabCustomerSide() {
		CabBookingDao cabDao = new CabBookingDaoImpl();
		try {
			List<Cab> list = cabDao.viewAllCabFromCustomerSide();
			System.out.println();
			for(Cab c : list) {
//				System.out.println();
				System.out.println("CabId: "+c.getCabId()+" Car Type: "+c.getCarType()+" Per KM Rate: "+c.getPerKmRate()+" rating: "+c.getRating()+" Driver Id: "+c.getDriver().getUserName());
                System.out.println("******************************************************************************************");			
			}
			System.out.println();
		}catch(Exception e) {
			System.out.println(e.getMessage()); 
		}
		
	}
	
	
}
