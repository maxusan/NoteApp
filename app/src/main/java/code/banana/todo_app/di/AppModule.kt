package code.banana.todo_app.di

import code.banana.todo_app.navigation.navigator.AppNavigator
import code.banana.todo_app.navigation.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindAppNavigator(
        appNavigatorImpl: AppNavigatorImpl,
    ): AppNavigator
}