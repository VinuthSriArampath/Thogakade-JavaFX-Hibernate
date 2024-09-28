package edu.icet.controller.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Customer;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CustomerService;
import edu.icet.util.CrudUtil;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    @FXML
    private TableColumn<?, ?> colladdress;

    @FXML
    private TableColumn<?, ?> collbday;

    @FXML
    private TableColumn<?, ?> collcity;

    @FXML
    private TableColumn<?, ?> collid;

    @FXML
    private TableColumn<?, ?> collname;

    @FXML
    private TableColumn<?, ?> collpostal;

    @FXML
    private TableColumn<?, ?> collprovince;

    @FXML
    private TableColumn<?, ?> collsalary;

    @FXML
    private TableView<Customer> customertable;

    @FXML
    private JFXTextField txtaddress;

    @FXML
    private JFXTextField txtcity;

    @FXML
    private DatePicker txtdob;

    @FXML
    private JFXTextField txtid;

    @FXML
    private JFXTextField txtname;

    @FXML
    private JFXTextField txtpostalcode;

    @FXML
    private JFXTextField txtprovince;

    @FXML
    private JFXTextField txtsalary;

    @FXML
    private JFXComboBox<String> txttitle;

    CustomerService service = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {

        Customer customer = new Customer(
                txtid.getText(),
                txttitle.getValue(),
                txtname.getText(),
                txtdob.getValue(),
                Double.parseDouble(txtsalary.getText()),
                txtaddress.getText(),
                txtcity.getText(),
                txtprovince.getText(),
                txtpostalcode.getText());

        if (service.addCustomer(customer)) {
            loadtable();
            new Alert(Alert.AlertType.INFORMATION, "Customer Added !!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Added :(").show();
        }
    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event) {
        if(service.deleteCustomer(txtid.getText())){
            loadtable();
            new Alert(Alert.AlertType.INFORMATION, "Customer Deleted !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Deleted :(").show();
        }
    }

    @FXML
    void btnSearchCustomerOnAction(ActionEvent event) {

        Customer customer = service.searchCustomer(txtid.getText());
        txttitle.setValue(customer.getCustTitle());
        txtname.setText(customer.getCustName());
        txtdob.setValue(customer.getDOB());
        txtsalary.setText(customer.getSalary().toString());
        txtaddress.setText(customer.getCustAddress());
        txtcity.setText(customer.getCity());
        txtprovince.setText(customer.getProvince());
        txtpostalcode.setText(customer.getPostalCode());
    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        if(service.updateCustomer(new Customer(txtid.getText(), txttitle.getValue(), txtname.getText(), txtdob.getValue(), Double.parseDouble(txtsalary.getText()), txtaddress.getText(), txtcity.getText(), txtprovince.getText(), txtpostalcode.getText()))){
            loadtable();
            new Alert(Alert.AlertType.INFORMATION, "Customer Updated !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Updated :(").show();
        }
    }

    private void loadtable() {
        collid.setCellValueFactory(new PropertyValueFactory<>("CustID"));
        collname.setCellValueFactory(new PropertyValueFactory<>("CustName"));
        colladdress.setCellValueFactory(new PropertyValueFactory<>("CustAddress"));
        collbday.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        collcity.setCellValueFactory(new PropertyValueFactory<>("city"));
        collprovince.setCellValueFactory(new PropertyValueFactory<>("Province"));
        collsalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        collpostal.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));

        customertable.setItems(service.getAll());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadtable();

        customertable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr. ");
        titles.add("Mrs. ");
        txttitle.setItems(titles);
    }

    private void setTextToValues(Customer newValue) {
        if (newValue != null) {
            txtid.setText(newValue.getCustID());
            txttitle.setValue(newValue.getCustTitle());
            txtname.setText(newValue.getCustName());
            txtdob.setValue(newValue.getDOB());
            txtaddress.setText(newValue.getCustAddress());
            txtcity.setText(newValue.getCity());
            txtprovince.setText(newValue.getProvince());
            txtpostalcode.setText(newValue.getPostalCode());
            txtsalary.setText("" + newValue.getSalary());
        }
    }
}
