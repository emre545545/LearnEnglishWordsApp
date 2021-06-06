package com.sefir.learnenglishwords.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


class PrivateSharedPreferences {

    companion object {

        private val MODE = "mode";
        private val POINT = "point"
        private val COUNT = "count"
        private var sharedPreferences: SharedPreferences? = null


        @Volatile
        private var instance: PrivateSharedPreferences? = null
        private val lock = Any()
        operator fun invoke(context: Context): PrivateSharedPreferences =
            instance ?: synchronized(lock) {
                instance ?: makePrivateSharedPreferences(context).also {
                    instance = it
                }
            }

        private fun makePrivateSharedPreferences(context: Context): PrivateSharedPreferences {
            sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPreferences()
        }
    }

    fun saveMode(mode: Int) {

        sharedPreferences?.edit {
            this.putInt(MODE, mode)
            this.commit()
        }

    }

    fun getMode() = sharedPreferences?.getInt(MODE, 0)

    fun savePoint(point: Int) {

        sharedPreferences?.edit {
            this.putInt(POINT, point)
            this.commit()
        }

    }

    fun getPoint() = sharedPreferences?.getInt(POINT, 0)

    fun saveCount(count: Int) {

        sharedPreferences?.edit {
            this.putInt(COUNT, count)
            this.commit()
        }

    }

    fun getCount() = sharedPreferences?.getInt(COUNT, 0)


}