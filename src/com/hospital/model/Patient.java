package com.hospital.model;

import com.hospital.structures.VisitHistoryList;

public class Patient {
    private final int patientId;
    private String name;
    private int age;
    private String contactNumber;
    private String medicalCondition;
    private final VisitHistoryList visitHistory;

    public Patient(int patientId, String name, int age,
                   String contactNumber, String medicalCondition) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
        this.medicalCondition = medicalCondition;
        this.visitHistory = new VisitHistoryList();
    }

    public int getPatientId() { return patientId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getContactNumber() { return contactNumber; }
    public String getMedicalCondition() { return medicalCondition; }
    public VisitHistoryList getVisitHistory() { return visitHistory; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public void setMedicalCondition(String medicalCondition) { this.medicalCondition = medicalCondition; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Age: %d | Contact: %s | Condition: %s",
                patientId, name, age, contactNumber, medicalCondition);
    }
}