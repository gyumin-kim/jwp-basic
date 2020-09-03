package core.di.factory;

import com.google.common.collect.Sets;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class BeanScanner {
	private final Reflections reflections;

	public BeanScanner(final Object... basePackage) {
		this.reflections = new Reflections(basePackage);
	}

	/**
	 * {@code Controller}, {@code Service}, {@code Repository} annotation이 붙어있는 모든 객체(bean)를 스캔한다.
	 */
	@SuppressWarnings("unchecked")
	public Set<Class<?>> scan() {
		return getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
	}

	@SuppressWarnings("unchecked")
	private Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation>... annotations) {
		Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
		for (Class<? extends Annotation> annotation : annotations) {
			preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
		}
		return preInstantiatedBeans;
	}
}
