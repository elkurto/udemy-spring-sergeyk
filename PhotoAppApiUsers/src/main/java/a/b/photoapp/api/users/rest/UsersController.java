package a.b.photoapp.api.users.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  @GetMapping("")
  public String index() {
    return "pong";
  }
  @GetMapping("/status/check")
  public String status() {
    return "working";
  }
  @GetMapping("/hello")
  public String hello() {
    return "Hello, World!";
  }
}