package utils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YamlReader {
  static String filePath="src\\main\\resources\\config.yaml";

  public static String getEnvironment(String environment){
    Map<String,Map> result = null;
    try {
      InputStream file = new FileInputStream(new File(filePath));
      Yaml yaml = new Yaml();
      result = (Map<String,Map>)yaml.load(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return result.get("environments").get(environment).toString();
  }
}
