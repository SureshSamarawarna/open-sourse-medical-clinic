package lk.ijse.dep9.clinic.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lk.ijse.dep9.clinic.entity.Field;

import java.sql.*;

public class ManageFieldsFormController {
    public TextField txtField;
    public Button btnSave;
    public ListView<Field> lstFields;
    public Button btnNew;
    public Button btnDelete;

    public void initialize(){
        Platform.runLater(txtField::requestFocus);
        loadAllFieldsFromDB();
    }

    private void loadAllFieldsFromDB(){
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_clinic", "root", "MORA@spsa8")){
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Field");
            lstFields.getItems().clear();
            while (rst.next()){
                int id = rst.getInt("id");
                String description = rst.getString("description");
                Field field = new Field(id, description);
                lstFields.getItems().add(field);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load fields").show();
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String field = txtField.getText();

        /* Data Validation */
        if (field.isBlank()){
            new Alert(Alert.AlertType.ERROR, "Field description can't be empty").show();
            txtField.requestFocus();
            txtField.selectAll();
            return;
        }else if (!field.matches("[A-Za-z ]+")){
            new Alert(Alert.AlertType.ERROR, "Invalid field description").show();
            txtField.requestFocus();
            txtField.selectAll();
            return;
        }

        /* Business Validation */
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_clinic", "root", "mysql")) {
            PreparedStatement stm1 = connection.
                    prepareStatement("SELECT * FROM Field WHERE description=?");
            stm1.setString(1, field);
            ResultSet rst = stm1.executeQuery();
            if (rst.next()){
                new Alert(Alert.AlertType.ERROR, "Field already exists").show();
                txtField.requestFocus();
                txtField.selectAll();
            }else {
                PreparedStatement stm2 = connection.
                        prepareStatement("INSERT INTO Field (description) VALUES (?)",
                                Statement.RETURN_GENERATED_KEYS);
                stm2.setString(1, field);
                stm2.executeUpdate();
                ResultSet generatedKeys = stm2.getGeneratedKeys();
                generatedKeys.next();
                int id = generatedKeys.getInt(1);

                lstFields.getItems().add(new Field(id, field));

                txtField.clear();
                txtField.requestFocus();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the field").show();
            e.printStackTrace();
        }
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }
}
