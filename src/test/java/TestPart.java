package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import netscape.javascript.JSObject;
import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class TestPart {
    private String token = "";
    private JSONObject requestParam = new JSONObject();

    public void setRequestParam(){
        requestParam.put("username", "klyuev_alexey");
        requestParam.put("password", "4~gBt(K4siN<bTR");
    }

    private void getToken(){
        setRequestParam();
        this.token = given()
                .header("Content-Type", "application/json")
                .body(requestParam.toString())
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().path("token");
    }

    @Test
    @DisplayName("Авторизация на портале")
    public void testLogin(){
        RestAssured.baseURI = "https://aqa-api.javacode.ru/api/auth";
        String jsonSchemaPath = "UserSchema.json";

//        requestParam.put("username", "klyuev_alexey");
//        requestParam.put("password", "4~gBt(K4siN<bTR");

        setRequestParam();

        String res = given().
                header("Content-Type","application/json").
                body(requestParam.toString()).
                when().
                post("/login")
                .then()
                .statusCode(200)

                .body(matchesJsonSchemaInClasspath(jsonSchemaPath))
                .extract().body().asPrettyString();
        System.out.println(res);

    }

    @Test
    @DisplayName("добавление юзера")
    public void testAddUsers(
                            String id,
                            String first_name,
                            String surname,
                            String email,
                            String username,
                            String plain_password,
                            String roles,
                            String isCV,
                            String salesOpenTime,
                            String salesStatus
                            ){
        RestAssured.baseURI = "https://aqa-api.javacode.ru/api";
        String jsonSchemaPath = "userSchemaTwo.json";

        String bodyRequest = "{\n" +
                "    \"customData\": {\n" +
                "        \"isCV\": " + isCV + ",\n" +
                "        \"salesOpenTime\": \""  + salesOpenTime + "\",\n" +
                "        \"salesStatus\": \"" + salesStatus + "\"\n" +
                "    },\n" +
                "    \"first_name\": \"" + first_name + "\",\n" +
                "    \"surname\": \"" + surname + "\",\n" +
                "    \"email\": \"" + email + "\",\n" +
                "    \"username\": \"" + username + "\",\n" +
                "    \"plain_password\": \"" + plain_password + "\",\n" +
                "    \"roles\": \"" + roles + "\"\n" +
                "}";

        System.out.println(bodyRequest);

        getToken();
        String res =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", token)
                        .body(bodyRequest)
                        .when()
                        .post("/user-auth1")
                        .then()
                        .statusCode(200)
//                        .body(matchesJsonSchemaInClasspath(jsonSchemaPath))
                        .extract().body().asPrettyString();

    }

}
