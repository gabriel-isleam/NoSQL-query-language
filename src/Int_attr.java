package src;

/**
 * 
 * @author Gabriel-Mihai Isleam
 */
public class Int_attr extends Attribute {

    int value_int;

    Int_attr(String name, String value) {
        super(name, value);
        this.value_int = Integer.parseInt(value);
    }

    /**
     * Metoda afiseaza valoarea de tip integer
     */
    @Override
    public void display_value() {
        System.out.print(value_int);
    }

    /**
     * Metoda intoarce valoarea atributului ca String
     * 
     * @return valoarea atributului convertita in String
     */
    @Override
    public String value_toString() {
        return Integer.toString(value_int);
    }

    /**
     * Metoda seteaza valoarea atributului la cea data ca parametru
     * 
     * @param to_set valoare de setat
     */
    @Override
    public void setValue(String to_set) {
        value_int = Integer.parseInt(to_set);
    }
}
