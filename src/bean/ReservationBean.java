package bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class ReservationBean implements Serializable {
	private int hotelId;
	private int planId;
	private int reservedCount;
	private String hotelName;
	private String planName;
	private Date checkInDate;
	private Date checkOutDate;
	private int price;
	private int lodgmentDays;
	private int accountId;
	
	public ReservationBean(int hotelId, int planId, int reservedCount,
			String hotelName, String planName, Date checkInDate, Date checkOutDate, int price) {
		this.hotelId = hotelId;
		this.planId = planId;
		this.reservedCount = reservedCount;
		this.hotelName = hotelName;
		this.planId = planId;
		this.planName = planName;
		this.setCheckInDate(checkInDate);
		this.checkOutDate = checkOutDate;
		this.reservedCount = reservedCount;
		this.price = price;
	}
	
	public ReservationBean() {
		
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public ArrayList<Date> getStayDate() {
		return getStayDate();
	}

	public void setStayDate(ArrayList<Date> stayDate) {
	}

	public int getReservedCount() {
		return reservedCount;
	}

	public void setReservedCount(int reservedCount) {
		this.reservedCount = reservedCount;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getLodgmentDays() {
		return lodgmentDays;
	}

	public void setLodgmentDays(long diffDays) {
		this.lodgmentDays = (int) diffDays;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
}