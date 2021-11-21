package com.pangtudy.boardapi.config;

import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig
{
    @Bean
    public GroupedOpenApi categoryGroupApi() {
        return GroupedOpenApi.builder()
                .group("Category")
                .pathsToMatch("/board/categories/**")
//                .addOpenApiCustomiser(getOpenApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi postGroupApi() {
        return GroupedOpenApi.builder()
                .group("Post")
                .pathsToMatch("/board/posts/**")
//                .pathsToExclude(/board/posts/{post_id}/comments/**") //it doesn't work..T_T why?
//                .addOpenApiCustomiser(getOpenApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi commentGroupApi() {
        return GroupedOpenApi.builder()
                .group("Comment")
                .pathsToMatch("/board/posts/{post_id}/comments/**")
//                .addOpenApiCustomiser(getOpenApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi tagGroupApi() {
        return GroupedOpenApi.builder()
                .group("Tag")
                .pathsToMatch("/board/posts/{post_id}/tags/**")
//                .addOpenApiCustomiser(getOpenApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi fileGroupApi() {
        return GroupedOpenApi.builder()
                .group("File")
                .pathsToMatch("/board/posts/{post_id}/files/**")
//                .addOpenApiCustomiser(getOpenApiCustomiser())
                .build();
    }

    public OpenApiCustomiser getOpenApiCustomiser() {
        return openAPI -> openAPI.getPaths().values().stream().flatMap(pathItem ->
                pathItem.readOperations().stream())
                .forEach(operation -> {
                    operation.addParametersItem(new Parameter().name("Authorization").in("header").
                            schema(new StringSchema().example("")).required(false));
                    operation.addParametersItem(new Parameter().name("userId").in("header").
                            schema(new StringSchema().example("")).required(false));
                });
    }
}
