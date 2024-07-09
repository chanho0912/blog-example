package com.noah.datajpa.lock.repository

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import org.springframework.data.domain.Persistable
import java.util.*

@MappedSuperclass
class PrimaryKey : Persistable<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private val id: UUID = UlidCreator.getMonotonicUlid().toUuid()

    @Transient
    private var _isNew: Boolean = true

    override fun getId(): UUID = id

    override fun isNew(): Boolean = _isNew

    @PostLoad
    protected fun markOldAfterRead() {
        _isNew = false
    }

    @PostPersist
    protected fun markOldAfterPersist() {
        _isNew = false
    }
}
