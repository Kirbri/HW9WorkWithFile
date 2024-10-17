package guru.qa.modelJackson;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private Children children;
    @JsonProperty("Responsibilities")
    private String[] Responsibilities;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassportIssued() {
        return passportIssued;
    }

    public void setPassportIssued(String passportIssued) {
        this.passportIssued = passportIssued;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Short getSeries() {
        return Series;
    }

    public void setSeries(Short series) {
        Series = series;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public PlaceOfResidence getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(PlaceOfResidence placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public Children getChildren() {
        return children;
    }

    public void setChildren(Children children) {
        this.children = children;
    }

    public String[] getResponsibilities() {
        return Responsibilities;
    }

    public void setResponsibilities(String[] responsibilities) {
        Responsibilities = responsibilities;
    }
}
