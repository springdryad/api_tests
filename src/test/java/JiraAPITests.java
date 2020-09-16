import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JiraAPISteps;
import utils.JiraJSONObjects;
import utils.ResponseBuilder;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class JiraAPITests {
  String username = "RuslanaChumachenko";
  String password = "RuslanaChumachenko";
  String ticketId;
  String commentURL;
  String newIssue = JiraJSONObjects.newIssueJSON();
  String newComment = JiraJSONObjects.commentJSON();
  public ResponseBuilder getDeletedCommentURL = new ResponseBuilder();

  @Test
  public void createIssueResponse() {
    Response createIssueResponse = JiraAPISteps.createIssue(newIssue);
    ticketId = createIssueResponse.path("id");
    assertTrue(createIssueResponse.path("key").toString().contains("WEBINAR-"));

    Response getIssueResponse = JiraAPISteps.getIssue(ticketId);
    assertEquals(getIssueResponse.path("fields.summary"), "API test summary");
    assertEquals(getIssueResponse.path("fields.creator.name"), "RuslanaChumachenko");

    Response deleteIssueResponse = JiraAPISteps.deleteIssue(ticketId);

    //Get deleted issue
    Response checkIfIssueDeletedResponse = JiraAPISteps.checkIfIssueDeleted(ticketId);
  }


  @Test
  public void addJiraComment(){
    Response addCommentResponse =
        given().
            auth().preemptive().basic("RuslanaChumachenko", "RuslanaChumachenko").
            contentType(ContentType.JSON).
            body(newComment).
            when().
            post("https://jira.hillel.it/rest/api/2/issue/WEBINAR-13256/comment").
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            time(lessThan(4L), TimeUnit.SECONDS).
            extract().response();
    //System.out.println("Time is: " + addCommentResponse.time());
    //assertTrue(addCommentResponse.time()<=1000);
    commentURL = addCommentResponse.path("self");
    System.out.println("Comment URL: " + commentURL);

    Response deleteCommentResponse =
        given().
            auth().preemptive().basic("RuslanaChumachenko", "RuslanaChumachenko").
            delete(commentURL).
            then().
            statusCode(204).
            extract().response();

    Response getIssueResponse =
        given().
            auth().preemptive().basic("RuslanaChumachenko", "RuslanaChumachenko").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/WEBINAR-13256").
            then().
            contentType(ContentType.JSON).
            statusCode(200).
            extract().response();
    //getIssueResponse.prettyPrint();
    Assert.assertFalse(getIssueResponse.toString().contains(commentURL));


    getDeletedCommentURL.getDeletedComment(commentURL,username, password);

  }
}

