package com.example.androidtask.LibraryFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.databinding.FragmentDailyQuizBinding
import com.example.androidtask.databinding.QuizListFragmentBinding
import java.time.Duration


class DailyQuizFragment : Fragment() {

    private  var binding1: FragmentDailyQuizBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_daily_quiz, container, false)
        val binding = FragmentDailyQuizBinding.bind(view)
        binding1 = binding
        binding.quizRecyclerView.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.quizRecyclerView.adapter=MyAdapter()
        return binding.root
    }
}

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.quiz_list_fragment, parent, false)
        val itemBinding = QuizListFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         holder.bind()
    }

    override fun getItemCount(): Int {
        return 6
    }

    class MyViewHolder(private val itemBinding: QuizListFragmentBinding) : RecyclerView.ViewHolder(itemBinding.root), OnClickListener {
        fun bind() {
            itemBinding.dailyQuiz.text="Daily Quiz 407"
            itemBinding.duration.text="1 Hour 30 min"
            itemBinding.miscellaneous.text="Miscellaneous"
            itemBinding.unattempted.text="Unattempted"
            itemBinding.textOver.text="Test Over"
            itemBinding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
              
        }
    }


}
