package lk.ijse.dep9.clinic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SettingsFormController {
    public AnchorPane pneContainer;
    public Button btnAbout;
    public Button btnPassword;
    public Button btnManageFields;
    public Button btnDiscount;
    public Button btnHospitalFee;

    public void initialize(){

    }

    public void btnHospitalFeeOnAction(ActionEvent actionEvent) {
        pneContainer.getChildren().clear();
    }

    public void btnDiscountOnAction(ActionEvent actionEvent) {
        pneContainer.getChildren().clear();
    }

    public void btnPasswordOnAction(ActionEvent actionEvent) {
        pneContainer.getChildren().clear();
    }

    public void btnAboutOnAction(ActionEvent actionEvent) {
        pneContainer.getChildren().clear();
    }

    public void btnManageFieldsOnAction(ActionEvent actionEvent) throws IOException {
        pneContainer.getChildren().clear();
        AnchorPane manageFieldsForm = FXMLLoader.load(getClass().getResource("/view/ManageFieldsForm.fxml"));
        pneContainer.getChildren().add(manageFieldsForm);
    }
}
