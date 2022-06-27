package ru.acediat.core_utils

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TimeUnitTest {

    private val someDate = LocalDateTime.of(2022,6,29, 2,12)

    private val someDates = arrayListOf<LocalDateTime>(
        LocalDateTime.of(2022,6,27, 2,12),
        LocalDateTime.of(2022,6,28, 2,12),
        LocalDateTime.of(2022,6,29, 2,12)
    )

    private val someStringDates = arrayListOf(
        "2022.06.27", "2022.06.28", "2022.06.29", "2022.06.30", "2022.07.01", "2022.07.02",
    )

    private val ruzFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    @Test
    fun currentMonday_isCorrect() {
        assertEquals("2022.06.27", Time.mondayDate(someDate, ruzFormatter))
    }

    @Test
    fun nextMonday_isCorrect(){
        assertEquals("2022.07.04", Time.nextMondayDate(someDate, ruzFormatter))
    }

    @Test
    fun getDaysLocalDateTime_isCorrect(){
        val dates = Time.getDates(someDates[0], 3)
        for(i in someDates.indices){
            assertEquals(someDates[i], dates[i])
        }
    }

    @Test
    fun getDaysString_isCorrect(){
        val dates = Time.getDates(someDates[0], 3, Time.RUZ)
        for(i in someDates.indices){
            assertEquals(someDates[i].format(Time.RUZ), dates[i])
        }
    }

    @Test
    fun getDaysStringToString_isCorrect(){
        val dates = Time.getDates(someStringDates[0], 6, Time.RUZ)
        for(i in someStringDates.indices){
            assertEquals(someStringDates[i], dates[i])
        }
    }
}