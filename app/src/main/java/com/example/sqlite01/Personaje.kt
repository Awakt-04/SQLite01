package com.example.sqlite01

class Personaje(
    private var nombre: String,
    private var pesoMochila: Int,
    private var estadoVital: String,
    private var raza: String,
    private var clase: String,

    ) {
    fun getNombre():String{
        return nombre
    }
    fun setNombre(nombre:String){
        this.nombre=nombre
    }
    fun getEstadoVital():String{
        return estadoVital
    }
    fun setEstadoVital(estadoVital:String){
        this.estadoVital=estadoVital
    }
    fun getRaza():String{
        return raza
    }
    fun setRaza(raza:String){
        this.raza=raza
    }
    fun getClase():String{
        return clase
    }
    fun setClase(clase:String){
        this.clase=clase
    }
    fun getPesoMochila(): Int {
        return pesoMochila
    }
}