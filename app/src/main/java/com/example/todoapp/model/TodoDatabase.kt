package com.example.todoapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.security.AccessControlContext


@Database(entities = arrayOf(Todo::class), version = 1)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object{
        @Volatile private var instance:TodoDatabase ?= null
        private val LOCK = Any()

        private val Mig_1 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 2 NOT NULL")
            }
        }

        private val Mig_2 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 NOT NULL")
            }
        }
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, TodoDatabase::class.java, "tododb")
                .addMigrations(Mig_1)
                .addMigrations(Mig_2)
                .build()
        
        operator fun invoke(context: Context){
            if(instance != null){
                synchronized(LOCK){
                    instance?: buildDatabase(context).also {
                        instance= it
                    }
                }
            }
        }
    }
}