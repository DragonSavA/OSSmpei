package ru.acediat.feature_auth

import android.widget.EditText
import android.widget.RadioButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import ru.acediat.feature_auth.di.AuthComponent
import javax.inject.Inject

class RegistrationViewModel : ViewModel() {

    @Inject lateinit var repository: RegistrationRepository

    private val emailRegex = Regex("[A-Za-z0-9.]+@mpei\\.ru")
    private val nameRegex = Regex("[А-Яа-я]+")

    private val registrationFinished = MutableLiveData<ResponseBody>()
    private val error = MutableLiveData<Throwable>()

    init{
       AuthComponent.init().inject(this)
    }

    fun setRegistrationFinishedObserver(lifecycleOwner: LifecycleOwner, observer: (ResponseBody) -> Unit){
        registrationFinished.observe(lifecycleOwner, observer)
    }

    fun setErrorObserver(lifecycleOwner: LifecycleOwner, observer: (Throwable) -> Unit){
        error.observe(lifecycleOwner, observer)
    }

    fun signUp(
        email: String,
        name: String,
        surname: String,
        gender: String,
        group: String,
        password: String
    ) = repository.signUp(email, name, surname, gender, group, password).subscribe({
        registrationFinished.postValue(it)
    }, {
        error.postValue(it)
    })

    fun getErrors(
        email: String,
        name: String,
        surname: String,
        maleChecked: Boolean,
        femaleChecked: Boolean,
        firstPass: String,
        secondPass: String
    ): ArrayList<String> = arrayListOf(
        isEmailValid(email),
        isNameValid(name),
        isNameValid(surname),
        isPasswordValid(firstPass, secondPass),
        isGenderValid(maleChecked, femaleChecked)
    )

    fun getGender(maleChecked: Boolean, femaleChecked: Boolean) =
        if(maleChecked) "male" else if(femaleChecked) "female" else "undefined"

    fun getGroup(groupEdit : EditText, otherRadioButton: RadioButton) : String{
        if(otherRadioButton.isChecked)
            return otherRadioButton.context.getString(R.string.other)

        return groupEdit.text.toString()
    }

    private fun isEmailValid(email: String): String =
        if(email.matches(emailRegex))
            ""
        else
            "Ошибка в почте!"

    private fun isNameValid(name: String): String =
        if(name.matches(nameRegex))
            ""
        else
            "Неверно указано имя или фамилия!"

    private fun isPasswordValid(firstPass: String, secondPass: String): String =
        if(firstPass == secondPass)
            ""
        else
            "Пароль и его повторение не совпадают!"

    private fun isGenderValid(c1 : Boolean, c2: Boolean): String = if(c1 || c2) "" else "Укажите пол!"
}