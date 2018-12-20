package application;

import java.util.ArrayList;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TheGUI extends Application {
	
	Button importBtn;
	TextField valueTxtFld;
	
	StackPane dangerZone;
	HBox menu;
	GridPane theTree;
	GridPane rootGUI;
	
	Group group;
	Canvas canvas;
	GraphicsContext gc;
	
	ButtonNode rootNode;
	
	@Override
	public void start(Stage stage) {
		initUI(stage);
	}
	
	public void initUI(Stage stage) {
		theTree = new GridPane();
		
        canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
		
        dangerZone = new StackPane();
        
		menu = new HBox();
		menu.setAlignment(Pos.CENTER);
		
		valueTxtFld = new TextField();
		importBtn = new Button("Add Value");
		
		menu.getChildren().addAll(valueTxtFld, importBtn);
		
		importBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				addNode();
			}
		});
		
		ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
		for(int x = 0; x < 31; x++) {
			columns.add(new ColumnConstraints());
			columns.get(x).setPercentWidth(3.22);
		}
		
		ArrayList<RowConstraints> rows = new ArrayList<RowConstraints>();
		for(int x = 0; x < 6; x++) {
			rows.add(new RowConstraints());
			rows.get(x).setPercentHeight(16.66);
		}
		
		theTree.getColumnConstraints().addAll(columns);
		theTree.getRowConstraints().addAll(rows);
		
		dangerZone.getChildren().addAll(canvas, theTree);
		//dangerZone.setStyle("-fx-background-color: yellow; -fx-border-color: black");
		
		rootGUI = new GridPane();
		
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(100);
		
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(10);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(90);
		
		rootGUI.getColumnConstraints().add(col1);
		rootGUI.getRowConstraints().addAll(row1, row2);
		
		rootGUI.add(menu, 0, 0);
		rootGUI.add(dangerZone, 0, 1);
		
		Scene scene = new Scene(rootGUI, 900, 300);
		stage.setTitle("Tree GUI");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void addNode() {
		if(rootNode == null) {
			Button rootBtn = new Button(valueTxtFld.getText());
			rootNode = new ButtonNode(rootBtn);
			
			theTree.add(rootBtn, theTree.getColumnConstraints().size()/2, 0);
		} else {
			rootNode.insert(Integer.parseInt(valueTxtFld.getText()), 1, theTree);

		}
		valueTxtFld.setText("");
	}
	
	/*private void drawLine(GraphicsContext gc, double x0, double y0, double x, double y) {
		gc.beginPath();
		gc.moveTo(x0, y0);
		gc.lineTo(x, y);
		gc.stroke();
	}*/
	
	private void removeNode() {
		
	}
	
	private void clearTree() {
		
	}
}

class ButtonNode {
	ButtonNode left, right;
	ButtonNode prev;
	Button data;
	int layer;
	
	public ButtonNode(Button data) {
		this.data = data;
		prev = null;
	}
	
	public void insert(int value, int layer, GridPane pane) {
		ButtonNode parent = this;
		if (value <= Integer.parseInt(data.getText())) {
			if (left == null) {
				Button btn = new Button(value + "");
				
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						parent.remove(pane);
					}
				});
				
				left = new ButtonNode(btn);
				left.layer = layer;
				left.prev = this;
				
				pane.add(btn, (int) (GridPane.getColumnIndex(left.prev.data) - 8*Math.pow(.5, layer-1)), layer);
			} else {
				left.insert(value, layer+1, pane);
			}
		} else {
			if (right == null) {
				Button btn = new Button(value + "");
				
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						parent.remove(pane);
					}
				});
				
				right = new ButtonNode(btn);
				right.layer = layer;
				right.prev = this;
				
				pane.add(btn, (int) (GridPane.getColumnIndex(right.prev.data) + 8*Math.pow(.5, layer-1)), layer);
			} else {
				right.insert(value, layer+1, pane);
			}
		}
	}
	
	public void remove(GridPane pane) {
		int value = Integer.parseInt(this.data.getText());
		ArrayList<Button> subValues = this.collectValues();
		
		for(Button btn: subValues) {
			pane.getChildren().remove(btn);
		}
		
		pane.getChildren().remove(this.data);
		
		subValues.remove(0);
		for(Button btn: subValues) {
			prev.insert(Integer.parseInt(btn.getText()), 0, pane);
		}
		
		
		
		if (value <= Integer.parseInt(prev.data.getText())) {
			this.prev.left = null;
		} else {
			this.prev.right = null;
		}
		
	}
	
	private ArrayList<Button> collectValues() {
		ArrayList<Button> list = new ArrayList<Button>();
		if (left != null) {
			list.addAll(left.collectValues());
		}
		list.add(data);
		if (right != null) {
			list.addAll(right.collectValues());
		}
		
		return list;
	}

	private void drawLine(GraphicsContext gc, double x0, double y0, double x, double y) {
		 gc.setStroke(Color.FORESTGREEN.brighter());
		 gc.beginPath();
		 gc.moveTo(x0, y0);
		 gc.lineTo(x, y);
		 gc.stroke();
	}
}

class Node {
	Node left, right;
	int data;
	
	public Node(int data) {
		this.data = data;
	}
	
	public void insert(int value) {
		if (value <= data) {
			if (left == null) {
				left = new Node(value);
			} else {
				left.insert(value);
			}
		} else {
			if (right == null) {
				right = new Node(value);
			} else {
				right.insert(value);
			}
		}
	}
	
	public boolean contains(int value) {
		if(value == data) {
			return true;
		} else if (value < data) {
			if(left == null) {
				return false;
			} else {
				return left.contains(value);
			}
		} else {
			if (right == null) {
				return false;
			} else {
				return right.contains(value);
			}
		}
		
	}
	
	public void printInOrder() {
		if (left != null) {
			left.printInOrder();
		}
		System.out.println(data);
		if(right != null) {
			right.printInOrder();
		}
	}
	
	public void printPreOrder() {
		System.out.println(data);
		if(left != null) {
			left.printPreOrder();
		}
		if(right != null) {
			right.printPreOrder();
		}
	}
	
	public void printPostOrder() {
		if(left != null) {
			left.printPreOrder();
		}
		if(right != null) {
			right.printPreOrder();
		}
		System.out.println(data);
	}
}
