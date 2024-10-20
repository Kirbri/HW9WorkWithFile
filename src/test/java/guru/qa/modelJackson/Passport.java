package guru.qa.modelJackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Passport {

    @JsonProperty("Country")
    private String country;
    private String passportIssued;
    private String dateOfIssue;
    private String departmentCode;
    @JsonProperty("Series")
    private Short Series;
    @JsonProperty("Number")
    private Integer Number;
    private String fullName;
    private Boolean sex;
    private String dateOfBirth;
    private String placeOfBirth;
    private PlaceOfResidence placeOfResidence;
    private List<Child> child;
    @JsonProperty("Responsibilities")
    private String[] Responsibilities;

    public String getCountry() {
        return country;
    }

    public String getPassportIssued() {
        return passportIssued;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public Short getSeries() {
        return Series;
    }

    public Integer getNumber() {
        return Number;
    }
    public String getFullName() {
        return fullName;
    }

    public Boolean getSex() {
        return sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public PlaceOfResidence getPlaceOfResidence() {
        return placeOfResidence;
    }

    public List<Child> getChild() {
        return child;
    }

    public String[] getResponsibilities() {
        return Responsibilities;
    }
}