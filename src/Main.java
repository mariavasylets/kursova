import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Patient> patients = new ArrayList<>();

    public static void main(String[] args) {
        boolean todo = true;
        while (todo) {
            System.out.println("\nВиберіть роль:");
            System.out.println("1. Я лікар");
            System.out.println("2. Я пацієнт");
            System.out.println("3. Вийти");
            System.out.print("Ваш вибір: ");
            String role = scanner.nextLine();

            switch (role) {
                case "1" -> doctorFlow();
                case "2" -> patientFlow();
                case "3" -> todo = false;
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private static void doctorFlow() {
        System.out.print("Введіть ім’я лікаря для входу: ");
        String doctorName = scanner.nextLine();
        System.out.print("Оберіть свою посаду: \n1. Сімейний лікар \n2. Спеціалізований лікар \n Ваш вибір: ");
        boolean isFamily = scanner.nextLine().equalsIgnoreCase("1");
        Doctor currentDoctor = isFamily ? new FamilyDoctor(doctorName) : new SpecialistDoctor(doctorName);

        boolean running = true;
        while (running) {
            System.out.println("\n--- Меню лікаря ---");
            System.out.println("1. Додати пацієнта");
            System.out.println("2. Додати запис");
            System.out.println("3. Призначити ліки");
            System.out.println("4. Призначити направлення");
            System.out.println("5. Переглянути записи пацієнта");
            System.out.println("6. Статистика");
            System.out.println("7. Повернутись до авторизації");
            System.out.println("8. Вийти");
            System.out.print("Ваш вибір: ");

            switch (scanner.nextLine()) {
                case "1" -> addPatient(currentDoctor);
                case "2" -> addRecord(currentDoctor);
                case "3" -> prescribe(currentDoctor);
                case "4" -> addReferral(currentDoctor);
                case "5" -> showRecords(currentDoctor);
                case "6" -> showStatistics();
                case "8" -> System.exit(0);
                case "7" -> running = false;
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private static void patientFlow() {
        System.out.print("Введіть ваше ім’я: ");
        String name = scanner.nextLine();
        System.out.print("Введіть вашу дату народження: ");
        String birth = scanner.nextLine();

        Optional<Patient> match = patients.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name) && p.getBirthDate().equals(birth))
                .findFirst();

        if (match.isEmpty()) {
            System.out.println("Пацієнта не знайдено в базі.");
            return;
        }

        Patient patient = match.get();
        boolean running = true;

        while (running) {
            System.out.println("\n--- Меню пацієнта ---");
            System.out.println("1. Переглянути сімейного лікаря");
            System.out.println("2. Переглянути медичні записи");
            System.out.println("3. Переглянути останні призначені ліки");
            System.out.println("4. Переглянути направлення");
            System.out.println("5. Повернутись до авторизації");
            System.out.print("Ваш вибір: ");
            switch (scanner.nextLine()) {
                case "1" -> System.out.println("Ваш сімейний лікар: " + patient.getFamilyDoctor().getName());
                case "2" -> {
                    if (patient.getMedicalRecords().isEmpty()) {
                        System.out.println("У вас ще немає медичних записів.");
                    } else {
                        System.out.println("Ваші медичні записи:");
                        patient.getMedicalRecords().forEach(System.out::println);
                    }
                }
                case "3" -> {
                    List<String> meds = patient.getMedications();
                    if (meds.isEmpty()) {
                        System.out.println("У вас ще немає призначених ліків.");
                    } else {
                        System.out.println("Ваші призначені ліки:");
                        meds.forEach(System.out::println);
                    }
                }
                case "4" -> {
                    if (patient.getReferral() != null) {
                        System.out.println("У вас є направлення до: " + patient.getReferral().getSpecialistName());
                    } else {
                        System.out.println("У вас немає активного направлення.");
                    }
                }
                case "5" -> running = false;
                default -> System.out.println("Невірний вибір.");
            }
        }
    }



    private static void addPatient(Doctor doctor) {
        System.out.print("Ім’я пацієнта: ");
        String name = scanner.nextLine();
        System.out.print("Дата народження: ");
        String birth = scanner.nextLine();
        if (!(doctor instanceof FamilyDoctor fd)) {
            System.out.println("Лише сімейний лікар може додавати пацієнтів.");
            return;
        }
        patients.add(new Patient(name, birth, fd));
        System.out.println("Пацієнта додано");
    }

    private static void addRecord(Doctor doctor) {
        Patient p = selectPatient();
        if (p == null) return;

        System.out.print("Тип запису (1-діагноз, 2-лікування, 3-аналіз): ");
        String type = scanner.nextLine();
        System.out.print("Опис: ");
        String desc = scanner.nextLine();
        MedicalRecord record = switch (type) {
            case "1" -> new DiagnosisRecord(desc);
            case "2" -> new TreatmentRecord(desc);
            case "3" -> new LabResultRecord(desc);
            default -> null;
        };
        if (record == null) {
            System.out.println("Невірний тип запису.");
            return;
        }

        try {
            if (doctor instanceof FamilyDoctor fd) {
                fd.addMedicalRecord(p, record);
            } else if (doctor instanceof SpecialistDoctor sd) {
                sd.addMedicalRecord(p, record);
            } else {
                System.out.println("Цей тип лікаря не підтримує додавання записів.");
            }
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void prescribe(Doctor doctor) {
        if (!(doctor instanceof TreatmentCapable tr)) return;
        Patient p = selectPatient();
        if (p == null) return;
        System.out.print("Назва препарату: ");
        String name = scanner.nextLine();
        System.out.print("Доза: ");
        String dose = scanner.nextLine();
        System.out.print("Частота: ");
        String freq = scanner.nextLine();
        Medication m = new Medication(name, dose, freq);
        tr.prescribeMedication(p, m.toString());
    }

    private static void addReferral(Doctor doctor) {
        Patient p = selectPatient();
        if (p == null) return;

        System.out.print("Назначення направлення: ");
        String spname = scanner.nextLine();

        if (doctor instanceof FamilyDoctor fd) fd.giveReferral(p, spname);
    }

    private static void showRecords(Doctor doctor) {
        Patient p = selectPatient();
        if (p == null) return;
        try {
            doctor.accessPatientRecord(p);
        } catch (Exception e) {
            System.out.println("Помилка доступу: " + e.getMessage());
        }
    }

    private static void showStatistics() {
        System.out.println("--- Статистика ---");
        patients.stream()
                .sorted(Comparator.comparingInt(p -> -p.getMedicalRecords().size()))
                .limit(3)
                .forEach(p -> System.out.println(p.getName() + ": " + p.getMedicalRecords().size() + " записів"));
    }

    private static Patient selectPatient() {
        if (patients.isEmpty()) {
            System.out.println("Немає пацієнтів.");
            return null;
        }
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i).getName());
        }
        System.out.print("Оберіть пацієнта: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            return (index >= 0 && index < patients.size()) ? patients.get(index) : null;
        }catch (NumberFormatException e) {
            System.out.println("Будь ласка, введіть число.");
            return null;
        }
    }
}