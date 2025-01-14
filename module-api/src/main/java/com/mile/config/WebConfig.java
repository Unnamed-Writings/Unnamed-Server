package com.mile.config;


import com.mile.common.interceptor.DuplicatedInterceptor;
import com.mile.common.interceptor.MoimAuthInterceptor;
import com.mile.common.resolver.comment.CommentVariableResolver;
import com.mile.common.resolver.moim.MoimVariableResolver;
import com.mile.common.resolver.post.PostVariableResolver;
import com.mile.common.resolver.reply.ReplyVariableResolver;
import com.mile.common.resolver.topic.TopicVariableResolver;
import com.mile.common.resolver.user.UserIdHeaderResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final MoimVariableResolver moimVariableResolver;
    private final TopicVariableResolver topicVariableResolver;
    private final PostVariableResolver postVariableResolver;
    private final CommentVariableResolver commentVariableResolver;
    private final ReplyVariableResolver replyVariableResolver;
    private final DuplicatedInterceptor duplicatedInterceptor;
    private final MoimAuthInterceptor moimAuthInterceptor;
    private final UserIdHeaderResolver userIdHeaderResolver;

    @Value("${client.local}")
    private String clientLocal;

    @Value("${client.develop}")
    private String clientDevelop;

    @Value("${client.dev-local}")
    private String clientDevLocal;

    @Value("${client.deploy}")
    private String clientDeploy;

    @Value("${backend.local-set1}")
    private String serverLocalSet1;

    @Value("${backend.local-set2}")
    private String serverLocalSet2;

    @Value("${backend.develop}")
    private String serverDevelop;

    @Value("${backend.deploy}")
    private String serverDeploy;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(clientLocal, clientDeploy, serverLocalSet1, serverLocalSet2, serverDevelop, serverDeploy, clientDevelop, clientDevLocal)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(duplicatedInterceptor)
                .addPathPatterns("/api/post/temporary", "/api/post", "/api/post/{postId}/comment", "/api/comment/{commentId}", "/api/moim/{moimId}/topic");
        registry.addInterceptor(moimAuthInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(moimVariableResolver);
        resolvers.add(topicVariableResolver);
        resolvers.add(commentVariableResolver);
        resolvers.add(postVariableResolver);
        resolvers.add(replyVariableResolver);
        resolvers.add(userIdHeaderResolver);
    }
}