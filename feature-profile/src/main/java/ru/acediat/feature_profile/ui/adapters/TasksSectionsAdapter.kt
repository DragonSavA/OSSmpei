package ru.acediat.feature_profile.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.acediat.core_android.BasePagerAdapter
import ru.acediat.core_res.linearRecyclerView
import ru.acediat.core_res.loadingFrame
import ru.acediat.feature_profile.model.dtos.TaskDTO
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

    fun setOnTaskClickListener(taskClickListener: (TaskDTO) -> Unit){
        onTaskClick = taskClickListener
    }

    fun setOnRefresh(onRefresh: () -> Unit){
        this.onRefresh = onRefresh
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

    override fun instantiateItem(container: ViewGroup, position: Int): Any =
        if(data == null){
            instantiateLoading(container)
        }else{
            instantiateTasksList(container, data!![position], adapters[position])
        }

    override fun getCount(): Int = 4

    override fun getPageTitle(position: Int): CharSequence = titles[position]

    override fun instantiateLoading(container: ViewGroup): View = with(loadingFrame(container)) {
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