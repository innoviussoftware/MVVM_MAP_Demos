package com.example.mvvm_map_demos

import android.app.Application
import com.maeiapp.data.repository.*
import com.maeiapp.network.MyApi
import com.maeiapp.network.NetworkConnectionInterceptor
import com.maeiapp.ui.bankdetails.BankDetailsViewModelFactory
import com.maeiapp.ui.calendar.TaskDetailsViewModelFactory
import com.maeiapp.ui.calendartask.AddEditTaskViewModelFactory
import com.maeiapp.ui.chagedpw.ChangedPwViewModelFactory
import com.maeiapp.ui.chat.ChatViewModelFactory
import com.maeiapp.ui.editprofile.EditProfileViewModelFactory
import com.maeiapp.ui.expertList.ExpertsListViewModelFactory
import com.maeiapp.ui.expertsDetails.ExpertsViewModelFactory
import com.maeiapp.ui.forgotpassword.ForgotPwViewModelFactory
import com.maeiapp.ui.home.HomeViewModelFactory
import com.maeiapp.ui.login.LoginViewModelFactory
import com.maeiapp.ui.notification.NotificationViewModelFactory
import com.maeiapp.ui.paymentdetails.PaymentDetailsViewModelFactory
import com.maeiapp.ui.profile.UserDetailsViewModelFactory
import com.maeiapp.ui.ratingbar.RatingViewModel
import com.maeiapp.ui.ratingbar.RatingViewModelFactory
import com.maeiapp.ui.requestdata.RequestViewModelFactory
import com.maeiapp.ui.signup.SignUpViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {

        import(androidXModule(this@MyApplication))

        //Comman used
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }

        //Login screen Injection
        bind() from singleton { LoginRepository(instance()) }
        bind() from provider { LoginViewModelFactory(instance()) }

        //SignUp Screen Injection
        bind() from singleton { SignUpRepository(instance()) }
        bind() from provider { SignUpViewModelFactory(instance()) }

        //Home Screen Injection
        bind() from singleton { HomeRepository(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }

        bind() from singleton { ExpertsListRepository(instance()) }
        bind() from provider { ExpertsListViewModelFactory(instance()) }

        //Experts details
        bind() from provider { ExpertsViewModelFactory(instance()) }

        bind() from singleton { UserDetailsRepository(instance()) }
        bind() from provider { UserDetailsViewModelFactory(instance()) }

        //Edit profile Injection
        bind() from singleton { EditProfileRepository(instance()) }
        bind() from provider { EditProfileViewModelFactory(instance()) }

        bind() from singleton { RequestDataRepository(instance()) }
        bind() from provider { RequestViewModelFactory(instance()) }

        bind() from singleton { TaskDetailsRepository(instance()) }
        bind() from provider { TaskDetailsViewModelFactory(instance()) }

        //Forgot Password Screen Injection
        bind() from singleton { ForgotPwRepository(instance()) }
        bind() from provider { ForgotPwViewModelFactory(instance()) }

        //Changed Password
        bind() from singleton { ChangedPwRepository(instance()) }
        bind() from provider { ChangedPwViewModelFactory(instance()) }

        bind() from singleton { RatingRepository(instance()) }
        bind() from provider { RatingViewModelFactory(instance()) }

        bind() from singleton { ChatRepository(instance()) }
        bind() from provider { ChatViewModelFactory(instance()) }

        bind() from provider { AddEditTaskViewModelFactory(instance()) }

         bind() from singleton { NotificationRepository(instance()) }
         bind() from provider { NotificationViewModelFactory(instance()) }

        bind() from singleton { BankDetailsRepository(instance()) }
        bind() from provider { BankDetailsViewModelFactory(instance()) }

        bind() from singleton { PaymentDetailsRepository(instance()) }
        bind() from provider { PaymentDetailsViewModelFactory(instance()) }

    }

}