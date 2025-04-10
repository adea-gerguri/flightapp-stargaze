package model.mapper;

import jakarta.enterprise.context.ApplicationScoped;

import java.lang.reflect.Method;

@ApplicationScoped
public class MapperService {

    public <T, R> R map(T source, Class<R> targetClass) {
        if (source == null) return null;

        try {
            R target = targetClass.getDeclaredConstructor().newInstance();
            Method[] sourceMethods = source.getClass().getMethods();
            Method[] targetMethods = targetClass.getMethods();

            for (Method sourceMethod : sourceMethods) {
                if (isGetter(sourceMethod)) {
                    String propertyName = sourceMethod.getName().substring(3);
                    for (Method targetMethod : targetMethods) {
                        if (isSetter(targetMethod) && targetMethod.getName().substring(3).equals(propertyName)) {
                            Object value = sourceMethod.invoke(source);
                            targetMethod.invoke(target, value);
                            break;
                        }
                    }
                }
            }

            return target;

        } catch (Exception e) {
            throw new RuntimeException("map failed", e);
        }
    }

    private boolean isGetter(Method method) {
        return method.getName().startsWith("get") &&
                method.getParameterCount() == 0 &&
                !void.class.equals(method.getReturnType());
    }

    private boolean isSetter(Method method) {
        return method.getName().startsWith("set") &&
                method.getParameterCount() == 1;
    }
}

