package application;

import java.sql.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ExtraExercise34_01 extends Application
{
    // Utility class for obtaining a DB connection using JDBC.
    public static class DatabaseUtil
    {
    	private static final String URL = "jdbc:mysql://localhost:3306/staff";
        private static final String USER = "root";
        private static final String PASSWORD = "";

        public static Connection getConnection() throws SQLException
        {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    // The Staff model class with properties, setters, getters,
    // and methods to send/retrieve data from MySQL.
    public static class Staff
    {
        private String id;
        private String lastName;
        private String firstName;
        private String mi;
        private String address;
        private String city;
        private String state;
        private String telephone;

        // Constructors
        public Staff() {}

        public Staff(String id, String lastName, String firstName, String mi,
                     String address, String city, String state, String telephone)
        {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.mi = mi;
            this.address = address;
            this.city = city;
            this.state = state;
            this.telephone = telephone;
        }

        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getMi() { return mi; }
        public void setMi(String mi) { this.mi = mi; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getTelephone() { return telephone; }
        public void setTelephone(String telephone) { this.telephone = telephone; }

        // Retrieve a record by ID from MySQL.
        public static Staff getStaffById(String id)
        {
            String sql = "SELECT * FROM Staff WHERE id = ?";
            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql))
            {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next())
                {
                    Staff staff = new Staff();
                    staff.setId(rs.getString("id"));
                    staff.setLastName(rs.getString("lastName"));
                    staff.setFirstName(rs.getString("firstName"));
                    staff.setMi(rs.getString("mi"));
                    staff.setAddress(rs.getString("address"));
                    staff.setCity(rs.getString("city"));
                    staff.setState(rs.getString("state"));
                    staff.setTelephone(rs.getString("telephone"));
                    return staff;
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        // Insert this staff record into the database.
        public boolean insert()
        {
            String sql = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql))
            {
                pstmt.setString(1, getId());
                pstmt.setString(2, getLastName());
                pstmt.setString(3, getFirstName());
                pstmt.setString(4, getMi());
                pstmt.setString(5, getAddress());
                pstmt.setString(6, getCity());
                pstmt.setString(7, getState());
                pstmt.setString(8, getTelephone());
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            return false;
        }

        // Update an existing record in the database.
        public boolean update()
        {
            String sql = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ? WHERE id = ?";
            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql))
            {
                pstmt.setString(1, getLastName());
                pstmt.setString(2, getFirstName());
                pstmt.setString(3, getMi());
                pstmt.setString(4, getAddress());
                pstmt.setString(5, getCity());
                pstmt.setString(6, getState());
                pstmt.setString(7, getTelephone());
                pstmt.setString(9, getId());
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public void start(Stage primaryStage)
    {
        // Label for displaying messages to the user.
        Label messageLabel = new Label("Welcome! Enter an ID and choose an action.");

        // Create text fields with labels.
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();

        Label miLabel = new Label("MI:");
        TextField miField = new TextField();

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();

        Label cityLabel = new Label("City:");
        TextField cityField = new TextField();

        Label stateLabel = new Label("State:");
        TextField stateField = new TextField();

        Label telephoneLabel = new Label("Telephone:");
        TextField telephoneField = new TextField();

        // Create buttons for operations.
        Button viewButton = new Button("View");
        Button insertButton = new Button("Insert");
        Button updateButton = new Button("Update");
        Button clearButton = new Button("Clear");

        // "View" button: Retrieve record from MySQL and populate fields.
        viewButton.setOnAction
        (e -> {
            String id = idField.getText().trim();
            Staff staff = Staff.getStaffById(id);
            if (staff == null)
            {
                messageLabel.setText("Record not found.");
                lastNameField.clear();
                firstNameField.clear();
                miField.clear();
                addressField.clear();
                cityField.clear();
                stateField.clear();
                telephoneField.clear();
            }
            else
            {
                lastNameField.setText(staff.getLastName());
                firstNameField.setText(staff.getFirstName());
                miField.setText(staff.getMi());
                addressField.setText(staff.getAddress());
                cityField.setText(staff.getCity());
                stateField.setText(staff.getState());
                telephoneField.setText(staff.getTelephone());
                messageLabel.setText("Record found.");
            }
        });

        // "Insert" button: Create a new Staff record and insert it.
        insertButton.setOnAction
        (e -> {
            Staff staff =
            new Staff(
            	idField.getText().trim(),
                lastNameField.getText().trim(),
                firstNameField.getText().trim(),
                miField.getText().trim(),
                addressField.getText().trim(),
                cityField.getText().trim(),
                stateField.getText().trim(),
                telephoneField.getText().trim()
            );

            if (staff.insert())
                messageLabel.setText("Record inserted.");
            else
                messageLabel.setText("Failed to insert record.");
        });

        // "Update" button: Update an existing record in the DB.
        updateButton.setOnAction
        (e -> {
            Staff staff =
            new Staff(
                idField.getText().trim(),
                lastNameField.getText().trim(),
                firstNameField.getText().trim(),
                miField.getText().trim(),
                addressField.getText().trim(),
                cityField.getText().trim(),
                stateField.getText().trim(),
                telephoneField.getText().trim()
            );
        
            if (staff.update())
                messageLabel.setText("Record updated.");
            else
                messageLabel.setText("Record not found.");
        });

        // "Clear" button: Clear all text fields and the message.
        clearButton.setOnAction
        (e -> {
            idField.clear();
            lastNameField.clear();
            firstNameField.clear();
            miField.clear();
            addressField.clear();
            cityField.clear();
            stateField.clear();
            telephoneField.clear();
            messageLabel.setText("All fields cleared.");
        });

        // Arrange the text fields in a GridPane.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0, 3, 1);

        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(firstNameLabel, 2, 1);
        gridPane.add(firstNameField, 3, 1);
        gridPane.add(miLabel, 4, 1);
        gridPane.add(miField, 5, 1);

        gridPane.add(addressLabel, 0, 2);
        gridPane.add(addressField, 1, 2, 5, 1);

        gridPane.add(cityLabel, 0, 3);
        gridPane.add(cityField, 1, 3);
        gridPane.add(stateLabel, 2, 3);
        gridPane.add(stateField, 3, 3);

        gridPane.add(telephoneLabel, 0, 4);
        gridPane.add(telephoneField, 1, 4);

        // Arrange the buttons in an HBox.
        HBox buttonBox = new HBox(10, viewButton, insertButton, updateButton, clearButton);

        // Main layout: VBox stacking the message label, grid, and button row.
        VBox mainLayout = new VBox(10, messageLabel, gridPane, buttonBox);
        mainLayout.setStyle("-fx-padding: 15;");

        Scene scene = new Scene(mainLayout, 700, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Staff Database App");
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
