public class DiagnosisRecord extends MedicalRecord {
    public DiagnosisRecord(String description) {
        super(description);
    }

    @Override
    public String getRecordType() {
        return "Діагноз";
    }
}