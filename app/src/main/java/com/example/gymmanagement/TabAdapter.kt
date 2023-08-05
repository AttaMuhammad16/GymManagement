package com.example.gymmanagement

import allMembers
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.gymmanagement.fragments.Attendance

internal class TabAdapter(var context: Context, fm: FragmentManager, var totalTabs:Int):
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                allMembers()
            }
            1 -> {
                Attendance()
            }
            else -> getItem(position)
        }
    }
}