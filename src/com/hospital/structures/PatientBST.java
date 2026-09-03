package com.hospital.structures;

import com.hospital.model.Patient;

public class PatientBST {

    private static class Node {
        Patient patient;
        Node left;
        Node right;

        Node(Patient patient) {
            this.patient = patient;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public PatientBST() {
        this.root = null;
        this.size = 0;
    }

    /** Inserts a patient. Returns false if the ID already exists. */
    public boolean insert(Patient patient) {
        if (patient == null) {
            return false;
        }
        int initialSize = size;
        root = insertRec(root, patient);
        return size > initialSize;
    }

    private Node insertRec(Node current, Patient patient) {
        if (current == null) {
            size++;
            return new Node(patient);
        }
        if (patient.getPatientId() < current.patient.getPatientId()) {
            current.left = insertRec(current.left, patient);
        } else if (patient.getPatientId() > current.patient.getPatientId()) {
            current.right = insertRec(current.right, patient);
        }
        // Duplicate ID: leave the tree unchanged; insert() reports failure
        return current;
    }

    /** Returns the patient with the given ID, or null. O(h). */
    public Patient search(int patientId) {
        return searchRec(root, patientId);
    }

    private Patient searchRec(Node current, int patientId) {
        if (current == null) {
            return null;
        }
        if (patientId == current.patient.getPatientId()) {
            return current.patient;
        }
        if (patientId < current.patient.getPatientId()) {
            return searchRec(current.left, patientId);
        } else {
            return searchRec(current.right, patientId);
        }
    }

    /** Deletes the patient with the given ID. Returns true if deleted. */
    public boolean delete(int patientId) {
        int initialSize = size;
        root = deleteRec(root, patientId);
        return size < initialSize;
    }

    private Node deleteRec(Node current, int patientId) {
        if (current == null) {
            return null; // not found
        }

        if (patientId < current.patient.getPatientId()) {
            current.left = deleteRec(current.left, patientId);
        } else if (patientId > current.patient.getPatientId()) {
            current.right = deleteRec(current.right, patientId);
        } else {
            // Case 1 & 2: no child, or a single child
            if (current.left == null) {
                size--;
                return current.right;
            } else if (current.right == null) {
                size--;
                return current.left;
            }

            // Case 3: two children — replace with in-order successor
            Node minNode = findMin(current.right);
            current.patient = minNode.patient;
            current.right = deleteRec(current.right, minNode.patient.getPatientId());
            // no size-- here: the recursive call above decrements it
        }
        return current;
    }

    /** Returns the leftmost (smallest) node of the given subtree. */
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }
}