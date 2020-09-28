package utils;

public interface APIPathes {
  //String baseURL = "https://jira.hillel.it";
  String baseURL = null;
  String issue = baseURL + "/rest/api/2/issue/";
  String comment = issue + "%s/comment/";
}
