package com.example.person;

import com.example.person.person.PersonController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class PersonControllerTest  {

    @Autowired
    private PersonController personController;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void testGETPersonController() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("api/v1/person")).andExpect(MockMvcResultMatchers.status().isOk());
    }

}
