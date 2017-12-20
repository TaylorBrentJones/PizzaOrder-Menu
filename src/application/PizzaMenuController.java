package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;

public class PizzaMenuController extends Pizza {

	public PizzaMenuController() throws IllegalPizza {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ObservableList<String> sizeList = FXCollections.observableArrayList("Small", "Medium", "Large");
	ObservableList<String> cheeseList = FXCollections.observableArrayList("Single", "Double", "Triple");
	ObservableList<String> pepperoniList = FXCollections.observableArrayList("None", "Single", "Double");
	ObservableList<String> mushroomsList = FXCollections.observableArrayList("None", "Single", "Double");
	SpinnerValueFactory<Integer> quantitySpinnerValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
	
	//Variable Declaration
	
	Integer quantity = 1;
	Integer totalQuantity = 0;
	Integer toppingNumber = 0;
	Integer cheeseAmount = 0;
	Integer pepperoniAmount = 0;
	Integer mushroomsAmount = 0;
	
	double sizeCost = 7.00;
	double toppingCost = 0.00;
	double pizzaCost = 7.00;
	double totalCost = 0.00;
	
	String msg = "";
	String size;
	String cheese;
	String pepperoni;
	String mushrooms;
	String order = "";
	
	//Creation Information 

	@FXML
	private Button cancelButton;
	@FXML
	private Button addButton;
	@FXML 
	private Button purchaseButton;
	
	@FXML
	private TextArea orderTextArea;
	@FXML
	private Label currentLabel;
	@FXML
	private Label totalLabel;
	
	@FXML
	private Spinner<Integer> quantitySpinner;
	
	@FXML
	private Label message;
	
	@FXML
	private ChoiceBox<String> sizeBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<String> cheeseBox = new ChoiceBox<>();
	@FXML 
	private ChoiceBox<String> pepperoniBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<String> mushroomsBox = new ChoiceBox<>();
	
	@FXML
	void initialize() {
		
		//Error Message
		message.setText(msg);
		
		//Quantity default
		quantitySpinner.setValueFactory(quantitySpinnerValue);
		
		//Choices defaults
		sizeBox.setValue("Small");
		sizeBox.setItems(sizeList);
		
		cheeseBox.setValue("Single");
		cheeseBox.setItems(cheeseList);
		
		pepperoniBox.setValue("None");
		pepperoniBox.setItems(pepperoniList);
		
		mushroomsBox.setVisible(false);
		mushroomsBox.setValue("None");
		mushroomsBox.setItems(mushroomsList);
		
		//Order defaults
		orderTextArea.textProperty();
		currentLabel.setText("Current Pizza: $" + pizzaCost + " x " + quantity);
		totalLabel.setText("Total: $" + totalCost);
		
		
		//Size adjustment
		sizeBox.valueProperty().addListener((observale, oldVal, newVal) -> {
			if (sizeBox.getValue() == "Small") {
				sizeCost = 7.00;
				toppingAmount();
				setCurrentAmount();
			} else if (sizeBox.getValue() == "Medium") {
				sizeCost = 9.00;
				toppingAmount();
				setCurrentAmount();
			} else {
				sizeCost = 11.00;
				toppingAmount();
				setCurrentAmount();
			}
			
		});
		
		//Cheese adjustment 
		cheeseBox.valueProperty().addListener((observableValue, oldVal, newVal) -> {
			if (cheeseBox.getValue() == "Double") {
				cheeseAmount = 1;
				toppingAmount();
				setCurrentAmount();
			} else if (cheeseBox.getValue() == "Triple") {
				cheeseAmount = 2;
				toppingAmount();
				setCurrentAmount();
				mushroomsBox.setVisible(false);
			} else {
				cheeseAmount = 0;
				toppingAmount();
				setCurrentAmount();
			}

		});
		
		//Pepperoni adjustment
		pepperoniBox.valueProperty().addListener((observableValue, oldVal, newVal) ->	{
			if (pepperoniBox.getValue() == "Single") {
				pepperoniAmount = 1;
				toppingAmount();
				setCurrentAmount();
				mushroomsBox.setVisible(true);
			} else if (pepperoniBox.getValue() == "Double") {
				pepperoniAmount = 2;
				toppingAmount();
				setCurrentAmount();
				mushroomsBox.setVisible(true);
			} else {
				pepperoniAmount = 0;
				toppingAmount();
				setCurrentAmount();
				mushroomsBox.setValue("None");
				mushroomsBox.setVisible(false);
			}
			
		});
		
		//Mushroom adjustment
		mushroomsBox.valueProperty().addListener((observableValue, oldVal, newVal) ->	{
			if (mushroomsBox.getValue() == "Single") {
				mushroomsAmount = 1;
				toppingAmount();
				setCurrentAmount();
			} else if (mushroomsBox.getValue() == "Double") {
				mushroomsAmount = 2;
				toppingAmount();
				setCurrentAmount();
			} else {
				mushroomsAmount = 0;
				toppingAmount();
				setCurrentAmount();
			}
			
		});
		
		//changes quantity displayed
		quantitySpinner.valueProperty().addListener((observableValue, oldVal, newVal) ->	{
			quantity = newVal;
			setCurrentAmount();
		});
		
		//if add button is pressed
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (totalQuantity + quantity > 100) {
					msg = "You can not order more than one hundred pizzas. Reduce the current quantity you are trying to add.";
					message.setText(msg);
				} else {
					msg = "";
					message.setText(msg);
					size = sizeBox.getValue();
					cheese = cheeseBox.getValue();
					pepperoni = pepperoniBox.getValue();
					mushrooms = mushroomsBox.getValue();
					totalQuantity += quantity;
					order = order + "\n" + PizzaMenuController.this.toString(size, cheese, pepperoni, mushrooms)
						+ " Quantity: " + quantity + ". Costs for each: $" + pizzaCost + ". Total Quantity: " + totalQuantity;
					orderTextArea.setText(order);
					totalCost += pizzaCost * quantity;
					if (totalQuantity > 10 && totalQuantity < 21) {
						totalLabel.setText("Total: $ "+ totalCost/1.10);
					} else if (totalQuantity > 20) {
						totalLabel.setText("Total: $" + totalCost/1.15);
					} else {
						totalLabel.setText("Total: $" + totalCost);
					}
					if (totalQuantity == 100) {
						msg = "Max pizzas ordered. Purchase or cancel.";
						message.setText(msg);
					}
					
					//Reset to default
					sizeBox.setValue("Small");
					cheeseBox.setValue("Single");
					pepperoniBox.setValue("None");
					mushroomsBox.setVisible(false);
					mushroomsBox.setValue("None");
					
				}
			}
		});
		
		//if purchase button is pressed
		purchaseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				order = "";
				orderTextArea.setText("");
				totalCost = 0.00;
				totalLabel.setText("Total: $" + totalCost);
				msg = "Your purchase has gone through.";
				message.setText(msg);
				totalQuantity = 0;
				
				//Reset to default
				sizeBox.setValue("Small");
				cheeseBox.setValue("Single");
				pepperoniBox.setValue("None");
				mushroomsBox.setVisible(false);
				mushroomsBox.setValue("None");
			}
		});
		
		//if cancel button is pressed
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
	}
	
	//adds topping prices to pizza price
	public void toppingAmount() {
		toppingNumber = cheeseAmount + pepperoniAmount + mushroomsAmount;
		if (toppingNumber > 3 ) {
			tooManyToppings();
		} else {
			msg = "";
			message.setText(msg);
			toppingCost = toppingNumber * 1.50;
			addButton.setVisible(true);
		}
	}
	
	//sets the  current order price
	public void setCurrentAmount() {
		pizzaCost();
		currentLabel.setText("Current Pizza: $" + pizzaCost + " x " + quantity);
	}
	
	//calculates current pizza price
	public void pizzaCost() {
		pizzaCost = sizeCost + toppingCost;
	}
	
	//makes sure there are at most 3 toppings
	public void tooManyToppings() {
		msg = "Too many toppings, you can only have 3 toppings.";
		message.setText(msg);
		addButton.setVisible(false);
	}
}
