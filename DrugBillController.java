/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import Mode.Accout;
import Mode.ChiTietHoaDon;
import Mode.Hoadon;
import Mode.HoadonCRUD;
import Mode.HodonCRUD;
import Mode.KhachHang;
import Mode.KhachHangCRUD;
import Mode.Kho;
import Mode.KhoCRUD;
import Mode.LoaiThuoc;
import Mode.LoaiThuocCRUD;
import Mode.NhanVien;
import Mode.NhanVienCRUD;
import Mode.PhieuXuat;
import Mode.ThongKeKhoCRUD;
import Mode.Thuoc;
import Mode.ThuocCRUD;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Adminss
 */
public class DrugBillController implements Initializable {

    @FXML
    private Label lb_menu;
    @FXML
    private Label lb_menuback;
    @FXML
    private AnchorPane slide;
    @FXML
    private ComboBox<LoaiThuoc> cb_loaithuoc;
    private LoaiThuocCRUD crud;
    private NhanVienCRUD crudnv;
    @FXML
    private Spinner<Integer> spn_soluong;
    @FXML
    private TextField txt_timthuoc;
    @FXML
    private TableView<Thuoc> tbv_thuoc;
    @FXML
    private TextField txt_thuoc;
    @FXML
    private TextField txt_khachang;
    private ComboBox<NhanVien> cb_nhanvien;
    @FXML
    private TableView<Hoadon> tbv_cthd;
    @FXML
    private Button btn_thanhtoan;
    @FXML
    private Text txt_kh;
    @FXML
    private Button btn_them;
    private HodonCRUD crudhd;
    private ThuocCRUD crudthuoc;
    @FXML
    private TableColumn<Thuoc, String> col_thuoc;
    @FXML
    private TableColumn<Thuoc, String> col_dvt;
    @FXML
    private TableColumn<Thuoc, Float> col_dongia;
    @FXML
    private TableColumn<Hoadon, String> col_cthdtenthuoc;
    @FXML
    private TableColumn<Hoadon, String> col_cthddvt;
    @FXML
    private TableColumn<Hoadon, Float> col_cthddongia;
    @FXML
    private TableColumn<Hoadon, Integer> col_cthdsoluong;
    private Thuoc t;
    //private Hoadon h;
    private ObservableList<Hoadon> listhd = FXCollections.observableArrayList();
    @FXML
    private TextField txt_name;
    @FXML
    private Text txt_thoigian;
    @FXML
    private Button btn_xoa;
    @FXML
    private Text txt_tonggia;
    @FXML
    private Button btn_sreachthuoc;
    @FXML
    private Button btn_sreachsdt;
    private ThongKeKhoCRUD khocrud;
    private KhachHangCRUD khcrud ;
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
        crud = new LoaiThuocCRUD();
        crudthuoc = new ThuocCRUD();
        crudnv = new NhanVienCRUD();
        crudhd = new HodonCRUD();
        khocrud = new ThongKeKhoCRUD();
        khcrud = new KhachHangCRUD();
        txt_name.setText("Xin Chào"+Accout.fullname);
        java.util.Date date = new java.util.Date();
        Timestamp sqldate = new java.sql.Timestamp(date.getTime());
        SimpleDateFormat time = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        txt_thoigian.setText(time.format(sqldate).toString());
        loadComboBoxLoaiThuoc();
        loadSpinner();
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
    private void loadComboBoxLoaiThuoc() {
      ObservableList<LoaiThuoc> dep = crud.selectALL();
        cb_loaithuoc.setItems(dep);
        cb_loaithuoc.setConverter(new StringConverter<LoaiThuoc>() {
            @Override
            public String toString(LoaiThuoc loaithuoc) {
                return String.valueOf(loaithuoc.getTenloai());
            }

            @Override
            public LoaiThuoc fromString(String string) {
                return null;
            }
        });
        
        cb_loaithuoc.setCellFactory(cell -> new ListCell<LoaiThuoc>() {
            // Create our layout here to be reused for each ListCell
            GridPane gridPane = new GridPane();
            Label lblName = new Label();

            // Static block to configure our layout
            {
                // Ensure all our column widths are constant
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblName, 0, 1);
            }

            // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
            @Override
            protected void updateItem(LoaiThuoc loaithuoc, boolean empty) {
                super.updateItem(loaithuoc, empty);

                if (!empty && loaithuoc != null) {
                    lblName.setText(loaithuoc.getTenloai());
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
    }  
    private void loadSpinner()
    {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        this.spn_soluong.setValueFactory(spinnerValueFactory);
    }

    @FXML
    private void handtext(ActionEvent event) {
        String empName = txt_khachang.getText();
        txt_kh.setText(empName);
    }

    /*private void handbutton(ActionEvent event) {
        Thuoc t = tbv_thuoc.getSelectionModel().getSelectedItem();
        ChiTietHoaDon cthd = new ChiTietHoaDon();
        int soluong = spn_soluong.getValue();
        List<ChiTietHoaDon> listcthd = new ArrayList<>();
        String mathuoc = t.getMathuoc();
        cthd.setMapx(mathuoc);
        cthd.setMathuoc(mathuoc);
        cthd.setSoluong(soluong);
        crudhd.insertCTHD(cthd);
    }*/

    @FXML
    private void handconbobox(ActionEvent event) {
        LoaiThuoc maloai = new LoaiThuoc();
        maloai = cb_loaithuoc.getValue();
        String ml = maloai.getMaloai();
        ObservableList<Thuoc> list = crudthuoc.selectALL(ml);
        col_thuoc.setCellValueFactory(new PropertyValueFactory<>("tenthuoc"));      
        col_dvt.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        col_dongia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tbv_thuoc.setItems(list);
    }

    @FXML
    private void handtable(MouseEvent event) throws SQLException {
        Thuoc t = tbv_thuoc.getSelectionModel().getSelectedItem();
        txt_thuoc.setText(t.getTenthuoc());
        hieuung();
        int m = khocrud.soluongcan(t.getTenthuoc());
        spn_soluong.getValueFactory().setValue(m);
    }
    @FXML
    private void handonAction(ActionEvent event) throws SQLException {
        t= tbv_thuoc.getSelectionModel().getSelectedItem();
        int m = khocrud.soluongcan(t.getTenthuoc());
         if(spn_soluong.getValue()>m)
        {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Thông tin báo cáo");
                al.setHeaderText("Lỗi");
                al.setContentText("Số lượng ko đủ !");
                al.showAndWait();
        }else{
        t = new Thuoc();
        Hoadon h = new Hoadon(); 
        ThuocCRUD tcrub =  new ThuocCRUD();
        t= tbv_thuoc.getSelectionModel().getSelectedItem();      
        h.setTenthuoc(t.getTenthuoc());
        h.setDvt(t.getDvt());
        h.setDongia(t.getGia());
        h.setSoluong(spn_soluong.getValue());
        listhd.add(h);
        col_cthdtenthuoc.setCellValueFactory(new PropertyValueFactory<>("tenthuoc"));
        col_cthddvt.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        col_cthddongia.setCellValueFactory(new PropertyValueFactory<>("dongia"));
        col_cthdsoluong.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        tbv_cthd.setItems(listhd);
        int tong = 0;
        for(int i = 0;i<tbv_cthd.getItems().size();i++)
        {
                String ml = tbv_cthd.getItems().get(i).getTenthuoc();
                int dg = tbv_cthd.getItems().get(i).getDongia();
                int sl = tbv_cthd.getItems().get(i).getSoluong();
                if(tcrub.getenthuoc(ml)==true)
                {
                   tong  += dg*sl;
                }
        }
        txt_tonggia.setText(""+tong);
        txt_thuoc.clear();
        spn_soluong.getValueFactory().setValue(1);
         }
    }

    @FXML
    private void handthanhtoan(ActionEvent event) throws SQLException {
        String dien  = txt_khachang.getText();
        if(txt_khachang.getText().equals(""))
        {
            Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Thông tin báo cáo");
                al.setHeaderText("Lỗi");
                al.setContentText("Vui lòng nhập vào !");
                al.showAndWait();
        }else if(khcrud.kiemtrasdt(dien)==false)
        {
            Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Thông tin báo cáo");
                al.setHeaderText("Lỗi");
                al.setContentText("Vui lòng nhập lại Số Điện Thoại vào !");
                al.showAndWait();
        }
        else{
        PhieuXuat px = new PhieuXuat();
        t = new Thuoc();
        Hoadon h = new Hoadon();
        ThuocCRUD tcrub =  new ThuocCRUD();
        HoadonCRUD crudh = new HoadonCRUD();
        KhoCRUD kcrud =new KhoCRUD();
        Kho k = new Kho();
        crudhd = new HodonCRUD();
        px.setManv(Accout.userid);
        String text = txt_khachang.getText();
        String m = khcrud.kiemtra(text);
        px.setMakh(m);
        //px.setMakh("KH1");       
        java.util.Date date = new java.util.Date();
        Timestamp sqldate = new java.sql.Timestamp(date.getTime());
        px.setNgatxuat(sqldate);
        crudhd.insertHD(px);
        ChiTietHoaDon cthd = new ChiTietHoaDon();        
        //t = tbv_thuoc.getSelectionModel().getSelectedItem();       
        for(int i = 0;i<tbv_cthd.getItems().size();i++)
        {
                String ml = tbv_cthd.getItems().get(i).getTenthuoc();
                int soluong  = tbv_cthd.getItems().get(i).getSoluong();
                int mk = tcrub.getmakho(ml);
                int slkho= kcrud.getsoluong(mk);
                int mathang= slkho-soluong;
                if(tcrub.getenthuoc(ml)==true)
                {
                    
                    String mathuoc = tcrub.getmathuoc(ml);
                    int ma = crudh.getmapx(m);
                    cthd.setMapx(ma);
                    cthd.setSoluong(soluong);
                    cthd.setMathuoc(mathuoc);
                    crudhd.insertCTHD(cthd);
                    k.setSoluong(mathang);
                    k.setMakho(mk);
                    kcrud.updateNhanVien(k);
                }
        }
        tbv_cthd.getItems().clear();
        txt_khachang.clear();
         Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Thông tin báo cáo");
                al.setHeaderText("Thành công");
                al.setContentText("Bạn đã nhập được giữ liệu !");
                al.showAndWait();
    }   
    }
    @FXML
    private void handonActionDelete(ActionEvent event) {
        ThuocCRUD tcrub =  new ThuocCRUD();
        ObservableList<Hoadon> t = FXCollections.observableArrayList();
        t =  tbv_cthd.getSelectionModel().getSelectedItems();
        tbv_cthd.getItems().removeAll(t);
        int tong = 0;
        for(int i = 0;i<tbv_cthd.getItems().size();i++)
        {
                String ml = tbv_cthd.getItems().get(i).getTenthuoc();
                int dg = tbv_cthd.getItems().get(i).getDongia();
                int sl = tbv_cthd.getItems().get(i).getSoluong();
                if(tcrub.getenthuoc(ml)==true)
                {
                   tong  += dg*sl;
                }
        }
        txt_tonggia.setText(""+tong);
    }
    private void hieuung()
    {
        Reflection t = new Reflection();
        t.setFraction(0.9);
        txt_thuoc.setEffect(t);
        txt_thuoc.setTranslateY(2);
    }

    @FXML
    private void OnActionsreachthuoc(ActionEvent event) {
        String tenthuoc = txt_timthuoc.getText();
        ObservableList<Thuoc> list = crudthuoc.Sreachtenthuoc(tenthuoc);
        col_thuoc.setCellValueFactory(new PropertyValueFactory<>("tenthuoc"));      
        col_dvt.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        col_dongia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tbv_thuoc.setItems(list);
    }

    @FXML
    private void OnActionsreachsdt(ActionEvent event) {
        
        String sdt  = txt_khachang.getText();
        String ten = khcrud.Sreachsdt(sdt);
        txt_kh.setText(ten);
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
