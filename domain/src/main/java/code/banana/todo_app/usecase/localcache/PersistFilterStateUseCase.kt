package code.banana.todo_app.usecase.localcache

import code.banana.todo_app.models.Priority
import code.banana.todo_app.repositories.cache.LocalCacheRepository
import javax.inject.Inject

class PersistFilterStateUseCase @Inject constructor(
    private val localCacheRepository: LocalCacheRepository
) {

    suspend operator fun invoke(priority: Priority) {
        localCacheRepository.persistFilterState(priority)
    }
}