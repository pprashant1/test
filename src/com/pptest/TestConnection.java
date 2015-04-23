package com.pptest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestConnection {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/queueTest";

	// Database credentials
	static final String USER = "ppuser";
	static final String PASS = "pppwd";

	public Connection getConnection() {
		Connection conn = null;

		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			return null;
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			return null;
		} finally {
			// finally block used to close resources
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (final SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
	}

	public void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			conn = getConnection();
			if (conn != null) {
				System.out.println("Creating statement...");
				stmt = conn.createStatement();
				String sql;
				sql = "SELECT id, first,second,gcd,processed FROM queueTest";
				final ResultSet rs = stmt.executeQuery(sql);

				// STEP 5: Extract data from result set
				while (rs.next()) {
					// Retrieve by column name
					final int first = rs.getInt("first");
					final int second = rs.getInt("second");
					final int gcd = rs.getInt("gcd");

					// Display values
					System.out.print(", first: " + first);
					System.out.print(", second: " + second);
					System.out.println(", gcd: " + gcd);
				}
				// STEP 6: Clean-up environment
				rs.close();
				stmt.close();
				conn.close();
			}
		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (final SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (final SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("Goodbye!");
	}// end main

	public boolean saveGCD(int i1, int i2, int i3) {
		Connection conn = null;
		Statement stmt = null;
		boolean b = false;
		try {

			conn = getConnection();
			if (conn != null) {
				System.out.println("Creating statement...");
				stmt = conn.createStatement();
				String sql;
				sql = "insert into  queueTest (first,second, gcd, processed) values ("
						+ i1 + "," + i2 + "," + i3 + ",0)";
				b = stmt.execute(sql);
				stmt.close();
				conn.close();
				return b;
			}
		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (final SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (final SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		return b;

	}

	public List<Integer> getGCD() {
		Connection conn = null;
		Statement stmt = null;
		final List<Integer> listA = new ArrayList();
		try {
			// STEP 2: Register JDBC driver
			conn = getConnection();
			if (conn != null) {
				System.out.println("Creating statement...");
				stmt = conn.createStatement();
				String sql;
				sql = "SELECT gcd FROM queueTest where processed = 1";
				final ResultSet rs = stmt.executeQuery(sql);

				// STEP 5: Extract data from result set
				while (rs.next()) {
					listA.add(rs.getInt("gcd"));
				}
				// STEP 6: Clean-up environment
				rs.close();
				stmt.close();
				conn.close();
				return listA;
			}
		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (final SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (final SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		return listA;
	}// end main

	public List<Integer> getGCDNumbers() {
		Connection conn = null;
		Statement stmt = null;
		int id = 0;
		final List<Integer> listA = new ArrayList();
		try {
			// STEP 2: Register JDBC driver
			conn = getConnection();
			if (conn != null) {
				System.out.println("Creating statement...");
				stmt = conn.createStatement();
				String sql;
				sql = "SELECT id, first,second,gcd FROM queueTest where processed = 0";
				final ResultSet rs = stmt.executeQuery(sql);

				// STEP 5: Extract data from result set
				while (rs.next()) {
					id = rs.getInt("id");
					listA.add(rs.getInt("first"));
					listA.add(rs.getInt("second"));
				}
				// STEP 6: Clean-up environment
				rs.close();

				sql = "update queueTest processed = 0  where id =" + id;
				stmt.execute(sql);

				stmt.close();
				conn.close();
				return listA;
			}
		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (final SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (final SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		return listA;
	}// end main

	public int getGCDSum() {
		int sum = 0;
		final List<Integer> listA = this.getGCD();

		for (final int temp : listA) {
			sum += temp;
		}
		return sum;
	}
}
