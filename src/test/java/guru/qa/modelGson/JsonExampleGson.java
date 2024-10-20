package guru.qa.modelGson;

import com.google.gson.annotations.SerializedName;

public class JsonExampleGson {
    private String squadName;
    private String homeTown;
    @SerializedName("ID")
    private String id;
    private Integer formed;
    private String secretBase;
    private Boolean active;
    private Member members;

    public String getSquadName() {
        return squadName;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public Integer getFormed() {
        return formed;
    }

    public String getSecretBase() {
        return secretBase;
    }

    public Boolean getActive() {
        return active;
    }

    public Member getMembers() {
        return members;
    }
}