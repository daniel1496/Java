package spring.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * For a full E2E end you would annotate the class with...
 * @SpringBootTest
 * @AutoConfigureMockMvc
 * @ActiveProfiles("dev")
 */

@WebMvcTest // <!-- JUnit will create an IoC Container that has only Controller and assoc. beans; no service/repos
@Import(SecurityConfig.class) // <!-- add our SecurityConfig bean to the container
public class MyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // <!-- replace the given bean in the container with a mock
    private UserRepo mockUserRepo;

    @MockBean
    private PasswordEncoder mockPasswordEncoder;

    @Test
    public void testGetGreeting() throws Exception {
        mockMvc.perform(get("/greeting")).andExpect(status().isOk());
    }

    @Test
    public void testGetSecretWithNoUser() throws Exception {
        mockMvc.perform(get("/secret")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser // <!-- have Spring add a mock user to the SecurityContext
    public void testGetSecretWithUser() throws Exception {
        mockMvc.perform(get("/secret")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void testForAdministratorsOnlyWithNonAdminUser() throws Exception {
        mockMvc.perform(get("/admin")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    public void testForAdministratorsOnlyWithAdminUser() throws Exception {
        mockMvc.perform(get("/admin")).andExpect(status().isOk());
    }
}
