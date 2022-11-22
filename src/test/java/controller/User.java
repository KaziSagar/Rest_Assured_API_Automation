package controller;

import Setup.Setup;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.AgentModel;
import model.UserModel;
import org.apache.commons.configuration.ConfigurationException;
import utils.Utils;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class User extends Setup {
    public User() throws IOException {
        initConfig();
    }
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void callingLoginAPI(String email, String password) throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");
        UserModel userModel = new UserModel(email, password);
        Response res =
                given()
                        .contentType("application/json")
                        .body(userModel)
                .when()
                        .post("/user/login")
                .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String token = jsonpath.get("token");
        String message=jsonpath.get("message");
        setMessage(message);
        Utils.setEnvVariable("TOKEN", token);
    }

    public String callingUserListAPI() throws IOException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("TOKEN"))
                        .when()
                        .get("/user/list")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        String id = response.get("users[0].id").toString();
        return id;
    }








    public void callingAgentCreationAPI(String name, String email, String password, String phone_number, String nid, String role) throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");
        AgentModel agentModel = new AgentModel(name, email, password, phone_number, nid, role);

        Response res =
                given()
                        .contentType("application/json")
                        .headers("Authorization", prop.getProperty("TOKEN"), "X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                        .body(agentModel)
                .when()
                        .post("/user/create")
                .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String token = jsonpath.get("token");
        String message=jsonpath.get("message");
        setMessage(message);
        //Utils.setEnvVariable("TOKEN", token);

    }









}
