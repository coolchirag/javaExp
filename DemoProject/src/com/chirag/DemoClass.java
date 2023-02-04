package com.chirag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DemoClass {


	  public static String MostFreeTime(String[] strArr) {
	    int length = strArr.length;
	    List<Double> startDates = new ArrayList();
	    List<Double> endDates = new ArrayList();
	    for(int i =0; i<length; i++) {
	      String combineDateStr = strArr[i];
	      int index = combineDateStr.indexOf("-");
	      Double startDate = convertStringToDate(combineDateStr.substring(0,index));
	      Double endDate = convertStringToDate(combineDateStr.substring(index+1));
	      startDates.add(startDate);
	      endDates.add(endDate);
	    }
	    Collections.sort(startDates);
	    Collections.sort(endDates);

	    double maxGap = 0d;
	    
	    for(int i =0; i<length-1;i++) {
	      double gap = startDates.get(i+1)-endDates.get(i);
	      if(gap> maxGap) {
	        maxGap = gap;
	      }
	    }

	    return convertDateToString(maxGap);
	  }

	  private static double convertStringToDate(String str) {
	    String hours = str.substring(0,2);
	    String minutes = str.substring(3,5);
	    String half = str.substring(5);

	    double date = Double.valueOf(hours);
	    if("pm".equalsIgnoreCase(half) && date < 12) {
	      date = date + 12;
	    }
	    Double m = Double.valueOf(minutes);
	    double dm = m/60;
	    date = date+dm;
	    return date;
	  }

	  private static String convertDateToString(double date) {
	    Double h = Math.floor(date);
	    date = date - h;
	    Double m = date*60;
	    
	   String hStr = h.intValue()<10?("0"+h.intValue()):  h.intValue()+"";
	   if(m<0.5) {
		   m = Math.floor(m);
	   } else {
		   m = Math.ceil(m);
	   }
	   String mStr = m.intValue()<10?("0"+m.intValue()):  m.intValue()+"";
	   return hStr+":"+mStr+"vp_c18_z2a";
	  }

	  public static void main (String[] args) {  
	    // keep this function call here     
		 String[] strArray = {"12:15PM-02:00PM","09:00AM-12:11PM","02:02PM-04:00PM"};//{"12:15PM-02:00PM","09:00AM-10:00AM","10:30AM-12:00PM"};//{"10:00AM-12:30PM","02:00PM-02:45PM","09:10AM-09:50AM"};
	    System.out.print(MostFreeTime(strArray)); 
	  }
}
