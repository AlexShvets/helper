package controllers;

import junit.framework.Assert;
import models.Setting;
import models.SettingType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Play;
import play.test.UnitTest;
import utils.BdHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static controllers.Organisations.update;


public class OrganisationsTest extends UnitTest {
  @Before
  public void setUp() throws Exception {
//    BdHelper.prepareDb();
//    BdHelper.fillBd();
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testUpdate() throws Exception {
    List<Setting> settingList = Setting.find("settingtype = ?", SettingType.FILES_ORGANISATION_1C.ordinal()).fetch();
    System.out.println("settingList.size() = "+settingList.size());
    for (Setting setting:settingList){
      try {
        System.out.println("Copy "+Paths.get(setting.value) +" to "+Paths.get(Play.applicationPath.getPath()));
        Files.copy(Paths.get(setting.value), Paths.get(Play.applicationPath.getPath()+"/"+Paths.get(setting.value).getFileName()));
        System.out.println("done");
      } catch (IOException e) {
        System.out.println("файлы не скопированы");
        System.out.println(e.toString());
      }
    }
  }
}
