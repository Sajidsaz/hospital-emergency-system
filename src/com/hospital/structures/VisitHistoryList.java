package com.hospital.structures;

import com.hospital.model.Visit;

public class VisitHistoryList {

    private static class Node {
        Visit visit;
        Node next;

        Node(Visit visit) {
            this.visit = visit;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public VisitHistoryList() {
        this.head = null;
        this.size = 0;
    }

    /** Inserts at head. O(1). Newest visit appears first. */
    public void addVisit(Visit visit) {
        Node newNode = new Node(visit);
        newNode.next = head;
        head = newNode;
        size++;
    }

    /** Returns the Visit with the given ID, or null if not found. O(n). */
    public Visit searchByVisitId(String visitId) {
        if (visitId == null) {
            return null;
        }
        Node current = head;
        while (current != null) {
            if (visitId.equals(current.visit.getVisitId())) {
                return current.visit;
            }
            current = current.next;
        }
        return null; // Visit ID not found
    }

    /** Removes the visit with the given ID. Returns true if removed. O(n). */
    public boolean removeByVisitId(String visitId) {
        if (head == null || visitId == null) {
            return false;
        }

        // Case 1: Head node holds the target visit ID
        if (visitId.equals(head.visit.getVisitId())) {
            head = head.next;
            size--;
            return true;
        }

        // Case 2: Target node is elsewhere in the list
        Node current = head;
        while (current.next != null) {
            if (visitId.equals(current.next.visit.getVisitId())) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false; // Visit ID was not found
    }

    /** Prints all visits, newest first. Handles the empty case. */
    public void displayHistory() {
        if (isEmpty()) {
            System.out.println("No visit history available for this patient.");
            return;
        }
        System.out.println("--- Patient Visit History (Newest First) ---");
        Node current = head;
        while (current != null) {
            System.out.println(" -> " + current.visit);
            current = current.next;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}