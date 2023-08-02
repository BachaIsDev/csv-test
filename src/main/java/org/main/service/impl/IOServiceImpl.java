package org.main.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.main.entity.Question;
import org.main.entity.Result;
import org.main.service.IOService;
import org.main.util.PathProvider;

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
