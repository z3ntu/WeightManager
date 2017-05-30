package xyz.z3ntu.weightmanager.rest

import java.util.*

/**
 * Written by Luca Weiss (z3ntu)
 * https://github.com/z3ntu
 */
data class WeightData(
        val version: Int = 0,
        val id: Int = 0,
        val new: Boolean = false,
        val weight: Int = 0,
        val date: Date = Calendar.getInstance().time
)