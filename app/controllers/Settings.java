package controllers;

import models.Setting;
import play.mvc.Controller;

import java.util.List;

public class Settings extends Controller {

  public static void list(){
    List<Setting> settingList = Setting.all().fetch();
    render(settingList);
  }

  public static void show(int id){
    Setting setting = Setting.findById(id);
    render(setting);
  }
}
