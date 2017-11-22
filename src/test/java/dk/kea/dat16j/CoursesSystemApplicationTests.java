package dk.kea.dat16j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static junit.framework.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoursesSystemApplicationTests {

	@Test
	public void contextLoads() {
		RestTemplate t = new RestTemplate();
//		assertTrue(t.getForObject("http://localhost:8080/", String.class).contains("This page will be displayed for users that are not signed in"));
	}

}
