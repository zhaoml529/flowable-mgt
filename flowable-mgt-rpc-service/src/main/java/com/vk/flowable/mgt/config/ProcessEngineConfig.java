package com.vk.flowable.mgt.config;

import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by zml on 2018/10/10.
 */
@Configuration
public class ProcessEngineConfig {

    @Resource
    private DataSource dataSource;

    @Bean
    public ProcessEngineConfiguration configuration() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngineConfiguration.setCreateDiagramOnDeploy(true);  // 流程发布时生成流程图
        processEngineConfiguration.setProcessDiagramGenerator(new DefaultProcessDiagramGenerator());
        processEngineConfiguration.setActivityFontName("幼圆");
        processEngineConfiguration.setAnnotationFontName("幼圆");
        processEngineConfiguration.setLabelFontName("幼圆");
//        processEngineConfiguration.setCustomSessionFactories()；
        return processEngineConfiguration;
    }

}
