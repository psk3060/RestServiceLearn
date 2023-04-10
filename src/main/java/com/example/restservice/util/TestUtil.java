package com.example.restservice.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.RandomStringUtils;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TestUtil {
 
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
        
    }
    
    @SuppressWarnings("unchecked")
	public static Map<String, Object> convertJsonToMap(String value) throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	try{
    		
    		if( !isEmpty(value) ) {
    			result = mapper.readValue(value, Map.class);
    			
    		}
    	
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    	
    	return result;

    }
    
    public static boolean isEmpty(String value) throws Exception {
    	
    	boolean result = false;
    	
    	if( value == null ) {
    		result = true;
    		
    	}
    	else {
    		value = value.trim();
    		// 앞 공백 제거
    		value = value.replaceAll("$?\\s", "");
    		// 뒷 공백 제거
    		value = value.replaceAll("\\s+$", "");
    		
    		result = "".equals(value);
    		
    	}
    	
    	
    	return result;
    	
    	
    }
    
    public static String getUniqueString() throws Exception  {
    	return RandomStringUtils.random(8, true, true);
    	
    }
    
}