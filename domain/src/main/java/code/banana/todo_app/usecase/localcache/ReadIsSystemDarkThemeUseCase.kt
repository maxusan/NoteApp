package code.banana.todo_app.usecase.localcache

import code.banana.todo_app.repositories.cache.LocalCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadIsSystemDarkThemeUseCase @Inject constructor(
    private val localCacheRepository: LocalCacheRepository,
) {
    operator fun invoke(): Flow<Boolean> {
        return localCacheRepository.readIsSystemDarkTheme()
    }
}