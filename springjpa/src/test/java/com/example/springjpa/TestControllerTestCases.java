package com.example.springjpa;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.springjpa.bean.Company;
import com.example.springjpa.repository.CompanyRepository;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTestCases {

	@Autowired
	private MockMvc mockMvc;
	
	/*
	 * @Autowired private WebApplicationContext wac;
	 */
	
	@MockBean
	private CompanyRepository cmpRepo;
	
	@Test
	public void test() throws Exception {
		
		//this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();//By this linke we are also mocking entier webApplicationCOntext means we will bypass all filters and other things
		
		Optional<Company> cmpOp = Optional.of(new Company());
		Mockito.when(cmpRepo.findById(anyInt())).thenReturn(cmpOp);
		Mockito.when(cmpRepo.save(any())).thenReturn(new Company());
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
        .andReturn();
		System.out.println("Done");

	}
}
