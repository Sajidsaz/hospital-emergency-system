# Mini Hospital Emergency Management System

A console-based hospital emergency management system built in Java, implementing four
fundamental data structures from scratch without using any built-in Java collections.

**Module:** CIT300 - Data Structures and Algorithms

**Institution:** SLTC Research University

**Student Name :** *S.Sajidh Ahamed*

**Student ID :** *23DA2-0840*

---

## Overview

The system simulates the workflow of a hospital emergency unit: patients are registered
into a central record store, queued for emergency treatment, treated in arrival order,
and their completed treatments and visit histories are retained for later review.

Every data structure in this project is hand-implemented. No `java.util.LinkedList`,
`Stack`, `Queue`, `ArrayList` or `TreeMap` is used for any of the core structures.

---

## Data Structures Used

- **Binary Search Tree** (`PatientBST`) - patient records keyed by Patient ID. Insert, search and delete are O(h).

- **Queue** (`PatientQueue`) - emergency waiting line, FIFO. Enqueue and dequeue are O(1).

- **Stack** (`TreatmentStack`) - completed treatment records, LIFO. Push and pop are O(1).

- **Singly Linked List** (`VisitHistoryList`) - per-patient visit history. Add is O(1); search and remove are O(n).

`h` is the height of the tree - O(log n) when balanced, O(n) in the worst case.

---

## Project Structure

```
hospital-emergency-system/
├── README.md
├── screenshots/
└── src/
    └── com/
        └── hospital/
            ├── Main.java                  Menu-driven console interface
            ├── model/
            │   ├── Patient.java           Patient record + owned visit history
            │   ├── Visit.java             A single hospital visit
            │   └── TreatmentRecord.java   A completed treatment
            └── structures/
                ├── PatientBST.java
                ├── PatientQueue.java
                ├── TreatmentStack.java
                └── VisitHistoryList.java
```

---

## How to Run

**Eclipse**

1. Import the project: *File → Import → Existing Projects into Workspace*
2. Right-click `Main.java` → *Run As → Java Application*
3. Enter menu choices in the Console panel

**Command line**

```bash
javac -d out src/com/hospital/*.java src/com/hospital/model/*.java src/com/hospital/structures/*.java
java -cp out com.hospital.Main
```

Requires JDK 14 or later (the menu uses arrow-syntax `switch`).

Five sample patients (IDs 20, 30, 40, 50, 70) are pre-loaded at startup so the system
can be demonstrated without manual data entry.

---

## Features

**Patient Records (BST)**
- Register a patient — duplicate IDs are rejected
- Search by Patient ID
- Delete a patient — handles all three BST deletion cases
- Display all patients in ascending ID order via in-order traversal

**Emergency Queue**
- Add a registered patient to the waiting queue
- Treat the next patient (FIFO order)
- Display the current waiting list with queue positions
- Empty-queue operations return safely without error

**Treatment History (Stack)**
- Completed treatments are pushed automatically when a patient is treated
- Undo the most recent treatment record (LIFO order)
- Display all records, most recent first
- Empty-stack operations return safely without error

**Visit History (Linked List)**
- Add, remove, search and display visits per patient
- Each patient owns an independent visit history list

---

## Design Decisions

**Patient ID is `final int`.**
The ID is the BST key. If it could change while the patient sat in the tree, the
ordering invariant would silently break and searches would fail unpredictably.
Making it final and immutable removes that risk entirely.

**The queue and stack store `Patient` references, not copies.**
The BST is the single source of truth for patient data. The other structures point at
the same objects, so an update to a patient's details is immediately visible everywhere
rather than leaving stale duplicates behind.

**Each `Patient` owns its `VisitHistoryList` as a `final` field.**
Visit history is composed into the patient rather than kept in a separate global
structure. Because the field is final and initialised in the constructor, a patient
always has a valid list — no null checks are needed anywhere in the codebase.

**Visits are inserted at the head of the linked list.**
Head insertion is O(1) and requires no tail pointer. It also produces
reverse-chronological display order for free, which matches how visit history is
naturally read.

**The queue keeps both head and tail pointers.**
Dequeue at the head is trivially O(1), but enqueue at the tail would require an O(n)
traversal with only a head pointer. The tail reference makes both operations O(1).
The implementation carefully nulls the tail when the last element is dequeued —
otherwise the next enqueue would attach to an orphaned node.

**The stack needs only a single pointer.**
Both push and pop act on the same end, so there is no tail bookkeeping and no
empty-transition edge case. This is why LIFO is structurally simpler than FIFO.

**BST deletion uses the in-order successor.**
For a node with two children, the replacement is the smallest key in the right subtree.
That value is larger than everything in the left subtree and smaller than everything
remaining on the right, so the ordering invariant is preserved. Because the successor
is by definition the leftmost node, it has no left child — the recursive call that
removes it always resolves to the zero- or one-child case and never recurses further.

**Each structure declares its own `private static` nested `Node` class.**
A single shared generic `Node<T>` would have to be visible across packages, leaking
implementation detail. The small amount of duplication buys proper encapsulation —
no code outside a structure can reach its internal nodes.

**Data structures perform no console I/O for control flow.**
Operations return `null` or `boolean` and let `Main` decide what to display. This keeps
the structures reusable and independent of the user interface. (Display methods are the
deliberate exception, since printing is their purpose.)

---

## Known Limitations

- Data is held in memory only; nothing persists between runs.
- Visit IDs entered manually are not validated for uniqueness against auto-generated
  ones, so a collision is possible. A search would then return only the first match.
- The BST is not self-balancing. Registering patients in ascending ID order degenerates
  the tree into a linked list, degrading operations to O(n). A production system would
  use an AVL or red-black tree.
- Deleting a patient from the BST does not remove them from the emergency queue or
  treatment history, since those hold direct references.

---

## Development

The repository contains incremental commits made throughout development, reflecting the
order in which the system was built: project structure, model classes, visit history
list, emergency queue, treatment stack, BST insertion and traversal, BST deletion,
menu integration, and testing.