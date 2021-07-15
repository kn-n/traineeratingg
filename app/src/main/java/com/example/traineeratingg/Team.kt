package com.example.traineeratingg

class Team {
    var name:String = ""
    var theme:String = ""
    var date:String = ""

    constructor()

    constructor(name: String, theme: String, date: String) {
        this.name = name
        this.theme = theme
        this.date = date
    }
}