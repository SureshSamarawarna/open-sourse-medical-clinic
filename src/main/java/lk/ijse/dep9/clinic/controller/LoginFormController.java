package lk.ijse.dep9.clinic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.dep9.clinic.misc.CryptoUtil;
import lk.ijse.dep9.clinic.security.SecurityContextHolder;
import lk.ijse.dep9.clinic.security.User;
import lk.ijse.dep9.clinic.security.UserRole;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button btnLogIn;

    public void initialize(){
        btnLogIn.setDefaultButton(true);
    }

    public void btnLogInOnAction(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        /* Let's implement some validation logic here */
        if (username.isBlank()){
            new Alert(Alert.AlertType.ERROR, "Username can't be empty").show();
            txtUsername.requestFocus();
            txtUsername.selectAll();
            return;
        }else if (password.isBlank()){
            new Alert(Alert.AlertType.ERROR, "Password can't be empty").show();
            txtPassword.requestFocus();
            txtPassword.selectAll();
            return;
        }else if (!username.matches("^[A-Za-z0-9]+$")){
            new Alert(Alert.AlertType.ERROR, "Invalid login credentials").show();
            txtUsername.requestFocus();
            txtUsername.selectAll();
            return;
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection = DriverManager.
                getConnection("jdbc:mysql://localhost:3306/medical_clinic", "root", "MORA@spsa8")){

            String sql = "SELECT role, password FROM User WHERE username=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rst = stm.executeQuery();

            if (rst.next()){
                String cipherText = rst.getString("password");

                /* Let's verify the password */
                if (!CryptoUtil.getSha256Hex(password).equals(cipherText)){
                    new Alert(Alert.AlertType.ERROR, "Invalid login credentials").show();
                    txtUsername.requestFocus();
                    txtUsername.selectAll();
                    return;
                }
                String role = rst.getString("role");
                SecurityContextHolder.setPrincipal(new User(username, UserRole.valueOf(role)));
                Scene scene = null;
                switch (role){
                    case "Admin":
                        scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/AdminDashboardForm.fxml")));
                        break;
                    case "Doctor":
                        scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/DoctorDashboardForm.fxml")));
                        break;
                    default:
                        scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/ReceptionistDashboardForm.fxml")));
                }
                Stage stage = new Stage();
                stage.setTitle("Open Source MEDICARE");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();

                btnLogIn.getScene().getWindow().hide();
            }else{
                new Alert(Alert.AlertType.ERROR, "Invalid login credentials").show();
                txtUsername.requestFocus();
                txtUsername.selectAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to connect with the database, try again").show();
        }
    }
}
