package com.example.consumerapp.view.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.consumerapp.R
import com.example.consumerapp.databinding.ActivitySettingBinding
import com.example.consumerapp.utils.AlarmReceiver
import java.util.*


class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    companion object{
        const val HOURS = 9
        const val MINUTE = 0
        const val SECOND = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.tvLanguage.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        setRemainder()

    }
    private fun setRemainder(){
        val sharedPreferences = getSharedPreferences(getString(R.string.alarm_remainder), Context.MODE_PRIVATE)
        binding.switchRemainder.setOnCheckedChangeListener { _, isChecked ->

            sharedPreferences.edit {
                putBoolean(getString(R.string.notification),isChecked)
            }

            if (isChecked){
                onDailyRemainder(this)
                Toast.makeText(this,getString(R.string.alarm_on),Toast.LENGTH_SHORT).show()
            }else{
                offDailyRemainder(this)
                Toast.makeText(this,getString(R.string.alarm_off),Toast.LENGTH_SHORT).show()
            }
        }
        val check : Boolean = sharedPreferences.getBoolean(getString(R.string.notification),false)
        binding.switchRemainder.isChecked = check
    }
    private fun onDailyRemainder(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, HOURS)
        calendar.set(Calendar.MINUTE, MINUTE)
        calendar.set(Calendar.SECOND, SECOND)

        val pendingIntent = PendingIntent.getBroadcast(context,AlarmReceiver.ID_REPEATING,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)

    }
    private fun offDailyRemainder(context : Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, AlarmReceiver.ID_REPEATING,intent,0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }
}