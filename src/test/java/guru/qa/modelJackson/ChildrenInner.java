package guru.qa.modelJackson;

public class ChildrenInner {
    private Boolean sex;
    private String fullName;
    private String dateOfBirth;
    private Short personalCode;

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Short getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(Short personalCode) {
        this.personalCode = personalCode;
    }
}
