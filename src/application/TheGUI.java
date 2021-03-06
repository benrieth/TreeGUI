package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	Button clearBtn;
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
        
		menu = new HBox(5);
		menu.setAlignment(Pos.CENTER);
		
		valueTxtFld = new TextField();
		importBtn = new Button("Add Value");
		clearBtn = new Button("Clear All");
		
		menu.getChildren().addAll(valueTxtFld, importBtn, clearBtn);
		
		importBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				addNode();
			}
		});
		
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clearTree();
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
	
	private void clearTree() {
		rootNode = null;
		theTree.getChildren().clear();
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
		if (value <= Integer.parseInt(data.getText())) {
			if (left == null) {
				Button btn = new Button(value + "");
				
				left = new ButtonNode(btn);
				left.layer = layer;
				left.prev = this;
				
				pane.add(btn, (int) (GridPane.getColumnIndex(left.prev.data) - 8*Math.pow(.5, layer-1)), layer);
				
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						ArrayList<Button> toAddBack;
						toAddBack = left.remove(left, pane);
						ButtonNode temp = left.prev;
						left = null;
						addBack(toAddBack, temp, pane);
					}
				});
			} else {
				left.insert(value, layer+1, pane);
			}
		} else {
			if (right == null) {
				Button btn = new Button(value + "");
				
				right = new ButtonNode(btn);
				right.layer = layer;
				right.prev = this;
				
				pane.add(btn, (int) (GridPane.getColumnIndex(right.prev.data) + 8*Math.pow(.5, layer-1)), layer);
				
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						ArrayList<Button> toAddBack;
						toAddBack = right.remove(right, pane);
						ButtonNode temp = right.prev;
						right = null;
						addBack(toAddBack, temp, pane);
					}
				});
			} else {
				right.insert(value, layer+1, pane);
			}
		}
	}
	
	public ArrayList<Button> remove(ButtonNode toBeRemoved, GridPane pane) {
		ArrayList<Button> subValues = toBeRemoved.collectValues();
		
		for(Button btn: subValues) {
			pane.getChildren().remove(btn);
			
		}
		
		subValues.remove(toBeRemoved.data);
		toBeRemoved = null;
		
		return subValues;
	}
	
	private void addBack(ArrayList<Button> subValues, ButtonNode parent, GridPane pane) {
		
		Collections.sort(subValues, new Comparator<Button>() {
			public int compare(Button a, Button b) {
				int val1 = Integer.parseInt(a.getText());
				int val2 = Integer.parseInt(b.getText());
				
				if(val1 == val2) {
					return 0;
				} else if(val1 > val2) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		
		Button toInsert;
		while(!subValues.isEmpty()) {
		
			toInsert = subValues.get(subValues.size()/2);
			
			subValues.remove(toInsert);
			parent.insert(Integer.parseInt(toInsert.getText()), parent.layer+1, pane);
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
