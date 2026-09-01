package com.hospital.model;

public class Patient {
    private final int patientId;
    private String name;
    private int age;
    private String contactNumber;
    private String medicalCondition;

    public Patient(int patientId, String name, int age,
                   String contactNumber, String medicalCondition) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
        this.medicalCondition = medicalCondition;
    }

    public int getPatientId() { return patientId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getContactNumber() { return contactNumber; }
    public String getMedicalCondition() { return medicalCondition; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setContactNumber(String c) { this.contactNumber = c; }
    public void setMedicalCondition(String m) { this.medicalCondition = m; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Age: %d | Contact: %s | Condition: %s",
                patientId, name, age, contactNumber, medicalCondition);
    }
}