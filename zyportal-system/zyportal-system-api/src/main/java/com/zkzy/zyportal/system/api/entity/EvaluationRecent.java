package com.zkzy.zyportal.system.api.entity;

import java.io.Serializable;

public class EvaluationRecent implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.ID
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.DEMENTIA
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String dementia;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.MENTAL_ILLNESS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String mentalIllness;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.CHRONIC_DISEASE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String chronicDisease;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.OTHER_DISEASES
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String otherDiseases;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.FALL
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String fall;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.LOST
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String lost;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.CHOKING_FOOD
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String chokingFood;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.SUICIDE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String suicide;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.OTHER
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String other;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.PROVIDER_NAME
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String providerName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.PROVIDER_RELATIONSHIP
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String providerRelationship;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.FILE_PATH
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String filePath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_RECENT.FILE_BASENAME
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String fileBasename;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.ID
     *
     * @return the value of EVALUATION_RECENT.ID
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.ID
     *
     * @param id the value for EVALUATION_RECENT.ID
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.DEMENTIA
     *
     * @return the value of EVALUATION_RECENT.DEMENTIA
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getDementia() {
        return dementia;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.DEMENTIA
     *
     * @param dementia the value for EVALUATION_RECENT.DEMENTIA
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setDementia(String dementia) {
        this.dementia = dementia == null ? null : dementia.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.MENTAL_ILLNESS
     *
     * @return the value of EVALUATION_RECENT.MENTAL_ILLNESS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getMentalIllness() {
        return mentalIllness;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.MENTAL_ILLNESS
     *
     * @param mentalIllness the value for EVALUATION_RECENT.MENTAL_ILLNESS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setMentalIllness(String mentalIllness) {
        this.mentalIllness = mentalIllness == null ? null : mentalIllness.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.CHRONIC_DISEASE
     *
     * @return the value of EVALUATION_RECENT.CHRONIC_DISEASE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getChronicDisease() {
        return chronicDisease;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.CHRONIC_DISEASE
     *
     * @param chronicDisease the value for EVALUATION_RECENT.CHRONIC_DISEASE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setChronicDisease(String chronicDisease) {
        this.chronicDisease = chronicDisease == null ? null : chronicDisease.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.OTHER_DISEASES
     *
     * @return the value of EVALUATION_RECENT.OTHER_DISEASES
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getOtherDiseases() {
        return otherDiseases;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.OTHER_DISEASES
     *
     * @param otherDiseases the value for EVALUATION_RECENT.OTHER_DISEASES
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setOtherDiseases(String otherDiseases) {
        this.otherDiseases = otherDiseases == null ? null : otherDiseases.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.FALL
     *
     * @return the value of EVALUATION_RECENT.FALL
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getFall() {
        return fall;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.FALL
     *
     * @param fall the value for EVALUATION_RECENT.FALL
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setFall(String fall) {
        this.fall = fall == null ? null : fall.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.LOST
     *
     * @return the value of EVALUATION_RECENT.LOST
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getLost() {
        return lost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.LOST
     *
     * @param lost the value for EVALUATION_RECENT.LOST
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setLost(String lost) {
        this.lost = lost == null ? null : lost.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.CHOKING_FOOD
     *
     * @return the value of EVALUATION_RECENT.CHOKING_FOOD
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getChokingFood() {
        return chokingFood;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.CHOKING_FOOD
     *
     * @param chokingFood the value for EVALUATION_RECENT.CHOKING_FOOD
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setChokingFood(String chokingFood) {
        this.chokingFood = chokingFood == null ? null : chokingFood.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.SUICIDE
     *
     * @return the value of EVALUATION_RECENT.SUICIDE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getSuicide() {
        return suicide;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.SUICIDE
     *
     * @param suicide the value for EVALUATION_RECENT.SUICIDE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setSuicide(String suicide) {
        this.suicide = suicide == null ? null : suicide.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.OTHER
     *
     * @return the value of EVALUATION_RECENT.OTHER
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getOther() {
        return other;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.OTHER
     *
     * @param other the value for EVALUATION_RECENT.OTHER
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.PROVIDER_NAME
     *
     * @return the value of EVALUATION_RECENT.PROVIDER_NAME
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.PROVIDER_NAME
     *
     * @param providerName the value for EVALUATION_RECENT.PROVIDER_NAME
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName == null ? null : providerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.PROVIDER_RELATIONSHIP
     *
     * @return the value of EVALUATION_RECENT.PROVIDER_RELATIONSHIP
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getProviderRelationship() {
        return providerRelationship;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.PROVIDER_RELATIONSHIP
     *
     * @param providerRelationship the value for EVALUATION_RECENT.PROVIDER_RELATIONSHIP
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setProviderRelationship(String providerRelationship) {
        this.providerRelationship = providerRelationship == null ? null : providerRelationship.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.FILE_PATH
     *
     * @return the value of EVALUATION_RECENT.FILE_PATH
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.FILE_PATH
     *
     * @param filePath the value for EVALUATION_RECENT.FILE_PATH
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_RECENT.FILE_BASENAME
     *
     * @return the value of EVALUATION_RECENT.FILE_BASENAME
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getFileBasename() {
        return fileBasename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_RECENT.FILE_BASENAME
     *
     * @param fileBasename the value for EVALUATION_RECENT.FILE_BASENAME
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setFileBasename(String fileBasename) {
        this.fileBasename = fileBasename == null ? null : fileBasename.trim();
    }
}