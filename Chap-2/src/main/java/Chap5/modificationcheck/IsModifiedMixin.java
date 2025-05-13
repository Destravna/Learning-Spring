package Chap5.modificationcheck;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class IsModifiedMixin extends DelegatingIntroductionInterceptor implements IsModified {

    private boolean isModified = false;
    private final Map<Method, Method> methodCache = new HashMap<>();
    private final Predicate<MethodInvocation> isSetter = invocation -> invocation.getMethod().getName()
            .startsWith("set") && (invocation.getArguments().length == 1);

    @Override
    public boolean isModified() {
        return isModified;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!isModified) {
            if (isSetter.test(invocation)) {
                Method getter = getGetter(invocation.getMethod());
                if (getter != null) {
                    Object newVal = invocation.getArguments()[0];
                    Object oldVal = getter.invoke(invocation.getThis(), null);
                    if (newVal == null && oldVal == null) {
                        isModified = false;
                    } else if ((newVal == null && oldVal != null) || (newVal != null && oldVal == null)) {
                        isModified = true;
                    } else {
                        isModified = !newVal.equals(oldVal);
                    }
                }
            }
        }
        return super.invoke(invocation); //"After I’ve done my own logic (checking if a setter changed a value), now go ahead and proceed with the actual method call on the underlying target object."
    }

    private Method getGetter(Method setter) {
        Method getter = methodCache.get(setter);
        if (getter != null) {
            return getter;
        }
        String getName = setter.getName().replaceFirst("set", "get");
        try {
            getter = setter.getDeclaringClass().getMethod(getName, null);
            synchronized (methodCache) {
                methodCache.put(setter, getter);
            }
            return getter;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
