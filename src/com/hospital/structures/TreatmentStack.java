package com.hospital.structures;
import com.hospital.model.TreatmentRecord;

public class TreatmentStack {
    private static class Node {
        TreatmentRecord record;
        Node next;
        Node(TreatmentRecord record) {
            this.record = record;
            this.next = null;
        }
    }
    private Node top;
    private int size;
    public TreatmentStack() {
        this.top = null;
        this.size = 0;
    }
    /** Adds a completed treatment record. O(1). */
    public void push(TreatmentRecord record) {
        if (record == null) {
            return;
        }
        Node newNode = new Node(record);
        newNode.next = top;
        top = newNode;
        size++;
    }
    /** Removes and returns the most recent record, or null if empty. O(1). */
    public TreatmentRecord pop() {
        if (isEmpty()) {
            return null;
        }
        TreatmentRecord poppedRecord = top.record;
        top = top.next;
        size--;
        return poppedRecord;
    }
    /** Returns the most recent record without removing. O(1). */
    public TreatmentRecord peek() {
        return isEmpty() ? null : top.record;
    }
    /** Prints all records, most recent first. Handles the empty case. */
    public void displayRecords() {
        if (isEmpty()) {
            System.out.println("No treatment records available. Stack is empty.");
            return;
        }
        System.out.println("--- Treatment History Stack (Most Recent First) ---");
        Node current = top;
        while (current != null) {
            System.out.println(" -> " + current.record);
            current = current.next;
        }
    }
    public boolean isEmpty() {
        return top == null;
    }
    public int size() {
        return size;
    }
}