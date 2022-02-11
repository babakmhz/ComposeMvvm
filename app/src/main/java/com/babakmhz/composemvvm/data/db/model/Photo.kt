package com.babakmhz.composemvvm.data.db.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.io.Serializable

@Entity
data class Photo(
    val albumId: Int,
    @Id(assignable = true)
    var id: Long = 0L,
    var thumbnailUrl: String = "",
    var title: String = "",
    var url: String = ""
):Serializable