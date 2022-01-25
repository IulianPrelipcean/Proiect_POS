package com.example.pos.Configuration.Interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
@RequiredArgsConstructor
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final ValidateTokenInterceptor validateTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry){
        interceptorRegistry.addInterceptor(validateTokenInterceptor);
    }


}
