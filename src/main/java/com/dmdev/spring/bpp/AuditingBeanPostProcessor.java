package com.dmdev.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class AuditingBeanPostProcessor implements BeanPostProcessor {

    private Map<String,Class> auditBeans = new HashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = auditBeans.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                    (proxy, method, args) -> {
                        System.out.println("Audit method: " + method.getName());
                        var start = System.nanoTime();
                        try {
                           return method.invoke(bean, args);
                        } finally {
                            System.out.println("Time execution " + (System.nanoTime() - start));
                        }
            });
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Auditing.class))
            auditBeans.put(beanName,bean.getClass());
        return bean;
    }
}
