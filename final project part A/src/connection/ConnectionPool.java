package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exeptions.couponExeptions;

public class ConnectionPool {

	private Set<Connection> connections = new HashSet<>();
	private static final int MaxConnections = 5;
	private boolean isActive;
	private String url = "jdbc:mysql://localhost:3306/project";
	private String user = "root";
	private String password = "lidorsh1";
	private static ConnectionPool instance;

	
	static {
		
		try {
			instance = new ConnectionPool();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this method add connection and connect to the database
	 * @throws SQLException
	 */
	private ConnectionPool() throws SQLException {
		for (int i = 0; i < MaxConnections; i++) {
			this.connections.add(DriverManager.getConnection(url, user, password));
		}
		isActive = true;
	}

	public static ConnectionPool getInstance() {
	
		return instance;

	}

	/**
	 * this method getting the connection and if fail throw massage if the connection
	 * @return
	 * @throws couponExeptions
	 */
	public synchronized Connection getConnection() throws couponExeptions {
		if (!isActive) {
			throw new couponExeptions("getConnection faild - connection pool not active");

		}
		while (this.connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new couponExeptions("wait faid");
			}
		}
		//System.out.println(Thread.currentThread().getName()+ "got connection");
		Iterator<Connection> it = this.connections.iterator();
		Connection con = it.next();
		it.remove();
		return con;

	}

	public synchronized void restoreConnection(Connection connection) {
		this.connections.add(connection);
		notify();
	}

	public synchronized void closeAllConnections() throws couponExeptions {
		this.isActive = false;
		while (this.connections.size() < MaxConnections) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (Connection connection : connections) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new couponExeptions("closeAllConnections failure",e);
			}
		}
			
		}
	public  int getCoSize() {
		
		return connections.size();
	}

}
