package org.virtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;


@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServerApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("启动成功");
    }

}
