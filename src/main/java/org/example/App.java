package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import org.service.CsvProcessor;
import org.service.Viewer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("application-context.xml");
        Viewer viewer = context.getBean("viewer", Viewer.class);

        viewer.launch();
    }
}
