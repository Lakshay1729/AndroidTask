package com.example.androidtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.androidtask.LibraryFragments.*
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match

class LibraryFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_library, container, false)
        tabLayout = view.findViewById(R.id.tabs) as TabLayout
        viewPager = view.findViewById(R.id.viewpager) as ViewPager2
        viewPager?.setAdapter(MyAdapter(childFragmentManager, lifecycle))


        TabLayoutMediator(tabLayout!!, viewPager!!, TabLayoutMediator.OnConfigureTabCallback { tab, position ->
//            val vie= View.inflate(context,R.layout.tabview,null)
//            val binding=TabviewBinding.bind(vie)
            when (position) {
                0 -> {
                    tab.setText("All")
//                    binding.actionChip.setText("Search Recipes")
//                    tab.setCustomView(vie)
                }
                1 -> {
                    tab.setText("Daily Quiz")
//                    vie.findViewById<Chip>(R.id.action_chip).setText("Search By Nutrition")
////                    vie.findViewById<Chip>(R.id.action_chip).setOnClickListener(View.OnClickListener {  })
//                    tab.setCustomView(vie)
                }
                2 -> {
                     tab.setText("Mock Text")
//                    vie.findViewById<Chip>(R.id.action_chip).setText("Search By Ingredients")
//                    tab.setCustomView(vie)
                }
                3 -> {
                     tab.setText("Library")
//                    vie.findViewById<Chip>(R.id.action_chip).setText("Get Recipe Nutrition")
//                    tab.setCustomView(vie)
                }
                4-> {
                    tab.setText("Time Table")
//                    vie.findViewById<Chip>(R.id.action_chip).setText("Get Recipe Nutrition")
//                    tab.setCustomView(vie)
                }
            }
            if(tab.isSelected()){
                viewPager?.currentItem=position
            }
                // this.action_chip.chipBackgroundColor= ColorStateList.createFromXml(R.color.colorstates,,null)



        }).attach()
        return view
    }

    private inner class MyAdapter(fm: FragmentManager?, lifecycle: Lifecycle) : FragmentStateAdapter(fm!!, lifecycle) {
        private val int_items = 5

        override fun getItemCount(): Int {
            return int_items
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return AllFragment()
                1 -> return DailyQuizFragment()
                2 -> return  MockTextFragment()
                3 -> return SubLibraryFragment()
                4 -> return  TimeTableFragment()
            }
            return AllFragment()
        }
    }


}