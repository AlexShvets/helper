package controllers;

import play.Logger;
import play.modules.pdf.PDF;
import play.mvc.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static play.modules.pdf.PDF.writePDF;

public class Templates extends Controller {

  public static void printTemplate(){
  try {
    File file = File.createTempFile("template", ".pdf");
    PDF.Options options = new PDF.Options();
    options.filename = file.getName();
    writePDF(file,"@Templates.template", "saa" );
    System.out.println(file.getAbsolutePath());
    FileInputStream fileInputStream = new FileInputStream(file);
    renderBinary(fileInputStream, file.getName(), file.length(), "text/pdf", true);
  } catch (IOException e) {
    Logger.error(e, "Ошибка печати договора");
    renderText("Ошибка печати договора");
  }
}
}