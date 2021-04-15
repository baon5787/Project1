/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import Mode.BaoCao;
import Mode.BaoCaoCRUD;
import Mode.HoadonCRUD;
import Mode.NhanVien;
import Mode.Statistics;
import Mode.StatisticsCRUD;
import Mode.ThuocCRUD;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
public class StatisticalController implements Initializable {

    @FXML
    private TextField txt_name;
    @FXML
    private Label lb_menu;
    @FXML
    private Label lb_menuback;
    @FXML
    private AnchorPane slide;
    @FXML
    private TableView<Statistics> tbv_kho;
    @FXML
    private TableColumn<Statistics, String> col_tenthuoc;
    @FXML
    private TableColumn<Statistics, String> col_tenloaithuoc;
    @FXML
    private TableColumn<Statistics, Integer> col_gia;
    @FXML
    private TableColumn<Statistics, String> col_donvi;
    @FXML
    private TableColumn<Statistics, Integer> col_soluong;
    @FXML
    private TableColumn<Statistics, Date> col_ngayban;
    private StatisticsCRUD scrud;
    private ThuocCRUD crud ;
    private HoadonCRUD hdcrud;
    @FXML
    private DatePicker datep_tungay;
    @FXML
    private DatePicker datep_denngay;
    @FXML
    private Button btn_sreach;
    @FXML
    private PieChart pieChart_LoaiMon;
    @FXML
    private Text lbl_GrandTotal;
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
        scrud = new StatisticsCRUD();
        crud = new ThuocCRUD();
        hdcrud = new HoadonCRUD();
        try {
            showKho();
            menu();
            displayPiechart();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalController.class.getName()).log(Level.SEVERE, null, ex);
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
     public void showKho()
    {
        ObservableList<Statistics> list = scrud.selectALL();
        col_tenthuoc.setCellValueFactory(new PropertyValueFactory<>("tenthuoc"));
        col_tenloaithuoc.setCellValueFactory(new PropertyValueFactory<>("tenloai"));
        col_gia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        col_donvi.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        col_soluong.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        col_ngayban.setCellValueFactory(new PropertyValueFactory<>("ngayxuat"));
        tbv_kho.setItems(list);
    }
    public void showKhosearch()
    {
        LocalDate localdate = datep_tungay.getValue();
        Date tungay = Date.valueOf(localdate);
         LocalDate localdatemax = datep_denngay.getValue();
        Date denngay = Date.valueOf(localdatemax);
        ObservableList<Statistics> list = scrud.SreachNgaThangNam(tungay, denngay);
        col_tenthuoc.setCellValueFactory(new PropertyValueFactory<>("tenthuoc"));
        col_tenloaithuoc.setCellValueFactory(new PropertyValueFactory<>("tenloai"));
        col_gia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        col_donvi.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        col_soluong.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        col_ngayban.setCellValueFactory(new PropertyValueFactory<>("ngayxuat"));
        tbv_kho.setItems(list);       
    }
    public void displayPiechart() throws SQLException
    {       
        DecimalFormat format = new DecimalFormat("#,###,###");
        float grandTotal = hdcrud.grandTotal();
        ObservableList<String> mlist = crud.tenthuoc();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        ObservableList<Statistics> listRep = FXCollections.observableArrayList();
        listRep = scrud.selectALL();
        showKho();
        tbv_kho.setItems(listRep);
        for(String tenloai: mlist)
        {
            float total = hdcrud.totalByLoaiMonAndDate(tenloai);
            PieChart.Data pd = new PieChart.Data(tenloai, total / grandTotal);
             data.add(pd);
        }
        pieChart_LoaiMon.setData(data);
        lbl_GrandTotal.setText("" + format.format(grandTotal));
    }
    @FXML
    private void handButtonAction(ActionEvent event) throws SQLException {
        if(event.getSource()==btn_sreach){
        DecimalFormat format = new DecimalFormat("#,###,###");
        LocalDate localdate = datep_tungay.getValue();        
        LocalDate localdatemax = datep_denngay.getValue();      
        if(localdate == null|| localdatemax == null)
        {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Thông tin báo cáo");
            al.setHeaderText("Lỗi");
            al.setContentText("Chọn thời gian để xuất báo cáo!");
            al.showAndWait();
        }else{
            Date tungay = Date.valueOf(localdate);
            Date denngay = Date.valueOf(localdatemax);
        float grandTotal = hdcrud.grandTotalByDate(tungay, denngay);
        ObservableList<Statistics> listRep = FXCollections.observableArrayList();
        listRep = scrud.SreachNgaThangNam(denngay, denngay);
        showKhosearch();
        tbv_kho.setItems(listRep);
        ObservableList<String> mlist = crud.tenthuoc();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for(String tenloai: mlist)
        {
             float total = hdcrud.totalByLoaiMonDate(tenloai, tungay, denngay);
             PieChart.Data pd = new PieChart.Data(tenloai, total / grandTotal);
             data.add(pd);
        }
        pieChart_LoaiMon.setData(data);
        lbl_GrandTotal.setText("" + format.format(grandTotal));
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
