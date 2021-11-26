package com.tvMaze.android.data

object FileReader{
    fun readFileFromResources(fileName: String): String {
        return getInputStreamFromResource(fileName)?.bufferedReader()
            .use { bufferReader -> bufferReader?.readText() } ?: ""
    }

    private fun getInputStreamFromResource(fileName: String) =
        javaClass.classLoader?.getResourceAsStream(fileName)
}