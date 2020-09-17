import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JiraAPISteps;
import utils.JiraJSONObjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class JiraAPITests {
  String username = "RuslanaChumachenko";
  String password = "RuslanaChumachenko";
  String issueURL = "https://jira.hillel.it/rest/api/2/issue";
  String ticketId;
  String commentURL;
  String newIssue = JiraJSONObjects.newIssueJSON();
  String newComment = JiraJSONObjects.commentJSON();

  @Test
  public void createIssue() {
    //create new issue
    Response createIssueResponse = JiraAPISteps.createIssue(newIssue, username, password, issueURL);
    ticketId = createIssueResponse.path("id");
    assertTrue(createIssueResponse.path("key").toString().contains("WEBINAR-"));

    //verify that issue contains summary and reporter sent in Json
    Response getIssueResponse = JiraAPISteps.getIssue(ticketId, username, password, issueURL);
    assertEquals(getIssueResponse.path("fields.summary"), "API test summary");
    assertEquals(getIssueResponse.path("fields.creator.name"), "RuslanaChumachenko");

    //delete issue
    Response deleteIssueResponse = JiraAPISteps.deleteIssue(ticketId, username, password, issueURL);

    //get deleted issue
    Response checkIfIssueDeletedResponse = JiraAPISteps.checkIfIssueDeleted(ticketId, username, password, issueURL);
  }

  @Test
  public void addJiraComment(){
    // create issue for addComment test
    Response createIssueResponse = JiraAPISteps.createIssue(newIssue, username, password, issueURL);
    ticketId = createIssueResponse.path("id");

    //add comment
    Response addCommentResponse = JiraAPISteps.addComment(newComment, username, password, issueURL, ticketId);
    commentURL = addCommentResponse.path("self");
    assertEquals(addCommentResponse.path("body"), "test comment to be delete");

    //delete comment
    Response deleteCommentResponse = JiraAPISteps.deleteComment(commentURL, username, password);

    //check if comment doesn't exist in the issue
    Response getIssueResponse = JiraAPISteps.getIssue(ticketId, username, password, issueURL);
    Assert.assertFalse(getIssueResponse.toString().contains(commentURL));

    //check if comment doesn't exist
    Response getDeletedCommentURL = JiraAPISteps.getDeletedComment(commentURL, username, password);

    //delete the issue
    Response deleteIssueResponse = JiraAPISteps.deleteIssue(ticketId, username, password, issueURL);
  }
}

