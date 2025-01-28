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
			System.out.println("sqlite db connected");
			System.out.println(todayDate + "");
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
			gp.ui.errorMessage = "Username can only have letters and numbers.";
			return;
		}
		
		// password not matching retyped password
		if(!gp.ui.password.equals(gp.ui.passwordAgain) ) {
			gp.ui.errorMessage = "Passwords didn't match.";
			return;
		}
		
		// checking username length
		if(gp.ui.username.length() > 19) {
			gp.ui.errorMessage = "Username should not contain more than 19 characters.";
			return;
		}
		// checking password length
		if(gp.ui.password.length() < 8) {
			gp.ui.errorMessage = "Password should contain at least 8 characters.";
			return;
		} else if (gp.ui.password.length() > 19) {
			gp.ui.errorMessage = "Password too long.";
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
				System.out.println("no. duplicates: " + resultSet.getInt(1));
				gp.ui.errorMessage = "Someone has already taken that username";
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

}
