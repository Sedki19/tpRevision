package Test.application;

import java.util.ArrayList;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


import Test.connexion.Connexion;
import Test.entities.Plat_cmd;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class RestaurantController implements Initializable{
	@FXML
	private ComboBox<String> cbplat; 
	 
	 @FXML 
	 private TextField txtqte; 
	 
	 @FXML 
	 private RadioButton suppOk; // représente le bouton radio-oui 
	 
	 @FXML 
	 private RadioButton SuppNo; // représente le bouton radio-non 
	 
	 @FXML 
	 private ToggleGroup G1; // composant qui regroupe les boutons radio pour permettre une sélection unique 
	 
	 @FXML 
	 private Button addhandle; // bouton ajouter 
	 
	 @FXML 
	 private Button removehandle; // bouton supprimer 
	 
	 @FXML 
	 private Button calculer; // bouton calculer 
	 
	 @FXML 
	 private Label lblmontant; // label pour l’affichage du montant total 
	 
	 @FXML 
	 private TableView<Plat_cmd> tableR; 
	 
	 @FXML 
	 private TableColumn<Plat_cmd, String> collibelle; // colonne libelle 
	 
	 @FXML // 
	 private TableColumn<Plat_cmd, Integer> colqte; // colonne quantite 
	 
	 @FXML 
	 private TableColumn<Plat_cmd, Double> colsupp; // colonne supplément 
	 
	 @FXML 
	 private TableColumn<Plat_cmd, Double> colpu; // colonne prix unitaire 
	 
	 @FXML 
	 private TableColumn<Plat_cmd, Double> colmontant; // colonne montant 
	 
	 ObservableList<Plat_cmd> list1 = FXCollections.observableArrayList(); 
	 
	 @Override 
	 public void initialize(URL arg0, ResourceBundle arg1) { 
	 
	 ArrayList<String> list = new ArrayList<String>(); 
	 
	 
	 Statement stmt;
	try {
		stmt = Connexion.getcon().createStatement();
		ResultSet rs = stmt.executeQuery("select libelle from plat");
		 while (rs.next()) {
			 list.add(rs.getString(1));
		 }
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	 
	 
	 /* 
	 * La liste construite va être attribuer à l’objet ComboBox cbPlat 
	(une liste 
	 * déroulante) pour lister les plats disponibles. 
	 */ 
	 
	 ObservableList<String> cb = FXCollections.observableArrayList(list); 
	 cbplat.setItems(cb); 
	 
	 collibelle.setCellValueFactory(new PropertyValueFactory<Plat_cmd, String>("libelle")); 
	 colqte.setCellValueFactory(new PropertyValueFactory<Plat_cmd, Integer>("quantite"));
	 colsupp.setCellValueFactory(new PropertyValueFactory<Plat_cmd, Double>("supplement"));
	 colpu.setCellValueFactory(new PropertyValueFactory<Plat_cmd, Double>("PU"));
	 colmontant.setCellValueFactory(new PropertyValueFactory<Plat_cmd, Double>("montant"));
	 // à compléter 
	            tableR.setItems(list1); 
	 } 
	 
	 @FXML 
	 void addhandle(ActionEvent event) { 
	 Plat_cmd placmd = new Plat_cmd(cbplat.getValue(), Integer.valueOf(txtqte.getText()), 0, 0, 0);
	 
	 
	 boolean supp = suppOk.isSelected();
	
	 Statement stmt;
		try {
			stmt = Connexion.getcon().createStatement();
			ResultSet rs = stmt.executeQuery("select prix from plat where libelle = '"+placmd.getLibelle()+"'");
			 if (rs.next()) {
				 placmd.setPU(rs.getDouble("prix"));
			 }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		
		if (supp)
			placmd.setSupplement(2000);
		tableR.getItems().add(placmd);
		
		placmd.setMontant();
	 
	 } 
	 
	 @FXML 
	 void calculer(ActionEvent event) throws IOException { 
		 double m = 0;
		 for (Plat_cmd c : tableR.getItems()) {
			 m += c.getMontant();
		 }
		 lblmontant.setText("MTT : "+m);
		 
		 BufferedWriter br = new BufferedWriter(new FileWriter("E://fichiers/commandes.txt",true));
		 br.newLine();
		 br.write("MTT : "+m);
		 br.close();
		 
		 
	 } 
	 @FXML 
	 void removehandle(ActionEvent event) { 
		 Alert alert = new Alert(AlertType.CONFIRMATION);
 		alert.setTitle("Delete");
 		
 		alert.setContentText("Are you sure you want to delete it ?");
 		
 		Optional<ButtonType> result = alert.showAndWait();
 		if (result.isPresent()) {
 			if (result.get() == ButtonType.OK) {
 				list1.remove(tableR.getSelectionModel().getSelectedIndex());
 			}
 		}
 		
		 
		 
	 }
	 
}
