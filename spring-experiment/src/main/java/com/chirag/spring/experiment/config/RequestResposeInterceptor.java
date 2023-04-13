package com.chirag.spring.experiment.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import com.chirag.spring.experiment.util.DecryptionUtility;
import com.chirag.spring.experiment.util.EncryptionUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

/* 
 * This class intercept request and response and perfrom encypt and decrypt it*/


//@Component
public class RequestResposeInterceptor extends AbstractHttpMessageConverter<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResposeInterceptor.class);
    @Autowired
    private ObjectMapper objectMapper;

    public RequestResposeInterceptor() {
        super(MediaType.ALL);
    }

    @Autowired
    private HttpServletRequest httpRequest;

    @Override
    protected boolean supports(Class clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String uri = httpRequest.getRequestURI();
        if (uri.contains("swagger") || uri.endsWith("/v2/api-docs")) {
        	/* Exclude swagger request*/
            return objectMapper.readValue(inputMessage.getBody(), clazz);
        } else {
            return objectMapper.readValue(decrypt(inputMessage.getBody()), clazz);
        }
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        String uri = httpRequest.getRequestURI();
        if (uri.contains("swagger") || uri.endsWith("/v2/api-docs")) {
        	/* Exclude swagger response*/
            outputMessage.getBody().write(objectMapper.writeValueAsBytes(o));
        } else {
            outputMessage.getBody().write(encrypt(objectMapper.writeValueAsBytes(o)));
        }
    }

    private InputStream decrypt(InputStream inputStream) {
        StringBuilder requestParamString = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c;
            while ((c = reader.read()) != -1) {
                requestParamString.append((char) c);
            }
        } catch (IOException e) {
            LOGGER.error("An error occurred decrypt api request : " + e.getMessage(), e);
        }
        try {
            JSONObject requestJsonObject = new
                    JSONObject(requestParamString.toString().replace("\n", ""));

            String decryptRequestString = DecryptionUtility.decrypt(requestJsonObject.getString("data"));
            LOGGER.info("decryptRequestString: " + decryptRequestString);

            if (decryptRequestString != null) {
                return new ByteArrayInputStream(decryptRequestString.getBytes(StandardCharsets.UTF_8));
            } else {
                return inputStream;
            }
        } catch (JSONException err) {
            LOGGER.error("An error occurred in json convert : " + err.getMessage(), err);
        }
        return inputStream;
    }
    /**
     * response of API
     *
     * @param bytesToEncrypt byte array of response
     * @return byte array of response
     */
    private byte[] encrypt(byte[] bytesToEncrypt) {
        // do your encryption here
        String apiJsonResponse = new String(bytesToEncrypt);
        LOGGER.info(apiJsonResponse);

        String encryptedString = EncryptionUtility.encrypt(apiJsonResponse);
        if (encryptedString != null) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("data", encryptedString);
            JSONObject jsob = new JSONObject(hashMap);
            return jsob.toString().getBytes();
        } else
            return bytesToEncrypt;
    }

}
