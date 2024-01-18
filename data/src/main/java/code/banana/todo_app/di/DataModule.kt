package code.banana.todo_app.di

import code.banana.todo_app.repositories.cache.LocalCacheRepository
import code.banana.todo_app.repositories.cache.LocalCacheRepositoryImpl
import code.banana.todo_app.repositories.task.TasksRepository
import code.banana.todo_app.repositories.task.TasksRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    @Singleton
    fun bindTasksRepository(tasksRepositoryImpl: TasksRepositoryImpl): TasksRepository

    @Binds
    @Singleton
    fun bindLocalCacheRepository(localCacheRepositoryImpl: LocalCacheRepositoryImpl): LocalCacheRepository
}