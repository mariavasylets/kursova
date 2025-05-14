public class LabResultRecord extends MedicalRecord {
    public LabResultRecord(String description) {
        super(description);
    }

    @Override
    public String getRecordType() {
        return "Аналіз";
    }
}
