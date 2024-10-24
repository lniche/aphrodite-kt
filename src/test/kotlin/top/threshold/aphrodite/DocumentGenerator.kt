package top.threshold.aphrodite

import cn.smallbun.screw.core.Configuration
import cn.smallbun.screw.core.engine.EngineConfig
import cn.smallbun.screw.core.engine.EngineFileType
import cn.smallbun.screw.core.engine.EngineTemplateType
import cn.smallbun.screw.core.execute.DocumentationExecute
import cn.smallbun.screw.core.process.ProcessConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

object DocumentGenerator {

    @JvmStatic
    fun main(args: Array<String>) {

        //数据源
        val hikariConfig = HikariConfig()
        hikariConfig.driverClassName = "org.postgresql.Driver"
        hikariConfig.jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/test"
        hikariConfig.username = "postgres"
        hikariConfig.password = "123123"

        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true")
        hikariConfig.minimumIdle = 2
        hikariConfig.maximumPoolSize = 5
        val dataSource: DataSource = HikariDataSource(hikariConfig)

        //生成配置
        val engineConfig = EngineConfig.builder() //生成文件路径
            .fileOutputDir("./docs") //打开目录
            .openOutputDir(false) //文件类型
            .fileType(EngineFileType.HTML) //生成模板实现
            .produceType(EngineTemplateType.freemarker) //自定义文件名称
            .fileName("db").build()


        //忽略表
        val ignoreTableName = ArrayList<String>()
        ignoreTableName.add("test_user")

        //忽略表前缀
        val ignorePrefix = ArrayList<String>()
        ignorePrefix.add("t_")

        //忽略表后缀
        val ignoreSuffix = ArrayList<String>()
        ignoreSuffix.add("_test")
        val processConfig = ProcessConfig.builder() //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
            //根据名称指定表生成
            .designatedTableName(ArrayList()) //根据表前缀生成
            .designatedTablePrefix(ArrayList()) //根据表后缀生成
            .designatedTableSuffix(ArrayList()) //忽略表名
            .ignoreTableName(ignoreTableName) //忽略表前缀
            .ignoreTablePrefix(ignorePrefix) //忽略表后缀
            .ignoreTableSuffix(ignoreSuffix).build()

        //配置
        val config: Configuration = Configuration.builder() //版本
            .version("1.0.0") //描述
            .description("数据库设计文档生成") //数据源
            .dataSource(dataSource) //生成配置
            .engineConfig(engineConfig) //生成配置
            .produceConfig(processConfig)
            .build()

        //执行生成
        DocumentationExecute(config).execute()
    }
}