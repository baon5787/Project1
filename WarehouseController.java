/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import Mode.Statistics;
import Mode.ThongKeKho;
import Mode.ThongKeKhoCRUD;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Adminss
 */
public class WarehouseController implements Initializable {

    @FXML
    private Text txt_nhanvien;
    @FXML
    private Label lb_menu;
    @FXML
    private Label lb_menuback;
    @FXML
    private AnchorPane slide;
    @FXML
    private TableView<ThongKeKho> tbv_kho;
    @FXML
    private TableColumn<ThongKeKho, String> col_tenthuoc;
    @FXML
    private TableColumn<ThongKeKho, String> col_loaithuoc;
    @FXML
    private TableColumn<ThongKeKho, String> col_donvi;
    @FXML
    private TableColumn<ThongKeKho, Integer> col_gia;
    @FXML
    private TableColumn<ThongKeKho, Integer> col_soluong;
    private ThongKeKhoCRUD tkcrud ; 
    @FXML
    private TextField txt_ten;
    @FXML
    private Button bnt_sreach;
    @FXML
    private Text txt_tong;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       tkcrud = new ThongKeKhoCRUD();
        try {
            showkho();
            menu();
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
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
    public void showkho() throws SQLException
    {
        ObservableList<ThongKeKho> list = tkcrud.selectALL();
        col_tenthuoc.setCellValueFactory(new PropertyValueFactory<>("tenthuoc"));
        col_loaithuoc.setCellValueFactory(new PropertyValueFactory<>("tenloai"));
        col_gia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        col_donvi.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        col_soluong.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        tbv_kho.setItems(list);
        txt_tong.setText(""+tkcrud.grandTotal());
    }
    public void sreach()
    {
        String ten = txt_ten.getText();
        ObservableList<ThongKeKho> list = tkcrud.Sreach(ten);
        col_tenthuoc.setCellValueFactory(new PropertyValueFactory<>("tenthuoc"));
        col_loaithuoc.setCellValueFactory(new PropertyValueFactory<>("tenloai"));
        col_gia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        col_donvi.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        col_soluong.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        tbv_kho.setItems(list);
    }
    @FXML
    private void OnAction(ActionEvent event) {
        if(event.getSource()==bnt_sreach)
        {
            if(txt_ten.getText().equals(""))
            {
                Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Thông tin báo cáo");
            al.setHeaderText("Lỗi");
            al.setContentText("Nhập lại tên thuốc gần đúng!");
            al.showAndWait();
            }
            else{
                sreach();
            }
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
