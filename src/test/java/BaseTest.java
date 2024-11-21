
import API.Question;
import API.Specifications;
import API.UserAdd;
import org.example.API.UserLoginRegistr;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class BaseTest {
    private final String URL = "https://aqa-api.javacode.ru/api";
    private String token = "";

    private void getToken() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(200));
        UserLoginRegistr userLogin = new UserLoginRegistr("klyuev_alexey", "4~gBt(K4siN<bTR");

        this.token = given()
                .body(userLogin)
                .post("/auth/login")
                .then()
                .extract().jsonPath().getString("token");

//        System.out.println(token);
    }


    @Test
    @DisplayName("Авторизация на портале")
    public void loginTest() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(200));
        UserLoginRegistr userLogin = new UserLoginRegistr("klyuev_alexey", "4~gBt(K4siN<bTR");
        String user = given()
                .body(userLogin)
                .post("/auth/login")
                .then()
                .extract()
//                .asPrettyString()
                .jsonPath().getString("token");
    }

    @DisplayName("добавление юзера")
    @CsvFileSource(resources = "Pairwise.csv")
    @ParameterizedTest
    public void testAddUser(
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
    ) {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(200));
        getToken();
        UserAdd userAdd = new UserAdd(isCV, salesOpenTime, salesStatus, first_name, surname, email, username, plain_password, roles);
        String res = given()
                .header("Authorization", token)
                .body(userAdd)
                .post("/user-auth1")
                .then()
                .extract().asPrettyString();
        System.out.println(res);


    }

    @Test
    @DisplayName("добавление вопроса")
    public void addQuestion() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(200));
        getToken();
        Question question = new Question("Что делать?");
        String res = given()
                .header("Authorization", token)
                .body(question)
                .post("/theme-question")
                .then().log().all()
                .extract().asPrettyString();
    }

    @Test
    @DisplayName("Редактирование вопроса")
    public void editQuestion() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(200));
        getToken();

        String bodyText = "{\n" +
                "  \"currentLTS\": \"\",\n" +
                "  \"changeKey\": \"version\",\n" +
                "  \"question\": \"1071\",\n" +
                "  \"LTP\": {\n" +
                "    \"data\": {\n" +
                "      \"jsDetails\": \"\",\n" +
                "      \"comment\": \"\",\n" +
                "      \"quizes\": [],\n" +
                "      \"hints\": [],\n" +
                "      \"type\": \"\",\n" +
                "      \"videos\": [],\n" +
                "      \"name\": \"тест\",\n" +
                "      \"hashTags\": [],\n" +
                "      \"title\": \"\",\n" +
                "      \"answer\": \"тест тест\",\n" +
                "      \"facts\": [\n" +
                "        {\n" +
                "          \"name\": \"тест\",\n" +
                "          \"desc\": \"тест\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"useCases\": [\n" +
                "        {\n" +
                "          \"name\": \"тест\",\n" +
                "          \"desc\": \"тест\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"originalDuplicateId\": \"\",\n" +
                "      \"questionId\": \"1006\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"versionDetails\": {\n" +
                "    \"patch\": 0,\n" +
                "    \"subVersion\": 0,\n" +
                "    \"version\": 1,\n" +
                "    \"versionStr\": \"1.0.0\"\n" +
                "  }\n" +
                "}";

        String res = given()
                .header("Authorization", token)
                .body(bodyText)
                .post("/create-lts")
                .then().log().all()
                .extract()
                .asPrettyString();

    }

    @Test
    @DisplayName("добавление квиза")
    public void addQuiz() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(200));
        getToken();

        String bodyText = "{\n" +
                "    \"answerType\": \"quiz\",\n" +
                "    \"isValid\": true,\n" +
                "    \"name\": \"test\",\n" +
                "    \"files\": [],\n" +
                "    \"variations\": [\n" +
                "        {\n" +
                "            \"name\": \"\",\n" +
                "            \"isCorrect\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        given()
                .header("Authorization", token)
                .body(bodyText)
                .post("/quiz")
                .then().log().all()
                .extract()
                .asPrettyString()
        ;

    }

    @Test
    @DisplayName("добавление модуля")
    public void addModule() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(200));
        getToken();

        String bodyText = "{\n" +
                "  \"name\": \"тест\",\n" +
                "  \"questions\": [\n" +
                "    1000,\n" +
                "    1001,\n" +
                "    1002,\n" +
                "    1005\n" +
                "  ]\n" +
                "}";

        given()
                .header("Authorization", token)
                .body(bodyText)
                .post("/course-module")
                .then().log().all()
                .extract()
                .asPrettyString()
        ;


    }

    @Test
    @DisplayName("добавление курса")
    public void addCourse() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(200));
        getToken();

        String bodyText = "{\n" +
                "    \"name\": \"Test\",\n" +
                "    \"modules\": [\n" +
                "        {\n" +
                "            \"module\": \"1000\",\n" +
                "            \"name\": \"test\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        given()
                .header("Authorization", token)
                .body(bodyText)
                .post("/course")
                .then().log().all()
                .extract()
                .asPrettyString()
        ;


    }

    @Test
    @DisplayName("добавление экзамена")
    public void addExam() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(200));
        getToken();

        String bodyText = "{\n" +
                "    \"New Test\": \"Test\",\n" +
                "    \"minutesStr\": \"100\",\n" +
                "    \"potQuizes\": [\n" +
                "        {}\n";

        String res = given()
                .header("Authorization", token)
                .body(bodyText)
                .post("/exam")
                .then().log().all()
                .extract()
                .jsonPath().getString("data");
        ;

        System.out.println(res);
    }

    @Test
    @DisplayName("Добавление темплейта")
    public void addTenplaase() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(200));
        getToken();

        String bodyText = "{\n" +
                "    \"name\": \"Test\",\n" +
                "    \"desc\": \"test\",\n" +
                "    \"exams\": [\n" +
                "        {\n" +
                "            \"sourceId\": \"1005\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"courses\": [\n" +
                "        {\n" +
                "            \"sourceId\": \"1003\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        String res = given()
                .header("Authorization", token)
                .body(bodyText)
                .post("/user-hr-template")
                .then().log().all()
                .extract()
                .asPrettyString();
        ;

        System.out.println(res);
    }


    @Test
    @DisplayName("Авторизация с неверным логином или паролем")
    public void badLogin() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecificationStatus(403));

        UserLoginRegistr userLogin = new UserLoginRegistr("klyuev_alexey", "");


        given()
                .body(userLogin)
                .post("/send-meters-by-seconds")
                .then().log().all()
                .extract().asPrettyString();

        System.out.println();
    }
}

//"Отправить POST запрос https://aqa-api.javacode.ru/api/
//"