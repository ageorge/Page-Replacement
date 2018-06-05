package com;

import java.util.List;

/**
 * Entity class to store the details of the page
 * @author anitageorge (UBID: 1032137)
 *
 */
public class PageFrame {
	private String algorithm;
	private int frame_size;
	private List<Integer> frames;
	private int pageFault;
	
	/**  
	 * Default constructor to initialize the class
	 */
	public PageFrame() {
		super(); 
	} 
	/**
	 * @param algorithm
	 * @param frame_size
	 * @param frames
	 */ 
	public PageFrame(String algorithm, int frame_size, List<Integer> frames) {
		super();
		this.algorithm = algorithm;
		this.frame_size = frame_size;
		this.frames = frames;
	}
	
	/**
	 * @return the pageFault
	 */
	public int getPageFault() {
		return pageFault;
	}
	/**
	 * @param pageFault the pageFault to set
	 */
	public void setPageFault(int pageFault) {
		this.pageFault = pageFault;
	}
	/**
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}
	/**
	 * @param algorithm the algorithm to set 
	 */
	public void setAlgorithm(char algorithm) {
		if(algorithm == 'F') {
			this.algorithm = "FIFO";
		}
		if(algorithm == 'O') {
			this.algorithm = "OPTIMAL";
		}
		if(algorithm == 'L') {
			this.algorithm = "LRU";
		}
	}
	/**
	 * @return the frame_size
	 */
	public int getFrame_size() {
		return frame_size;
	}
	/**
	 * @param frame_size the frame_size to set
	 */
	public void setFrame_size(int frame_size) {
		this.frame_size = frame_size;
	}
	/**
	 * @return the frames
	 */
	public List<Integer> getFrames() {
		return frames;
	}
	/**
	 * @param frames the frames to set
	 */
	public void setFrames(List<Integer> frames) {
		this.frames = frames;
	}
}
