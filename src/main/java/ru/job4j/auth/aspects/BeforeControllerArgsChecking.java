package ru.job4j.auth.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.job4j.auth.model.Person;

@Aspect
@Component
public class BeforeControllerArgsChecking {

    @Before("execution(* ru.job4j.auth.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] argsObj = joinPoint.getArgs();
        for (Object obj : argsObj) {
            if (obj == null) {
                throw new NullPointerException();
            } else if (obj.getClass().equals(Person.class)
                    && !((Person) obj).loginAndPasswordIsNotEmpty()) {
                throw new IllegalArgumentException("login or password is empty");
            }
        }
    }
}
