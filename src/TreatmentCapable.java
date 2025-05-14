public interface TreatmentCapable {
    void addMedicalRecord(Patient patient, MedicalRecord record);
    void prescribeMedication(Patient patient, String medication);
}
