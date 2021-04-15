/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import Mode.Accout;
import Mode.NhanVien;
import Mode.NhanVienCRUD;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Adminss
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txt_user;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Button btn_login;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void hanlelogin(ActionEvent event) {
        String user=txt_user.getText();
        String password=txt_password.getText();
        NhanVienCRUD login = new NhanVienCRUD();
        NhanVien nv = new NhanVien();
        nv.setUserid(user);
        nv.setPassword(password);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        try{
            if(login.kiemtra(user,password))
            {    
                Accout.fullname= login.Getname(user);
                Accout.userid = login.GetMaNV(user);
                Accout.role = login.getRoll(nv);
                alert.setTitle("Thông tin đăng nhập");
                alert.setHeaderText("Đăng nhập thành công");
                if (login.getRoll(nv) == 0) {
                alert.setContentText("Bạn đang đăng nhập với quyền ADMIN");
                alert.setHeaderText("Xin Chào : "+Accout.fullname);
                } else {
                alert.setContentText("Bạn đang đăng nhập với quyền USER");
                alert.setHeaderText("Xin Chào : "+Accout.fullname);
                }
                
                alert.showAndWait();
                Stage appStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                Parent root3 = FXMLLoader.load(getClass().getResource("/view/ImportGoods.fxml"));
                Scene scene = new Scene(root3);
                appStage.setScene(scene);
                appStage.show();
            }
            else
            {                
                alert.setTitle("Thông báo");
                alert.setHeaderText("Sai đăng nhập");
                alert.setContentText("Bạn nhập sai user name hoặc password rồi\n"
                                   + "Bạn có muốn đăng ký mới không?");
                Optional<ButtonType> opt = alert.showAndWait();
                if(opt.get()==ButtonType.OK)
                {
                    txt_password.clear();
                }
            }
        }catch (Exception e) {
            System.out.println(e);
        }        
    }
    
    
}
