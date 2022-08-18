package com.storyous.hwbridge_terminal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val ACTION = "com.storyous.hwbridge_terminal.payTransaction"

@Parcelize
data class Transaction(
    val transactionId: String
) : Parcelable
