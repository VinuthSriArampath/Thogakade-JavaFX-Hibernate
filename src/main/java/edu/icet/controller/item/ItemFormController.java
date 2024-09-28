package edu.icet.controller.item;

import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Item;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.ItemService;
import edu.icet.util.CrudUtil;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colldesc;

    @FXML
    private TableColumn<?, ?> collitemcode;

    @FXML
    private TableColumn<?, ?> collpacksize;

    @FXML
    private TableColumn<?, ?> collqty;

    @FXML
    private TableColumn<?, ?> collunitprize;

    @FXML
    private TableView<Item> itemtable;

    @FXML
    private JFXTextField txtdesc;

    @FXML
    private JFXTextField txtid;

    @FXML
    private JFXTextField txtpacksize;

    @FXML
    private JFXTextField txtqty;

    @FXML
    private JFXTextField txtunitprize;

    ItemService1 service;
    ItemService service1=ServiceFactory.getInstance().getService(ServiceType.ITEM);
    @FXML
    void btnAdditemOnAction(ActionEvent event) {
        if(service1.addItem(new Item(txtid.getText(),txtdesc.getText(), txtpacksize.getText(),Double.parseDouble(txtunitprize.getText()),Integer.parseInt(txtqty.getText())))){
            new Alert(Alert.AlertType.INFORMATION,"Item Added Successfully").show();
            loadtable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Added").show();
        }
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        if (service1.deleteItem(txtid.getText())){
            new Alert(Alert.AlertType.INFORMATION,String.format("Item Name :- %s Added Successfully",txtid.getText())).show();
            loadtable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Deleted").show();
        }
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        Item item=new Item(txtid.getText(),txtdesc.getText(), txtpacksize.getText(),Double.parseDouble(txtunitprize.getText()),Integer.parseInt(txtqty.getText()));
        if (service1.updateItem(item)){
            new Alert(Alert.AlertType.INFORMATION,String.format("Item Name :- %s Updated Successfully",item.getDescription())).show();
            loadtable();
        }else {
            new Alert(Alert.AlertType.ERROR, "Item Not Updated").show();
        }
    }

    @FXML
    void btnViewSearchOnAction(ActionEvent event) {
        Item item = service1.saerchItem(txtid.getText());
            txtdesc.setText(item.getDescription());
            txtpacksize.setText(item.getPackSize());
            txtunitprize.setText(item.getUnitPrice().toString());
            txtqty.setText(item.getQtyOnHand().toString());

    }

    private void loadtable(){
        collitemcode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colldesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        collpacksize.setCellValueFactory(new PropertyValueFactory<>("PackSize"));
        collunitprize.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        collqty.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
        itemtable.setItems(service1.getAllItems());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service= ItemService1Impl.getInstance();
        loadtable();

        itemtable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));

    }

    private void setTextToValues(Item newValue) {
        if(newValue!=null){
           txtid.setText(newValue.getItemCode());
           txtdesc.setText(newValue.getDescription());
           txtpacksize.setText(""+newValue.getQtyOnHand());
           txtunitprize.setText(""+newValue.getUnitPrice());
           txtqty.setText(""+newValue.getQtyOnHand());
        }
    }
}
