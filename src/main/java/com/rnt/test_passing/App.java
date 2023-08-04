package com.rnt.test_passing;

import com.rnt.test_passing.service.AppService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main( String[] args ) {
        ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("application-context.xml");
        AppService appService = context.getBean("appService", AppService.class);

        appService.launchTest();
    }
}
