package com.github.tarcv.orderme.app

import com.github.tarcv.orderme.core.data.entity.Place

class PlaceDiffCallback(
    current: List<Place>,
    next: List<Place>
) : ListItemDiffCallback<Place>(current, next) {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            current[oldItemPosition].id == next[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            current[oldItemPosition] == next[newItemPosition]
}