package API;

import java.time.LocalDate;

public class UserAdd {
    private String isCV;
    private String salesOpenTime;
    private String salesStatus;
    private String first_name;
    private String surname;
    private String email;
    private String username;
    private String plain_password;
    private String roles;

    public UserAdd(String isCV, String salesOpenTime, String salesStatus, String first_name, String surname, String email, String username, String plain_password, String roles) {
        this.isCV = isCV;
        this.salesOpenTime = salesOpenTime;
        this.salesStatus = salesStatus;
        this.first_name = first_name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.plain_password = plain_password;
        this.roles = roles;
    }

    public String getIsCV() {
        return isCV;
    }

    public void setIsCV(String isCV) {
        this.isCV = isCV;
    }

    public String getSalesOpenTime() {
        return salesOpenTime;
    }

    public void setSalesOpenTime(String salesOpenTime) {
        this.salesOpenTime = salesOpenTime;
    }

    public String getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = salesStatus;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlain_password() {
        return plain_password;
    }

    public void setPlain_password(String plain_password) {
        this.plain_password = plain_password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
