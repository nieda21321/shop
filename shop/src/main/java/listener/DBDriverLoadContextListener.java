package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * 
 *
 */
@WebListener
public class DBDriverLoadContextListener implements ServletContextListener {

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         
    	try {
    		
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Oracle DB Driver Load SUCCESS");
		} catch (ClassNotFoundException e) {
			
			System.out.println("Oracle DB Driver Load FAILED");
			e.printStackTrace();
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 

    	
    }
	
}
