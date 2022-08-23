package ru.acediat.feature_timetable

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ru.acediat.feature_timetable.dtos.GroupValidDTO
import ru.acediat.feature_timetable.dtos.LessonDTO

class TestAPI : TimetableApi {

    override fun getGroupLessons(
        group: String,
        fromDate: String,
        toDate: String
    ): Observable<List<LessonDTO>> = Observable.fromCallable {
        Thread.sleep(500)
        return@fromCallable arrayListOf(
            LessonDTO(
                lessonOId = 1, beginLesson = "9:20", endLesson = "10:55",
                dayOfWeek = 2,discipline = "Хуета раз", auditorium = "A-100",
                kindOfWork = "Лекция", lecturer = "Сосиби Б. У."
            ),
            LessonDTO(
                lessonOId = 2, beginLesson = "9:20", endLesson = "10:55",
                dayOfWeek = 2,discipline = "Хуета раз", auditorium = "A-100",
                kindOfWork = "Лекция", lecturer = "Сосиби Б. У."
            ),
            LessonDTO(
                lessonOId = 3, beginLesson = "9:20", endLesson = "10:55",
                dayOfWeek = 3,discipline = "Хуета раз", auditorium = "A-100",
                kindOfWork = "Лекция", lecturer = "Сосиби Б. У."
            ),
            LessonDTO(
                lessonOId = 4, beginLesson = "9:20", endLesson = "10:55",
                dayOfWeek = 4,discipline = "Хуета раз", auditorium = "A-100",
                kindOfWork = "Лекция", lecturer = "Сосиби Б. У."
            ),
            LessonDTO(
                lessonOId = 5, beginLesson = "9:20", endLesson = "10:55",
                dayOfWeek = 5,discipline = "Хуета раз", auditorium = "A-100",
                kindOfWork = "Лекция", lecturer = "Сосиби Б. У."
            ),
            LessonDTO(
                lessonOId = 6, beginLesson = "9:20", endLesson = "10:55",
                dayOfWeek = 6,discipline = "Хуета раз", auditorium = "A-100",
                kindOfWork = "Лекция", lecturer = "Сосиби Б. У."
            )
        )
    }

    override fun isGroupValid(group: String): Single<GroupValidDTO> = Single.fromCallable {
        return@fromCallable GroupValidDTO(true)
    }
}