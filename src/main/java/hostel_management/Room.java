package hostel_management;

public class Room {
	
	
	private String roomNumber;
	private boolean available = true;
	private String tenant;
	
	
	
	public Room(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public boolean isAvailable() {
		return available;
	}
	
	public void assignTenant (String tenantName) {
		if (!available) {
			throw new IllegalStateException ("Room already occupied");
		}
		this.tenant=tenantName;
		this.available= false;		
	}
	public void vacate() {
		this.tenant=null;
		this.available=true;
	}
	public String getTenant() {
		return tenant;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	
	
	@Override
	public String toString() {
		if (tenant == null || tenant.isEmpty()) {
			return roomNumber + " (Empty)" ; 
			}
		return roomNumber + tenant;	
	}
	
	
	}
	
