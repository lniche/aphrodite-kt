package top.threshold.aphrodite

import com.baomidou.mybatisplus.generator.FastAutoGenerator
import com.baomidou.mybatisplus.generator.config.GlobalConfig
import com.baomidou.mybatisplus.generator.config.PackageConfig
import com.baomidou.mybatisplus.generator.config.StrategyConfig
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine
import top.threshold.aphrodite.pkg.entity.BaseOprDO
import java.nio.file.Paths

object CodeGenerator {

    @JvmStatic
    fun main(args: Array<String>) {
        FastAutoGenerator.create("jdbc:postgresql://127.0.0.1:5432/test", "postgres", "123123")
            .globalConfig { builder: GlobalConfig.Builder ->
                builder
                    .outputDir(Paths.get(System.getProperty("user.dir")).toString() + "/src/main/kotlin")
                    .enableKotlin()
                    .disableOpenDir()
                    .author("qingshan")
            }
            .packageConfig { builder: PackageConfig.Builder ->
                builder
                    .parent("top.threshold.aphrodite.app")
                    .entity("entity.pojo")
                    .mapper("mapper")
                    .service("repository")
                    .serviceImpl("repository.impl")
                    .controller("controller.v1")
            }
            .strategyConfig { builder: StrategyConfig.Builder ->
                builder
                    .addInclude("t_user")
                    .addTablePrefix("t_")
                    .entityBuilder()
                    .enableFileOverride()
                    .superClass(BaseOprDO::class.java)
                    .disableSerialVersionUID()
                    .enableChainModel()
                    .enableLombok()
                    .enableRemoveIsPrefix()
                    .enableTableFieldAnnotation()
                    .addIgnoreColumns(
                        "id",
                        "created_by",
                        "created_at",
                        "updated_by",
                        "updated_at",
                        "deleted_at",
                        "version",
                    )
//                    .versionColumnName("version")
//                    .logicDeleteColumnName("id_deleted")
                    .formatFileName("%sDO")
                    .mapperBuilder().disableMapperXml().enableFileOverride().disable()
                    .controllerBuilder().enableFileOverride().disable()
                    .serviceBuilder()
                    .formatServiceFileName("%sRepository")
                    .formatServiceImplFileName("%sRepositoryImpl")
                    .enableFileOverride().disable()
            }
            .templateEngine(FreemarkerTemplateEngine())
            .execute()
    }
}
