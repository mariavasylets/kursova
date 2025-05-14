import java.util.*;

public abstract class Patient extends MedicalRecord {
    private String name;
    private String birthDate;
    private FamilyDoctor familyDoctor;
    private List<MedicalRecord> medicalRecords = new ArrayList<>();
    private Referral referral;
    private List<String> medications = new ArrayList<>();

    public Patient(String name, String birthDate, FamilyDoctor doctor) {
        this.name = name;
        this.birthDate = birthDate;
        this.familyDoctor = doctor;
    }

    public String getName() {
        return name;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public FamilyDoctor getFamilyDoctor() {
        return familyDoctor;
    }

    public Referral getReferral() {
        return referral;
    }

    public void setReferral(Referral referral) {
        this.referral = referral;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public List<String> getMedications() {
        return medications;
    }
}