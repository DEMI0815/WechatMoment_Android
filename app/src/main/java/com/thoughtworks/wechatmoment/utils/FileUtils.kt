package com.thoughtworks.wechatmoment.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class FileUtils {
    fun storageFile(fileName: String, jsonString: String?, context: Context) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            if (jsonString != null) {
                it.write(jsonString.toByteArray())
            }
        }
    }

    fun readerFile(fileName: String, context: Context): String {
        var result = ""
        BufferedReader(InputStreamReader(context.openFileInput(fileName))).use {
            var line: String
            while (true) {
                line = it.readLine() ?: break //当有内容时读取一行数据，否则退出循环
                result += line
            }
        }
        return result
    }
}