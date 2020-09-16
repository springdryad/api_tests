package utils;

import org.json.simple.JSONObject;

public class JiraJSONObjects {

  public static String newIssueJSON() {
    JSONObject newIssueJSON = new JSONObject();
    JSONObject fields = new JSONObject();

    fields.put("summary", "API test summary");
    JSONObject issueType = new JSONObject();
    issueType.put("id", "10105");
    issueType.put("name", "test");
    JSONObject project = new JSONObject();
    project.put("id", "10508");
    JSONObject reporter = new JSONObject();
    reporter.put("name", "RuslanaChumachenko");

    fields.put("issuetype", issueType);
    fields.put("project", project);
    fields.put("reporter", reporter);

    newIssueJSON.put("fields", fields);
    return newIssueJSON.toJSONString();
  }

  public static String commentJSON(){
    JSONObject commentJSON = new JSONObject();
    JSONObject body = new JSONObject();
    commentJSON.put("body", "test comment to be delete");

    return commentJSON.toJSONString();
  }
}
