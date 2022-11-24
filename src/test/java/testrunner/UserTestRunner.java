package testrunner;

import controller.User;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.junit.Test;
import utils.Utils;

import java.io.IOException;

public class UserTestRunner {
    @Test
    public void doLogin() throws ConfigurationException, IOException {
        User user=new User();
        user.callingLoginAPI("salman@grr.la","1234");
        String messageExpected="Login successfully";
        Assert.assertEquals(user.getMessage(),messageExpected);
    }

    @Test
    public void getUserList() throws IOException {
        User user=new User();
        String id= user.callingUserListAPI();
        System.out.println(id);
        Assert.assertEquals(id,String.valueOf(58));
    }

    @Test
    public void createAgent() throws ConfigurationException, IOException {
        User user=new User();
        int randomId = Utils.generateRandomNumber(1000000, 9999999);
        String email = "agent"+randomId+"@test.com";
        String phone = "0170"+randomId;
        user.callingAgentCustomerCreationAPI("Test Agent 5", email, "123422", phone, "123456789", "Agent");
        //user.callingAgentCreationAPI("Test Agent 5", "agent9772983@test.com", "1234", "01706258983", "123456789", "Agent");
        String messageExpected="User created successfully";
        Assert.assertEquals(user.getMessage(),messageExpected);
    }

    @Test
    public void createCustomer() throws ConfigurationException, IOException {
        User user=new User();
        int randomId = Utils.generateRandomNumber(1000000, 9999999);
        String email = "customer"+randomId+"@test.com";
        String phone = "0170"+randomId;

        user.callingAgentCustomerCreationAPI("Test Customer 1", email, "1234", phone, "123456789", "Customer");

        String messageExpected="User created successfully";
        Assert.assertEquals(user.getMessage(),messageExpected);
    }

    @Test
    public void doLoginAsCustomer() throws ConfigurationException, IOException {
        User user=new User();
        user.callingLoginAPI("eric1234@test.com","1234");
        String messageExpected="Login successfully";
        Assert.assertEquals(user.getMessage(),messageExpected);
    }

    @Test
    public void updateCustomer() throws ConfigurationException, IOException {
        User user=new User();

        //updating customer phone number only
        user.callingCustomerUpdateAPI("Eric Ebert", "eric1234@test.com", "1234", "01963500123", "123456789", "Customer");

        String messageExpected="User updated successfully";
        Assert.assertEquals(user.getMessage(),messageExpected);
    }

    @Test
    public void depositToAgent() throws IOException, ConfigurationException {
        User user = new User();
        user.callingDepositToAgentAPI();

        String messageExpected= "Deposit successful";
        String messageActual = user.getMessage();

        Assert.assertTrue(messageActual.contains(messageExpected));
    }
}
