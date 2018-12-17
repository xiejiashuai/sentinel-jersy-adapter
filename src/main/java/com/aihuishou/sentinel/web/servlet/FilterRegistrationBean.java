package com.aihuishou.sentinel.web.servlet;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

/**
 * Servlet 3.0 Spec Implement to register {@link javax.servlet.Filter}
 *
 * @author jiashuai.xie
 * @since 2018/12/13 17:47
 */
public class FilterRegistrationBean implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        FilterRegistration.Dynamic dynamic = servletContext.addFilter("sentinel-filter", new CommonFilter());
        dynamic.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class),false,"/*");
        dynamic.setAsyncSupported(true);
    }

}
