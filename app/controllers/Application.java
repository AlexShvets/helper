package controllers;

import models.Organisation;
import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import utils.BdHelper;

public class Application extends Controller {

    public static void index() {
      if (Organisation.count()<1){
        BdHelper.fillBd();
      }
      controllers.Organisations.showOrgsSmr();
    }

}