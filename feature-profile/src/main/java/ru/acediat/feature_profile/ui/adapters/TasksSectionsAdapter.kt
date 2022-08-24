package ru.acediat.feature_profile.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.acediat.core_android.BasePagerAdapter
import ru.acediat.core_res.linearRecyclerView
import ru.acediat.core_res.loadingFrame
import ru.acediat.core_res.notifyScreen
import ru.acediat.feature_profile.model.dtos.TaskDTO
import ru.acediat.feature_profile.R
import ru.acediat.core_res.R as resR
import ru.acediat.core_navigation.R as navR
import javax.inject.Inject

class TasksSectionsAdapter @Inject constructor(
    private val titles: Array<String>,
    private val adapters: Array<TasksAdapter>
): BasePagerAdapter<ArrayList<ArrayList<TaskDTO>>>() {

    companion object{
        const val TAKEN_SECTION = 0
        const val IN_CHECK_SECTION = 1
        const val ACCEPTED_SECTION = 2
        const val REFUSED_SECTION = 3
    }

    private var onTaskClick: (TaskDTO) -> Unit = {}
    private var onRefresh: () -> Unit = {}
    private var navController: NavController? = null

    fun setOnTaskClickListener(taskClickListener: (TaskDTO) -> Unit){
        onTaskClick = taskClickListener
    }

    fun setOnRefresh(onRefresh: () -> Unit){
        this.onRefresh = onRefresh
    }

    fun setNavController(controller: NavController){
        this.navController = controller
    }

    fun setTakenTasks(list: ArrayList<TaskDTO>) = setTasks(TAKEN_SECTION, list)

    fun setInCheckTasks(list: ArrayList<TaskDTO>) = setTasks(IN_CHECK_SECTION, list)

    fun setAcceptedTasks(list: ArrayList<TaskDTO>) = setTasks(ACCEPTED_SECTION, list)

    fun setRefusedTasks(list: ArrayList<TaskDTO>) = setTasks(REFUSED_SECTION, list)

    private fun setTasks(section: Int, list: ArrayList<TaskDTO>){
        if(data == null)
            data = arrayListOf(arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf())
        adapters[section].setItems(list)
        data!![section] = list
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any = data?.let{
        if(it[position].isNotEmpty())
            instantiateTasksList(container, data!![position], adapters[position])
        else
            instantiateEmpty(container, position)

    } ?: run{
        instantiateLoading(container)
    }


    override fun getCount(): Int = 4

    override fun getPageTitle(position: Int): CharSequence = titles[position]

    override fun instantiateLoading(container: ViewGroup): View = with(loadingFrame(container)) {
        container.addView(this)
        return this
    }

    private fun instantiateEmpty(container: ViewGroup, position: Int): View = when(position){
        TAKEN_SECTION -> instantiateNotify(
            container, resR.drawable.ic_sad,
            R.string.no_taken_tasks, R.string.take_task
        ){
            navController?.navigate(navR.id.newTasks)
        }
        IN_CHECK_SECTION -> instantiateNotify(
            container, resR.drawable.ic_sad,
            R.string.no_incheck_tasks
        )
        ACCEPTED_SECTION -> instantiateNotify(
            container, resR.drawable.ic_sad,
            R.string.no_accepted_tasks
        )
        REFUSED_SECTION -> instantiateNotify(
            container, R.drawable.ic_party,
            R.string.no_refused_tasks
        )
        else -> instantiateLoading(container)
    }

    private fun instantiateNotify(
        container: ViewGroup,
        @DrawableRes drawable: Int,
        @StringRes text: Int,
        @StringRes buttonText: Int? = null,
        onClick: (View) -> Unit = {}
    ): View = with(
        notifyScreen(
            container, drawable, text,
            buttonTitleId = buttonText, onClick = onClick
        )
    ){
        container.addView(this)
        return this
    }

    private fun instantiateTasksList(
        container: ViewGroup,
        list: ArrayList<TaskDTO>,
        tasksAdapter: TasksAdapter
    ): View = with(SwipeRefreshLayout(container.context)){
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        addView(linearRecyclerView(context).apply {
            adapter = tasksAdapter.apply {
                setItems(list)
                setOnTaskClickListener(onTaskClick)
            }
        })
        setOnRefreshListener { onRefresh() }
        container.addView(this)
        return@with this
    }

}