public abstract class MedicalRecord {
    protected String description;

    public MedicalRecord(String description) {
        this.description = description;
    }

    public abstract String getRecordType();

    @Override
    public String toString() {
        return getRecordType() + ": " + description;
    }
}