package src;

/**
 * Clasa abstracta ce reprezinta un atribut al unei instante
 * 
 * @author Gabriel-Mihai Isleam
 */
public abstract class Attribute {

    String name;

    Attribute(String name, String value) {
        this.name = name;
    }

    /**
     * Metoda ce afiseaza valoarea specifica fiecarui tip de atribut
     */
    public abstract void display_value();

    /**
     * Metoda intoarce valoarea atributului sub de string
     * 
     * @return valoarea atributului sub forma de string
     */
    public abstract String value_toString();

    /**
     * Metoda seteaza valoarea atributului la cea data ca parametru
     * 
     * @param to_set noua valoarea a atributului
     */
    public abstract void setValue(String to_set);
}
