package com.rnt.test_passing;

import com.rnt.test_passing.service.Launcher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main( String[] args ) {
        ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("application-context.xml");
        Launcher launcher = context.getBean("launcher", Launcher.class);

        launcher.launchTest();
    }
}
