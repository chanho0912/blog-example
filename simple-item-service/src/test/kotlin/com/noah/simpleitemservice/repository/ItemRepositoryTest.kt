package com.noah.simpleitemservice.repository

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.memory.InMemoryItemRepository
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.DefaultTransactionDefinition
import org.springframework.transaction.support.SimpleTransactionStatus

/**
 * Test는 다른 Test와 격리되는 것이 중요
 * Test는 반복해서 실행할 수 있어야 한다.
 */
@Transactional
@SpringBootTest
class ItemRepositoryTest(
    private val itemRepository: ItemRepository,
//    private val txManager: PlatformTransactionManager
) : FunSpec({
    extension(SpringExtension)

//    var status: TransactionStatus = SimpleTransactionStatus()

    beforeEach {
//        status = txManager.getTransaction(DefaultTransactionDefinition())
    }

    afterEach {
        if (itemRepository is InMemoryItemRepository) {
            itemRepository.clear()
        }

//        txManager.rollback(status)
    }

    test("save") {
        val item = Item(itemName = "itemA", price = 10000, quantity = 10)

        val saved = itemRepository.save(item)

        with(itemRepository.findById(saved.id)!!) {
            this shouldBe saved
        }
    }

    test("update") {
        val item = Item(itemName = "itemA", price = 10000, quantity = 10)
        val saved = itemRepository.save(item)
        val itemId = saved.id

        val updateCommand = ItemUpdateCommand("item2", price = 20000, quantity = 30)
        itemRepository.update(itemId, updateCommand)

        with(itemRepository.findById(itemId)!!) {
            itemName shouldBe updateCommand.itemName
            price shouldBe updateCommand.price
            quantity shouldBe updateCommand.quantity
        }
    }

    test("findAll") {
        var item1 = Item(itemName = "itemA-1", price = 10000, quantity = 10)
        var item2 = Item(itemName = "itemA-2", price = 20000, quantity = 20)
        var item3 = Item(itemName = "itemB-1", price = 30000, quantity = 30)

        itemRepository.run {
            item1 = save(item1)
            item2 = save(item2)
            item3 = save(item3)
        }

        verify(itemRepository, null, null, item1, item2, item3)
        verify(itemRepository, "", null, item1, item2, item3)

        verify(itemRepository, "itemA", null, item1, item2)
        verify(itemRepository, "temA", null, item1, item2)
        verify(itemRepository, "itemB", null, item3)

        verify(itemRepository, null, 10000, item1)
        verify(itemRepository, "itemA", 10000, item1)
    }
})

internal fun verify(
    itemRepository: ItemRepository,
    itemName: String?,
    maxPrice: Int?,
    vararg items: Item
) {
    val result = itemRepository.findAll(ItemSearchCommand(itemName, maxPrice))
    result shouldContainExactly items.toList()
}

