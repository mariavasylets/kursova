public abstract class Doctor {
    protected String name;

    public Doctor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void accessPatientRecord(Patient patient) throws Exception;
}
