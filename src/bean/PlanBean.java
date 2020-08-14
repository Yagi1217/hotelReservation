package bean;

//import java.util.Date;
import java.sql.Date;


public class PlanBean {
	private String hotelName;
	private String hotelAddress;
	private Date checkInDate;
	private Date checkOutDate;
	private int planId;
	private int hotelId;
	private String planName;
	private int planPrice;
	private int roomQuantity;
	private String roomType;
	
	public PlanBean() {
	
	}
	public PlanBean(String hotelName, String hotelAddress, Date checkInDate, Date checkOutDate, int planId, int hotelId,
			String planName, int planPrice, int roomQuantity, String roomType) {
		this.hotelName = hotelName;
		this.hotelAddress = hotelAddress;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.planId = planId;
		this.hotelId = hotelId;
		this.planName = planName;
		this.planPrice = planPrice;
		this.roomQuantity = roomQuantity;
		this.roomType = roomType;

	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelAddress() {
		return hotelAddress;
	}

	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
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

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public int getPlanPrice() {
		return planPrice;
	}

	public void setPlanPrice(int planPrice) {
		this.planPrice = planPrice;
	}

	public int getRoomQuantity() {
		return roomQuantity;
	}

	public void setRoomQuantity(int roomQuantity) {
		this.roomQuantity = roomQuantity;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
}
