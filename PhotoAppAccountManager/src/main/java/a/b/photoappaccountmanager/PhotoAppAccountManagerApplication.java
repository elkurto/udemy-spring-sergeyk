package a.b.photoappaccountmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAppAccountManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhotoAppAccountManagerApplication.class, args);
  }

}
