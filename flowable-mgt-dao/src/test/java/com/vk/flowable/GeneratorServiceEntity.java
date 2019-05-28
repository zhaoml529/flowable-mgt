package com.vk.flowable;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class GeneratorServiceEntity {

    @Test
    public void generateCode() {
        String packageName = "com.vk.flowable";
        generateByTables(packageName, "message_send_task");
    }

    private void generateByTables(String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://localhost:3306/flowable-mgt";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("")
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(false)
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("tb_")
                .setVersionFieldName("version")
                .setLogicDeleteFieldName("deleted")
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor("zml")
                .setOutputDir("/Users/zml/Desktop/GeneratorCode")
                .setEnableCache(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setFileOverride(true)
                .setServiceName("%sService");
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("domain")
                ).execute();
    }
}