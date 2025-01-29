package spring.boot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*
 *@SpringBootTest is effectively the same as:
 * @ExtendWith(SpringExtension.class)
 * @ContextCOnfiguration(Classes = App.class)
 *
 */


@SpringBootTest
class ContainerConfigTest {

	@Test
	void contextLoads() {
	}

}
