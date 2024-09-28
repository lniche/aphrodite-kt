package top.threshold.ktscaffold.helper

import com.baomidou.mybatisplus.generator.FastAutoGenerator
import com.baomidou.mybatisplus.generator.config.GlobalConfig
import com.baomidou.mybatisplus.generator.config.PackageConfig
import com.baomidou.mybatisplus.generator.config.StrategyConfig
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine
import top.threshold.ktscaffold.entity.BaseDO
import java.nio.file.Paths


/**
 * @link https://baomidou.com/pages/981406/
 */
object CodeGenerator {

    @JvmStatic
    fun main(args: Array<String>) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/test", "root", "123123")
            .globalConfig { builder: GlobalConfig.Builder ->
                builder
                    .outputDir(Paths.get(System.getProperty("user.dir")).toString() + "/src/main/kotlin")
                    .enableKotlin()
                    .disableOpenDir()
                    .author("qingshan")
            }
            .packageConfig { builder: PackageConfig.Builder ->
                builder
                    .parent("top.threshold.ktscaffold")
                    .entity("entity.pojo")
                    .mapper("mapper")
                    .service("repository")
                    .serviceImpl("repository.impl")
                    .controller("controller.v1")
            }
            .strategyConfig { builder: StrategyConfig.Builder ->
                builder
                    .addInclude("t_account")
                    .addTablePrefix("t_")
                    .entityBuilder()
                    .enableFileOverride()
                    .superClass(BaseDO::class.java)
                    .disableSerialVersionUID()
                    .enableChainModel()
                    .enableLombok()
                    .enableRemoveIsPrefix()
                    .enableTableFieldAnnotation()
                    .addIgnoreColumns(
                        "id",
                        "create_by",
                        "create_time",
                        "update_by",
                        "update_time",
                        "deleted",
                        "version",
                    )
                    .formatFileName("%sDO")
                    .mapperBuilder().disableMapperXml().enableFileOverride()
                    .controllerBuilder().enableFileOverride()
                    .serviceBuilder().enableFileOverride()
            }
            .templateEngine(FreemarkerTemplateEngine())
            .execute()
    }
}