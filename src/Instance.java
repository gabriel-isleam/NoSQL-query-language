package src;

import java.util.ArrayList;

/**
 *
 * @author Gabriel-Mihai Isleam
 */
public class Instance implements Comparable<Instance> {

    String entity_name;
    ArrayList<Attribute> attributes;
    long timestamp;

    Instance(String name) {
        entity_name = name;
        timestamp = System.nanoTime();                                          // preia timestamp-ul local al masinii
    }                                                                           // (in nanosecunde)

    @Override
    public int compareTo(Instance to_compare) {                                 // metoda compareTo este suprascrisa pentru
                                                                                // a putea fi realizata sortarea dupa criteriul
        if (to_compare.timestamp > this.timestamp) {                            // impus, acesta fiind timestamp-ul (data la 
            return 1;                                                           // care s-a facut ultimul update)
        } else if (to_compare.timestamp < this.timestamp) {
            return -1;
        } else {
            return 0;
        }
    }
}
