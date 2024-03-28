package com.sample.service.manager.controler;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WithMockUser
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class DictionaryControllerTest {
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mockMvc;
    
    @BeforeAll
    public static void beforeAll() {
        System.setProperty("NACOS_SERVER_URL", "localhost:8848");
    }
    
    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        // @formatter:off
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                      .apply(documentationConfiguration(restDocumentation)
                                                     .snippets()
                                                     .withEncoding("UTF-8")
                                                     .and()
                                                     .operationPreprocessors()
                                                     .withRequestDefaults(prettyPrint())
                                                     .withResponseDefaults(prettyPrint()))
                                      .build();
        // @formatter:on
    }
    
    @Test
    public void testFindDictionaryByKey() throws Exception {
        // @formatter:off
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/dict/get/{key}", "session_out_time").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("dictionary-query-by-key.adoc",
                                    pathParameters(
                                            parameterWithName("key").description("字典对应的key值")
                                    ),
                                    responseFields(
                                            fieldWithPath("code").description("响应码"),
                                            fieldWithPath("message").description("响应的消息"),
                                            fieldWithPath("data").description("响应的数据"),
                                            fieldWithPath("data.createBy").description("创建者"),
                                            fieldWithPath("data.createTime").type("LocalDateTime").description("创建时间"),
                                            fieldWithPath("data.delFlag").description("是否已删除：true已删除，false未删除"),
                                            fieldWithPath("data.id").description("字典的ID"),
                                            fieldWithPath("data.key").description("字典的key"),
                                            fieldWithPath("data.remark").type("String").description("字典备注说明"),
                                            fieldWithPath("data.status").description("字典启用状态，true启用，false已禁用"),
                                            fieldWithPath("data.updateBy").description("上次更新人"),
                                            fieldWithPath("data.updateTime").type("LocalDateTime").description("上次更新时间"),
                                            fieldWithPath("data.value").description("字典的值")
                                   )
                    ));
        // @formatter:on
    }
    
    @Test
    public void testFindDictionaryAll() throws Exception {
        // @formatter:off
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dict/query/all").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("dictionary-query-all",
                                    responseFields(
                                            fieldWithPath("code").description("响应码"),
                                            fieldWithPath("message").description("响应的消息"),
                                            fieldWithPath("data").description("响应的数据"),
                                            fieldWithPath("data[].createBy").description("创建者"),
                                            fieldWithPath("data[].createTime").type("LocalDateTime").description("创建时间"),
                                            fieldWithPath("data[].delFlag").description("是否已删除：true已删除，false未删除"),
                                            fieldWithPath("data[].id").description("字典的ID"),
                                            fieldWithPath("data[].key").description("字典的key"),
                                            fieldWithPath("data[].remark").type("String").description("字典备注说明"),
                                            fieldWithPath("data[].status").description("字典启用状态，true启用，false已禁用"),
                                            fieldWithPath("data[].updateBy").description("上次更新人"),
                                            fieldWithPath("data[].updateTime").type("LocalDateTime").description("上次更新时间"),
                                            fieldWithPath("data[].value").description("字典的值")
                                    )
                    ));
        // @formatter:on
    }
}
