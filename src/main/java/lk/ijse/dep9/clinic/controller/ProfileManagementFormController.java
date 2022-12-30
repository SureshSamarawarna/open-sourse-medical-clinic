package lk.ijse.dep9.clinic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileManagementFormController {
    public TableView tblUsers;
    public Button btnNew;
    public Button btnModify;
    public Button btnDelete;

    public void btnNewOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Open Source MEDICARE: New Profile");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ProfileForm.fxml"))));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(btnNew.getScene().getWindow());
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Open Source MEDICARE: Edit Profile");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ProfileForm.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        ProfileFormController controller = (ProfileFormController)(fxmlLoader.getController());
        controller.initData(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(btnNew.getScene().getWindow());
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }
}
