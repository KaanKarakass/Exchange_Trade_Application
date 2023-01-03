package com.kaankarakas.exchangetrade
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import kotlin.math.roundToInt

class ExchangeViewModel:ViewModel()  {
    private var usdRate = (Math.random())*(14.80-14.70)+14.70
    private var eurRate = (Math.random())*(16.10-16.00)+16.00

    private val _usdValue = MutableLiveData<Double>(usdRate)
    val usdValue:LiveData<Double>
        get() = _usdValue

    private val _eurValue = MutableLiveData<Double>(eurRate)
    val eurValue:LiveData<Double>
        get() = _eurValue

    init {
        update_rates()
    }
    fun update_rates()
    {
        usdRate =(Math.random())*(14.80-14.70)+14.70
        eurRate = (Math.random())*(16.10-16.00)+16.00

        _usdValue.value = ((usdRate * 100.0).roundToInt() /100.0)
        _eurValue.value = ((eurRate * 100.0).roundToInt() /100.0)

    }
}