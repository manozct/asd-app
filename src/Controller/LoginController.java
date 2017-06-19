package Controller;

import com.asd.framework.DatabaseConnection.Db.DatabaseType;
import com.asd.framework.DatabaseConnection.Db.DbAccess;
import com.asd.framework.DatabaseConnection.Db.DbConnection;
import com.asd.framework.LoginAuthorization.Proxy.LoginAuthorizationProxy;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by manozct on 6/17/2017.
 */
public class LoginController extends Application {

    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Hyperlink hlinkRegister;
    private FXMLLoader loader;
    private Stage primaryStage;
    private String role;
    private HashMap<String,List<Menu>>hmapControl=new HashMap<>();
    private List<Menu> lstShowControl=new ArrayList<>();
    DashBoard dashBoard=new DashBoard();

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("../View/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("LoginController");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    @Override
    public void init() throws Exception {
        super.init();
        getConnection();


    }

    @FXML
    public void initialize() {
        //  List<String> roles = Arrays.asList("Admin", "Doctor", "Nurse", "Patient");
        // Role role = new Role(roles);
        // cmbRole.getItems().addAll(role.getRoles());

    }

    @FXML
    public void loginAction(ActionEvent actionEvent) {
        String userName = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();
        if(checkAuthentication(userName,password)){
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/MainDashBoard.fxml"));
            try {

                stage = new Stage();
                Home home = new Home();
                stage.setScene(new Scene(fxmlLoader.load()));
                //lstShowControl=dashBoard.lstShowControl();
               // hmapControl.put(role,lstShowControl);

                //checkAuthorization(hmapControl);
                stage.show();
               /* dashBoard=new DashBoard();*/

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        //LoginAuthorizationProxy loginAuthorizationProxy = new LoginAuthorizationProxy(userName, password);


    }
    public void checkAuthorization(HashMap<String,List<Menu>>hmp){
        LoginAuthorizationProxy loginAuthorizationProxy=new LoginAuthorizationProxy();
       /* loginAuthorizationProxy.hideAllControls(dashBoard.lstAllMenu());
        loginAuthorizationProxy.authorizationCheck(hmp);*/

    }
    public boolean checkAuthentication(String userName,String password){
        boolean isAuthenticate=false;
        ResultSet rs = DbAccess.table("user").select("Email", "Password","Role").where("Email",userName).orWhere("Password",password).get();
        int count=0;
        try {
            while(rs.next()){
                count++;
                role=rs.getString(3);
                System.out.println(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(count>0){
            isAuthenticate=true;
        }
        return isAuthenticate;

    }
    public void getConnection() {
        try {
            DbConnection.getCOnnection(DatabaseType.MySql, "localhost",
                    3306, "das", "root", "1234");
            //Connection conn = DbConnection.dbConnectionObj.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clickRegisterAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/Signup.fxml"));
        try {

            Stage stage = new Stage();

            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
