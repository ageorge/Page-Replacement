package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Main class that demonstrates Page Replacement
 * @author anitageorge (UBID: 1032137)
 *
 */
public class PageReplacement {
	
	private List<PageFrame> All_pageFrames;
	private int pageFault; 
	 
	/**
	 * Constructor to initialize the list
	 */
	public PageReplacement() {
		All_pageFrames = new ArrayList<>();
	} 
	
	/**
	 * Method that demonstrates the FIFO Page Replacement algorithm
	 * Steps:
	 * 1. Create a page frame - Data structure used in 'Queue' to insert at the back and remove from front (First In First Out)
	 * 2. Traverse through the pages and search the frame for the page
	 * 3. If present, then there is no page fault and continue to the next iteration
	 * 4. If absent, then increment page fault value
	 * 		4.1 if page frame has reached max_capacity: then remove the first element that was inserted and insert the new page
	 * 		4.2 else: insert the new page 
	 * 5. Format the output to represent clearly what is happening at each stage
	 * 6. Display the page fault
	 * @param pageframe
	 */
	public void FIFO_pagereplacement(PageFrame pageframe) {
		int size;
		Queue<Integer> frame; //Step 1
		pageFault = 0;
		size = pageframe.getFrame_size();
		frame = new LinkedList<>();
		System.out.println("FIFO: Size = " + size);
		System.out.print("Page\tPage Fault");
		for(Integer f : pageframe.getFrames()) { // Step 2 and 3
			System.out.print("\n" + f + "\t");
			if(!frame.contains(f)) { //Step 4
				pageFault++;
				if(frame.size() == size) //Step 4.1
					frame.remove();
				frame.add(f); //Step 4.2
				System.out.print(frame);
				if(frame.size() < size) {  //Step 5
					int diff = size - frame.size();
					for(int i = 0; i < diff; i++) {
						System.out.print(" - ");
					}
				}
				System.out.print("\t");
			}
		}
		pageframe.setPageFault(pageFault);
		System.out.println("\nPage Fault using FIFO: " + pageFault + "\n"); //Step 6
	}
	
	/**
	 * 
	 * Method that demonstrates the LRU Page Replacement algorithm
	 * Steps:
	 * 1. Create a page frame - Data structure used in 'STACK' (least used element -> top of the stack)
	 * 2. Traverse through the pages and search the frame for the page
	 * 3. If absent, then increment page fault value
	 * 		3.1 if page frame has reached max_capacity: then remove the top element(least recently used element) and insert the new page to the bottom of the stack
	 * 		3.2 else: insert the new page to the bottom of the stack
	 * 4. If present, then there is no page fault and move the element to the bottom of the stack
	 * 5. Format the output to represent clearly what is happening at each stage
	 * 6. Display the page fault
	 * @param pageframe
	 */
	public void LRU_pagereplacement(PageFrame pageframe) {
		int size;
		Stack<Integer> frame; //Step 1
		pageFault = 0;
		size = pageframe.getFrame_size();
		frame = new Stack<Integer>();
		System.out.println("LRU: Size = " + size);
		System.out.print("Page\tPage Fault");
		for(Integer f : pageframe.getFrames()) { //Step 2 
			System.out.print("\n" + f + "\t");
			if(frame.search(f) == -1) { //Step 3
				pageFault++;
				if(frame.size() == size) //Step 3.1
					frame.pop();
				frame.insertElementAt(f, 0); //Step 3.2
				System.out.print(frame);
				if(frame.size() < size) { //Step 5
					int diff = size - frame.size();
					for(int i = 0; i < diff; i++) {
						System.out.print(" - ");
					}
				}
				System.out.print("\t");
			} else { //Step 4
				frame.remove(f);
				frame.insertElementAt(f, 0);
			}
		}
		pageframe.setPageFault(pageFault);
		System.out.println("\nPage Fault using LRU: " + pageFault + "\n"); //Step 6
	}
	
