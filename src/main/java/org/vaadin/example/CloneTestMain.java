package org.vaadin.example;


public class CloneTestMain {

    public static void main(String[] args) {
        Customer c = new Customer();
        c.setFirstName("Detlef");
        c.setLastName("Böhm");
        Customer d = null;
        try {
            d = c.clone();
            d.setLastName("Böemmel");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        System.out.println(c);
        System.out.println(d);
    }
}
