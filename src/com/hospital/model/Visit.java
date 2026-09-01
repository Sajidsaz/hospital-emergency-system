package com.hospital.model;

public class Visit {
    private final String visitId;
    private final String visitDate;
    private final String doctorName;
    private final String diagnosis;
    private final String treatment;

    public Visit(String visitId, String visitDate, String doctorName,
                 String diagnosis, String treatment) {
        this.visitId = visitId;
        this.visitDate = visitDate;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public String getVisitId() { return visitId; }
    public String getVisitDate() { return visitDate; }
    public String getDoctorName() { return doctorName; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Dr. %s | Diagnosis: %s | Treatment: %s",
                visitId, visitDate, doctorName, diagnosis, treatment);
    }
}
