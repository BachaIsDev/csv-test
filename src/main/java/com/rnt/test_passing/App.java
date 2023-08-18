package com.rnt.test_passing;

import com.rnt.test_passing.service.Launcher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@ComponentScan
@PropertySource("classpath:application.properties")
@Configuration
public class App {
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            App.class);
        Launcher launcher = context.getBean("launcher", Launcher.class);

        launcher.launchTest();
    }
}
