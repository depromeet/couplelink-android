package com.desertfox.couplelink.model.responses


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MsgModel(
    @ColumnInfo(name = "banned_index_range")
    val bannedIndexRange: List<IndexRangeModel?>,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "message")
    val message: String,
    @ColumnInfo(name = "writer")
    val writer: WriterModel
)