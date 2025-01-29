package spring.rest;

// NB: this is not really a unit test given we're still exploiting the IoC Container

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest // <!-- the IoC Container will have Controllers in it but not Services and Repos
@ActiveProfiles("dev")
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // <!-- mock the service and put it into the IoC Container
    private BookService mockBookService;

//    @Test
//    public void testGetBooks() throws Exception {
//        when(mockBookService.getBooks()).thenReturn(List.of(
//           new Book("1984", "Orwell"),
//           new Book("Brave New World", "Huxley"),
//           new Book("On Liberty", "Mill"),
//           new Book("Rasselas", "Johnson")
//        ));
//        mockMvc.perform(get("/books"))
//                .andExpect(status().isOk())
//                // the $ represents the JSON string in the response body
//                .andExpect(jsonPath("$", hasSize(4)))
//                .andExpect(jsonPath("$[0].title", is("1984")));
//    }

    // TODO add tests for the other endpoints
}
