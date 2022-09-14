package com.develop.project_auth;

import com.develop.project_auth.core.config.io.Base64ProtocolResolver;
import com.develop.project_auth.domain.repository.impl.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class ProjectAuthApplication {

  public static void main(String[] args) {
    var app = new SpringApplication(ProjectAuthApplication.class);
    app.addListeners(new Base64ProtocolResolver());
    app.run(args);
  }

}
