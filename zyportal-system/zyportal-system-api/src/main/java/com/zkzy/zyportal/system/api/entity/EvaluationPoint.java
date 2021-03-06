package com.zkzy.zyportal.system.api.entity;

import java.io.Serializable;

public class EvaluationPoint implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.ID
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.FOOD
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short food;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.BATH
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short bath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.MODIFIED
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short modified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.DRESSING
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short dressing;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.STOOL
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short stool;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.URINATE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short urinate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.TOILET
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short toilet;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.BED_CHAIR_MOVE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short bedChairMove;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.WALK
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short walk;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.STAIRS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short stairs;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.COGNITIVE_FUNCTION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short cognitiveFunction;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.AGGRESSIVE_BEHAVIOR
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short aggressiveBehavior;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.DEPRESSION_SYMPTOMS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short depressionSymptoms;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.AWARENESS_LEVEL
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short awarenessLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.VISION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short vision;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.LISTENING
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short listening;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.COMMUNICATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short communication;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.SURVIVAL_SKILLS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short survivalSkills;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.ABILITY_WORK
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short abilityWork;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.TIMESPACE_ORIENTATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short timespaceOrientation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.CHARACTER_ORIENTATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short characterOrientation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.SOCIAL_COMMUNICATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private Short socialCommunication;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EVALUATION_POINT.CAUSE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    private String cause;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.ID
     *
     * @return the value of EVALUATION_POINT.ID
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.ID
     *
     * @param id the value for EVALUATION_POINT.ID
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.FOOD
     *
     * @return the value of EVALUATION_POINT.FOOD
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getFood() {
        return food;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.FOOD
     *
     * @param food the value for EVALUATION_POINT.FOOD
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setFood(Short food) {
        this.food = food;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.BATH
     *
     * @return the value of EVALUATION_POINT.BATH
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getBath() {
        return bath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.BATH
     *
     * @param bath the value for EVALUATION_POINT.BATH
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setBath(Short bath) {
        this.bath = bath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.MODIFIED
     *
     * @return the value of EVALUATION_POINT.MODIFIED
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getModified() {
        return modified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.MODIFIED
     *
     * @param modified the value for EVALUATION_POINT.MODIFIED
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setModified(Short modified) {
        this.modified = modified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.DRESSING
     *
     * @return the value of EVALUATION_POINT.DRESSING
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getDressing() {
        return dressing;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.DRESSING
     *
     * @param dressing the value for EVALUATION_POINT.DRESSING
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setDressing(Short dressing) {
        this.dressing = dressing;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.STOOL
     *
     * @return the value of EVALUATION_POINT.STOOL
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getStool() {
        return stool;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.STOOL
     *
     * @param stool the value for EVALUATION_POINT.STOOL
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setStool(Short stool) {
        this.stool = stool;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.URINATE
     *
     * @return the value of EVALUATION_POINT.URINATE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getUrinate() {
        return urinate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.URINATE
     *
     * @param urinate the value for EVALUATION_POINT.URINATE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setUrinate(Short urinate) {
        this.urinate = urinate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.TOILET
     *
     * @return the value of EVALUATION_POINT.TOILET
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getToilet() {
        return toilet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.TOILET
     *
     * @param toilet the value for EVALUATION_POINT.TOILET
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setToilet(Short toilet) {
        this.toilet = toilet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.BED_CHAIR_MOVE
     *
     * @return the value of EVALUATION_POINT.BED_CHAIR_MOVE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getBedChairMove() {
        return bedChairMove;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.BED_CHAIR_MOVE
     *
     * @param bedChairMove the value for EVALUATION_POINT.BED_CHAIR_MOVE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setBedChairMove(Short bedChairMove) {
        this.bedChairMove = bedChairMove;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.WALK
     *
     * @return the value of EVALUATION_POINT.WALK
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getWalk() {
        return walk;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.WALK
     *
     * @param walk the value for EVALUATION_POINT.WALK
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setWalk(Short walk) {
        this.walk = walk;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.STAIRS
     *
     * @return the value of EVALUATION_POINT.STAIRS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getStairs() {
        return stairs;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.STAIRS
     *
     * @param stairs the value for EVALUATION_POINT.STAIRS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setStairs(Short stairs) {
        this.stairs = stairs;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.COGNITIVE_FUNCTION
     *
     * @return the value of EVALUATION_POINT.COGNITIVE_FUNCTION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getCognitiveFunction() {
        return cognitiveFunction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.COGNITIVE_FUNCTION
     *
     * @param cognitiveFunction the value for EVALUATION_POINT.COGNITIVE_FUNCTION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setCognitiveFunction(Short cognitiveFunction) {
        this.cognitiveFunction = cognitiveFunction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.AGGRESSIVE_BEHAVIOR
     *
     * @return the value of EVALUATION_POINT.AGGRESSIVE_BEHAVIOR
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getAggressiveBehavior() {
        return aggressiveBehavior;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.AGGRESSIVE_BEHAVIOR
     *
     * @param aggressiveBehavior the value for EVALUATION_POINT.AGGRESSIVE_BEHAVIOR
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setAggressiveBehavior(Short aggressiveBehavior) {
        this.aggressiveBehavior = aggressiveBehavior;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.DEPRESSION_SYMPTOMS
     *
     * @return the value of EVALUATION_POINT.DEPRESSION_SYMPTOMS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getDepressionSymptoms() {
        return depressionSymptoms;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.DEPRESSION_SYMPTOMS
     *
     * @param depressionSymptoms the value for EVALUATION_POINT.DEPRESSION_SYMPTOMS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setDepressionSymptoms(Short depressionSymptoms) {
        this.depressionSymptoms = depressionSymptoms;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.AWARENESS_LEVEL
     *
     * @return the value of EVALUATION_POINT.AWARENESS_LEVEL
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getAwarenessLevel() {
        return awarenessLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.AWARENESS_LEVEL
     *
     * @param awarenessLevel the value for EVALUATION_POINT.AWARENESS_LEVEL
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setAwarenessLevel(Short awarenessLevel) {
        this.awarenessLevel = awarenessLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.VISION
     *
     * @return the value of EVALUATION_POINT.VISION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getVision() {
        return vision;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.VISION
     *
     * @param vision the value for EVALUATION_POINT.VISION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setVision(Short vision) {
        this.vision = vision;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.LISTENING
     *
     * @return the value of EVALUATION_POINT.LISTENING
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getListening() {
        return listening;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.LISTENING
     *
     * @param listening the value for EVALUATION_POINT.LISTENING
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setListening(Short listening) {
        this.listening = listening;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.COMMUNICATION
     *
     * @return the value of EVALUATION_POINT.COMMUNICATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getCommunication() {
        return communication;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.COMMUNICATION
     *
     * @param communication the value for EVALUATION_POINT.COMMUNICATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setCommunication(Short communication) {
        this.communication = communication;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.SURVIVAL_SKILLS
     *
     * @return the value of EVALUATION_POINT.SURVIVAL_SKILLS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getSurvivalSkills() {
        return survivalSkills;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.SURVIVAL_SKILLS
     *
     * @param survivalSkills the value for EVALUATION_POINT.SURVIVAL_SKILLS
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setSurvivalSkills(Short survivalSkills) {
        this.survivalSkills = survivalSkills;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.ABILITY_WORK
     *
     * @return the value of EVALUATION_POINT.ABILITY_WORK
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getAbilityWork() {
        return abilityWork;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.ABILITY_WORK
     *
     * @param abilityWork the value for EVALUATION_POINT.ABILITY_WORK
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setAbilityWork(Short abilityWork) {
        this.abilityWork = abilityWork;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.TIMESPACE_ORIENTATION
     *
     * @return the value of EVALUATION_POINT.TIMESPACE_ORIENTATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getTimespaceOrientation() {
        return timespaceOrientation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.TIMESPACE_ORIENTATION
     *
     * @param timespaceOrientation the value for EVALUATION_POINT.TIMESPACE_ORIENTATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setTimespaceOrientation(Short timespaceOrientation) {
        this.timespaceOrientation = timespaceOrientation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.CHARACTER_ORIENTATION
     *
     * @return the value of EVALUATION_POINT.CHARACTER_ORIENTATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getCharacterOrientation() {
        return characterOrientation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.CHARACTER_ORIENTATION
     *
     * @param characterOrientation the value for EVALUATION_POINT.CHARACTER_ORIENTATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setCharacterOrientation(Short characterOrientation) {
        this.characterOrientation = characterOrientation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.SOCIAL_COMMUNICATION
     *
     * @return the value of EVALUATION_POINT.SOCIAL_COMMUNICATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public Short getSocialCommunication() {
        return socialCommunication;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.SOCIAL_COMMUNICATION
     *
     * @param socialCommunication the value for EVALUATION_POINT.SOCIAL_COMMUNICATION
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setSocialCommunication(Short socialCommunication) {
        this.socialCommunication = socialCommunication;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EVALUATION_POINT.CAUSE
     *
     * @return the value of EVALUATION_POINT.CAUSE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public String getCause() {
        return cause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EVALUATION_POINT.CAUSE
     *
     * @param cause the value for EVALUATION_POINT.CAUSE
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }
}