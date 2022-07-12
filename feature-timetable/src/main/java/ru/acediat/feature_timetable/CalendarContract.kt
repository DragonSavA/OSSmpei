package ru.acediat.feature_timetable

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.acediat.core_utils.Time
import java.time.LocalDateTime

class CalendarContract : ActivityResultContract<LocalDateTime, LocalDateTime>() {

    override fun createIntent(context: Context, input: LocalDateTime?): Intent =
        Intent(context, CalendarActivity::class.java).apply {
            putExtra(CURRENT_DATE, input)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): LocalDateTime = when(resultCode) {
        Activity.RESULT_OK -> intent?.getSerializableExtra(CURRENT_DATE) as LocalDateTime
        else -> Time.currentDate()
    }

    companion object{
        val CURRENT_DATE = "CURRENT_DATE"
    }

}