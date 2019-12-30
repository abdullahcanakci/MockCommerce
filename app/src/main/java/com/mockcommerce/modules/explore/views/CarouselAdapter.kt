package com.mockcommerce.modules.explore.views

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mockcommerce.models.CarouselItem

class CarouselAdapter(private var items: List<CarouselItem>, fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        val  v = CarouselFragment()
        v.set(items.get(position))
        return v
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(newItems: List<CarouselItem>){
        items = newItems
        this.notifyDataSetChanged()
    }

}