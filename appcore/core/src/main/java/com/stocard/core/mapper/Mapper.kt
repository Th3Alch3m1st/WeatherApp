package com.stocard.core.mapper

/**
 * Created By Rafiqul Hasan
 */
interface Mapper<IN, OUT> {
    fun map(input: IN): OUT
}