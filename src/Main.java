package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Employment Application");

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setVgap(10);
        grid.setHgap(10);

        // Company Logo
        ImageView logo = new ImageView(new Image("file:resources/company_logo.png"));
        logo.setFitHeight(80);
        logo.setFitWidth(200);
        grid.add(logo, 0, 0, 2, 1);

        // Full Name
        Label lblName = new Label("Full Name:");
        TextField txtName = new TextField();
        grid.add(lblName, 0, 1);
        grid.add(txtName, 1, 1);

        // Contact Number
        Label lblContact = new Label("Contact Number:");
        TextField txtContact = new TextField();
        grid.add(lblContact, 0, 2);
        grid.add(txtContact, 1, 2);

        // Education
        Label lblEdu = new Label("Highest Education:");
        ComboBox<String> cmbEdu = new ComboBox<>();
        cmbEdu.getItems().addAll("Masters", "Bachelors", "College Diploma");
        grid.add(lblEdu, 0, 3);
        grid.add(cmbEdu, 1, 3);

        // DOB
        Label lblDOB = new Label("Date of Birth:");
        DatePicker datePicker = new DatePicker();
        grid.add(lblDOB, 0, 4);
        grid.add(datePicker, 1, 4);

        // Salary
        Label lblSalary = new Label("Salary:");
        TextField txtSalary = new TextField();
        grid.add(lblSalary, 0, 5);
        grid.add(txtSalary, 1, 5);

        // Company Name
        Label lblCompany = new Label("Company Name:");
        TextField txtCompany = new TextField();
        grid.add(lblCompany, 0, 6);
        grid.add(txtCompany, 1, 6);

        // Position
        Label lblPosition = new Label("Position:");
        TextField txtPosition = new TextField();
        grid.add(lblPosition, 0, 7);
        grid.add(txtPosition, 1, 7);

        // Submit Button
        Button btnSubmit = new Button("Submit Application");
        grid.add(btnSubmit, 1, 8);

        btnSubmit.setOnAction(e -> {
            String name = txtName.getText().trim();
            String contact = txtContact.getText().trim();
            String edu = cmbEdu.getValue();
            LocalDate dob = datePicker.getValue();
            String salaryStr = txtSalary.getText().trim();
            String company = txtCompany.getText().trim();
            String position = txtPosition.getText().trim();

            // Validation
            if (!name.matches("^[a-zA-Z\\s]{1,50}$")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Name", "Only letters, up to 50 characters.");
                return;
            }
            if (!contact.matches("^\\d{10}$")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Contact", "Must be exactly 10 digits.");
                return;
            }
            if (edu == null) {
                showAlert(Alert.AlertType.ERROR, "Invalid Education", "Please select education.");
                return;
            }
            if (dob == null) {
                showAlert(Alert.AlertType.ERROR, "Invalid Date", "Please select a date.");
                return;
            }
            if (!salaryStr.matches("^\\d{1,8}\\.\\d{2}$")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Salary", "Format: up to 8 digits and 2 decimals.");
                return;
            }
            if (company.isEmpty() || position.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Missing Fields", "Company name and position required.");
                return;
            }

            // DB Insert
            try (Connection conn = DBUtil.getConnection()) {
                conn.setAutoCommit(false);

                String sqlApplicant = "INSERT INTO ApplicantTable (fullname, contact_number, highest_education, dob, salary) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psApplicant = conn.prepareStatement(sqlApplicant, Statement.RETURN_GENERATED_KEYS);
                psApplicant.setString(1, name);
                psApplicant.setString(2, contact);
                psApplicant.setString(3, edu);
                psApplicant.setDate(4, Date.valueOf(dob));
                psApplicant.setBigDecimal(5, new java.math.BigDecimal(salaryStr));
                psApplicant.executeUpdate();

                ResultSet rs = psApplicant.getGeneratedKeys();
                int applicantId = 0;
                if (rs.next()) {
                    applicantId = rs.getInt(1);
                }

                String sqlEmployment = "INSERT INTO EmploymentTable (applicant_id, company_name, position) VALUES (?, ?, ?)";
                PreparedStatement psEmployment = conn.prepareStatement(sqlEmployment);
                psEmployment.setInt(1, applicantId);
                psEmployment.setString(2, company);
                psEmployment.setString(3, position);
                psEmployment.executeUpdate();

                conn.commit();

                showAlert(Alert.AlertType.INFORMATION, "Success", "Application submitted successfully!");
                txtName.clear();
                txtContact.clear();
                cmbEdu.setValue(null);
                datePicker.setValue(null);
                txtSalary.clear();
                txtCompany.clear();
                txtPosition.clear();

            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(grid, 550, 500);
        stage.setScene(scene);
        stage.show();
    }

    // ✅ Alert method should be outside of start()
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // ✅ Main method to launch JavaFX
    public static void main(String[] args) {
        launch(args);
    }
}
