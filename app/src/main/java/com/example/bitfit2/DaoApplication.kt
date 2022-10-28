//  UserViewModel in video part "create view model"


package com.example.bitfit2

import android.app.Application


class DaoApplication: Application() {
    val db by lazy { DataBaseUser.getInstance(this) }

}