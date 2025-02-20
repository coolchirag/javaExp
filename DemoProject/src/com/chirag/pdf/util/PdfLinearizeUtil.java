package com.chirag.pdf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class PdfLinearizeUtil {

        public static void main(String[] args) throws IOException, InterruptedException {
                //String filepath = "/home/ubuntu/chirag/doc1";
                String filepath = "/home/ubuntu/cj/100-mb";
                String[] linearizePDFCMD = { "qpdf", "--object-streams=generate", "--recompress-flate", "--compression-level=9", "--linearize", filepath+".pdf", filepath+"_line.pdf" };
                //String[] linearizePDFCMD = { "qpdf", "--check-linearization", filepath+".pdf"};

                boolean linearizeStatus = false;

                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command(linearizePDFCMD);
                //processBuilder.command("d:\\development\\java-8\\bin\\java.exe -help");
                Process process = processBuilder.start();
                System.out.println("1");
                //String errorLog = "";// getErrors(process);
                System.out.println("2");
                boolean isTimeout = !process.waitFor(10l, TimeUnit.SECONDS);
                System.out.println("2.1");
                //String errorLog = getErrors(process);
                //process.destroy();
                System.out.println("3");
                //System.out.println(errorLog);
                if(isTimeout) {
                        System.out.println("Processing Linearize timeout file : ");
                        process.destroy();
                        linearizeStatus = false;
                } else if (0 != process.exitValue()) {
                        System.out.println("Processing linearize failed error : "+ getErrors(process));
                        process.destroy();
                        linearizeStatus =  false;
                }else {
                        linearizeStatus = true;
                        process.destroy();
                }
                System.out.println("DOne : "+linearizeStatus);
        }

        private static String getErrors(Process process) throws IOException {
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                StringBuilder sb = new StringBuilder();
                while ((s = stdError.readLine()) != null) {
                        System.out.println(s);
                        sb.append(s);
                        sb.append("\n");
                }
                return sb.toString();
        }
}