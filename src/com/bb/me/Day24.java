/**
 * 
 */
package com.bb.me;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author mratiu
 *
 */
public class Day24 {
	
	private static Stack<Integer> front = new Stack<Integer>();
	private static Stack<Integer> sideA = new Stack<Integer>();
	private static Stack<Integer> sideB = new Stack<Integer>();
	
	private static String[] loadFile(String fileMap) {
		return fileMap.split("\n");
    }
	
	
	private static int calculateQuantumEnt(ArrayList<Integer> numbers) {
		int quantum = 0;
		Stack<Integer> buffer = new Stack<Integer>();
		for(Integer number: numbers) {
			buffer.push(number);						
		}
		calibrateStacks(buffer, front, sideA, sideB);
		
		return quantum;
	}
	
	private static Stack<Integer> calibrateStacks(Stack<Integer> buffer, Stack<Integer> front, Stack<Integer> sideA, Stack<Integer> sideB) {
		int minSize = Integer.MAX_VALUE;
		int minQuantum = Integer.MAX_VALUE;
		if(buffer.empty()) {
			return front;
		}
		int fW = getWeight(front);
		int aW = getWeight(sideA);
		int bW = getWeight(sideB);
		
		if((fW != 0) && (fW == aW) && (aW == bW)) {		
			System.out.println("Found a combination of stacks "+ front.toString() + " " +sideA.toString() + " " +sideB.toString());
			if(minSize > getSize(front)) {
				minSize = getSize(front);
			}
			if(minQuantum > getQuantum(front)) {
				minQuantum = getQuantum(front);
			}
			
		}
		
		Integer element = buffer.pop();
		if(isMin(front, sideA, sideB)) {
			front.push(element);
		} else if(isMin(sideA, front, sideB)) {
			sideA.push(element);
		} else if(isMin(sideB, front, sideA)) {
			sideB.push(element);
		}
		calibrateStacks(buffer, front, sideA, sideB);		
		buffer.push(element);
		
		return front;
	}
	
	private static int getWeight(Stack<Integer> stack) {
		int sum = 0;
		for(Integer number: stack) {
			sum += number;			
		}
		return sum;
	}
	
	
	
	private static int getQuantum(Stack<Integer> stack) {
		int prod = 0;
		for(Integer number: stack) {
			prod *= number;			
		}
		return prod;
	}
	
	private static int getSize(Stack<Integer> stack) {
		return stack.size();	
	}
	
	private static boolean isMin(Stack<Integer> a, Stack<Integer> b, Stack<Integer> c) {
		int wA = getWeight(a);
		int wB = getWeight(b);
		int wC = getWeight(c);
		
		if(wA < wB) {
			if (wA < wC) {
				return true;
			} 
		}
		return false;
	}
	
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String fileMap = FileReader.getString("day24.txt");

        String[] lines = loadFile(fileMap);
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        
        
        for(String line : lines) {
        	numbers.add(new Integer(line));     	
        }
        
        int q = calculateQuantumEnt(numbers);
        
        System.out.println("Minimum quantum entanglement is "+ q);


	}

}
