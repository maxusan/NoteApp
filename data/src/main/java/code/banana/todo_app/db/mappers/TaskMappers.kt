package code.banana.todo_app.db.mappers

import code.banana.todo_app.db.models.TaskEntity
import code.banana.todo_app.models.Task

fun Task.toData(): TaskEntity {
    return run {
        TaskEntity(
            id = id,
            title = title,
            description = description,
            priority = priority,
            timestamp = timestamp
        )
    }
}

fun TaskEntity.toDomain(): Task {
    return run {
        Task(
            id = id,
            title = title,
            description = description,
            priority = priority,
            timestamp = timestamp
        )
    }
}