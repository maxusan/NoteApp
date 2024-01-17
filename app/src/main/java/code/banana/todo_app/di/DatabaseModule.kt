package code.banana.todo_app.di

import android.content.Context
import androidx.room.Room
import code.banana.todo_app.Constants
import code.banana.todo_app.db.TasksDao
import code.banana.todo_app.db.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTasksDatabase(@ApplicationContext context: Context): TasksDatabase {
        return Room.databaseBuilder(
            context,
            TasksDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTasksDao(tasksDatabase: TasksDatabase): TasksDao {
        return tasksDatabase.taskDao()
    }
}