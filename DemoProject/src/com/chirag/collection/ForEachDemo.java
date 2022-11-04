package com.chirag.collection;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ForEachDemo {

	public static void main(String[] args) {
		List<String> gamesList = new ArrayList<String>();  
        gamesList.add("Football");  
        gamesList.add("Cricket");  
        gamesList.add("Chess");  
        gamesList.add("Hocky");  
        System.out.println("------------Iterating by passing lambda expression--------------");  
        gamesList.forEach(games -> System.out.println(games));  
        
        LinkedHashSet<String> codes = new LinkedHashSet<>();
		codes.forEach(data -> System.out.println(data));
	}
}
