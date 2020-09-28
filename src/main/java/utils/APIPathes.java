package utils;

public class APIPathes {

  public static String baseURL(){
    String baseURL = YamlReader.getEnvironment(System.getenv("environment"));
    return baseURL;
  }

  public static String issueURL(){
    String issue = baseURL() + "/rest/api/2/issue/";
    return issue;
  }

  public static String commentURL(){
    String comment = issueURL() + "%s/comment/";
    return comment;
  }

}
