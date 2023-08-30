package com.rnt.test_passing;

import com.rnt.test_passing.service.Launcher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class App {
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            App.class);
        Launcher launcher = context.getBean("launcher", Launcher.class);

        launcher.launchTest();
    }
}
