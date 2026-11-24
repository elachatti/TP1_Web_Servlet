package lab.mvc.model;

import java.io.Serializable;

/** Partie 3.2 : le Modele. */
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String birthDay; // format mm/dd/yyyy
    private String sex;

    public Person() {
    }

    public Person(String firstName, String lastName, String birthDay, String sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.sex = sex;
    }

    /** Annee de naissance lue a la fin de la date mm/dd/yyyy, ou -1 si illisible. */
    public int getBirthYear() {
        if (birthDay == null) {
            return -1;
        }
        String[] parts = birthDay.trim().split("/");
        try {
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getBirthDay() { return birthDay; }
    public void setBirthDay(String birthDay) { this.birthDay = birthDay; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
}
