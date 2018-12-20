package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class IntegerListButATree extends Application {
	
	int numNodes = 0;
	Node root;
	TextField toAddTxtFld;
	TextArea printedTree;
	
	@Override
	public void start(Stage stage) {
		initUI(stage);
	}
	
	public void initUI(Stage stage) {
		VBox operations = new VBox(5);
		operations.setPrefWidth(75);
		
		/*Button createBtn = new Button("Create Tree");
		createBtn.setMinWidth(operations.getPrefWidth());
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
			}
		});*/
		
		Button addNumBtn = new Button("Add Element");
		addNumBtn.setMinWidth(operations.getPrefWidth());
		addNumBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addNodetoTree();
			}
		});
		
		toAddTxtFld = new TextField();
		toAddTxtFld.setPrefWidth(operations.getPrefWidth());
		
		operations.getChildren().addAll(addNumBtn, toAddTxtFld);
		operations.setPadding(new Insets(5));
		operations.setAlignment(Pos.CENTER);
		
		printedTree = new TextArea();
		
		/*TabPane tabs = new TabPane();
		
		Tab printTab = new Tab();
		printTab.setText("Tree in Print");
		
		
		
		printTab.setContent(printedTree);
		
		Tab visualTab = new Tab();
		visualTab.setText("Tree Visualizaiton");
		
		tabs.getTabs().addAll(printTab, visualTab);*/
		
		GridPane root = new GridPane();
		root.setPrefSize(600, 200);
		
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(80);
		
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(20);
		
		root.getColumnConstraints().addAll(col1, col2);
		
		root.add(printedTree, 0, 0);
		root.add(operations, 1, 0);
		
		Scene scene = new Scene(root, 600, 200);
		stage.setTitle("Integer List but With Trees");
		stage.setScene(scene);
		stage.show();
	}
	
	/*public static void main(String[] args) {
		launch(args);
	}*/
	
	private void addNodetoTree() {
		int intToAdd = Integer.parseInt(toAddTxtFld.getText());
		if(numNodes == 0) {
			root = new Node(intToAdd);
		} else {
			root.insert(intToAdd);
		}
		
		String printedTreeTxt = "";
		ArrayList<Object> printedTreeRaw = root.getSubNodes();
		
		for(int x = 0; x < printedTreeRaw.size(); x++) {
			printedTreeTxt = printedTreeRaw.get(x) + "\n";
		}
		
		printedTree.setText(printedTreeTxt);
	}
	
	class Node {
		Node left, right;
		int data;
		ArrayList<Object> subNodes;
		
		public Node(int data) {
			this.data = data;
		}
		
		public void calculateSubNodes() {
			if (left != null) {
				left.calculateSubNodes();
			}
			subNodes.add(data);
			if(right != null) {
				right.calculateSubNodes();
			}
		}
		
		public ArrayList<Object> getSubNodes() {
			return subNodes;
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
			calculateSubNodes();
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
			System.out.println(data + " (root)");
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
}
