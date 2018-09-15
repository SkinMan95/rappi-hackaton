package eci.rappi.rappihackathon;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RappiHackathonApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testDePrueba() {
		System.out.println("Test de prueba");

		Assert.assertTrue(true);
	}

	@Test
	public void testConectionPosgres(){
		Assert.assertNotNull(AppConfiguration.CreateConectionPostgres());
	}

}
