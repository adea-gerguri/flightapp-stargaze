package shared;

import io.quarkus.hibernate.validator.runtime.ValidatorProvider;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;

import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class GlobalHibernateValidator {
    @Inject
    Validator validator;

    public <T> Uni<T> validate(T dto){
        return Uni.createFrom().emitter(emitter->{
            if(dto==null){
                emitter.fail(new ValidationException("Class is not valid"));
            }
            Set<ConstraintViolation<T>> violations = validator.validate(dto);

            if(violations.isEmpty()){
                emitter.complete(dto);
                return;
            }

            emitter.fail(new ValidationException(
                    violations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining())
            ));
        });

    }

}
