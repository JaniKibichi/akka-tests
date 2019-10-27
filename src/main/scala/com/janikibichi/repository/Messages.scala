package com.janikibichi.repository

object Messages{
  final case class AkkaHttpRestServer(app:String,version:String)
  final case class Donut(name:String, price:Double)
  final case class Donuts(donuts:Seq[Donut])
  final case class Ingredient(donutName:String,priceLevel:Double)
}