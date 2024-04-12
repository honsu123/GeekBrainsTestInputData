package Seminar2.test1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class Person {
    private String lastName;
    private String firstName;
    private String middleName;
    private String dateOfBirth;
    private long phoneNumber;
    private char gender;

    public Person(String lastName, String firstName, String middleName, String dateOfBirth, long phoneNumber, char gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public char getGender() {
        return gender;
    }
}

class InvalidDataFormatException extends Exception {
    public InvalidDataFormatException(String message) {
        super(message);
    }
}

public class NotWorking {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    
        System.out.println("Введите данные (Фамилия Имя Отчество дата_рождения номер_телефона пол):");
        try {
            reader.toString();
            String input = reader.readLine();
            Person person = parseInput(input);
            writeToTextFile(person);
            System.out.println("Данные успешно записаны в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        } catch (InvalidDataFormatException e) {
            System.out.println("Ошибка в формате введенных данных: " + e.getMessage());
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии потока ввода: " + e.getMessage());
            }
        }
    }
    
    

    private static Person parseInput(String input) throws InvalidDataFormatException {
        String[] parts = input.split(" ");

        if (parts.length != 6) {
            throw new InvalidDataFormatException("Неверное количество данных.");
        }

        String lastName = parts[0];
        String firstName = parts[1];
        String middleName = parts[2];
        String dateOfBirth = parts[3];
        long phoneNumber;
        char gender;

        try {
            phoneNumber = Long.parseLong(parts[4]);
        } catch (NumberFormatException e) {
            throw new InvalidDataFormatException("Неверный формат номера телефона.");
        }

        if (!parts[5].matches("[mf]")) {
            throw new InvalidDataFormatException("Неверный формат пола.");
        } else {
            gender = parts[5].charAt(0);
        }

        return new Person(lastName, firstName, middleName, dateOfBirth, phoneNumber, gender);
    }

    private static void writeToTextFile(Person person) throws IOException {
        String filename = person.getLastName() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(person.getLastName() + " " +
                    person.getFirstName() + " " +
                    person.getMiddleName() + " " +
                    person.getDateOfBirth() + " " +
                    person.getPhoneNumber() + " " +
                    person.getGender());
            writer.newLine();
        }
    }
}