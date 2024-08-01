package com.noah.simpleitemservice.web

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand
import com.noah.simpleitemservice.service.ItemService
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/items")
class ItemController(
    private val itemService: ItemService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun items(
        @ModelAttribute("itemSearch") searchCommand: ItemSearchCommand,
        model: Model
    ): String {
        val items = itemService.findItems(searchCommand)
        model.addAttribute("items", items)
        return "items"
    }

    @GetMapping("/{itemId}")
    fun item(
        @PathVariable
        itemId: Long,
        model: Model
    ): String {
        val item = itemService.findById(itemId)
            ?: run {
                logger.error { "not found item with itemId=$itemId" }
                throw NoSuchElementException("itemId=$itemId")
            }

        model.addAttribute("item", item)
        return "item"
    }

    @GetMapping("/add")
    fun addForm(): String = "addForm"

    @PostMapping("/add")
    fun addItem(
        @ModelAttribute
        item: Item,
        redirectAttributes: RedirectAttributes
    ): String {
        val saved = itemService.save(item)
        redirectAttributes.addAttribute("itemId", saved.id)
        redirectAttributes.addAttribute("status", true)
        return "redirect:/items/{itemId}"
    }

    @GetMapping("/{itemId}/edit")
    fun editForm(
        @PathVariable
        itemId: Long,
        model: Model
    ): String {
        val item = itemService.findById(itemId)
            ?: run {
                logger.error { "not found item with itemId=$itemId" }
                throw NoSuchElementException("itemId=$itemId")
            }
        model.addAttribute("item", item)
        return "editForm"
    }

    @PostMapping("/{itemId}/edit")
    fun edit(
        @PathVariable
        itemId: Long,
        @ModelAttribute
        updateCommand: ItemUpdateCommand
    ): String {
        itemService.update(itemId, updateCommand)
        return "redirect:/items/{itemId}"
    }
}
