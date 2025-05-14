public class FamilyDoctor extends Doctor implements TreatmentCapable {

    public FamilyDoctor(String name) {
        super(name);
    }

    @Override
    public void addMedicalRecord(Patient patient, MedicalRecord record) {
        patient.getMedicalRecords().add(record);
        System.out.println("Запис додано: " + record);
    }

    @Override
    public void accessPatientRecord(Patient patient) {
        System.out.println("Медичні записи пацієнта:");
        patient.getMedicalRecords().forEach(System.out::println);
    }

    @Override
    public void prescribeMedication(Patient patient, String medication) {
        patient.getMedications().add(medication);
        System.out.println("Призначено: " + medication);
    }

    public void giveReferral(Patient patient, String specialistName) {
        patient.setReferral(new Referral(specialistName));
        System.out.println("Направлення видано до: " + specialistName);
    }
}
