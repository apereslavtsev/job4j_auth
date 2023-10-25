package ru.job4j.auth.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.job4j.auth.model.FieldsCheckable;

@Aspect
@Component
public class BeforeControllerArgsChecking {

    @Before("@annotation(ru.job4j.auth.controller.CheckRequestArguments)")
    public void checkBefore(JoinPoint joinPoint) throws IllegalArgumentException {
        Signature signature = joinPoint.getSignature();
        Object[] argsObj = joinPoint.getArgs();
        for (Object obj : argsObj) {
            if (obj == null) {
                throw new NullPointerException();
            } else if (obj instanceof FieldsCheckable) {
                ((FieldsCheckable) obj).checkFields();
            }
        }
    }
}