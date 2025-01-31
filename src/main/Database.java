package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Database {
	GamePanel gp; // gamepanel cus duh we need it
	LocalDate todayDate = LocalDate.now();

	// path to sqlite db
	private final String URL = "jdbc:sqlite:E:/eclipse-workspace/BhaagKanchha/res/db/bhaagKanchha.db";

	public Database(GamePanel gp) {
		this.gp = gp;
	}

	// craxy method to establish a connection.. i wish we connected like this
	public Connection connectToDatabase() {
		Connection connection = null;
		try {

			connection = DriverManager.getConnection(URL); // get connection
	
		} catch (SQLException e) { // if something happens catch it
			System.err.println("couldn't connect to sqlite db");
			e.printStackTrace();
		}
		return connection;
	}

	public void signUP() {

		// CHECKING POSSIBLE ERRORS

		// missing inputs error
		if (gp.ui.username == "placeholderUsername" || gp.ui.password == "placeholderPassword"
				|| gp.ui.passwordAgain == "placeholderPasswordAgain" || gp.ui.security == "placeholderSecurity") {
			gp.ui.errorMessage = "Please fill all input fields.";
			return;
		}

		// invalid input format (we have restricted some key inputs in specific inputs
		// but still)
		if (!gp.ui.username.matches("[a-zA-Z0-9_]+")) {
			gp.ui.errorMessage = "Invalid Username";
			gp.ui.commandNum = 0;
			return;
		}

		// password not matching retyped password
		if (!gp.ui.password.equals(gp.ui.passwordAgain)) {
			gp.ui.errorMessage = "Passwords didn't match.";
			gp.ui.commandNum = 1;
			return;
		}

		// checking username length
		if (gp.ui.username.length() > 19) {
			gp.ui.errorMessage = "Username too long.";
			gp.ui.commandNum = 0;
			return;
		}
		// checking password length
		if (gp.ui.password.length() < 8) {
			gp.ui.errorMessage = "Password too short.";
			gp.ui.commandNum = 1;
			return;
		} else if (gp.ui.password.length() > 19) {
			gp.ui.errorMessage = "Password too long.";
			gp.ui.commandNum = 1;
			return;
		}

		Connection connection = connectToDatabase(); // connect to db
		// if username already exists
		String query = "SELECT COUNT(*) FROM Players WHERE username = ?";
		try (PreparedStatement checkExistingPlayer = connection.prepareStatement(query)) {
			checkExistingPlayer.setString(1, gp.ui.username);
			ResultSet resultSet = checkExistingPlayer.executeQuery();

			// CHECK IF THAT USER EXISTS
			if (resultSet.getInt(1) > 0) {
		
				gp.ui.errorMessage = "Username already taken.";
				gp.ui.commandNum = 0;
				connection.close();
				return;
			}

		} catch (SQLException e) {
			System.err.println("Error while checking for duplicate username: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		query = "INSERT INTO Players (playerID, username, password, security, registrationDate) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement pStatement = connection.prepareStatement(query)) {
			pStatement.setString(2, gp.ui.username);
			pStatement.setString(3, gp.ui.password);
			pStatement.setString(4, gp.ui.security);
			pStatement.setString(5, todayDate + "");

			int rowsAffected = pStatement.executeUpdate();
			if (rowsAffected > 0) {
				gp.gameState = gp.loginState;
				gp.ui.errorMessage = "You are registered now.";
				gp.ui.commandNum = 0;
				gp.ui.resetInputFields();
			} else {
				gp.ui.errorMessage = "Invalid Signup";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void login() {
		// CHECKING POSSIBLE ERRORS

		// Missing inputs error
		if (gp.ui.username.equals("placeholderUsername") || gp.ui.password.equals("placeholderPassword")) {
			gp.ui.errorMessage = "Please fill all input fields.";
			gp.ui.commandNum = 0;
			return;
		}

		Connection connection = connectToDatabase(); // Connect to database

		// Check if user exists
		String query = "SELECT playerID, password FROM Players WHERE username = ?";
		try (PreparedStatement checkUser = connection.prepareStatement(query)) {
			checkUser.setString(1, gp.ui.username);
			ResultSet resultSet = checkUser.executeQuery();

			if (resultSet.next()) { // If user exists
				String storedPassword = resultSet.getString("password");
				if (storedPassword.equals(gp.ui.password)) { // Check if password matches
	

					// Set logged-in user's ID as currentUserID
					gp.currentUserID = String.valueOf(resultSet.getInt("playerID"));
					gp.currentUsername = gp.ui.username;

					// Reset input fields
					gp.ui.resetInputFields();
					gp.config.saveConfig(); // Save config
					gp.config.loadConfig();
					gp.ui.commandNum = 0;
					gp.ui.cursorZoom = 0; // to fix a bug(log out appearing bigger)
					gp.gameState = gp.titleState;
				} else {
					gp.ui.errorMessage = "Incorrect password. Try again.";
					gp.ui.commandNum = 1;
				}
			} else {
				gp.ui.errorMessage = "User " + gp.ui.username + " doesn't exist.";
				gp.ui.commandNum = 0;
			}
		} catch (SQLException e) {
			System.err.println("Error while logging in: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void logout() {
		gp.currentUserID = null;
		gp.config.saveConfig(); // config is saved
		gp.gameState = gp.loginState;
	}

	public String getUsername() {
		if (gp.currentUserID == null || gp.currentUserID.isEmpty()) {
			return null;
		}

		String query = "SELECT username FROM Players WHERE playerID = ?";
		try (Connection connection = connectToDatabase();
				PreparedStatement gettingUsername = connection.prepareStatement(query)) {

			gettingUsername.setString(1, gp.currentUserID);
			ResultSet resultSet = gettingUsername.executeQuery();

			if (resultSet.next()) {
				String username = resultSet.getString("username");
	
				return username;
			} else {
				System.err.println("No username found for playerID: " + gp.currentUserID);
				return null;
			}

		} catch (SQLException e) {
			System.err.println("Error while retrieving username: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public int getPlayerID(String username) {
		Connection connection = connectToDatabase();
		String query = "SELECT playerID FROM Players WHERE username = ?";
		try (PreparedStatement gettingID = connection.prepareStatement(query)) {
			gettingID.setString(1, username);
			ResultSet resultSet = gettingID.executeQuery();
			resultSet.next();
			return resultSet.getInt(1); // returns id

		} catch (SQLException e) {
			System.err
					.println("Something unexpected happened while checking if the password matches: " + e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	
	public void storeGameData(String  playerID, int mapID, int score) {

	    String query = "INSERT INTO completedGames (playerID, mapID, score, gameDate) VALUES (?, ?, ?, ?)";

	  
	    try (Connection connection = connectToDatabase();
	         PreparedStatement stmt = connection.prepareStatement(query)) {


	        stmt.setString(1, playerID);    
	        stmt.setInt(2, mapID);       
	        stmt.setInt(3, score);       
	        stmt.setString(4, todayDate + ""); 

	
	        int rowsAffected = stmt.executeUpdate();

	        // Check if the data was inserted successfully
	        if (rowsAffected > 0) {
	            System.out.println("Game data successfully stored!");
	        } else {
	            System.out.println("Failed to store game data.");
	        }

	    } catch (SQLException e) {
	        System.err.println("Error while storing game data: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}
