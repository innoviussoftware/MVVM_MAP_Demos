package com.example.mvvm_map_demos.utils

class GlobalValues {

    //Todo: Any Global Data and Values
    companion object {
        val SPLASH_TIMEOUT: Long = 3000

        //Test Mode
        const val PAYMENT_SECRET_KEY =
            "Xatqp10vEoaKhB6dV1qLBRPy5AsFIanGkR6JT7fxvpiM1oZWqqH1hxzm721QcpxxgDgaKwHgWZ2Xo2UOtCQKap6is89EyGbOjsY4"
        const val PAYMENT_MERCHANT_ACCOUNT = "robin.innovius@gmail.com"

        //Live Mode: CLient account with Join Harshal sir account
          /*const val PAYMENT_SECRET_KEY="4D3hWbcAAwhNN3ApuZXWxstNuvzLQu8V7cd3npDYVVMIYm8sm5pUySH3ohOfLXbS9oWwOAvKwituFBX1HnWfgDiFV6UG45zduwKj"
          const val PAYMENT_MERCHANT_ACCOUNT = "harshal@innoviussoftware.com"*/

        //Api call Code
        val Success_Code = 200
        val Internal_Server_Code = 500
        val Not_Data_Found_Code = 422
        val Unauthorized_Code = 401

        var date_formate = "YYYY-MM-DD" //2020-03-26


        val selectDate = ""
        val PAYMENT_REMAINING = 0
        val PAYMENT_SUCCESS = 1


        var NOTIFICATION_OPEN_SCREEN_TYPE = "openScreen"
        var NOTIFICATION_USER_REQUEST_TYPE = "user_request"
        var NOTIFICATION_EXPERT_REQUEST_TYPE = "expert_request"
        var NOTIFICATION_CHAT_TYPE = "chat_request"
        var NOTIFICATION_ADD_TASK_TYPE = "task_request"

       /* var NOTIFICATION_NEWS_TYPE = "news"
        var NOTIFICATION_EVENT_TYPE = "event"
        var NOTIFICATION_COMPLAINT_TYPE = "complaint"*/

        var NOTIFICATION_TYPE="notification_type"
        var NOTIFICATION_VALUES="notification_values"

        var setHomeTitleName: String? = "Dashboard"


        var USER_ROLE = "user"
        var USER_EXPERTS = "experts"

        var USER_ROLE_ID = 2
        var USER_EXPERTS_ID = 3

        //Request type
        var REQUEST_PENDING = "0"
        var REQUEST_ACCEPTED = "1"
        var REQUEST_REJECT = "2"
        var REQUEST_PENDING_PAYMENT = "3"
        var REQUEST_PAYMENT_SUCCESS = "4"
        var REPORT_ABUSE = "5"

        //Chat Message Type
        const val message_text = "text"
        const val message_image = "image"
        const val message_audio = "audio"
        const val message_video = "video"
        const val message_pdf = "pdf"

        const val text_type = 0
        const val image_type = 1
        const val video_type = 2
        const val audio_type = 3
        const val document_type = 4

        const val NOTIFICATION_ON = "1"
        const val NOTIFICATION_OFF = "0"

        const val ARABIC_LANG="Arabic"
        const val ENGLISH_LANG="English"

        //Related OTP
        const val OTP_RAGULAR = 1 //-RegularLogin
        const val OTP_USER_REGISTER = 2 //-UserRegister
        const val OTP_EXPERT_REGISTER = 3 //-ExpertRegister
        const val OTP_FORGOT_PW = 4 //-Forgotpassword
    }
}