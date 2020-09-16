package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ResponseBuilder {

  public ResponseBuilder() {
  }

  public void getDeletedComment(String url, String username, String password){
    Response getDeletedComment =
        given().
            auth().preemptive().basic(username, password).
            contentType(ContentType.JSON).
            when().
            get(url).
            then().
            statusCode(404).
            extract().response();
    //getDeletedComment.prettyPrint();
  }
}
