package a.b.photoappaccountmanager.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountManagerController {
  @GetMapping("")
  public String index() {
    return "pong";
  }
  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }

}
