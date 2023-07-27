package org.main;

import org.main.service.AppService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("application-context.xml");
        AppService appService = context.getBean("appService", AppService.class);

        appService.launchTest();
    }
}
