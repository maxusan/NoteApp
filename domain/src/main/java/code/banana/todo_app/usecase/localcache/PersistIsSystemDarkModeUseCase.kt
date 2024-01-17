package code.banana.todo_app.usecase.localcache

import code.banana.todo_app.repositories.cache.LocalCacheRepository
import javax.inject.Inject

class PersistIsSystemDarkModeUseCase @Inject constructor(
    private val localCacheRepository: LocalCacheRepository,
) {
    suspend operator fun invoke(isSystemDarkTheme: Boolean) {
        localCacheRepository.persistIsSystemDarkTheme(isSystemDarkTheme)
    }
}