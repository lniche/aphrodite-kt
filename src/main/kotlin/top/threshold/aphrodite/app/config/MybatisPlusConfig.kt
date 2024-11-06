package top.threshold.aphrodite.app.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.DataChangeRecorderInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@MapperScan("top.threshold.aphrodite.app.mapper")
class MybatisPlusConfig {
    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()
        interceptor.addInnerInterceptor(BlockAttackInnerInterceptor())
        val paginationInnerInterceptor = PaginationInnerInterceptor(DbType.MYSQL)
        paginationInnerInterceptor.maxLimit = 1000L
        interceptor.addInnerInterceptor(paginationInnerInterceptor)
        val dataChangeRecorderInnerInterceptor = DataChangeRecorderInnerInterceptor()
        dataChangeRecorderInnerInterceptor.setBatchUpdateLimit(1000)
        interceptor.addInnerInterceptor(dataChangeRecorderInnerInterceptor)
        interceptor.addInnerInterceptor(OptimisticLockerInnerInterceptor())
//        interceptor.addInnerInterceptor(IllegalSQLInnerInterceptor())
        return interceptor
    }
}