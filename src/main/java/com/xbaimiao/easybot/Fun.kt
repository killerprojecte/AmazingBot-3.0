package com.xbaimiao.easybot

import java.sql.ResultSet

fun <T> ResultSet.use(func: ResultSet.() -> T): T {
    return func(this)
}