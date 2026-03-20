package com.movieapp;

public class AnotherBuggySample {
    public static void main(String[] args) {
        AnotherBuggySample abs = new AnotherBuggySample();
        abs.arrayOutOfBoundsBug();
        abs.infiniteLoopBug();
        // Insecure deserialization demo
        abs.insecureDeserializationBug();
    }

    // Array index out of bounds bug
    public void arrayOutOfBoundsBug() {
        int[] numbers = {1, 2, 3};
        // This will throw ArrayIndexOutOfBoundsException
        System.out.println(numbers[5]);
    }

    // Infinite loop bug
    public void infiniteLoopBug() {
        int i = 0;
        while (i >= 0) { // This loop never ends
            System.out.println("Infinite loop: " + i);
            // i is never incremented or decremented
            if (i == 10) break; // This line is unreachable
        }
    }

    // Insecure deserialization vulnerability
    public void insecureDeserializationBug() {
        try {
            java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(getUntrustedSerializedData());
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bis);
            Object obj = ois.readObject(); // Insecure: deserializing untrusted data
            System.out.println("Deserialized object: " + obj);
        } catch (Exception e) {
            System.out.println("Deserialization error: " + e.getMessage());
        }
    }

    // Simulate untrusted serialized data (for demo only)
    private byte[] getUntrustedSerializedData() {
        // In a real attack, this could be attacker-controlled input
        return new byte[]{-84, -19, 0, 5, 115, 114, 0, 14, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46, 68, 97, 116, 101, -117, -104, -104, -104, -104, 2, 0, 0, 120, 112};
    }
}
