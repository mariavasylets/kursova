public class TreatmentRecord extends MedicalRecord {
    public TreatmentRecord(String description) {
        super(description);
    }

    @Override
    public String getRecordType() {
        return "Лікування";
    }
}
