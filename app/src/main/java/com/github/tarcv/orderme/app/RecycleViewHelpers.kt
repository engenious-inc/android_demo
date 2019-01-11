package com.github.tarcv.orderme.app

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import java.util.Collections

fun <T, Holder : UpdatableListHolder<T>>
        Observable<List<T>>.wireToAdapter(
            adapter: UpdatableListAdapter<T, Holder>,
            diffCallbackFactory: BiFunction<List<T>, List<T>, DiffUtil.Callback>
        ): Disposable {
    val initialDiffResult = DiffUtil.calculateDiff(diffCallbackFactory.apply(listOf(), listOf()))
    val initialUpdate = RecyclerViewUpdate<T>(listOf(), listOf(), initialDiffResult)
    adapter.reset() // make sure current adapter state corresponds to empty list
    return this
            .scan(initialUpdate, { update, next ->
                val diff = DiffUtil.calculateDiff(diffCallbackFactory.apply(update.listNow, next))
                RecyclerViewUpdate<T>(update.listNow, next, diff)
            })
            .skip(1) // skip initial state
            .subscribe(adapter::updateItems)
}

abstract class ListItemDiffCallback<T>(
    val current: List<T>,
    val next: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = current.size

    override fun getNewListSize(): Int = next.size
}

data class RecyclerViewUpdate<T>(
    val listBeforeUpdate: List<T>,
    val listNow: List<T>,
    val difference: DiffUtil.DiffResult
)

abstract class UpdatableListAdapter<T, Holder : UpdatableListHolder<T>>
    : RecyclerView.Adapter<Holder>() {
    private val list: MutableList<T> = Collections.synchronizedList(ArrayList())

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder

    override fun getItemCount(): Int = list.size

    fun getBoundItemAt(position: Int) = list[position]

    fun updateItems(update: RecyclerViewUpdate<T>) {
        // TODO: make first update not animated
        if (list != update.listBeforeUpdate) { // TODO: enable only for Debug build
            throw IllegalStateException("UpdatableListAdapter is corrupted")
        }
        synchronized(list) {
            list.clear()
            list.addAll(update.listNow)
        }
        update.difference.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val restaurant = getBoundItemAt(position)
        holder.bind(restaurant)
    }

    internal fun reset() {
        list.clear()
        this.notifyDataSetChanged()
    }
}

abstract class UpdatableListHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}
