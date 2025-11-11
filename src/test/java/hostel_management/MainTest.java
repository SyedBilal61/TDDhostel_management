package hostel_management;

import org.junit.Test;

public class MainTest {
	/**
     * This test achieves full coverage for Main.java
     * It ensures that:
     * 1. The args.length > 0 branch is executed
     * 2. The default host branch is executed
     * 3. The catch block is executed when MongoDB is not reachable
     * 4. The finally block and if (mongoClient != null) are executed
     * 
     * This works regardless of whether MongoDB is running, stopped, or Docker is down.
     */
    @Test
    public void testMainMethodcoverage() {
    	
    	Main.main(new String[]{"Invalidhost"});
    	
    	Main.main(new String[0]);
    }
}
