package model;

import Setup.Setup;
import controller.User;

import java.io.IOException;

public class ToCustomerTransactionModel extends Setup {

    public String from_account;
    public String to_account;
    public int amount;

    public ToCustomerTransactionModel() throws IOException {
        initConfig();
        User user = new User();

        String agent_phone_number = prop.getProperty("agent_phone_number");
        String customer_phone_number = prop.getProperty("customer_phone_number");

        this.from_account = agent_phone_number;
        this.to_account = customer_phone_number;
        this.amount = 1000;
    }
}
