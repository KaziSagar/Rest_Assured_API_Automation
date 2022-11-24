package model;

import Setup.Setup;
import controller.User;

import java.io.IOException;

public class CustomerWithdrawModel extends Setup {

    public String from_account;
    public String to_account;
    public int amount;

    public CustomerWithdrawModel() throws IOException {
        initConfig();
        User user = new User();

        String customer_phone_number = prop.getProperty("customer_phone_number");
        String agent_phone_number = prop.getProperty("agent_phone_number");

        this.from_account = customer_phone_number;
        this.to_account = agent_phone_number;
        this.amount = 50;
    }
}
