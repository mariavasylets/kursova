public class SpecialistDoctor extends Doctor implements TreatmentCapable {

    public SpecialistDoctor(String name) {
        super(name);
    }

    @Override
    public void accessPatientRecord(Patient patient) throws Exception {
        if (patient.getReferral() == null || !patient.getReferral().getSpecialistName().equals(this.name)) {
            throw new Exception("Немає направлення для цього спеціаліста!");
        }
        System.out.println("Спеціаліст переглядає записи пацієнта: " + patient.getName());
        patient.getMedicalRecords().forEach(System.out::println);
    }

    @Override
    public void prescribeMedication(Patient patient, String medication) {
        if (patient.getReferral() == null) {
            System.out.println("Потрібне направлення для призначення");
            return;
        }
        patient.getMedications().add(medication);
        System.out.println("Спеціаліст призначив: " + medication);
    }

}