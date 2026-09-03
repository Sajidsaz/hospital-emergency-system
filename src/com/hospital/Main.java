package com.hospital;

import java.util.Scanner;

import com.hospital.model.Patient;
import com.hospital.model.TreatmentRecord;
import com.hospital.model.Visit;
import com.hospital.structures.PatientBST;
import com.hospital.structures.PatientQueue;
import com.hospital.structures.TreatmentStack;

public class Main {

    private static final PatientBST patientRecords = new PatientBST();
    private static final PatientQueue emergencyQueue = new PatientQueue();
    private static final TreatmentStack treatmentHistory = new TreatmentStack();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1  -> registerPatient();
                case 2  -> searchPatient();
                case 3  -> deletePatient();
                case 4  -> patientRecords.displayInOrder();
                case 5  -> addToEmergencyQueue();
                case 6  -> treatNextPatient();
                case 7  -> emergencyQueue.displayQueue();
                case 8  -> treatmentHistory.displayRecords();
                case 9  -> undoLastTreatment();
                case 10 -> addVisit();
                case 11 -> removeVisit();
                case 12 -> searchVisit();
                case 13 -> displayVisitHistory();
                case 0  -> running = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
            System.out.println();
        }
        System.out.println("System shutting down. Goodbye.");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("========================================");
        System.out.println("  HOSPITAL EMERGENCY MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("--- Patient Records (BST) ---");
        System.out.println(" 1. Register new patient");
        System.out.println(" 2. Search patient by ID");
        System.out.println(" 3. Delete patient");
        System.out.println(" 4. Display all patients (ascending ID)");
        System.out.println("--- Emergency Queue ---");
        System.out.println(" 5. Add patient to emergency queue");
        System.out.println(" 6. Treat next patient");
        System.out.println(" 7. Display waiting queue");
        System.out.println("--- Treatment History (Stack) ---");
        System.out.println(" 8. Display treatment records");
        System.out.println(" 9. Undo last treatment record");
        System.out.println("--- Visit History (Linked List) ---");
        System.out.println("10. Add visit to patient history");
        System.out.println("11. Remove visit");
        System.out.println("12. Search visit");
        System.out.println("13. Display patient visit history");
        System.out.println(" 0. Exit");
        System.out.println("========================================");
    }

    // ---------- BST operations ----------

    private static void registerPatient() {
        int id = readInt("Patient ID: ");
        String name = readLine("Name: ");
        int age = readInt("Age: ");
        String contact = readLine("Contact number: ");
        String condition = readLine("Medical condition: ");

        Patient patient = new Patient(id, name, age, contact, condition);
        if (patientRecords.insert(patient)) {
            System.out.println("Patient registered successfully.");
        } else {
            System.out.println("Patient ID " + id + " already exists.");
        }
    }

    private static void searchPatient() {
        int id = readInt("Patient ID to search: ");
        Patient found = patientRecords.search(id);
        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("No patient found with ID " + id + ".");
        }
    }

    private static void deletePatient() {
        int id = readInt("Patient ID to delete: ");
        if (patientRecords.delete(id)) {
            System.out.println("Patient with ID " + id + " was successfully deleted.");
        } else {
            System.out.println("No patient found with ID " + id + ".");
        }
    }

    // ---------- Queue operations ----------

    private static void addToEmergencyQueue() {
        int id = readInt("Patient ID to add to queue: ");
        Patient patient = patientRecords.search(id);
        if (patient == null) {
            System.out.println("No patient found with ID " + id + ". Register them first.");
            return;
        }
        emergencyQueue.enqueue(patient);
        System.out.println(patient.getName() + " added to the emergency queue.");
    }

    /**
     * Treats the next patient. This is where all four structures interact:
     * dequeue from the queue, push a record onto the stack, and add a visit
     * to that patient's linked list. The patient stays in the BST throughout.
     */
    private static void treatNextPatient() {
        Patient patient = emergencyQueue.dequeue();
        if (patient == null) {
            System.out.println("Emergency queue is empty. No patients waiting.");
            return;
        }

        System.out.println("Now treating: " + patient.getName());
        String treatment = readLine("Treatment given: ");
        String doctor = readLine("Doctor name: ");
        String date = readLine("Date (YYYY-MM-DD): ");

        treatmentHistory.push(new TreatmentRecord(patient, treatment, date));

        String visitId = "V" + (patient.getVisitHistory().size() + 1);
        patient.getVisitHistory().addVisit(
                new Visit(visitId, date, doctor, patient.getMedicalCondition(), treatment));

        System.out.println("Treatment completed and recorded.");
    }

    // ---------- Stack operations ----------

    private static void undoLastTreatment() {
        TreatmentRecord removed = treatmentHistory.pop();
        if (removed == null) {
            System.out.println("No treatment records to undo. Stack is empty.");
        } else {
            System.out.println("Removed most recent record: " + removed);
        }
    }

    // ---------- Linked list operations ----------

    private static void addVisit() {
        int id = readInt("Patient ID: ");
        Patient patient = patientRecords.search(id);
        if (patient == null) {
            System.out.println("No patient found with ID " + id + ".");
            return;
        }

        String visitId = readLine("Visit ID: ");
        String date = readLine("Date (YYYY-MM-DD): ");
        String doctor = readLine("Doctor Name: ");
        String diagnosis = readLine("Diagnosis: ");
        String treatment = readLine("Treatment: ");

        Visit visit = new Visit(visitId, date, doctor, diagnosis, treatment);
        patient.getVisitHistory().addVisit(visit);
        System.out.println("Visit record added to patient history.");
    }

    private static void removeVisit() {
        int id = readInt("Patient ID: ");
        Patient patient = patientRecords.search(id);
        if (patient == null) {
            System.out.println("No patient found with ID " + id + ".");
            return;
        }

        String visitId = readLine("Visit ID to remove: ");
        if (patient.getVisitHistory().removeByVisitId(visitId)) {
            System.out.println("Visit " + visitId + " removed successfully.");
        } else {
            System.out.println("Visit ID " + visitId + " not found in patient history.");
        }
    }

    private static void searchVisit() {
        int id = readInt("Patient ID: ");
        Patient patient = patientRecords.search(id);
        if (patient == null) {
            System.out.println("No patient found with ID " + id + ".");
            return;
        }

        String visitId = readLine("Visit ID to search: ");
        Visit visit = patient.getVisitHistory().searchByVisitId(visitId);
        if (visit != null) {
            System.out.println("Found: " + visit);
        } else {
            System.out.println("Visit ID " + visitId + " not found in patient history.");
        }
    }

    private static void displayVisitHistory() {
        int id = readInt("Patient ID: ");
        Patient patient = patientRecords.search(id);
        if (patient == null) {
            System.out.println("No patient found with ID " + id + ".");
            return;
        }

        System.out.println("--- Visit History for " + patient.getName()
                + " (ID: " + patient.getPatientId() + ") ---");
        patient.getVisitHistory().displayHistory();
    }

    // ---------- Input helpers ----------

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    
}