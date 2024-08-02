package com.noah.simpleitemservice.config

import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.jdbctemplate.JdbcTemplateItemRepositoryV1
import com.noah.simpleitemservice.service.ItemService
import com.noah.simpleitemservice.service.ItemServiceV1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JdbcTemplateV1Configuration {
    @Bean
    fun itemService(itemRepository: ItemRepository): ItemService =
        ItemServiceV1(itemRepository)

    @Bean
    fun itemRepository(dataSource: DataSource): ItemRepository =
        JdbcTemplateItemRepositoryV1(dataSource)
}
