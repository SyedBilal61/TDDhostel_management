package hostel_management;

public class Room {
	private String roomnumber;
	private boolean available = true;
	
	public Room(String roomnumber) {
		this.roomnumber = roomnumber;
	}
	public boolean isAvailable() {
		return available;
	}
}
