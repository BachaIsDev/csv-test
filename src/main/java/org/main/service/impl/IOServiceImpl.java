package org.main.service.impl;

import java.util.Scanner;
import org.main.service.IOService;

public class IOServiceImpl implements IOService {

  public IOServiceImpl() { }

  @Override
  public void printText(String text){
    System.out.println(text);
  };

  @Override
  public String nextString(){
    String fileName = "";
    Scanner scanner = new Scanner(System.in);

    if (scanner.hasNext()) {
      fileName = scanner.nextLine();
    }

    return fileName;
  }

}
