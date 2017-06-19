package Controller;

import Model.Category;
import Model.User;
import Service.CategoryService;
import Service.UserService;
import com.asd.framework.DataValidation.Context.ValidationContext;
import com.asd.framework.DataValidation.ValidationConstraint;
import com.asd.framework.DatabaseConnection.Db.DatabaseType;
import com.asd.framework.DatabaseConnection.Db.DbConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by manozct on 6/18/2017.
 */
public class UserController extends Application {
    @FXML
    private TextField txtContact;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TextField txtName;

    @FXML
    private DatePicker txtDOB;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private Button btnCreateAccount;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label lblCategory;
    ListView<String> lstViewErrors = new ListView<>();
    Stage popup = new Stage();


    @FXML
    void createAccountAction(ActionEvent event) {

        if (checkValidation()) {
            User user = new User();
            user.setName(txtName.getText());
            user.setAddress(txtAddress.getText());
            user.setDOB(Date.valueOf(txtDOB.getValue()));
            user.setContact(txtContact.getText());
            user.setRole(cmbRole.getSelectionModel().getSelectedItem().toString());
            if (cmbCategory.getSelectionModel().getSelectedItem() == null) {
                user.setCategory("Empty");
            } else {
                user.setCategory(cmbCategory.getSelectionModel().getSelectedItem().toString());

            }
            user.setEmail(txtEmail.getText().trim());
            user.setPassword(txtPassword.getText().trim());
            UserService userService = new UserService();
            userService.insert(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Account created sucessfully..");
            alert.showAndWait();
            Stage stage = (Stage) btnCreateAccount.getScene().getWindow();
            stage.close();
        }


    }

    public boolean checkValidation() {
        boolean isValid = true;
        ValidationContext validationContext = new ValidationContext();
        validationContext.addValidationConstraint(txtName, Arrays.asList(ValidationConstraint.REQUIRED));
        validationContext.addValidationConstraint(txtAddress, Arrays.asList(ValidationConstraint.REQUIRED));
        validationContext.addValidationConstraint(txtDOB, Arrays.asList(ValidationConstraint.REQUIRED, ValidationConstraint.DATE));
        validationContext.addValidationConstraint(txtContact, Arrays.asList(ValidationConstraint.REQUIRED, ValidationConstraint.NUMBER));
        validationContext.addValidationConstraint(cmbRole, Arrays.asList(ValidationConstraint.REQUIRED));
        validationContext.addValidationConstraint(txtEmail, Arrays.asList(ValidationConstraint.REQUIRED, ValidationConstraint.EMAIL));
        validationContext.addValidationConstraint(txtPassword, Arrays.asList(ValidationConstraint.REQUIRED, ValidationConstraint.ALPHANUMERIC));
        validationContext.checkValidate();
        if (!validationContext.isValid()) {
            List<String> errors = validationContext.getErrors();
            ObservableList<String> obErrors = FXCollections.observableArrayList(errors);
            lstViewErrors.setItems(obErrors);
            Scene scene3 = new Scene(new VBox(lstViewErrors));
            popup.setScene(scene3);
            popup.setHeight(360);
            popup.setWidth(430);
            popup.setTitle("Validation Errors");
            popup.showAndWait();
            isValid = false;
        }
        return isValid;


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Signup.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toString());
        primaryStage.setTitle("UserController Registration");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void init() throws Exception {
        super.init();
        //getConnection();


    }

    @FXML
    public void initialize() {
        cmbRole.getItems().addAll("Patient", "Doctor");
        CategoryService categoryService = new CategoryService();
        List<Category> lstCategory = categoryService.getAll();
        for (Category c : lstCategory) {
            cmbCategory.getItems().add(c.getCategoryName());
        }


    }

    public void cmbRoleChanged(ActionEvent actionEvent) {
        if (cmbRole.getSelectionModel().getSelectedItem().equals("Patient")) {
            cmbCategory.setVisible(false);
            lblCategory.setVisible(false);

        }
        if (cmbRole.getSelectionModel().getSelectedItem().equals("Doctor")) {
            cmbCategory.setVisible(true);
            lblCategory.setVisible(true);
        }
    }

   /* public void getConnection() {
        try {
            DbConnection.getCOnnection(DatabaseType.MySql, "localhost",
                    3306, "appointmentsystem", "root", "root");
            //Connection conn = DbConnection.dbConnectionObj.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

}
