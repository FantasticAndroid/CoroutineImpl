package com.co.routine.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class Util {

    companion object {
        fun convertInputStreamToString(inputStream: InputStream): String? {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val sb = StringBuilder()
            var line: String?
            try {
                while (bufferedReader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return sb.toString()
        }
    }
}