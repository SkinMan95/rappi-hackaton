package eci.rappi.rappihackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class RappiHackathonApplication {

	public static void main(String[] args) {
		AppConfiguration.CreateConection();
		try {
			ResultSet x = AppConfiguration.makeQuery("*","storekeepers");
			x.next();
			x.next();
			System.out.println(x.getInt("id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//SpringApplication.run(RappiHackathonApplication.class, args);
	}

	
}
