package lk.ijse.dep9.clinic.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lk.ijse.dep9.clinic.security.UserRole;

public class ProfileFormController {
    public Label lblTitle;
    public Label lblSubTitle;
    public ComboBox<String> cmbUserRole;
    public TextField txtFullName;
    public TextField txtUserName;
    public ComboBox<String> cmbField;
    public ComboBox<String> cmbGender;
    public TextField txtNic;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtPassword;
    public Button btnSave;
    public HBox hBoxField;
    public VBox container;

    public void initialize(){
        UserRole[] roles = UserRole.values();
        for (UserRole role : roles) {
            cmbUserRole.getItems().add(role.toString());
        }

        cmbGender.getItems().add("Male");
        cmbGender.getItems().add("Female");
        container.getChildren().remove(hBoxField);
        Platform.runLater(cmbUserRole::requestFocus);
    }

    public void initData(boolean newProfile){
        if (!newProfile){
            lblTitle.setText("Edit Profile");
            lblSubTitle.setText("Update existing profile details");
        }
    }

    public void cmbUserRoleOnAction(ActionEvent actionEvent) {
        if (cmbUserRole.getSelectionModel().getSelectedItem().equals("Doctor")){
            container.getChildren().add(3, hBoxField);
        }else{
            container.getChildren().remove(hBoxField);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }
}
