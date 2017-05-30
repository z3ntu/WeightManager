package xyz.z3ntu.weightmanager.rest

import java.util.*

/**
 * Written by Luca Weiss (z3ntu)
 * https://github.com/z3ntu
 */
data class WeightDataSend(
        val weight: Int = 0,
        val date: Date = Calendar.getInstance().time
)