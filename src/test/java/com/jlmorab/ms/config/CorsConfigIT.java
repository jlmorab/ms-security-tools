package com.jlmorab.ms.config;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.jlmorab.ms.controller.TestController;

@WebMvcTest(value = TestController.class, 
		properties = {"spring.cross.origins=http://allowed-origin.com"} )
@ContextConfiguration(classes = {CorsConfig.class, TestController.class})
@AutoConfigureMockMvc(addFilters = false)
class CorsConfigIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void corsHeadersArePresent() throws Exception {
		 mockMvc.perform(options("/test")
	                .header("Origin", "http://allowed-origin.com")
	                .header("Access-Control-Request-Method", "GET"))
	                .andExpect(status().isOk())
	                .andExpect(header().string("Access-Control-Allow-Origin", "http://allowed-origin.com"))
	                .andExpect(header().string("Access-Control-Allow-Methods", 
	                        containsString("GET")));
	}//end corsHeadersArePresent()
	
	@Test
    void shouldRejectUnallowedOrigin() throws Exception {
        mockMvc.perform(options("/test")
                .header("Origin", "http://unauthorized-site.com")
                .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isForbidden())
                .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
    }//end shouldRejectUnallowedOrigin()
	
	@Test
	void shouldRejectUnallowedMethod() throws Exception {
	    mockMvc.perform(options("/test")
	            .header("Origin", "http://allowed-origin.com")
	            .header("Access-Control-Request-Method", "MOVE")) 
	        .andExpect(status().isForbidden())  
	        .andExpect(header().doesNotExist("Access-Control-Allow-Methods")); 
	}//end shouldRejectUnallowedMethod()

}
