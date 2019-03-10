package src;

import java.text.DecimalFormat;

/**
 * Atribut de tip Float
 * 
 * @author Gabriel-Mihai Isleam
 */
public class Float_attr extends Attribute {

    float value_float;

    Float_attr(String name, String value) {
        super(name, value);
        this.value_float = Float.parseFloat(value);
    }

    /**
     * Metoda afiseaza valoarea de tip float
     */
    @Override
    public void display_value() {
        System.out.print(value_float);
    }

    /**
     * Metoda intoarce valoarea atributului ca String
     * 
     * @return valoarea de tip String cu formatul "#.##"
     */
    @Override
    public String value_toString() {

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value_float);
    }

    /**
     * Metoda seteaza valoarea atributului la cea data ca parametru
     * 
     * @param to_set valoare de setat
     */
    @Override
    public void setValue(String to_set) {
        value_float = Float.parseFloat(to_set);
    }
}
