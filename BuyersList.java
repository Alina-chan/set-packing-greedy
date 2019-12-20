import java.io.File;
import java.util.ArrayList;

/**
 * Save item's pointer into var item.
 * Save next item's reference into var next.
 */
class ItemNode {
	public int item;
	public ItemNode next;

	public ItemNode(int item) {
		this.item = item;
		this.next = null;
	}
}

/**
 * Simple linked list structure.
 * Saves item pointers.
 */
class ItemsList {
	private int nbNodes; // number of nodes in list
	private ItemNode first;
	private ItemNode last;

	/**
	 * Construct a new empty list
	 */
	public ItemsList() {
		this.first = null;
		this.last = null;
		this.nbNodes = 0;
	}

	/**
	 * @return number of nodes in list
	 */
	public int size() {
		return nbNodes;
	}

	/**
	 * @return true if list is empty
	 */
	public boolean empty() {
		return first == null;
	}
	
	/**
	 * Check if items exists in list
	 * 
	 * @param item
	 * @return true if item exists
	 */
	private boolean contains_item(int item) {
		ItemNode iter = first;
		
		while(iter != null) {
			if (iter.item == item) {
				return true;
			}
			iter = iter.next;
		}
		
		return false;
	}

	/**
	 * @param ilist
	 * @return true if list is a superset to ilist
	 */
	public boolean contains(ItemsList ilist) {
		if (nbNodes < ilist.nbNodes) {
			return false;
		} 
		
		ItemNode iter = ilist.first;
		
		while(iter != null) {
			if (contains_item(iter.item)) {
				iter = iter.next;
				continue;
			} else {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Create node and hold current item.
	 * Add node at the end of the list.
	 * 
	 * @param item
	 * @return new sum of list nodes
	 */
	public int append(int item) {
		ItemNode newitem =  new ItemNode(item);
		
		if (this.empty()) {
			first = newitem;
			last = newitem;
		} else {
			last.next = newitem;
			last = newitem;
		}
		
		nbNodes++;
		return nbNodes;
	}

	/**
	 * Remove a single item from list
	 * 
	 * @param item
	 */
	private void remove_element(int item) {
		ItemNode previous = null;
		ItemNode current = first;
		
		while (current != null) {
			if (current.item == item) {
				if(current == first) {
					if(nbNodes == 1) {
						first = null;
						last = null;
					} else {
						first = current.next;
					}
				} else if (current == last) {
					last = previous;
					last.next = null;
				} else {
					previous.next = current.next;
				}
				
				current = null;
				nbNodes--;
				return;
			}
			
			previous = current;
			current = current.next;
		}
	}
	
	/**
	 * Remove all nodes whose elements/keys are included in the ilist nodes.
	 * 
	 * @param ilist
	 */
	public void remove(ItemsList ilist) {
		if (this.contains(ilist)) {
			ItemNode iter = ilist.first;
			
			while(iter != null) {
				remove_element(iter.item);
				iter = iter.next;
			}
		}
	}
}

/**
 * Keep references of first and last nodes of buyers list.
 */
class BuyerNode {
	public int id;
	public int value;
	public ItemsList itemsList;
	public BuyerNode next;

	public BuyerNode(int id, int value, ItemsList ilist) {
		this.id = id;
		this.value = value;
		this.itemsList = ilist;
	}
}

/**
 * Declare a simple linked list with one node per buyer.
 */
class BuyersList {
	public int opt; // Write/read optimal solution's value
	private int nbNodes; // Current number of list nodes
	public int n; // Hold number of items from file data
	private BuyerNode first;
	private BuyerNode last;
	
	/**
	 * Construct a new empty list.
	 */
	public BuyersList() {
		this.first = null;
		this.last = null;
		this.nbNodes = 0;
	}

	/**
	 * Read data from file.
	 * Create nodes from BuyersList.
	 * Write optimal solution's value into opt.
	 * 
	 * @param filename
	 * @return m items
	 */
	public int readFile(String filename) {
		int m = 0;
		java.io.BufferedReader file = null;

		try {
			file = new java.io.BufferedReader(new java.io.FileReader(filename));

			// Read dimensions
			String line = file.readLine();
			String[] data = line.split(" ");
			
			// Number of items
			m = Integer.parseInt(data[0]);
			
			// Number of buyers
			this.n = Integer.parseInt(data[1]);
			
			// Optimum revenue
			this.opt = Integer.parseInt(data[2]);

			// Read Buyers Information
			int id = 0;
			while((line = file.readLine()) != null) {
				data = line.split(" ");
				
				// Read value
				int value = Integer.parseInt(data[0]);
				
				// Read Item List
				ItemsList itemsList = new ItemsList();
				
				for(int i = 1; i < data.length; i++) {
					itemsList.append(Integer.parseInt(data[i]));
				}
				
				// Insert new buyer
				this.append(id++, value, itemsList);
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (file != null) {
					file.close();
				}
			} catch (java.io.IOException ex) {
				ex.printStackTrace();
			}
		}
		return m;
	}

	/**
	 * @return true if list is empty
	 */
	public boolean empty() {
		return first == null; 
	}

	/**
	 * @return current number of list nodes
	 */
	public int size() {
		return nbNodes;
	}

	/**
	 * @return total value of items (sum) for buyer
	 */
	public int totalValue() {
		BuyerNode iter = first;
		int sum = 0;
		
		while(iter != null) {
			sum += iter.value;
			iter = iter.next;
		}
		
		return sum;
	}

	/**
	 * Create BuyerNode node and store the pointer (id), the value (value) 
	 * and the list of items the buyer wants to get.
	 * 
	 * Append the node at the end of the list.
	 * 
	 * @param id
	 * @param value
	 * @param ilist
	 * @return updated list length
	 */
	public int append(int id, int value, ItemsList ilist) {
		BuyerNode newbuyer = new BuyerNode(id,value,ilist);
		
		if (this.empty()) {
			first = newbuyer;
			last = newbuyer;
		} else {
			last.next = newbuyer;
			last = newbuyer;
		}
		
		nbNodes++;
		return nbNodes;
	}
	
	
	/**
	 * Remove buyer from buyerslist
	 * */
	public void remove(int id) {
		BuyerNode previous = null;
		BuyerNode current = first;
		
		while (current != null) {
			if (current.id == id) {
				if(current == first) {
					if(nbNodes == 1) {
						first = null;
						last = null;
					} else {
						first = current.next;
					}
				} else if (current == last) {
					last = previous;
					last.next = null;
				} else {
					previous.next = current.next;
				}
				
				current = null;
				nbNodes--;
				return;
			}
			
			previous = current;
			current = current.next;
		}	
	}
	
	
	/**
	 * Greedy algorithm implementation to find the maximum sum value of m items.
	 * 
	 * @param m
	 * @return list with subset of selected buyers
	 */
	public BuyersList greedy(int m) {
		// Stock with our items
		ItemsList stock = new ItemsList();
		
		for(int item_idx = 0; item_idx < m; item_idx++) {
			stock.append(item_idx);
		}
		
		// Solution of Buyers
		BuyersList B = new BuyersList();
		
		// Loop over Buyers
		boolean stop = false;
		boolean available;
		
		while(!stop) {
			BuyerNode current_buyer = first;
			BuyerNode best_buyer = null;
			double max_fraction = 0;
			double buyers_fraction = 0;
			available = false;
			
			while(current_buyer != null) {
				if(stock.contains(current_buyer.itemsList)) {
				    buyers_fraction = current_buyer.value / current_buyer.itemsList.size();
					
				    if(buyers_fraction > max_fraction) {
						max_fraction = buyers_fraction;
						best_buyer = current_buyer;
					}
				    
					available = true;
				}
				
				current_buyer = current_buyer.next;
			}
			
			// If item doesn't exist exit
			if (!available) {
				break;
			}
			
			// Append to solution
			B.append(best_buyer.id,best_buyer.value,best_buyer.itemsList);
		
			// Remove buyer from perspective buyerslist
			this.remove(best_buyer.id);
			
			// Remove from stock the buyers itemlist
			stock.remove(best_buyer.itemsList);
		}
		
		return B;
	}

	
	public static void main(String[] args) {
		String filename;
		
		// Get user directory
		String dir = System.getProperty("user.dir");
		
		// Get txt files in the m500 folder
		ArrayList<String> m500filenames = new ArrayList<String>();
		File m500 = new File(dir + "/m500/");
		File[] m500files = m500.listFiles();

		for (int i = 0; i < m500files.length; i++) {
		  if (m500files[i].isFile()) {
			  m500filenames.add(m500files[i].getName());
		  }
		}
		
		// Get results for the m500 txt files
		for(int i=0 ; i < m500filenames.size(); i++) {
			filename = m500filenames.get(i);
			BuyersList buyersList = new BuyersList();
			
			int m = buyersList.readFile(dir + "/m500/" + filename);
			
			// Start a time counter 
			long startTime = System.nanoTime();
	
			// Call the greedy algorithm and get the solution
			BuyersList solution = buyersList.greedy(m);
			
			// Stop time counter
			long endTime = System.nanoTime();
	
	        // Get difference of two nanoTime values
			long timeElapsed = endTime - startTime;
			
			System.out.println(
					"* m = " + m + ":" + "\n" +
					"- n = " + buyersList.n + "\t" +
					"avgTime = " + timeElapsed / 1000000 + "\t" +
					"greedy value = " + solution.totalValue() + "\t" +
					"opt value = " + buyersList.opt
			);
			
		}
		
		System.out.println("---");
		
		// Get all txt files in the n2000 folder
		ArrayList<String> n2000filenames = new ArrayList<String>();
		File n2000 = new File(dir + "/n2000/");
		File[] n2000files = n2000.listFiles();

		for (int i = 0; i < n2000files.length; i++) {
		  if (n2000files[i].isFile()) {
			  n2000filenames.add(n2000files[i].getName());
		  }
		}
		
		// Get results for the n2000 txt files
		for(int i=0 ; i < n2000filenames.size(); i++) {
			filename = n2000filenames.get(i);
			BuyersList buyersList = new BuyersList();
			
			int m = buyersList.readFile(dir + "/n2000/" + filename);
			
			// Start a time counter 
			long startTime = System.nanoTime();
	
			// Call the greedy algorithm and get the solution
			BuyersList solution = buyersList.greedy(m);
			
			// Stop time counter
			long endTime = System.nanoTime();
	
	        // Get difference of two nanoTime values
			long timeElapsed = endTime - startTime;
			
			System.out.println( 
					"* n = " + buyersList.n + ":" + "\n" +
					"- m = " + m + "\t" +
					"avgTime = " + timeElapsed / 1000000 + "\t" +
					"greedy value = " + solution.totalValue() + "\t" +
					"opt value = " + buyersList.opt
			);
		}	
	}
}

