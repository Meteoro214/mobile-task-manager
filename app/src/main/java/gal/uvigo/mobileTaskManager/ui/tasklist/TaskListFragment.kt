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

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val viewModel: TaskViewModel by activityViewModels()
    private lateinit var binding: FragmentTaskListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskListBinding.bind(view)
        binding.taskRV.layoutManager = LinearLayoutManager(context)
        binding.taskRV.adapter = TaskAdapter { task ->
            findNavController()
                .navigate(TaskListFragmentDirections.checkTaskDetails(task.id))
        }

        viewModel.taskListItems.observe(viewLifecycleOwner) { taskList ->
            (binding.taskRV.adapter as TaskAdapter).submitList(taskList)
        }

        setupSwipe()
        setupDrag()

        setHasOptionsMenu(true)
    }

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
                if (item is TaskListItem.TaskItem) {
                    when (direction) {
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
                            }
                        }

                        ItemTouchHelper.RIGHT -> {
                            val updated = viewModel.markTaskDone(item.task.id)
                            //Used so adapter recharges view & removes the swipe background
                            binding.taskRV.adapter?.notifyItemChanged(pos)
                            if (updated == true) {
                                Toast.makeText(
                                    requireContext(), getString(R.string.swipe_mark_OK_msg),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (updated == false) {
                                Toast.makeText(
                                    requireContext(), getString(R.string.swipe_mark_wasDone_msg),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {//Should never happen
                                Toast.makeText(
                                    requireContext(), getString(R.string.swipe_mark_error_msg),
                                    Toast.LENGTH_SHORT
                                ).show()
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

                val moved = viewModel.move(from,to)
                return moved
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.elevation = 0F
                viewHolder.itemView.alpha = 1F
            }

            override fun onSelectedChanged(
                viewHolder: RecyclerView.ViewHolder?,
                actionState: Int
            ) {
                viewHolder?.itemView?.elevation = 16F
                viewHolder?.itemView?.alpha = 0.9F
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

            override fun onChildDraw( //Change
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

        dragHelper.attachToRecyclerView(binding.taskRV)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}