package controller;

import Setup.Setup;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.*;
import org.apache.commons.configuration.ConfigurationException;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class User extends Setup {
    public User() throws IOException {
        initConfig();
    }
    private String message;
    private String agentNumber;

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


    public void callingAgentCustomerCreationAPI(String name, String email, String password, String phone_number, String nid, String role) throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");
        AgentCustomerModel agentCustomerModel = new AgentCustomerModel(name, email, password, phone_number, nid, role);

        Response res =
                given()
                        .contentType("application/json")
                        .headers("Authorization", prop.getProperty("TOKEN"), "X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                        .body(agentCustomerModel)
                .when()
                        .post("/user/create")
                .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message=jsonpath.get("message");
        setMessage(message);
    }

    public void callingCustomerUpdateAPI(String name, String email, String password, String phone_number, String nid, String role) throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");
        AgentCustomerModel agentCustomerModel = new AgentCustomerModel(name, email, password, phone_number, nid, role);

        Response res =
                given()
                        .contentType("application/json")
                        .headers("Authorization", prop.getProperty("TOKEN"), "X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                        .body(agentCustomerModel)
                        .when()
                        .put("/user/update/102")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message=jsonpath.get("message");
        setMessage(message);
    }

    public void callingDepositToAgentAPI() throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");

        ToAgentTransactionModel toAgentTransactionModel = new ToAgentTransactionModel();

        Response res =
                given()
                        .contentType("application/json")
                        .headers("Authorization", prop.getProperty("TOKEN"), "X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                        .body(toAgentTransactionModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message=jsonpath.get("message");
        setMessage(message);
    }

    public void callingDepositToCustomerAPI() throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");

        ToCustomerTransactionModel toCustomerTransactionModel = new ToCustomerTransactionModel();

        Response res =
                given()
                        .contentType("application/json")
                        .headers("Authorization", prop.getProperty("TOKEN"), "X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                        .body(toCustomerTransactionModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message=jsonpath.get("message");
        setMessage(message);
    }

    public void callingCheckAgentBalanceAPI() throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");
        String agent_phone_number = prop.getProperty("agent_phone_number");

        Response res =
                given()
                        .contentType("application/json")
                        .headers("Authorization", prop.getProperty("TOKEN"), "X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                        .when()
                        .get("/transaction/balance/" + agent_phone_number)
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message=jsonpath.get("message");
        setMessage(message);
    }

    public void callingCheckCustomerBalanceAPI() throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");
        String customer_phone_number = prop.getProperty("customer_phone_number");

        Response res =
                given()
                        .contentType("application/json")
                        .headers("Authorization", prop.getProperty("TOKEN"), "X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                        .when()
                        .get("/transaction/balance/" + customer_phone_number)
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message=jsonpath.get("message");
        setMessage(message);
    }









    public void callingCustomerWithdrawAPI() throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("BASE_URL");

        CustomerWithdrawModel customerWithdrawModel = new CustomerWithdrawModel();

        Response res =
                given()
                        .contentType("application/json")
                        .headers("Authorization", prop.getProperty("TOKEN"), "X-AUTH-SECRET-KEY", prop.getProperty("secretKey"))
                        .body(customerWithdrawModel)
                        .when()
                        .post("/transaction/withdraw")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String message=jsonpath.get("message");
        setMessage(message);
    }
}
