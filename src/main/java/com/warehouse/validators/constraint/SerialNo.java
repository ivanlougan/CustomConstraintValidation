package com.warehouse.validators.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = SerialNoValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface SerialNo {

    String message() default "${validatedValue} is incorrect. It should start with {startsWith} and have {codeLength} digit number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int codeLength();
    String startsWith();

    // - startsWith definiuje jaki powinien być początek numeru seryjnego (np. PL dla produktów),
    // - codeLength określa długość kodu w numerze seryjnym (np. dla kodu PL12345 długość będzie wynosiła 5).
    // W komunikacie ograniczenia wykorzystałem interpolację i w przypadku jego złamania zostanie
    // do niego wstawiona nieprawidłowa wartość numeru seryjnego,
    // a także informacja o tym, jaki powinien być jego prefix oraz długość kodu.
}
