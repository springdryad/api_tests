package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class JiraAPISteps {

  public static Response createIssue(String newIssue, String username, String password, String issueURL){
    Response response =
        given().
        auth().preemptive().basic(username, password).
        contentType(ContentType.JSON).
        body(newIssue).
        when().
        post(issueURL).
        then().
        contentType(ContentType.JSON).
        statusCode(201).
        extract().response();
    return response;
  }

  public static Response getIssue(String ticketId, String username, String password, String issueURL){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            get(issueURL + "/" + ticketId).
            then().
            contentType(ContentType.JSON).
            statusCode(200).
            extract().response();
    return response;
  }

  public static Response deleteIssue(String ticketId, String username, String password, String issueURL){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            delete(issueURL + "/" + ticketId).
            then().
            statusCode(204).
            extract().response();
    return response;
  }

  public static Response checkIfIssueDeleted(String ticketId, String username, String password, String issueURL){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            get(issueURL + "/" + ticketId).
            then().
            statusCode(404).
            extract().response();
    return response;
  }

  public static Response addComment(String newComment, String username, String password, String issueURL, String ticketID){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            body(newComment).
            when().
            post(issueURL + "/" + ticketID + "/comment").
            then().
            contentType(ContentType.JSON).
            statusCode(201).
            time(lessThan(4L), TimeUnit.SECONDS).
            extract().response();
    return response;
  }

  public static Response deleteComment(String commentURL, String username, String password){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            delete(commentURL).
            then().
            statusCode(204).
            extract().response();
    return response;
  }

  public static Response getDeletedComment(String commentURL, String username, String password){
    Response response =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            get(commentURL).
            then().
            statusCode(404).
            extract().response();
    return response;
  }
}
