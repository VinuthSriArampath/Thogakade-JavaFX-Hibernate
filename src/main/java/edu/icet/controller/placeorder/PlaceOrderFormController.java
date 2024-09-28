package edu.icet.controller.placeorder;
import com.jfoenix.controls.JFXTextField;

import edu.icet.controller.item.ItemService1Impl;
import edu.icet.model.*;
import edu.icet.service.ServiceFactory;
import edu.icet.service.SuperService;
import edu.icet.service.custom.ItemService;
import edu.icet.service.custom.impl.CustomerServiceImpl;
import edu.icet.util.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;


import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PlaceOrderFormController implements Initializable {

    public JFXTextField txtorderid;
    @FXML
    private ComboBox<String> cmbcusid;

    @FXML
    private ComboBox<String> cmbitemcode;

    @FXML
    private TableColumn<?, ?> colldesc;

    @FXML
    private TableColumn<?, ?> collitemcode;

    @FXML
    private TableColumn<?, ?> collqty;

    @FXML
    private TableColumn<?, ?> colltotal;

    @FXML
    private TableColumn<?, ?> collunitprice;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblorderid;

    @FXML
    private Label lbltime;

    @FXML
    private Label lbltotalbill;

    @FXML
    private TableView<Cart> tablecart;

    @FXML
    private JFXTextField txtcusaddress;

    @FXML
    private JFXTextField txtcusname;

    @FXML
    private JFXTextField txtitemdesc;

    @FXML
    private JFXTextField txtitemstock;

    @FXML
    private JFXTextField txtitemunitprice;

    @FXML
    private JFXTextField txtqty;

    ObservableList<Cart> cart=FXCollections.observableArrayList();
    @FXML
    void btnAddItemOnAction(ActionEvent event) {

        if(cmbcusid.getValue()!=null){
            if (!Objects.equals(txtcusname.getText(), "") && !Objects.equals(txtcusaddress.getText(), "")){
                if (cmbitemcode.getValue()!=null){
                    if(!Objects.equals(txtitemdesc.getText(), "") && !Objects.equals(txtitemstock.getText(), "") && !Objects.equals(txtitemunitprice.getText(), "")){
                        if(!Objects.equals(txtqty.getText(), "")){
                            int itemstocklevel = Integer.parseInt(txtitemstock.getText());
                            int enteredqty = Integer.parseInt(txtqty.getText());

                            if (enteredqty>0 && itemstocklevel>enteredqty) {
                                loadtable();
                                new Alert(Alert.AlertType.CONFIRMATION,"Item Added To the Cart Successfully!!");
                            }else{
                                new Alert(Alert.AlertType.ERROR,"Current Stock level is less than Quantity you Entered!!").show();
                            }
                        }else {
                            new Alert(Alert.AlertType.ERROR, "Quantity Level Is Not Entered !!").show();
                        }
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR,"Item Id Is Not Selected!!").show();
                }
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Id Is Not Selected !!").show();
        }

    }

    private void loadtable(){
        collitemcode.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        colldesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        collqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        collunitprice.setCellValueFactory(new PropertyValueFactory<>("unitprize"));
        colltotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cart.add(new Cart(
                cmbitemcode.getValue(),
                txtitemdesc.getText(),
                Integer.parseInt(txtqty.getText()),
                Double.parseDouble(txtitemunitprice.getText()),
                (Double.parseDouble(txtitemunitprice.getText())*Integer.parseInt(txtqty.getText()))
        ));
        tablecart.setItems(cart);
        calculateTotalAmount();
    }
    @FXML
    void btnUpdateCartOnAction(ActionEvent event) {
        int itemindex=searchItemInCart(cmbitemcode.getValue());
        if (itemindex==-1){
            new Alert(Alert.AlertType.ERROR,"Item Not Found In the Cart !!").show();
        }else {
            Cart updatecart=cart.get(itemindex);
            updatecart.setQty(Integer.parseInt(txtqty.getText()));
            cart.set(itemindex,updatecart);
            loadtable();
        }
    }
    @FXML
    void btnRemoveItemOnAction(ActionEvent event) {
        int itemindex=searchItemInCart(cmbitemcode.getValue());
        if (itemindex==-1){
            new Alert(Alert.AlertType.ERROR,"Item Not Found In the Cart !!").show();
        }else {

            loadtable();
        }
    }

    private void calculateTotalAmount(){
        Double totalammount=0.0;
        for (int i=0;i<cart.size();i++){
            totalammount+=cart.get(i).getTotal();
        }
        lbltotalbill.setText(totalammount.toString());
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderid = txtorderid.getText();
        LocalDate orderdate=LocalDate.now();
        String customerid=cmbcusid.getValue();

        List<OrderDetail> orderDetails=new ArrayList<>();

        cart.forEach(obj ->{
            orderDetails.add(new OrderDetail(orderid,obj.getItemcode(),obj.getQty(),0.0));
        });
        Order order=new Order(orderid,orderdate,customerid,orderDetails);
        try {
            new OrderController().placeOrder(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private int searchItemInCart(String code){
        for (int i=0;i<cart.size();i++){
            if(cart.get(i).getItemcode().equals(code)){
                return i;
            }
        }
        return -1;
    }
    private void loadDateAndTime(){
        Date date=new Date();
        SimpleDateFormat r = new SimpleDateFormat("yyyy-MM-dd");
        String datenow=r.format(date);
        lbldate.setText(datenow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lbltime.setText(time.getHour() + " : " + time.getMinute() + " : " + time.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cart.removeAll();

        loadDateAndTime();
        loadCustomerIds();
        loadItemCodes();

        cmbcusid.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomer(newValue);
        });
        cmbitemcode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItem(newValue);
        });

        tablecart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateItemCart(newValue);
        });

    }

    private void updateItemCart(Cart newValue) {
        setItem(newValue.getItemcode());
    }

    private void setItem(String newValue) {
        if (newValue!=null){
            Item item = searchItem(newValue);
            cmbitemcode.setValue(item.getItemCode());
            txtitemdesc.setText(item.getDescription());
            txtitemstock.setText(item.getQtyOnHand().toString());
            txtitemunitprice.setText(item.getUnitPrice().toString());
        }
    }

    private Item searchItem(String id) {
        return ItemService1Impl.getInstance().saerchItem(id);
    }

    private void setCustomer(String newValue) {
        if (newValue!=null){
            Customer customer = searchCustomer(newValue);
            txtcusname.setText(customer.getCustName());
            txtcusaddress.setText(customer.getCustAddress());
        }
    }

    private Customer searchCustomer(String id) {
        CustomerServiceImpl customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
        return customerService.searchCustomer(id);
    }

    private void loadItemCodes(){
        ItemService itemService = ServiceFactory.getInstance().getService(ServiceType.ITEM);
        cmbitemcode.setItems(itemService.getItemCodes());
    }
    private void loadCustomerIds(){
        CustomerServiceImpl customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
        cmbcusid.setItems(customerService.getCustomerIds());
    }

}
