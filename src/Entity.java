package src;

import java.util.ArrayList;

/**
 * @author Gabriel-Mihai Isleam
 */
public class Entity {

    String name;
    int replication_no;
    int attributes_no;
    ArrayList<AttributeType> attributes_list;

    Entity(String name, int no_1, int no_2) {
        this.name = name;
        replication_no = no_1;
        attributes_no = no_2;
        attributes_list = new ArrayList<>(attributes_no);
    }
}
