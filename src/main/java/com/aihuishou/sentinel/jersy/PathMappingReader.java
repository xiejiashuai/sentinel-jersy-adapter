package com.aihuishou.sentinel.jersy;

import com.aihuishou.sentinel.util.MethodIntrospector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.util.ReflectionUtils;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.Set;

import static com.aihuishou.sentinel.servlet.callback.CustomizerUrlCleaner.MAPPING_URLS;

/**
 * Utility class which used to read all {@link Path} value in the container
 *
 * @author jiashuai.xie
 * @since 2018/12/13 19:06
 */
@Configuration
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PathMappingReader implements BeanPostProcessor {

    private static final String DEFAULT_SEPARATOR = "/";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Path rootAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), Path.class);

        if (null != rootAnnotation) {

            String rootPath = rootAnnotation.value().startsWith(DEFAULT_SEPARATOR) ? rootAnnotation.value() : DEFAULT_SEPARATOR + rootAnnotation.value();

            Set<Method> pathMethods = MethodIntrospector.selectMethods(bean.getClass(),
                    (ReflectionUtils.MethodFilter) method -> null != AnnotationUtils.getAnnotation(method, Path.class)
            );

            pathMethods.forEach(method -> {

                Path methodAnnotation = AnnotationUtils.getAnnotation(method, Path.class);

                String methodPath = methodAnnotation.value();

                if (!methodPath.startsWith(DEFAULT_SEPARATOR)) {
                    methodPath = DEFAULT_SEPARATOR + methodPath;
                }

                String finalPath = rootPath + methodPath;

                log.info("\n method {} mapped to {} ", bean.getClass().getName() + "#" + method.getName(), finalPath);

                MAPPING_URLS.add(finalPath);

            });

        }


        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
