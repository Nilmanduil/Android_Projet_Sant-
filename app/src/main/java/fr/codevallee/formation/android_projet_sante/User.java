package fr.codevallee.formation.android_projet_sante;

import android.telephony.PhoneNumberUtils;
import android.util.Patterns;

import java.util.regex.*;

/**
 * Created by tgoudouneix on 19/10/2017.
 */

public class User {
    public final static String GENDER_MALE = "male";
    public final static String GENDER_FEMALE = "female";
    public final static String GENDER_AGENDER = "agender";
    public final static String GENDER_OTHER = "other";

    private Integer id;
    private String firstname;
    private String lastname;
    private String gender;
    private String job;
    private String service;
    private String mail;
    private String telephone;
    private String cv;

    public User(Integer id, String firstname, String lastname, String gender, String job, String service, String mail, String telephone, String cv) throws RequiredFieldException, BadFormattedFieldException{
        this.id = id;
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setGender((gender != null ? gender.toLowerCase() : null));
        this.job = job;
        this.service = service;
        this.setMail(mail);
        this.setTelephone(telephone);
        this.cv = cv;
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) throws RequiredFieldException {
        if (firstname.isEmpty())
            throw new RequiredFieldException("Require firstname");
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) throws RequiredFieldException {
        if (lastname.isEmpty())
            throw new RequiredFieldException("Require lastname");
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        // if (gender != User.GENDER_MALE && gender != User.GENDER_FEMALE && gender != User.GENDER_AGENDER && gender != User.GENDER_OTHER)
        //    this.gender = null;
        // else
            this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) throws BadFormattedFieldException {
        if (Patterns.EMAIL_ADDRESS.matcher(mail).matches() || mail.isEmpty())
            this.mail = mail;
        else
            throw new BadFormattedFieldException("Mail bad formatted");
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) throws BadFormattedFieldException  {
        if (Patterns.PHONE.matcher(telephone).matches() || telephone.isEmpty())
            this.telephone = telephone;
        else
            throw new BadFormattedFieldException("Telephone bad formatted");
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender='" + gender + '\'' +
                ", job='" + job + '\'' +
                ", service='" + service + '\'' +
                ", mail='" + mail + '\'' +
                ", telephone='" + telephone + '\'' +
                ", cv='" + cv + '\'' +
                '}';
    }

    public class UserException extends Exception {
        public UserException(String message) {
            super(message);
        }
    }
    public class RequiredFieldException extends UserException {
        public RequiredFieldException(String message) {
            super(message);
        }
    }
    public class BadFormattedFieldException extends UserException {
        public BadFormattedFieldException(String message) {
            super(message);
        }
    }
}
