package application;

import java.util.Scanner;

public class IntegerListTest{
	static IntegerList list = new IntegerList(10);
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args){
		printMenu();
		int choice = scan.nextInt();
		
		while (choice != 0) {
			dispatch(choice);
			printMenu();
			choice = scan.nextInt();
		}
	}

	public static void dispatch(int choice){
		int loc;
		
		switch(choice){
			case 0:
				System.out.println("Bye!");
				break;
				
			case 1:
				System.out.println("How big should the list be?");
				int size = scan.nextInt();
				list = new IntegerList(size);
				list.randomize();
				break;
				
			case 2:
				list.print();
		
				break;
				
			case 3:
				System.out.println("What is the value of the new number?");
				int value = scan.nextInt();
				list.addElement(value);
				
				break;
				
			case 4:
				System.out.println("What value do you want deleted?");
				int delete = scan.nextInt();
				list.removeFirst(delete);
				
				break;
				
			case 5:
				System.out.println("What value do you want deleted?");
				int deleteAll = scan.nextInt();
				list.removeAll(deleteAll);
				
				break;
				
			case 6:
				list.sortList();
				break;
				
			default:
				System.out.println("Sorry, invalid choice");
				
		}
	}

	public static void printMenu() {
		System.out.println("\n Menu ");
		System.out.println(" ====");
		System.out.println("0: Quit");
		System.out.println("1: Create a new list (** do this first!! **)");
		System.out.println("2: Print the list");
		System.out.println("3: Add a Number to List");
		System.out.println("4: Delete First Instance of a Number");
		System.out.println("5: Delete All Instances of a Number");
		System.out.println("6: Sort List");
		
		System.out.print("\nEnter your choice: ");
	}
}