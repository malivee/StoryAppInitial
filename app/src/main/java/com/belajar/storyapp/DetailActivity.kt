package com.belajar.storyapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.storyapp.databinding.ActivityDetailBinding
import com.belajar.storyapp.helper.Result
import com.belajar.storyapp.helper.ViewModelFactory
import com.belajar.storyapp.helper.toDateFormat
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.apply {
            title = null
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_new_24)
//            setBackgroundDrawable(ColorDrawable(getColor(R.color.dark_blue)))
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        }
        val viewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: DetailViewModel by viewModels { viewModelFactory }

        val id = intent.getStringExtra(EXTRA_ID)
        Log.d("IDDETAIL", id.toString())

        if (id != null) {
            viewModel.getDetailStory(id).observe(this) {
                if (it != null) {
                    when (it) {
                        is Result.Failure -> Toast.makeText(this@DetailActivity, "Failed", Toast.LENGTH_SHORT).show()
                        Result.Loading -> {}
                        is Result.Success -> {
                            binding.apply {

                            }
                            Glide.with(this)
                                .load(it.data.story?.photoUrl)
                                .into(binding.imgDetailPhoto)
                            binding.apply {
                                itemDetailName.text = it.data.story?.name
                                itemDetailDesc.text = it.data.story?.description
                                itemDetailDate.text = it.data.story?.createdAt?.toDateFormat()


                            }


                        }
                    }
                }
            }
        }


    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}