package org.launcher;


import org.service.AppService;

public class Launcher {
  private final AppService appService;

  public Launcher(AppService appService) {
    this.appService = appService;
  }

  public void launch(){
    appService.launchTests();
  }

}
