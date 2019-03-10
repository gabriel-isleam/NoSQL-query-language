package src;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Gabriel-Mihai Isleam
 */
public class Functions {

    /**
     * Metoda "create" creeaza o entitate noua si o introduce in vectorul de entitati
     * 
     * @param entities vectorul in care sunt pastrate toate tipurile de entitati folosite
     * @param words vectorul cu datele de intrare preluate din fisier (liniile ce contin cuvantul "CREATE")
     */
    public static void create(ArrayList<Entity> entities, String[] words) {

        int attributes_no = Integer.parseInt(words[3]);
        AttributeType attribute;
        Entity new_entity = new Entity(words[1], Integer.parseInt(words[2]), attributes_no);
        for (int i = 4; i < 4 + attributes_no * 2; i += 2) {
            attribute = new AttributeType(words[i], words[i + 1]);
            new_entity.attributes_list.add(attribute);
        }
        entities.add(new_entity);
    }

    /**
     * Metoda "search_instance" cauta o instanta intr-un nod, dupa numele entitatii dar si dupa cheia primara
     * 
     * @param instances vector de instante; reprezinta un nod al bazei de date
     * @param value cheia primara
     * @param name numele instantei
     * @return pozitia instantei in cazul in care o gaseste; altfel, -1
     */
    public static int search_instance(ArrayList<Instance> instances, String value, String name) {

        for (int i = 0; i < instances.size(); i++) {
            if (instances.get(i).entity_name.equals(name)) {
                if (instances.get(i).attributes.get(0).value_toString().equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Metoda "search_node" cauta un nod corespunzator pentru inserare - nodul cel mai ocupat, cu indicele cel mai mic
     * si in care nu se gaseste deja instanta ce trebuie inserata
     * 
     * @param database baza de date
     * @param attributes vectorul de atribute ale instantei ce va trebui inserata, de aici se va prelua cheia primara
     * @param name numele entitatii pentru instanta ce trebuie inserata
     * @param capacity capacitatea maxima a unui nod
     * @return nodul in care va fi inserata noua instanta, sau null daca nu exista niciun nod disponibil pentru inserare
     */
    public static ArrayList<Instance> search_node(ArrayList<ArrayList<Instance>> database, ArrayList<Attribute> attributes, String name, int capacity) {

        int max = -1, aux = -1;
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i).size() > max && database.get(i).size() != capacity) {
                if (search_instance(database.get(i), attributes.get(0).value_toString(), name) == -1) {
                    max = database.get(i).size();
                    aux = i;
                }
            }
        }

        if (max != -1) {
            return database.get(aux);
        } else {
            return null;
        }
    }

    /**
     * Metoda "insert" insereaza o instanta in baza de date, folosindu-se de celelalte functii ("search_node", "search_instance")
     * 
     * @param database - baza de date
     * @param entities - vectorul de entitati
     * @param capacity - capacitatea maxima a unui nod
     * @param words - o linie citita din fisierul cu datele de intrare
     */
    public static void insert(ArrayList<ArrayList<Instance>> database, ArrayList<Entity> entities, int capacity, String[] words) {

        ArrayList<Instance> to_insert;
        int i;
        for (i = 0; i < entities.size(); i++) {
            if (entities.get(i).name.equals(words[1])) {
                break;
            }
        }

        int rf = entities.get(i).replication_no;
        int attr_no = entities.get(i).attributes_no;
        ArrayList<AttributeType> attributes_type = entities.get(i).attributes_list;
        ArrayList<Attribute> attributes = new ArrayList<>(attr_no);

        for (int k = 2; k < 2 + attr_no; k++) {
            if (attributes_type.get(k - 2).type.equals("Float")) {
                attributes.add(new Float_attr(attributes_type.get(k - 2).name, words[k]));
            } else if (attributes_type.get(k - 2).type.equals("Integer")) {
                attributes.add(new Int_attr(attributes_type.get(k - 2).name, words[k]));
            } else if (attributes_type.get(k - 2).type.equals("String")) {
                attributes.add(new String_attr(attributes_type.get(k - 2).name, words[k]));
            }
        }

        Instance new_instance;
        for (int j = 0; j < rf; j++) {
            to_insert = search_node(database, attributes, entities.get(i).name, capacity);
            if (to_insert != null) {
                new_instance = new Instance(words[1]);
                new_instance.attributes = attributes;
                to_insert.add(new_instance);
                Collections.sort(to_insert);
            } else {
                database.add(new ArrayList<>(capacity));
                to_insert = database.get(database.size() - 1);
                new_instance = new Instance(words[1]);
                new_instance.attributes = attributes;
                to_insert.add(new_instance);
            }
        }
    }

    /**
     * Metoda "display_database" afiseaza baza de date, afisand pe rand toate nodurile si instantele acestora; in cazul 
     * in care baza de date este goala se afiseaza mesajul "EMPTY DB"
     * 
     * @param database baza de date
     * @param writer "pointer" catre fisierul in care se scriu datele de iesire
     */
    public static void display_database(ArrayList<ArrayList<Instance>> database, PrintWriter writer) {

        int empty_database = 1;
        ArrayList<Instance> node;
        Instance instance;
        ArrayList<Attribute> attributes;
        for (int i = 0; i < database.size(); i++) {
            node = database.get(i);
            if (!node.isEmpty()) {
                empty_database = 0;
                writer.println("Nod" + (i + 1));
                for (int j = 0; j < node.size(); j++) {
                    instance = node.get(j);
                    attributes = instance.attributes;
                    writer.print(instance.entity_name);
                    for (int k = 0; k < attributes.size(); k++) {
                        writer.print(" " + attributes.get(k).name + ":" + attributes.get(k).value_toString());
                    }
                    writer.println();
                }
            }
        }
        if (empty_database == 1) {
            writer.println("EMPTY DB");
        }
    }

    /**
     * Metoda "get" cauta instanta dorita (specificata prin numele entitatii si cheia primara) in baza de date si afiseaza
     * mai intai toate nodurile in care apare, apoi toate atributele si valorile acestora
     * 
     * @param database baza de date
     * @param words linie citita din fisierul cu datele de intrare
     * @param writer "pointer" catre fisierul in care se scriu datele de iesire
     */
    public static void get(ArrayList<ArrayList<Instance>> database, String[] words, PrintWriter writer) {

        String entity_name = words[1];
        String primary_key = words[2];
        ArrayList<Instance> node;
        Instance to_find, aux = null;
        int index;
        for (int i = 0; i < database.size(); i++) {
            node = database.get(i);
            index = search_instance(node, primary_key, entity_name);

            if (index != -1) {
                to_find = database.get(i).get(index);
                aux = to_find;
                writer.print("Nod" + (i + 1) + " ");
            }
        }
        if (aux != null) {
            writer.print(aux.entity_name);
            for (int i = 0; i < aux.attributes.size(); i++) {
                writer.print(" " + aux.attributes.get(i).name + ":" + aux.attributes.get(i).value_toString());
            }
        } else {
            writer.print("NO INSTANCE FOUND");
        }
        writer.println();
    }

    /**
     * Metoda "search_attribute" este folosita de functia "update" pentru a cauta atributul caruia trebuie sa ii fie 
     * schimbata valoarea
     * 
     * @param attributes vectorul de atribute
     * @param attr_name numele atributului
     * @return atributul caruia i se va schimba valoarea
     */
    public static Attribute search_attribute(ArrayList<Attribute> attributes, String attr_name) {

        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).name.equals(attr_name)) {
                return attributes.get(i);
            }
        }
        return null;
    }

    /**
     * Metoda "update" actualizeaza valoarea anumitor atribute ale unei instante specificate prin numele entitatii si 
     * cheia primara
     * 
     * @param database baza de date
     * @param words linie citita din fisierul cu datele de intrare
     */
    public static void update(ArrayList<ArrayList<Instance>> database, String[] words) {

        Instance to_update;
        Attribute to_change = null;
        int index, aux;
        for (int i = 0; i < database.size(); i++) {
            index = 3;
            aux = search_instance(database.get(i), words[2], words[1]);

            if (aux != -1) {
                to_update = database.get(i).get(aux);
                to_update.timestamp = System.nanoTime();
                while (index < words.length) {
                    to_change = search_attribute(to_update.attributes, words[index]);
                    if (to_change != null) {
                        to_change.setValue(words[index + 1]);
                    }
                    index += 2;
                }
                Collections.sort(database.get(i));
            }
        }

    }

    /**
     * Metoda "delete" sterge o instanta din toate nodurile bazei de date
     * 
     * @param database baza de date
     * @param primary_key cheia primara a instantei ce trebuie stearsa
     * @param entity_name numele entitatii
     * @param writer "pointer" catre fisierul in care se scriu datele de iesire
     */
    public static void delete(ArrayList<ArrayList<Instance>> database, String primary_key, String entity_name, PrintWriter writer) {

        int index = -1, deleted = 0;
        for (int i = 0; i < database.size(); i++) {
            index = search_instance(database.get(i), primary_key, entity_name);
            if (index != -1) {
                database.get(i).remove(index);
                deleted = 1;
            }
        }
        if (deleted == 0) {
            writer.println("NO INSTANCE TO DELETE");
        }
    }

    /**
     * Metoda "cleanup" realizeaza stergerea instantelor cu un timestamp mai mic decat cel dat ca parametru
     * 
     * @param database baza de date
     * @param timestamp
     */
    public static void cleanup(ArrayList<ArrayList<Instance>> database, long timestamp) {

        Instance inst;
        ArrayList<Instance> instances;
        for (int i = 0; i < database.size(); i++) {
            instances = database.get(i);
            for (int j = 0; j < instances.size(); j++) {
                inst = instances.get(j);
                if (inst.timestamp < timestamp) {
                    instances.remove(j);
                    j--;
                }
            }
        }
    }
}
