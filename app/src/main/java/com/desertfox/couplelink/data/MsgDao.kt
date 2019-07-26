package com.desertfox.couplelink.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.desertfox.couplelink.model.responses.MsgModel

@Dao
interface MsgDao {
    @Query("SELECT * FROM MsgModel")
    fun getAllMsg(): List<MsgModel>

    @Insert
    fun insertMsg(msgModel: MsgModel)
}