package individuals.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// логирование вызовов методов в сервисном слое
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //лог  в метод перед его выполнением и записать название метода и переданные аргументы
    @Before("execution(* individuals.api.service.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        log.info("Вызов метода: {} с  аргументами: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs()
        );
    }

    // Логи успешного завершения метода и  название метода и возвращаемый результат

    @AfterReturning(pointcut = "execution(* individuals.api.service.*.*(..))", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        log.info(" Метод {} завершен. Результат: {}",
                joinPoint.getSignature().toShortString(),
                result
        );
    }

    //Логи ошибок при выполнении метода иназвание метода и полную информацию об исключении

    @AfterThrowing(pointcut = "execution(* individuals.api.service.*.*(..))", throwing = "error")
    public void logMethodException(JoinPoint joinPoint, Throwable error) {
        log.error("Ошибка вметоде {}: {}",
                joinPoint.getSignature().toShortString(),
                error.getMessage(),
                error
        );
    }
}