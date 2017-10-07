package org.yakimovdenis.test.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.yakimovdenis.test.AbstractDatabaseTest;
import org.yakimovdenis.resttask.controllers.ContactController;
import org.yakimovdenis.resttask.models.Contact;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@AutoConfigureMockMvc
@ContextConfiguration(classes = ContactController.class)
public class RestMvcTest extends AbstractDatabaseTest {
    private static final String REGEX = ".*";
    private static List<Contact> result;

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(
        		applicationContext).build();
    }

    @Test
    public void getList() throws Exception{
        mockMvc.perform(get("/hello/contacts").param("nameFilter",REGEX)).andExpect(status().isOk());
    }
}