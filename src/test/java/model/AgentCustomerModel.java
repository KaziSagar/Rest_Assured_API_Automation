package model;

public class AgentCustomerModel {
    public String name;
    public String email;
    public String password;
    public String phone_number;
    public String nid;
    public String role;


    public AgentCustomerModel(String name, String email, String password, String phone_number, String nid, String role){
        this.name=name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.nid = nid;
        this.role = role;
    }
}