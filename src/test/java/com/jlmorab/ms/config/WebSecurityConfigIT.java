package com.jlmorab.ms.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.jlmorab.ms.controller.TestController;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {WebSecurityConfig.class, TestController.class})
class WebSecurityConfigIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
    void whenAccessingAnyEndpoint_thenIsPermitted() throws Exception {
        mockMvc.perform(get("/test"))
        	.andExpect(status().isOk());
    }//end whenAccessingAnyEndpoint_thenIsPermitted()
	
	@Test
    void whenCheckingHeaders_thenArePresent() throws Exception {
        mockMvc.perform(get("/test"))
        	.andExpect(header().string("Content-Security-Policy-Report-Only", "script-src 'self'"))
        	.andExpect(header().string("Expect-CT", "max-age=3600, enforce"));
    }//end whenCheckingHeaders_thenArePresent()

}
