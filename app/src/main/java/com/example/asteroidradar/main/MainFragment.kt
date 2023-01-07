package com.example.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentMainBinding
import com.example.asteroidradar.recyclerview.AsteroidsAdapter
import com.squareup.picasso.Picasso


class MainFragment : Fragment() {

    private lateinit var binding:FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel

    //private lateinit var recyclerviewAdapter: RecyclerviewAdapter
    private lateinit var adapter: AsteroidsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        mainViewModel =ViewModelProvider(this)[MainViewModel::class.java]


        mainViewModel.getDayImage()
        mainViewModel.dayImage.observe(viewLifecycleOwner, Observer { dayImage ->
            Picasso.get().load(dayImage.url).into(binding.dayImage)
            binding.imageModel = dayImage
        })

        val layoutManger = LinearLayoutManager(context)
        binding.RV.layoutManager = layoutManger
        adapter = AsteroidsAdapter()
        binding.RV.adapter = adapter
        //binding.RV.setHasFixedSize(true)

       getAllData()
        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.show_week_menu -> getWeekData()
            R.id.show_today_menu -> getTodayData()
            R.id.show_saved_menu -> getAllData()
        }

        return true
    }

    private fun itemClicked() {
        adapter.onItemClick = { clickedAsteroid ->
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(clickedAsteroid))
        }
    }

    private fun getAllData(){
        mainViewModel.asteroids?.observe(viewLifecycleOwner, Observer { asteroids ->
            adapter.submitList(asteroids)
            itemClicked()
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTodayData() {
        mainViewModel.getTodayData()?.observe(this, Observer { todayList ->
            adapter.submitList(todayList)
            itemClicked()

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getWeekData(){
        mainViewModel.getWeekData()?.observe(this, Observer { weekList ->
            adapter.submitList(weekList)
            itemClicked()
        })
    }


}