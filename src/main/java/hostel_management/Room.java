package hostel_management;

public class Room {
	private String roomnumber;
	private boolean available = true;
	private String tenant;
	
	
	
	public Room(String roomnumber) {
		this.roomnumber = roomnumber;
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
	
}
