package com.mockcommerce.modules.explore.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mockcommerce.models.ShowcaseItem

class ShowcaseAdapter(private var items: List<ShowcaseItem>, fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        val  v = ShowcaseFragment()
        v.set(items.get(position))
        return v
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(newItems: List<ShowcaseItem>){
        items = newItems
        this.notifyDataSetChanged()
    }

}