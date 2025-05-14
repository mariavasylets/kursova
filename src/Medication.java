public class Medication {
    private String name;
    private String dose;
    private String frequency;

    public Medication(String name, String dose, String frequency) {
        this.name = name;
        this.dose = dose;
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return name + " - " + dose + ", " + frequency;
    }
}