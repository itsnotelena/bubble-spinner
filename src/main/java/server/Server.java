package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@SpringBootApplication
public class Server {
    private DbImplement dbImplement;

    public static void main(String[] args) {
        SpringApplication.run(Server.class,args);
        DbImplement dbImplement = new DbImplement(new DbAdapter());
    }

    @PostMapping(value = "/login")
    public boolean checkLogin(@RequestBody User details) throws SQLException {
        return dbImplement.checkLogin(details);
    }

}
