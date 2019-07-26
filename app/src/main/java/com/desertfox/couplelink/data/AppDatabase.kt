package com.desertfox.couplelink.data

import androidx.room.RoomDatabase


//@Database(entities = {MsgModel.class}, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val msgDao: MsgDao
}