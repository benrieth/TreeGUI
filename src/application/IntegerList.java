package application;

public class IntegerList {
	
	int[] list; //values in the list

	public IntegerList(int size) {
		list = new int[size];
	}

	public void randomize() {
		for (int i=0; i<list.length; i++)
		list[i] = (int)(Math.random() * 100) + 1;
	
	}

	public void print() {
		for (int i=0; i<list.length; i++)
		System.out.println(i + ":\t" + list[i]);
	}
	
	private void increaseSize() {
		int [] newList = new int[list.length * 2];
		
		for (int x = 0; x < list.length; x++) {
			newList[x] = list[x];
		}
		list = newList;
	}
	
	public void addElement(int newVal) {
    	int numElements = 0;
		for (int x = 0; x < list.length; x++) {
    		if (list[x] != 0) { numElements++; }
    	}
		numElements -= 1;
		
		int size = list.length - 1;
    	if (numElements == size) {
    		increaseSize();
    	}
    	
    	int firstEmptyIndex = 0;
    	for (int x = 0; x < list.length; x++) {
    		if (list[x] == 0) {
    			firstEmptyIndex = x;
    			break;
    		} 
    	}
    	
    	list[firstEmptyIndex] = newVal;
	}
	
	public void removeFirst(int newVal) {
		int indexRemovedAt = -1;
		for (int x = 0; x < list.length; x++) {
			if (list[x] == newVal) {
				indexRemovedAt = x;
				list[x] = 0;
				break;
			}
		}
		
		if (indexRemovedAt != -1) {
			for (int x = indexRemovedAt; x < list.length-1; x++) {
				list[x] = list[x+1];
			}
			list[list.length-1] = 0;
		}
	}
	
	public void removeAll(int newVal) {
		for (int x = 0; x < list.length; x++) {
			if (list[x] == newVal) {
				list[x] = 0;
				for(int j = x; j < list.length-1; j++) {
					list[x] = list[x+j];
				}
				
			list[list.length-1] = 0;
			}
		}
	}
	
	public void sortList() {
		Sorts.insertionSort(list);
	}
}