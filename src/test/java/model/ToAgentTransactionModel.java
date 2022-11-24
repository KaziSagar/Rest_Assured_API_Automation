package model;

import Setup.Setup;
import controller.User;

import java.io.IOException;

public class ToAgentTransactionModel extends Setup {

    public String from_account;
    public String to_account;
    public int amount;

    public ToAgentTransactionModel() throws IOException {
        initConfig();
        User user = new User();

        String agent_phone_number = prop.getProperty("agent_phone_number");

        this.from_account = "SYSTEM";
        this.to_account = agent_phone_number;
        this.amount = 2000;
    }
}
