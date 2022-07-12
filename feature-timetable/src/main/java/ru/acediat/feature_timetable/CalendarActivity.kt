package ru.acediat.feature_timetable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import ru.acediat.core_utils.Time
import java.time.LocalDateTime


//Здесь не используется viewBinding, потому что с ним слетает тема диалогового окна
//потом возможно стоит поправить
//TODO: проверить насколько работает все без viewBinding
class CalendarActivity : AppCompatActivity() {

    private lateinit var okButton : Button
    private lateinit var cancelButton : Button
    private lateinit var calendar : CalendarView

    private var resultDate = Time.currentDate()

    private val onDateChangeListener = CalendarView.OnDateChangeListener { _, year, month, day ->
        resultDate = LocalDateTime.of(year, month + 1, day, 12, 30)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        okButton = findViewById(R.id.calendarOk)
        cancelButton = findViewById(R.id.calendarCancel)
        calendar = findViewById(R.id.calendarView)

        okButton.setOnClickListener(::ok)
        cancelButton.setOnClickListener(::cancel)
        calendar.setOnDateChangeListener(onDateChangeListener)
    }

    private fun ok(v : View){
        val intent = Intent().apply { putExtra(CalendarContract.CURRENT_DATE, resultDate) }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun cancel(v : View){
        setResult(RESULT_CANCELED)
        finish()
    }

}