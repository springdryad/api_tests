import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JiraAPISteps;
import utils.JiraJSONObjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class JiraAPITests {


  @Test
  public void createIssue() {

    String ticketId;
    String commentURL;

    //create new issue
    Response createIssueResponse = JiraAPISteps.createIssue();
    ticketId = createIssueResponse.path("id");
    assertTrue(createIssueResponse.path("key").toString().contains("WEBINAR-"));

    //verify that issue contains summary and reporter sent in Json
    Response getIssueResponse = JiraAPISteps.getIssue(ticketId);
    assertEquals(getIssueResponse.path("fields.summary"), "API test summary");
    assertEquals(getIssueResponse.path("fields.creator.name"), "RuslanaChumachenko");

    //delete issue
    Response deleteIssueResponse = JiraAPISteps.deleteIssue(ticketId);

    //get deleted issue
    Response checkIfIssueDeletedResponse = JiraAPISteps.checkIfIssueDeleted(ticketId);
  }

  @Test
  public void addJiraComment() {

    String ticketId;
    String commentURL;

    // create issue for addComment test
    Response createIssueResponse = JiraAPISteps.createIssue();
    ticketId = createIssueResponse.path("id");

    //add comment
    Response addCommentResponse = JiraAPISteps.addComment(ticketId);
    commentURL = addCommentResponse.path("self");
    assertEquals(addCommentResponse.path("body"), "test comment to be delete");

    //delete comment
    Response deleteCommentResponse = JiraAPISteps.deleteComment(commentURL);

    //check if comment doesn't exist in the issue
    Response getIssueResponse = JiraAPISteps.getIssue(ticketId);
    Assert.assertFalse(getIssueResponse.toString().contains(commentURL));

    //check if comment doesn't exist
    Response getDeletedCommentURL = JiraAPISteps.getDeletedComment(commentURL);

    //delete the issue
    Response deleteIssueResponse = JiraAPISteps.deleteIssue(ticketId);
  }
}

