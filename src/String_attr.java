package src;

/**
 * 
 * @author Gabriel-Mihai Isleam
 */
public class String_attr extends Attribute {

    String value;

    String_attr(String name, String value) {
        super(name, value);
        this.value = value;
    }

    /**
     * Metoda afiseaza valoarea de tip string
     */
    @Override
    public void display_value() {
        System.out.print(value);
    }

    /**
     * Metoda returneaza valoarea atributului
     * 
     * @return valoarea atributului
     */
    @Override
    public String value_toString() {
        return value;
    }

    /**
     * Metoda seteaza valoarea atributului la cea data ca parametru
     * 
     * @param to_set valoare de setat
     */
    @Override
    public void setValue(String to_set) {
        value = to_set;
    }
}
