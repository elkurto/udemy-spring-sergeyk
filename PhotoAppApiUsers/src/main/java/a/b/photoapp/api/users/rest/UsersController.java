package a.b.photoapp.api.users.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  @Autowired
  Environment environment;

  @GetMapping("")
  public String index() {
    return "pong";
  }
  @GetMapping("/status/check")
  public String status() {
    return "working on port ="+ environment.getProperty("local.server.port");
  }
  @GetMapping("/hello")
  public String hello() {
    return "Hello, World!";
  }
}