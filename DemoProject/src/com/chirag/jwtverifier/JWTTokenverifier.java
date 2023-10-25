package com.chirag.jwtverifier;

import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;

public class JWTTokenverifier {

	public static void main(String[] args) throws Exception {
		// Provide the JWT token as a string
		String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InFhcDFQWUF1S1hTZ2RZTXFaN3NxaU9SYVZycyIsImtpZCI6InFhcDFQWUF1S1hTZ2RZTXFaN3NxaU9SYVZycyJ9.eyJhdWQiOiJmODQ5ZjMyYS03MjQ4LTQwN2YtYWExYi1mMjMwOWIyZWY4MmMiLCJpc3MiOiJodHRwczovL2FkZnMucmFhcGlkLmFpL2FkZnMiLCJpYXQiOjE2OTc3MjM5MDQsIm5iZiI6MTY5NzcyMzkwNCwiZXhwIjoxNjk3NzI3NTA0LCJhdXRoX3RpbWUiOjE2OTc3MjEyNjIsIm5vbmNlIjoiMTIzNDUiLCJzdWIiOiJOQnlDK0tEMDk4RzJqKzZTR3lwOU96VFlGNG91NEhPZHo4NkE2Z09vcGlvPSIsInVwbiI6InJhYXBpZEByYWFwaWQuYWkiLCJ1bmlxdWVfbmFtZSI6IlJBQVBJRFxccmFhcGlkIiwicHdkX2V4cCI6IjM0MzE3MjEiLCJzaWQiOiJTLTEtNS0yMS00MTI2MjM5MTcxLTE2MTQyNzQ1MTYtMzM0NTA3ODQ5MC01MDAiLCJhdF9oYXNoIjoiTlZmVmhVemU5THhpeWxPRV96MTl1QSJ9.gKhiAojUTPsuDl0nuDUHjbNsSW7xDdLl7_VFqmYHO_DUeG9VLF8wSfKfCiLDU5Mpee26mPy5XOInykBH7yzz9GW6svEKk3EMJ0AE4myNxEnoxP2-SzDKKOSSki-eXnY6PtrYQSv0w57mVp0N3DS76Mzdc44Yoq5Tq00rguSeCgMi3Jf9g0Da09QCKa-RXCI5AgUd0BSPCHZL-8D6Wtm82GZlDMo_oIhO-ItDD7gpqfgOLAGvaintWIWNkzJImYM5OjbqC7bTTupTr6fWn0p8V3P4UoX877XQufifdcmqTclcExqhGgrRHC8oQIOhyzDqVrT5O_cvDIHIAu4MBkzkRw";

		// Provide the ADFS public key or certificate
		String adfsPublicKey = "Test";

		// Parse the JWT token
		SignedJWT jwt = SignedJWT.parse(jwtToken);

		// Create a JWS verifier with the ADFS public key
		JWSVerifier verifier = null;//new RSASSAVerifier(adfsPublicKey);

		// Verify the token's signature
		if (jwt.verify(verifier)) {
		    // Signature is valid
		    // You can now access the token's claims
		    System.out.println("JWT signature is valid.");
		    System.out.println("Claims: " + jwt.getJWTClaimsSet());
		} else {
		    // Signature is invalid
		    System.out.println("JWT signature is not valid.");
		}
	}
}
