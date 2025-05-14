public class SpecialistDoctor extends Doctor implements TreatmentCapable {

    public SpecialistDoctor(String name) {
        super(name);
    }

    @Override
    public void addMedicalRecord(Patient patient, MedicalRecord record) {
        if (patient.getReferral() == null || !patient.getReferral().getSpecialistName().equalsIgnoreCase(this.name)) {
            System.out.println("Пацієнт не має направлення до цього спеціаліста.");
            return;
        }
        patient.getMedicalRecords().add(record);
        System.out.println("Спеціаліст додав запис: " + record);
    }



    @Override
    public void accessPatientRecord(Patient patient) throws Exception {
        if (patient.getReferral() == null || !patient.getReferral().getSpecialistName().equalsIgnoreCase(this.name)) {
            throw new Exception("Пацієнт не має направлення до цього спеціаліста.");
        }
        System.out.println("Медичні записи пацієнта:");
        patient.getMedicalRecords().forEach(System.out::println);
    }


    @Override
    public void prescribeMedication(Patient patient, String medication) {
        if (patient.getReferral() == null || !patient.getReferral().getSpecialistName().equalsIgnoreCase(this.name)) {
            System.out.println("Пацієнт не має направлення до цього спеціаліста.");
            return;
        }
        patient.getMedications().add(medication);
        System.out.println("Спеціаліст призначив: " + medication);
    }


}