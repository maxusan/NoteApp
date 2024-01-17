package code.banana.todo_app.usecase.localcache

import code.banana.todo_app.models.Priority
import code.banana.todo_app.repositories.cache.LocalCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadFilterKeyUseCase @Inject constructor(
    private val localCacheRepository: LocalCacheRepository,
) {

    operator fun invoke(): Flow<Priority> {
        return localCacheRepository.readFilterKey()
    }
}