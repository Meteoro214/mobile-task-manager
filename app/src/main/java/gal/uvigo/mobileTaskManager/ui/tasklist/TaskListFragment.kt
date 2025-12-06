package gal.uvigo.mobileTaskManager.ui.tasklist

import android.graphics.Canvas
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskListBinding
import gal.uvigo.mobileTaskManager.model.TaskViewModel
import gal.uvigo.mobileTaskManager.ui.tasklist.adapter.TaskAdapter
import gal.uvigo.mobileTaskManager.ui.tasklist.adapter.TaskListItem

/**
 * Fragment to show the list of tasks
 */
class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    /**
     * Binding for the layout
     */
    private lateinit var binding: FragmentTaskListBinding

    /**
     * TaskViewModel to handle Tasks
     */
    private val viewModel: TaskViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskListBinding.bind(view)
        binding.taskRV.layoutManager = LinearLayoutManager(context)
        //Creates task adapter and sends lambda for ItemTask onClick
        binding.taskRV.adapter = TaskAdapter { task ->
            findNavController()
                .navigate(TaskListFragmentDirections.checkTaskDetails(task.id))
        }

        //Observes taskList
        viewModel.taskListItems.observe(viewLifecycleOwner) { taskList ->
            (binding.taskRV.adapter as TaskAdapter).submitList(taskList)
        }

        setupSwipe()
        setupDrag()

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Setups Swipe actions
     */
    private fun setupSwipe() {
        val swipeHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            //not used
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //Not used
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                //gets the position of the viewholder on the adapter
                val pos = viewHolder.bindingAdapterPosition
                //retrieves the item
                val item = (binding.taskRV.adapter as TaskAdapter).currentList.getOrNull(pos)

                if (item is TaskListItem.TaskItem) { //Checks not header

                    when (direction) {
                        //Deletes
                        ItemTouchHelper.LEFT -> {
                            val deleted = viewModel.deleteTask(item.task.id)
                            if (deleted) {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.check_delete_OK_msg),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.check_delete_error_msg),
                                    Toast.LENGTH_SHORT
                                ).show()
                                //Needed incase delete fails
                                binding.taskRV.adapter?.notifyItemChanged(pos)
                            }
                        }

                        ItemTouchHelper.RIGHT -> {
                            val updated = viewModel.markTaskDone(item.task.id)
                            when (updated) {
                                true -> {
                                    Toast.makeText(
                                        requireContext(), getString(R.string.swipe_mark_OK_msg),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                false -> {
                                    Toast.makeText(
                                        requireContext(),
                                        getString(R.string.swipe_mark_wasDone_msg),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //Used so adapter recharges view & removes the swipe background
                                    binding.taskRV.adapter?.notifyItemChanged(pos)
                                }

                                else -> {//Should never happen
                                    Toast.makeText(
                                        requireContext(),
                                        getString(R.string.swipe_mark_error_msg),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //Used so adapter recharges view & removes the swipe background
                                    binding.taskRV.adapter?.notifyItemChanged(pos)
                                }
                            }
                        }

                        else -> {}
                    }
                }
                //RV updates through observer & submitList()
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                //gets the position of the viewholder on the adapter
                val pos = viewHolder.bindingAdapterPosition
                //retrieves the item
                val item = (recyclerView.adapter as TaskAdapter).currentList.getOrNull(pos)
                val swipes = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return if (item is TaskListItem.TaskItem) makeMovementFlags(0, swipes) else 0
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                //gets the position of the viewholder on the adapter
                val pos = viewHolder.bindingAdapterPosition
                //retrieves the item
                val item = (recyclerView.adapter as TaskAdapter).currentList.getOrNull(pos)
                val itemView = viewHolder.itemView
                if (item is TaskListItem.TaskItem && actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (dX > 0) { //Swipping right
                        c.drawColor(itemView.context.getColor(R.color.grassgreen))
                        val d = itemView.context.getDrawable(R.drawable.ic_checkmark)
                        if (d != null) {
                            val iconMargin = (itemView.height - d.intrinsicHeight) / 2
                            val iconTop = itemView.top + iconMargin
                            val iconBottom = iconTop + d.intrinsicHeight
                            val iconLeft = itemView.left + iconMargin
                            val iconRight = iconLeft + d.intrinsicWidth
                            d.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                            d.draw(c)
                        }
                    } else if (dX < 0) { //Swipping left
                        c.drawColor(itemView.context.getColor(R.color.red))
                        val d = itemView.context.getDrawable(R.drawable.ic_delete)

                        if (d != null) {
                            val iconMargin = (itemView.height - d.intrinsicHeight) / 2
                            val iconTop = itemView.top + iconMargin
                            val iconBottom = iconTop + d.intrinsicHeight
                            val iconRight = itemView.right - iconMargin
                            val iconLeft = iconRight - d.intrinsicWidth
                            d.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                            d.draw(c)
                        }
                    }
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        })

        swipeHelper.attachToRecyclerView(binding.taskRV)
    }

    /**
     * Setups Drag actions
     */
    private fun setupDrag() {
        val dragHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                val from = viewHolder.bindingAdapterPosition
                val to = target.bindingAdapterPosition
                val adapter = (recyclerView.adapter as TaskAdapter)
                val list = adapter.currentList.toMutableList()
                val fromItem = list.getOrNull(from)
                val toItem = list.getOrNull(to)

                return if (fromItem is TaskListItem.TaskItem && toItem is TaskListItem.TaskItem
                    && fromItem.task.category == toItem.task.category
                ) {
                    //Reorder only in View at first
                    list.removeAt(to)
                    list.add(to, fromItem)
                    list.removeAt(from)
                    list.add(from, toItem)
                    adapter.submitList(list)
                    //Tell VM what we reordered
                    viewModel.reorder(fromItem.task.id, toItem.task.id)
                    true
                } else {
                    false
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.elevation = 5.5F
                viewHolder.itemView.alpha = 1F
                //Commit reordering to Room
                viewModel.persistOrder()
            }

            override fun onSelectedChanged(
                viewHolder: RecyclerView.ViewHolder?,
                actionState: Int
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
                    viewHolder.itemView.elevation = 16F
                    viewHolder.itemView.alpha = 0.8F
                }
                super.onSelectedChanged(viewHolder, actionState)
            }

            //not used
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
            }//Not used

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                //gets the position of the viewholder on the adapter
                val pos = viewHolder.bindingAdapterPosition
                //retrieves the item
                val item = (recyclerView.adapter as TaskAdapter).currentList.getOrNull(pos)
                val drags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return if (item is TaskListItem.TaskItem) makeMovementFlags(drags, 0) else 0
            }

        })

        dragHelper.attachToRecyclerView(binding.taskRV)
    }

}