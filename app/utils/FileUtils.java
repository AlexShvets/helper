package utils;


import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

  public static boolean isExistFile(String fileName) {
   return Files.exists (Paths.get(fileName));
  }

  public static boolean collectionOfBaselineData(){

    return true;
  }

}
