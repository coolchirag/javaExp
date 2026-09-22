package com.example.springjpa;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration(exclude={ErrorMvcAutoConfiguration.class})
@EnableScheduling
public class SpringjpaApplication {

	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException {
		System.setProperty("io.netty.handler.ssl.noOpenSsl", "true");

        String trustStorePath = "D:\\dev\\java\\java-8\\lib\\security\\cacerts_v";
        String trustStorePassword = "cjraapid";

        
        KeyStore trustStore = KeyStore.getInstance("JKS");
        FileInputStream fis = new FileInputStream(trustStorePath);
        trustStore.load(fis, trustStorePassword.toCharArray());
        fis.close();

        System.out.println("TrustStore loaded, cert count: " + trustStore.size());

        TrustManagerFactory tmf = TrustManagerFactory
            .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        TrustManager[] tms = tmf.getTrustManagers();
        System.out.println("TrustManager type: " + tms[0].getClass().getName());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tms, new java.security.SecureRandom());
        SSLContext.setDefault(sslContext);

        System.out.println("SSLContext default set successfully");				
        //------------------------------------------------------------------//
        SpringApplication.run(SpringjpaApplication.class, args);
		 System.out.println("TrustStore: " + System.getProperty("javax.net.ssl.trustStore"));
		    System.out.println("TrustStore Password: " + System.getProperty("javax.net.ssl.trustStorePassword"));
	}

}
