package controllers;

import models.Setting;
import play.mvc.Controller;

import java.io.File;
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

  public static void changeValue(int id, String newValue){
    Setting setting = Setting.findById(id);
    setting.value = newValue;
    setting.save();
    show(id);
  }
}
