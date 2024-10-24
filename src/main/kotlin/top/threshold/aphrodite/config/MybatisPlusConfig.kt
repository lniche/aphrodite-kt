package top.threshold.aphrodite.config

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
@MapperScan("top.threshold.aphrodite.mapper")
class MybatisPlusConfig {
    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()
        // 防止全表更新与删除
        interceptor.addInnerInterceptor(BlockAttackInnerInterceptor())
        // 分页插件
        val paginationInnerInterceptor = PaginationInnerInterceptor(DbType.MYSQL)
        // 取消MyBatis Plus的最大分页500条的限制
        paginationInnerInterceptor.maxLimit = 1000L
        interceptor.addInnerInterceptor(paginationInnerInterceptor)
        val dataChangeRecorderInnerInterceptor = DataChangeRecorderInnerInterceptor()
        // 配置安全阈值，例如限制批量更新或插入的记录数不超过 1000 条
        dataChangeRecorderInnerInterceptor.setBatchUpdateLimit(1000)
        interceptor.addInnerInterceptor(dataChangeRecorderInnerInterceptor)
        // 乐观锁插件
        interceptor.addInnerInterceptor(OptimisticLockerInnerInterceptor())
        // 添加非法SQL拦截器
//        interceptor.addInnerInterceptor(IllegalSQLInnerInterceptor())
        return interceptor
    }
}