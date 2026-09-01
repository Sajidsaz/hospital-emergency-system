package com.hospital.structures;

import com.hospital.model.Patient;

public class PatientQueue {

    private static class Node {
        Patient patient;
        Node next;

        Node(Patient patient) {
            this.patient = patient;
            this.next = null;
        }
    }

    private Node head;   // front — dequeue here
    private Node tail;   // back  — enqueue here
    private int size;

    public PatientQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /** Adds a patient to the back of the queue. O(1). */
    public void enqueue(Patient patient) {
        if (patient == null) {
            return;
        }

        Node newNode = new Node(patient);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /** Removes and returns the patient at the front, or null if empty. O(1). */
    public Patient dequeue() {
        if (isEmpty()) {
            return null;
        }

        Patient dequeuedPatient = head.patient;
        head = head.next;
        size--;

        // If queue became empty after removal, null out tail as well
        if (head == null) {
            tail = null;
        }

        return dequeuedPatient;
    }

    /** Returns the front patient without removing. O(1). */
    public Patient peek() {
        return isEmpty() ? null : head.patient;
    }

    /** Prints all waiting patients, front first. Handles the empty case. */
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("Emergency Queue is currently empty. No patients waiting.");
            return;
        }

        System.out.println("--- Emergency Waiting Queue (Front to Back) ---");
        Node current = head;
        int position = 1;
        while (current != null) {
            System.out.println(position + ". " + current.patient);
            current = current.next;
            position++;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}