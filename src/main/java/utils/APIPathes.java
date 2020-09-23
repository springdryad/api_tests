package utils;

public interface APIPathes {
  String baseURL = "https://jira.hillel.it";
  String issue = baseURL + "/rest/api/2/issue/";
  String comment = issue + "%s/comment/";
}
