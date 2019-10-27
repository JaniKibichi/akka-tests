package com.janikibichi.util

import com.typesafe.config.ConfigFactory

object DonutConfig{
  private val config = ConfigFactory.load()

  object http{
    val host:String=config.getString("http.host")
    val port:Int = config.getInt("http.port")
  }
}