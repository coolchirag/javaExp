package com.chirag.string;

public class StringExp {

	public static void main2(String[] args) {
		//String str = "123456789";
		String st = null;
		String blobName = "/tmp/hello/test.txt";
        int lastIndexOf = blobName.lastIndexOf("/");
        String filename = blobName.substring(lastIndexOf+1);
        System.out.println(filename);
		st+="hello";
		System.out.println(st);
		String str = "[sh]";
		System.out.println(str.length());
		System.out.println(str.substring(2,str.length()-1));
		
	}
	
	public static void main(String[] args) {
		boolean isUnnecessaryCharPresent = false;
		char unnecessaryChar = 'U';
		char[] passwordArray = "rQyzFR+bu2?u*-".toCharArray();
		for (char pwdChar : passwordArray) {
			if (!Character.isLetterOrDigit(pwdChar) && !String.valueOf(pwdChar).matches("[\\^.!@#$%&*_+=?-]")) {
				isUnnecessaryCharPresent = true;
				unnecessaryChar = pwdChar;
				break;
			}
		}
		System.out.println(isUnnecessaryCharPresent+" : "+unnecessaryChar);
	}
}