	/**
	 * Method that demonstrates the Optimal Page Replacement algorithm
	 * Steps:
	 * 1. Create a page frame - Data structure used is list
	 * 2. Traverse through the pages and search the frame for the page
	 * 3. If present, then there is no page fault and continue to the next iteration
	 * 4. If absent, then increment page fault value
	 * 		4.1 create a sublist of all pages including current page
	 * 		4.2 if page frame has reached max_capacity: then find the optimal page to remove and remove it, then insert the new page
	 * 		4.3 else: just insert the new page 
	 * 5. Format the output to represent clearly what is happening at each stage
	 * 6. Display the page fault
	 * @param pageframe
	 */
	public void Optimal_pagereplacement(PageFrame pageframe) {
		int size;
		List<Integer> frame; //Step 1
		pageFault = 0;
		size = pageframe.getFrame_size();
		frame = new LinkedList<>();
		System.out.println("Optimal: Size = " + size);
		System.out.print("Page\tPage Fault");
		for(Integer f : pageframe.getFrames()) { //Step 2 and 3
			System.out.print("\n" + f + "\t");
			if(!frame.contains(f)) {  //Step 4
				pageFault++;
				List<Integer> sublist = pageframe.getFrames().subList(pageframe.getFrames().indexOf(f) + 1, pageframe.getFrames().size()); //Step 4.1
				if(frame.size() == size) { //Step 4.2
					Integer ele = findOptimal(frame, sublist);
					frame.remove(ele);
				} 
				frame.add(f); //Step 4.3
				System.out.print(frame);
				if(frame.size() < size) { //Step 5
					int diff = size - frame.size();
					for(int i = 0; i < diff; i++) {
						System.out.print(" - ");
					}
				}
				System.out.print("\t");
			} 
		}
		pageframe.setPageFault(pageFault);
		System.out.println("\nPage Fault using Optimal: " + pageFault + "\n"); //Step 6
	}
	
	/**
	 * Method to find the optimal element to remove
	 * Steps:
	 * 1. search for position the page in the sublist 
	 * 2. if not found:  then that page is returned
	 * 3. if found: the variable max stores the highest position, compare this position with max
	 * 		3.1 if pos > max: then set max to pos
	 * 		3.2 if not continue iteration
	 * @param frame
	 * @param sublist
	 * @return page to remove 
	 */
	public Integer findOptimal(List<Integer> frame, List<Integer> sublist) {
		int max = -1, pos, ele = frame.get(0);
		for(Integer f:frame) {
			pos = sublist.indexOf(f); //Step 1
			if(pos == -1) { //Step 2
				ele = f;
				break;
			}else if(pos > max) { //Step 3 and 3.2
				max = pos; //Step 3.1
				ele = f;
			}
		}
		return ele;
	}
	
	/**
	 * Method that iterates through the list of inputs and executes according to the algorithm
	 */
	public void execute() {
		for(PageFrame pageframe : All_pageFrames) {
			switch(pageframe.getAlgorithm()) {
			case "FIFO":
				FIFO_pagereplacement(pageframe);
				break;
			case "OPTIMAL":
				Optimal_pagereplacement(pageframe);
				break;
			case "LRU":
				LRU_pagereplacement(pageframe);
				break;
			}
		}
	}
	
	/**
	 * Method to print the consolidated output to the console
	 */
	public void output() {
		System.out.println("----------Consolidated Output-------------");
		System.out.println("Algorithm\tSize\tPageFault\tPages");
		for(PageFrame frame : All_pageFrames) {
			System.out.println(frame.getAlgorithm() + "\t\t" + frame.getFrame_size() + "\t" + frame.getPageFault() + "\t\t" + frame.getFrames());
		}
	}
	
	/**
	 * Method to read the PageFrames data from a file
	 * @param filename
	 */
	public void readPageFrames(String filename) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("resrc/"+filename));
			String input = br.readLine(); //Read line by line
			while(input != null) {
				String content[] = input.split(","); //Retrieve the individual data from each line
				List<Integer> frame = new ArrayList<>(Integer.parseInt(content[1])); //Creating a list to hold all the pages
				for(int i = 2; i < content.length; i++) { //Adding the pages to the frames
					frame.add(Integer.parseInt(content[i]));
				}
				PageFrame pageFrame = new PageFrame();
				pageFrame.setAlgorithm(content[0].charAt(0)); //Setting the algorithm
				pageFrame.setFrame_size(Integer.parseInt(content[1])); //Setting the frame size
				pageFrame.setFrames(frame); //Setting the list of pages
				All_pageFrames.add(pageFrame); //Adding each page frame to a list to hold them until execution completes
				input = br.readLine(); //Read the next line
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to execute the page replacement demonstration
	 * @param args
	 */
	public static void main(String[] args) {
		PageReplacement pr = new PageReplacement();
		pr.readPageFrames("input.txt");
		pr.execute();
		pr.output();
	}
}
