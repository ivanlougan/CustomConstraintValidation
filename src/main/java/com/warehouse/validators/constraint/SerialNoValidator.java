package com.warehouse.validators.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SerialNoValidator implements ConstraintValidator<SerialNo, String> {

    private String startsWith;
    private int codeLength;

    // W metodzie initialize() pobieramy wartości atrybutów przypisanych do adnotacji.
    // W przypadku obiektów Product zostaną pobrane wartości "PL" i 5, natomiast w przypadku walidacji
    // obiektów Equipment będzie to "EQ" i 4. W metodzie isValid() wykorzystujemy koniunkcję
    // do sprawdzenia trzech warunków:
    //
    //checkPrefix() - czy numer seryjny rozpoczyna się od prawidłowego prefixu,
    //checkCodeLength() - czy kod w numerze seryjnym ma prawidłową długość,
    //checkCode() - czy kod w numerze seryjnym składa się wyłącznie z cyfr.
    //
    //Jeżeli wszystkie powyższe warunki są spełnione, to znaczy,
    // że numer seryjny jest poprawny. W innym przypadku coś jest z nim nie tak
    // i ograniczenie @SerialNo zostanie złamane.

    @Override
    public void initialize(SerialNo constraintAnnotation) {
        startsWith = constraintAnnotation.startsWith();
        codeLength = constraintAnnotation.codeLength();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return checkPrefix(value) && checkCodeLength(value) && checkCode(value);
    }

    private boolean checkPrefix(String serialNumber) {
        return serialNumber.startsWith(startsWith);
    }

    private boolean checkCodeLength(String serialNumber) {
        int prefixLength = startsWith.length();
        int serialNumberLength = serialNumber.length();
        return serialNumberLength - prefixLength == codeLength;
    }

    private boolean checkCode(String serialNumber) {
        int prefixLength = startsWith.length();
        char[] serialNumberCode = serialNumber.substring(prefixLength).toCharArray();
        for (char c : serialNumberCode) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
