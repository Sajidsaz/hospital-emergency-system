package com.hospital.model;

public class TreatmentRecord {
    private final Patient patient;
    private final String treatmentGiven;
    private final String completedAt;

    public TreatmentRecord(Patient patient, String treatmentGiven, String completedAt) {
        this.patient = patient;
        this.treatmentGiven = treatmentGiven;
        this.completedAt = completedAt;
    }

    public Patient getPatient() { return patient; }
    public String getTreatmentGiven() { return treatmentGiven; }
    public String getCompletedAt() { return completedAt; }

    @Override
    public String toString() {
        return String.format("%s | Treatment: %s | Completed: %s",
                patient.getName(), treatmentGiven, completedAt);
    }
}