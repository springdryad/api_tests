package utils;

public interface APIPathes {
  String BaseURL = null;
  String baseURL = YamlReader.getEnvironment(System.getenv("environment"));
  String issue = baseURL + "/rest/api/2/issue/";
  String comment = issue + "%s/comment/";
}
