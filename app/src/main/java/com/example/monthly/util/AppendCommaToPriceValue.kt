package com.example.monthly.util

import android.util.Log

fun AppendCommaToPriceValue(resultInt: Int): String? {
    val sb = StringBuilder()
    val resultStr = Integer.toString(resultInt)
    val length = resultStr.length
    // 7654321  ->  7,654,321

    var start = 0
    if(resultStr[0] == '-') start = 1

    for (i in start until length) {
        sb.append(resultStr[i])
        if ((length - (i + 1)) % 3 == 0 && i != length - 1) sb.append(",")
    }
    Log.e("sb = ", sb.toString())
    var answer = sb.toString()
    if(start == 1) {
        answer = "-$answer"
    }
    return answer
}