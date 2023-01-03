package com.kaankarakas.exchangetrade

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kaankarakas.exchangetrade.databinding.FragmentExchangeBinding
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

class ExchangeFragment : Fragment() {
    private var _binding:FragmentExchangeBinding? = null
    private val binding get() =_binding!!
    lateinit var viewModel: ExchangeViewModel

    private var runnable: Runnable? = null
    private var handler: Handler = Handler(Looper.myLooper()!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExchangeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel =ViewModelProvider(this).get(ExchangeViewModel::class.java)


        viewModel.usdValue.observe(viewLifecycleOwner, Observer { newValue ->
            binding.usdValueText.text ="$newValue"
        })
        viewModel.eurValue.observe(viewLifecycleOwner, Observer { newValue ->
            binding.eurValueText.text ="$newValue"
        })

        binding.usdButton.setOnClickListener {
            if (binding.amountEdit.text.isEmpty())
            {
                Snackbar.make(it,"Enter Amount",Snackbar.LENGTH_LONG).show()
            }
            else{
                val amount = binding.amountEdit.text.toString().toInt()
                val currentRate = viewModel.usdValue.value
                var result = amount * currentRate!!
                result = ((result * 100.0).roundToInt() /100.0)
                val action = ExchangeFragmentDirections
                    .actionExchangeFragmentToResultFragment("You Bought $amount USD for $result TRY.")
                view.findNavController().navigate(action)
            }
        }
        binding.eurButton.setOnClickListener {
            if (binding.amountEdit.text.isEmpty())
            {
                Snackbar.make(it,"Enter Amount",Snackbar.LENGTH_LONG).show()
            }
            else{
                val amount = binding.amountEdit.text.toString().toInt()
                val currentRate = viewModel.eurValue.value
                var result = amount * currentRate!!
                result = ((result * 100.0).roundToInt() /100.0)
                val action = ExchangeFragmentDirections
                    .actionExchangeFragmentToResultFragment("You Bought $amount Eur " +
                            "for $result TRY.")
                view.findNavController().navigate(action)
            }
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
    override fun onResume() {
        val time = 1500
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, time.toLong())
            viewModel.update_rates()
        }.also { runnable = it }, time.toLong())
        super.onResume()
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }
}