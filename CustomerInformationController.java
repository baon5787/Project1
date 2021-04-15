/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import Mode.Accout;
import Mode.KhachHang;
import Mode.KhachHangCRUD;
import Mode.NhanVien;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Adminss
 */
public class CustomerInformationController implements Initializable {

    @FXML
    private Label lb_menu;
    @FXML
    private AnchorPane slide;
    @FXML
    private Label lb_menuback;
    @FXML
    private TextField txt_khID;
    @FXML
    private TextField txt_khLname;
    @FXML
    private TextField txt_khFname;
    @FXML
    private TextField txt_khPhone;
    @FXML
    private TextField txt_khAddress;
    @FXML
    private RadioButton rdb_male;
    @FXML
    private ToggleGroup tg_gender;
    @FXML
    private RadioButton rdb_female;
    @FXML
    private AnchorPane ap_slide;
    @FXML
    private TableView<KhachHang> tbv_KhachHang;
    @FXML
    private TableColumn<KhachHang, String> col_id;
    @FXML
    private TableColumn<KhachHang, String> col_Lname;
    @FXML
    private TableColumn<KhachHang, String> col_Fname;
    @FXML
    private TableColumn<KhachHang, Integer> col_Phone;
    @FXML
    private TableColumn<KhachHang, String> col_Address;
    @FXML
    private TableColumn<KhachHang, String> col_Gender;
    private KhachHangCRUD crud;
    @FXML
    private Button btn_insert;
    @FXML
    private Text txt_nhanvien;
    @FXML
    private Button btn_manage;
    @FXML
    private Button btn_information;
    @FXML
    private Button btn_createbill;
    @FXML
    private Button btn_warehouse;
    @FXML
    private Button btn_statistis;
    @FXML
    private Button btn_ImportGoods;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menu();
        crud = new KhachHangCRUD();
        txt_nhanvien.setText("Xin Chào: "+Accout.fullname);
        showKhachHang();
    }    
    private void menu()
    {
        
        lb_menu.setOnMouseClicked(event -> {
            TranslateTransition slider = new TranslateTransition();
            slider.setDuration(Duration.seconds(0.4));
            slider.setNode(this.slide);

            slider.setToX(0);
            slider.play();

            slide.setTranslateX(-176);

            slider.setOnFinished((ActionEvent e)-> {
                lb_menu.setVisible(false);
                lb_menuback.setVisible(true);
            });
        });
        //slide.setTranslateX(-176);
        lb_menuback.setOnMouseClicked(event -> {
            TranslateTransition slider = new TranslateTransition();
            slider.setDuration(Duration.seconds(0.4));
            slider.setNode(slide);
            slider.setToX(-176);
            slider.play();
            ap_slide.setTranslateX(0);
            slide.setTranslateX(0);

            slider.setOnFinished((ActionEvent e)-> {
                lb_menu.setVisible(true);
                lb_menuback.setVisible(false);
            });
        });
    }
    public void showKhachHang()
    {
        ObservableList<KhachHang> list = crud.selectALL();
        col_id.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        col_Lname.setCellValueFactory(new PropertyValueFactory<>("khLName"));
        col_Fname.setCellValueFactory(new PropertyValueFactory<>("khFName"));
        col_Phone.setCellValueFactory(new PropertyValueFactory<>("khPhone"));
        col_Address.setCellValueFactory(new PropertyValueFactory<>("khAddress"));
        col_Gender.setCellValueFactory(new PropertyValueFactory<>("khgender"));
        tbv_KhachHang.setItems(list);
    }
    public void insertKhachHang()
    {
        KhachHang kh = new KhachHang();
        String gender = null;
        if(rdb_female.isSelected())
        {
            gender="Female";
        }
        else if(rdb_male.isSelected())
        {
            gender="Male";
        }       
        kh.setMaKH(txt_khID.getText());
        kh.setKhLName(txt_khLname.getText());
        kh.setKhFName(txt_khFname.getText());
        kh.setKhPhone(txt_khPhone.getText());
        kh.setKhAddress(txt_khAddress.getText());
        kh.setKhgender(gender);
        crud.insert(kh);
        showKhachHang();
    }

    @FXML
    private void handButtonAction(ActionEvent event) {
        if(event.getSource()==btn_insert)
        {
            String id = crud.getmakh(txt_khID.getText());
            String sdt = crud.getsdt(txt_khPhone.getText());
            if(txt_khID.getText().equals(id))
            {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Thông tin báo cáo");
                al.setHeaderText("Lỗi");
                al.setContentText("Nhập lại ID !");
                al.showAndWait();
            }else if(txt_khPhone.getText().equals(sdt))
            {
                 Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Thông tin báo cáo");
                al.setHeaderText("Lỗi");
                al.setContentText("Nhập lại Số Điện thoai !");
                al.showAndWait(); 
            }else{
            insertKhachHang();}
        }
    }

    @FXML
    private void OnActionButton(ActionEvent event) throws IOException {
        if(event.getSource() == btn_information)
        {
            Stage appStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                Parent root3 = FXMLLoader.load(getClass().getResource("/view/CustomerInformation.fxml"));
                Scene scene = new Scene(root3);
                appStage.setScene(scene);
                appStage.show();
        }
        else if(event.getSource() == btn_createbill)
        {
            Stage appStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                Parent root3 = FXMLLoader.load(getClass().getResource("/view/DrugBill.fxml"));
                Scene scene = new Scene(root3);
                appStage.setScene(scene);
                appStage.show();
        }
         else if(event.getSource() == btn_statistis)
         {
             Stage appStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                Parent root3 = FXMLLoader.load(getClass().getResource("/view/Statistical.fxml"));
                Scene scene = new Scene(root3);
                appStage.setScene(scene);
                appStage.show();
         }
        else if(event.getSource() == btn_warehouse)
        {
             Stage appStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                Parent root3 = FXMLLoader.load(getClass().getResource("/view/Warehouse.fxml"));
                Scene scene = new Scene(root3);
                appStage.setScene(scene);
                appStage.show();
        }else if(event.getSource() == btn_manage){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Employee.fxml"));
            Parent sampleParent = loader.load();
            Scene scene = new Scene(sampleParent);
            stage.setScene(scene);}
         else if(event.getSource() == btn_ImportGoods)
        {
             Stage appStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                Parent root3 = FXMLLoader.load(getClass().getResource("/view/ImportGoods.fxml"));
                Scene scene = new Scene(root3);
                appStage.setScene(scene);
                appStage.show();
        }
    }
}
