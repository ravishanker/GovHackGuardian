package com.fovea.govhackguardian.model;


import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by ravi on 4/07/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        " Group",
        " Sex",
        " year",
        " Category",
        " Number of Respondents",
        " Actual estimate (Per cent)",
        " LL 95% CI",
        " UL 95% CI"
})
public class AlcoholConsumption {

    @JsonProperty(" Group")
    private String Group;
    @JsonProperty(" Sex")
    private String Sex;
    @JsonProperty(" year")
    private String Year;
    @JsonProperty(" Category")
    private String Category;
    @JsonProperty(" Number of Respondents")
    private String NumberOfRespondents;
    @JsonProperty(" Actual estimate (Per cent)")
    private String ActualEstimatePerCent;
    @JsonProperty(" LL 95% CI")
    private String LL95CI;
    @JsonProperty(" UL 95% CI")
    private String UL95CI;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The Group
     */
    @JsonProperty(" Group")
    public String getGroup() {
        return Group;
    }

    /**
     *
     * @param Group
     * The Group
     */
    @JsonProperty(" Group")
    public void setGroup(String Group) {
        this.Group = Group;
    }

    /**
     *
     * @return
     * The Sex
     */
    @JsonProperty(" Sex")
    public String getSex() {
        return Sex;
    }

    /**
     *
     * @param Sex
     * The Sex
     */
    @JsonProperty(" Sex")
    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    /**
     *
     * @return
     * The Year
     */
    @JsonProperty(" year")
    public String getYear() {
        return Year;
    }

    /**
     *
     * @param Year
     * The year
     */
    @JsonProperty(" year")
    public void setYear(String Year) {
        this.Year = Year;
    }

    /**
     *
     * @return
     * The Category
     */
    @JsonProperty(" Category")
    public String getCategory() {
        return Category;
    }

    /**
     *
     * @param Category
     * The Category
     */
    @JsonProperty(" Category")
    public void setCategory(String Category) {
        this.Category = Category;
    }

    /**
     *
     * @return
     * The NumberOfRespondents
     */
    @JsonProperty(" Number of Respondents")
    public String getNumberOfRespondents() {
        return NumberOfRespondents;
    }

    /**
     *
     * @param NumberOfRespondents
     * The Number of Respondents
     */
    @JsonProperty(" Number of Respondents")
    public void setNumberOfRespondents(String NumberOfRespondents) {
        this.NumberOfRespondents = NumberOfRespondents;
    }

    /**
     *
     * @return
     * The ActualEstimatePerCent
     */
    @JsonProperty(" Actual estimate (Per cent)")
    public String getActualEstimatePerCent() {
        return ActualEstimatePerCent;
    }

    /**
     *
     * @param ActualEstimatePerCent
     * The Actual estimate (Per cent)
     */
    @JsonProperty(" Actual estimate (Per cent)")
    public void setActualEstimatePerCent(String ActualEstimatePerCent) {
        this.ActualEstimatePerCent = ActualEstimatePerCent;
    }

    /**
     *
     * @return
     * The LL95CI
     */
    @JsonProperty(" LL 95% CI")
    public String getLL95CI() {
        return LL95CI;
    }

    /**
     *
     * @param LL95CI
     * The LL 95% CI
     */
    @JsonProperty(" LL 95% CI")
    public void setLL95CI(String LL95CI) {
        this.LL95CI = LL95CI;
    }

    /**
     *
     * @return
     * The UL95CI
     */
    @JsonProperty(" UL 95% CI")
    public String getUL95CI() {
        return UL95CI;
    }

    /**
     *
     * @param UL95CI
     * The UL 95% CI
     */
    @JsonProperty(" UL 95% CI")
    public void setUL95CI(String UL95CI) {
        this.UL95CI = UL95CI;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "AlcoholConsumption{" +
                "Group='" + Group + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Year='" + Year + '\'' +
                ", Category='" + Category + '\'' +
                ", NumberOfRespondents='" + NumberOfRespondents + '\'' +
                ", ActualEstimatePerCent='" + ActualEstimatePerCent + '\'' +
                ", LL95CI='" + LL95CI + '\'' +
                ", UL95CI='" + UL95CI + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
