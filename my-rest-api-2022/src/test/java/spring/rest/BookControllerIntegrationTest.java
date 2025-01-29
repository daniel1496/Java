package spring.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

/*
 * Unit test:
 * - annotate the class with @WebMvcTest which creates a container with controllers only
 * - use @MockBean to mock your service inside the container
 *
 * Integration test:
 * - annotate the class with @SpringBootTest and @AutoConfigureMockMvc
 *
 * In either case you should ensure the dev profile is active.
 */

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Transactional
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // <!-- effectively a mock Servlet Container

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                // the $ represents the JSON string in the response body
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].title", is("1984")));
    }

    @Test
    public void testAddNewBook() throws Exception {
        var serialisedBook = objectMapper.writeValueAsString(new Book("My Book", "Stuart"));
        mockMvc.perform(post("/books")
                    .contentType("application/json") // <!-- set the Content-Type header on the request
                    .content(serialisedBook)) // <!-- set the request body
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(5)));
    }

    // TODO add tests for the other endpoints
}
