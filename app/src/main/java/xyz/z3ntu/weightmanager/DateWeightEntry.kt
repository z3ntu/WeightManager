package xyz.z3ntu.weightmanager

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Written by Luca Weiss (z3ntu)
 * https://github.com/z3ntu
 */
class DateWeightEntry(date: Date, val weight: Int) {

    internal var df: DateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.GERMAN)

    val date: String

    init {
        this.date = df.format(date)
    }

    override fun toString(): String {
        return date + " - " + weight
    }
}