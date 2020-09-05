package core.di.factory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private final Set<Class<?>> preInstantiatedBeans;

    private final Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstantiatedBeans) {
        this.preInstantiatedBeans = preInstantiatedBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> clazz : preInstantiatedBeans) {
            if (beans.get(clazz) == null) {
                instantiateClass(clazz);
            }
        }
    }

    private Object instantiateClass(final Class<?> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return bean;
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (injectedConstructor == null) {  // @Inject annotation이 설정되어 있지 않으면 기본 생성자를 제공 (p.400)
            bean = BeanUtils.instantiate(clazz);
            beans.put(clazz, bean);
            return bean;
        }

        logger.debug("Constructor: {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beans.put(clazz, bean);
        return bean;
    }

    private Object instantiateConstructor(final Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : parameterTypes) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstantiatedBeans);
            if (!preInstantiatedBeans.contains(concreteClazz)) {
                throw new IllegalStateException(clazz + "is not a bean.");
            }
            Object bean = beans.get(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : preInstantiatedBeans) {
            Controller annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, beans.get(clazz));
            }
        }
        return controllers;
    }
}
