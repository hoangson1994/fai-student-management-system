package entity;

public class FuUser {

    private String user_pass;
    private int user_level;
    private String user_login;
    private String user_surname;
    private String user_middlename;
    private String user_givenname;
    private int sex;
    private String user_code;
    private String user_DOB;
    private String user_email;
    private String user_address;
    private String user_telephone;
    private String user_create;
    private String user_modified;

    public FuUser() {
    }

    public FuUser(String user_pass, int user_level, String user_login, String user_surname, String user_middlename, String user_givenname, int sex, String user_code, String user_DOB, String user_email, String user_address, String user_telephone, String user_create, String user_modified) {
        this.user_pass = user_pass;
        this.user_level = user_level;
        this.user_login = user_login;
        this.user_surname = user_surname;
        this.user_middlename = user_middlename;
        this.user_givenname = user_givenname;
        this.sex = sex;
        this.user_code = user_code;
        this.user_DOB = user_DOB;
        this.user_email = user_email;
        this.user_address = user_address;
        this.user_telephone = user_telephone;
        this.user_create = user_create;
        this.user_modified = user_modified;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public String getUser_middlename() {
        return user_middlename;
    }

    public void setUser_middlename(String user_middlename) {
        this.user_middlename = user_middlename;
    }

    public String getUser_givenname() {
        return user_givenname;
    }

    public void setUser_givenname(String user_givenname) {
        this.user_givenname = user_givenname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_DOB() {
        return user_DOB;
    }

    public void setUser_DOB(String user_DOB) {
        this.user_DOB = user_DOB;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_telephone() {
        return user_telephone;
    }

    public void setUser_telephone(String user_telephone) {
        this.user_telephone = user_telephone;
    }

    public String getUser_create() {
        return user_create;
    }

    public void setUser_create(String user_create) {
        this.user_create = user_create;
    }

    public String getUser_modified() {
        return user_modified;
    }

    public void setUser_modified(String user_modified) {
        this.user_modified = user_modified;
    }
}
