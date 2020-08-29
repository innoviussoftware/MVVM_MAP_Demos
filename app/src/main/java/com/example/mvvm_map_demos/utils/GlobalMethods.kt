package com.example.mvvm_map_demos.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.widget.Toast
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class GlobalMethods {

    //Todo: Create Global Method as per using all project
    companion object {
        fun showToast(context: Context?, message: String) {
            val biggerText = SpannableStringBuilder(message)
            biggerText.setSpan(RelativeSizeSpan(1.35f), 0, message.length, 0)
            Toast.makeText(context, biggerText, Toast.LENGTH_LONG).show()
        }

        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager
            connectivityManager.activeNetworkInfo.also {
                return it != null && it.isConnected
            }
        }

        fun isEmailValid(email: String): Boolean {
            return Pattern.compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-].+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            ).matcher(email).matches()

          /*  return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
            ).matcher(email).matches()*/
        }

        fun convertDate(sendDate:String): String{
            var format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val newsDate = sendDate
            var formatedDate:String?=""
            if (newsDate != null) {
                val newDate = format.parse(newsDate)
                format = SimpleDateFormat("MMM dd, yyyy")
                 formatedDate = format.format(newDate)

            }

            return formatedDate!!
        }

        fun convertOnlySendDate(sendDate:String): String{
            var format = SimpleDateFormat("yyyy-MM-dd")
            val newsDate = sendDate
            var formatedDate:String?=""
            if (newsDate != null) {
                val newDate = format.parse(newsDate)
                format = SimpleDateFormat("MMM dd, yyyy")
                formatedDate = format.format(newDate)

            }

            return formatedDate!!
        }

        fun convertTime(sendTime:String): String{
            var format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val newsDate = sendTime
            var formatedDate:String?=""
            if (newsDate != null) {
                val newDate = format.parse(newsDate)
                format = SimpleDateFormat("hh:mm aa")
                formatedDate = format.format(newDate)

            }

            return formatedDate!!
        }

        @Throws(ParseException::class)
        fun formatToYesterdayOrToday(date: String): String {
            val dateTime = SimpleDateFormat("EEE hh:mma MMM d, yyyy").parse(date)
            val calendar = Calendar.getInstance()
            calendar.setTime(dateTime)
            val today = Calendar.getInstance()
            val yesterday = Calendar.getInstance()
            yesterday.add(Calendar.DATE, -1)
            val timeFormatter = SimpleDateFormat("hh:mma")

            return if (calendar.get(Calendar.YEAR) === today.get(Calendar.YEAR)
                && calendar.get(Calendar.DAY_OF_YEAR) === today.get(Calendar.DAY_OF_YEAR)) {
                "Today " + timeFormatter.format(dateTime)
            } else if (calendar.get(Calendar.YEAR) === yesterday.get(Calendar.YEAR)
                && calendar.get(Calendar.DAY_OF_YEAR) === yesterday.get(Calendar.DAY_OF_YEAR)) {
                "Yesterday " + timeFormatter.format(dateTime)
            } else {
                date
            }
        }

        fun getDisplayableTimeUsingDate(selectedDate: String): String? {

            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val date = formatter.parse(selectedDate) as Date
            System.out.println("Today is " + date.time)

            var timestamp:Long=date.time

            var difference: Long = 0
            val mDate = System.currentTimeMillis()
            if (mDate > timestamp) {
                difference = mDate - timestamp
                val seconds = difference / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24
                val months = days / 31
                val years = days / 365
                return if (seconds < 0) {
                    "not yet"
                } else if (seconds < 60) {
                    if (seconds == 1L) "one second ago" else "$seconds seconds ago"
                } else if (seconds < 120) {
                    // "a minute ago"
                    "$minutes minutes ago"
                } else if (seconds < 2700) // 45 * 60
                {
                    "$minutes minutes ago"
                } else if (seconds < 5400) // 90 * 60
                {
                    // "an hour ago"
                    "$hours hours ago"
                } else if (seconds < 86400) // 24 * 60 * 60
                {
                    "$hours hours ago"
                } else if (seconds < 172800) // 48 * 60 * 60
                {
                    "yesterday"
                } else if (seconds < 2592000) // 30 * 24 * 60 * 60
                {
                    "$days days ago"
                } else if (seconds < 31104000) // 12 * 30 * 24 * 60 * 60
                {
                    if (months <= 1) "one month ago" else "$days months ago"
                } else {
                    if (years <= 1) "one year ago" else "$years years ago"
                }
            }
            return null
        }

        fun getDisplayableTime(timestamp: Long): String? {

            var difference: Long = 0
            val mDate = System.currentTimeMillis()
            if (mDate > timestamp) {
                difference = mDate - timestamp
                val seconds = difference / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24
                val months = days / 31
                val years = days / 365
                return if (seconds < 0) {
                    "not yet"
                } else if (seconds < 60) {
                    if (seconds == 1L) "one second ago" else "$seconds seconds ago"
                } else if (seconds < 120) {
                   // "a minute ago"
                    "$minutes minutes ago"
                } else if (seconds < 2700) // 45 * 60
                {
                    "$minutes minutes ago"
                } else if (seconds < 5400) // 90 * 60
                {
                   // "an hour ago"
                    "$hours hours ago"
                } else if (seconds < 86400) // 24 * 60 * 60
                {
                    "$hours hours ago"
                } else if (seconds < 172800) // 48 * 60 * 60
                {
                    "yesterday"
                } else if (seconds < 2592000) // 30 * 24 * 60 * 60
                {
                    "$days days ago"
                } else if (seconds < 31104000) // 12 * 30 * 24 * 60 * 60
                {
                    if (months <= 1) "one month ago" else "$days months ago"
                } else {
                    if (years <= 1) "one year ago" else "$years years ago"
                }
            }
            return null
        }

        public fun getAge(dobString: String): Int {
            var date: Date? = null
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            try {
                date = sdf.parse(dobString)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            if (date == null) return 0
            val dob = Calendar.getInstance()
            val today = Calendar.getInstance()
            dob.time = date
            val year = dob[Calendar.YEAR]
            val month = dob[Calendar.MONTH]
            val day = dob[Calendar.DAY_OF_MONTH]
            dob[year, month + 1] = day
            var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
            if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
                age--
            }
            return age
        }


    }
}