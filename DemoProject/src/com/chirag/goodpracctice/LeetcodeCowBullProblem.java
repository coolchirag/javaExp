package com.chirag.goodpracctice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LeetcodeCowBullProblem {

	public static void main(String[] args) {
		System.out.println(getHint("1123", "0111"));
	}
	
	public static String getHint(String secret, String guess) {
		int cowCount = 0;
		int bullCount = 0;
		if(secret !=null && !secret.isEmpty() && guess != null && !guess.isEmpty()) {
			char[] secretArray = secret.toCharArray();
			char[] guessArray = guess.toCharArray();
			Map<Integer, Set<Integer>> secretMap = new HashMap<>();
			int secretIndex = 0;
			for(char sc : secretArray) {
				int data = Integer.parseInt(sc+"");
				Set<Integer> indexes = secretMap.get(data);
				if(indexes == null) {
					indexes = new HashSet<>();
					secretMap.put(data, indexes);
				}
				indexes.add(secretIndex);
				secretIndex++;
			}
			
			int guessIndex = 0;
			for(char gc : guessArray) {
				int data = Integer.parseInt(gc+"");
				Set<Integer> indexes = secretMap.get(data);
				if(indexes != null && !indexes.isEmpty()) {
					if(indexes.remove(guessIndex)) {
						cowCount++;
					} else {
						Integer foundIndex = -1;
						for(Integer index : indexes) {
							if(index < guessIndex) {
								foundIndex = index;
								break;
							} else {
								if(index < guessArray.length && guessArray[index] == gc) {
									continue;
								} else {
									foundIndex = index;
								}
							}
						}
						if(foundIndex > -1) {
							indexes.remove(foundIndex);
							bullCount++;
						}
						
						
					}
				}
				guessIndex++;
			}
		}
		
		return cowCount+"A"+bullCount+"B";
	}
	
}
