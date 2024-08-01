package com.noah.simpleitemservice.repository

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.memory.InMemoryItemRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ItemRepositoryTest(
    private val itemRepository: ItemRepository
) : FunSpec({

    afterEach {
        if (itemRepository is InMemoryItemRepository) {
            itemRepository.clear()
        }
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
        val item1 = Item(itemName = "itemA-1", price = 10000, quantity = 10)
        val item2 = Item(itemName = "itemA-2", price = 20000, quantity = 20)
        val item3 = Item(itemName = "itemB-1", price = 30000, quantity = 30)

        itemRepository.run {
            save(item1)
            save(item2)
            save(item3)
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

