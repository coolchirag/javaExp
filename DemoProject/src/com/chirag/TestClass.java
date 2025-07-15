package com.chirag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {

	private static int x;

	public static void main(String[] args) {
		 String mergedDocumentContent = "<xml-output>  ***  ** COMMUNITY INSTITUTION , INC** **STREET-ADDRESS** **STREET-ADDRESS, St 102** **PLACE, PA **ZIP-CODE** Phone: **PHONE** FAX: **PHONE**  ---  **Patient Information**  *   **Patient:** **NAME[ZZZ YYY]** *   **Date of Birth:** **DATE[Dec 04 1979]** *   **Birth Sex:** Male *   **Current Gender:** Male *   **Gender Identity:** Male *   **MRN:** **ID-NUM**  **Visit Information**  *   **Date:** **DATE[Aug 31 2024] 01:45 PM** *   **Visit Type:** Office Visit *   **Provider:** **NAME[UUU, TTT] MD**  ---  This **AGE[in 40s]** year old patient presents for follow up of Mult Med Prob.  **History of Present Illness**  *   **PLANTAR FASCIITIS**     *   Somewhat better with Meloxicam 15mg/d. Wondering if can take BID. No ADR; denies abd pain, melena, and hematochezia.     *   Hasn't yet tried treatment options on Care Instructions sheet.     *   Is wondering if he should get a steroid injection in feet, which helped his parents, who had plantar fasciitis.  *   **DEPRESSION and occasional anxiety**     *   Interested in starting an antidepressant.     *   Feeling about the same today as at visit 2 wks ago.     *   Depression worse when pain is worse and impairing function.     *   Tried not taking Nortriptyline 50mg 2 BID for 3-4 days in a row. No withdrawal effects.     *   REstarted it, and it didn't seem to help. Back on it ~6-7 days.  *   **POLYSUBSTANCE ABUSE**     *   Trying to get funding for 90 day Teen Challenge (TC) program.     *   No drugs since returning to TC.";
			 Integer pageNumber = 1;
			 String evidenceText="effects";
			 Integer evidenceBegin = 0;
			 
				 String documentContentArrays[] = mergedDocumentContent.split("<xml-output>");
				 if(documentContentArrays.length < pageNumber) {
					 final String errorMsg = "Invalida page number found";
					 
		                 
				 }
				 String documentContent = documentContentArrays[pageNumber];
				 documentContent = documentContent.replaceAll("<xml-output>", "").replaceAll("</xml-output>", "");
				 
				 System.out.println(exactMatch(documentContent));
				 
				 Integer evidenceOccurrenceCount = 1;
				 Integer newBegin = evidenceBegin;
				if(evidenceOccurrenceCount != null) {
					Integer lastIndex = 0;
					 for(int i = 0;i<evidenceOccurrenceCount; i++) {
						 Integer index = documentContent.indexOf(evidenceText, lastIndex);
							if (index > 0) {
								lastIndex = index + 1;
								newBegin = index - 1;
							} else {
								break;
							}
					 }
				} else {
					Integer index = documentContent.indexOf(evidenceText,9);
					newBegin = evidenceBegin;
					
					documentContent.substring(0, evidenceBegin+5).lastIndexOf("Office");
					while(index > 0 && index < evidenceBegin) {
						newBegin = index;
						index = documentContent.indexOf(evidenceText, index+1);
					}
					documentContent.subSequence(newBegin, newBegin+evidenceText.length()+5);
				}
				
				System.out.println("newBegin: " + newBegin);
		}
	public static boolean exactMatch(String text) {
        if (text == null) {
            return false;
        }
        try {
        	String word = "Java";
            Pattern pattern = Pattern.compile("\\b"+word+"\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher("This is a string containing the word Java. And another Java.");
            return matcher.matches();
        } catch (Exception e) {
            System.out.println("Invalid regex pattern: " + e.getMessage());
            return false;
        }
    }
	
}
