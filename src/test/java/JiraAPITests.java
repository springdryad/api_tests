import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class JiraAPITests {
  String ticketId;
  String newIssue = JiraJSONObjects.newIssueJSON();

  @Test
  public void createIssue() {
    Response response =
        given().
            auth().preemptive().basic("RuslanaChumachenko", "RuslanaChumachenko").
            contentType(ContentType.JSON).
            body(newIssue).
            when().
            post("https://jira.hillel.it/rest/api/2/issue").
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            extract().response();
    response.prettyPrint();
    ticketId = response.path("id");
    System.out.println("Ticket ID: " + ticketId);

    Response response2 =
        given().
            auth().preemptive().basic("RuslanaChumachenko", "RuslanaChumachenko").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            contentType(ContentType.JSON).
            statusCode(200).
            extract().response();
    response2.prettyPrint();
    assertEquals(response2.path("fields.summary"), "API test summary");
    assertEquals(response2.path("fields.creator.name"), "RuslanaChumachenko");


    Response deleteIssueResponse =
        given().
            auth().preemptive().basic("RuslanaChumachenko", "RuslanaChumachenko").
            contentType(ContentType.JSON).
            when().
            delete("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            statusCode(204).
            extract().response();
    deleteIssueResponse.print();

    //Get deleted issue
    Response checkIfIssueDeletedResponse =
        given().
            auth().preemptive().basic("RuslanaChumachenko", "RuslanaChumachenko").
            contentType(ContentType.JSON).
            when().
            get("https://jira.hillel.it/rest/api/2/issue/" + ticketId).
            then().
            statusCode(404).
            extract().response();
  }
}

