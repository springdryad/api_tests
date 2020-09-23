package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class JiraAPISteps {

  static String newIssueJSON = JiraJSONObjects.newIssueJSON();
  static String commentJSON = JiraJSONObjects.commentJSON();

  public static Response createIssue() {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            body(newIssueJSON).
            when().
            post(APIPathes.issue).
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            extract().response();
    return response;
  }

  public static Response getIssue(String ticketId) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            when().
            get(APIPathes.issue + ticketId).
            then().
            contentType(ContentType.JSON).
            statusCode(200).
            extract().response();
    return response;
  }

  public static Response deleteIssue(String ticketId) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            when().
            delete(APIPathes.issue + ticketId).
            then().
            statusCode(204).
            extract().response();
    return response;
  }

  public static Response checkIfIssueDeleted(String ticketId) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            when().
            get(APIPathes.issue + ticketId).
            then().
            statusCode(404).
            extract().response();
    return response;
  }

  public static Response addComment(String ticketID) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            body(commentJSON).
            when().
            post(String.format(APIPathes.comment, ticketID)).
            then().
            statusCode(201).
            contentType(ContentType.JSON).
            time(lessThan(4L), TimeUnit.SECONDS).
            extract().response();
    return response;
  }

  public static Response deleteComment(String commentURL) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            delete(commentURL).
            then().
            statusCode(204).
            extract().response();
    return response;
  }

  public static Response getDeletedComment(String commentURL) {
    Response response =
        given().
            auth().preemptive().basic(Credentials.username, Credentials.password).
            contentType(ContentType.JSON).
            when().
            get(commentURL).
            then().
            statusCode(404).
            extract().response();
    return response;
  }
}
