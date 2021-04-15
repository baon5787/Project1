/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import Mode.Accout;
import Mode.NhanVien;
import Mode.NhanVienCRUD;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.DatePicker;
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
public class EmployeeController implements Initializable {

    @FXML
    private Label lb_menu;
    @FXML
    private Label lb_menuback;
    @FXML
    private AnchorPane slide;
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_lastname;
    @FXML
    private TextField txt_firstname;
    @FXML
    private TextField txt_phone;
    @FXML
    private DatePicker txt_date;
    @FXML
    private TableView<NhanVien> tbv_nhanvien;
    @FXML
    private TableColumn<NhanVien,String> col_id;
    @FXML
    private TableColumn<NhanVien, String> col_Fname;
    @FXML
    private TableColumn<NhanVien, String> col_gmail;
    @FXML
    private TableColumn<NhanVien, Date> col_date;
    @FXML
    private TableColumn<NhanVien, Integer> col_phone;
    @FXML
    private TableColumn<NhanVien, String> col_gender;
    private NhanVienCRUD crud;
    @FXML
    private ToggleGroup genderr;
    @FXML
    private RadioButton rdb_male;
    @FXML
    private Button btn_insert;   
    @FXML
    private Button btn_update;
    @FXML
    private RadioButton rdb_female;
    private TextField txt_name;
    private Accout accout;
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
    @FXML
    private AnchorPane ap_slide;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crud = new NhanVienCRUD();
        txt_nhanvien.setText("Xin Chào: "+Accout.fullname);
        showNhanVien();
        menu();
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
    public void showNhanVien()
    {
        ObservableList<NhanVien> list = crud.selectALL();
        col_id.setCellValueFactory(new PropertyValueFactory<>("manv"));
        col_Fname.setCellValueFactory(new PropertyValueFactory<>("nvFName"));
        col_gmail.setCellValueFactory(new PropertyValueFactory<>("gmail"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("nvYOB"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("nvPhone"));
        col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tbv_nhanvien.setItems(list);
    }
    public void insertNhanVien()
    {
        NhanVien e = new NhanVien();
        String gender = null;
        if(rdb_female.isSelected())
        {
            gender="Female";
        }
        else if(rdb_male.isSelected())
        {
            gender="Male";
        }
        LocalDate localdate = txt_date.getValue();
        Date date = Date.valueOf(localdate);
        e.setManv(txt_id.getText());
        e.setNvFName(txt_lastname.getText());
        e.setGmail(txt_firstname.getText());
        e.setNvYOB(date);
        e.setNvPhone(txt_phone.getText());
        e.setGender(gender);
        e.setUserid(txt_firstname.getText());
        e.setPassword(txt_phone.getText());
        crud.insert(e);
        showNhanVien();
    }
     public void updateNhanVien()
    {
        NhanVien e = new NhanVien();
        String gender = null;
        if(rdb_female.isSelected())
        {
            gender="Female";
        }
        else if(rdb_male.isSelected())
        {
            gender="Male";
        }
        e.setManv(txt_id.getText());
        e.setNvFName(txt_lastname.getText());
        e.setGmail(txt_firstname.getText());
        LocalDate localdate = txt_date.getValue();
        Date date = Date.valueOf(localdate);
        e.setNvYOB(date); 
        e.setGender(gender);
        e.setNvPhone(txt_phone.getText());
        crud.updateNhanVien(e);
        showNhanVien();
    }  
    @FXML
    private void insert(ActionEvent event) {
      
    }

    @FXML
    private void handButtonAction(ActionEvent event) {
        if(event.getSource() == btn_insert)
        {
            String id = crud.getmanv(txt_id.getText());
            String gmail = crud.getgmail(txt_firstname.getText());
            if(txt_id.getText().equals(id))
            {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Thông tin báo cáo");
                al.setHeaderText("Lỗi");
                al.setContentText("Nhập lại ID !");
                al.showAndWait();
            }else if(txt_firstname.getText().equals(gmail))
            {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Thông tin báo cáo");
                al.setHeaderText("Lỗi");
                al.setContentText("Nhập lại Gmail !");
                al.showAndWait(); 
            }
            else{
            insertNhanVien();}
        }
         else if(event.getSource() == btn_update)
        {
            updateNhanVien();
        }     
    }
    
    @FXML
    private void handonMouseclicked(MouseEvent event) {
        NhanVien nv = tbv_nhanvien.getSelectionModel().getSelectedItem();
        txt_id.setText(nv.getManv());
        txt_lastname.setText(nv.getNvFName());
        txt_firstname.setText(nv.getGmail());
        txt_phone.setText(nv.getNvPhone());
        DatePicker date = txt_date;
        date.setValue(LocalDate.parse(""+nv.getNvYOB()));
        String gender = nv.getGender();
        if(gender.equals("Male"))
        {
            rdb_male.setSelected(true);
        }
        else if(gender.equals("Female"))
        {
            rdb_female.setSelected(true);
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
