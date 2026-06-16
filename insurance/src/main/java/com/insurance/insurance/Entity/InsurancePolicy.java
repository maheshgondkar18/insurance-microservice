package com.insurance.insurance.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "insurance_policy")
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private Long userId;

    private Long agentId;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    private String firstName;

    private String lastName;

    private int age;

    private int termYears;

    private double sumAssured;

    private double annualPremium;

    private double monthlyPremium;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTermYears() {
        return termYears;
    }

    public void setTermYears(int termYears) {
        this.termYears = termYears;
    }

    public double getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(double sumAssured) {
        this.sumAssured = sumAssured;
    }

    public double getAnnualPremium() {
        return annualPremium;
    }

    public void setAnnualPremium(double annualPremium) {
        this.annualPremium = annualPremium;
    }

    public double getMonthlyPremium() {
        return monthlyPremium;
    }

    public void setMonthlyPremium(double monthlyPremium) {
        this.monthlyPremium = monthlyPremium;
    }

    public boolean isSmoker() {
        return smoker;
    }

    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private boolean smoker;

    private String message;

    private LocalDateTime createdAt;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
}
