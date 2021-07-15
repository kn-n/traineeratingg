package com.example.traineeratingg

class Team {
    var name:String = ""
    var theme:String = ""
    var task:String = ""
    var date:String = ""
    var users:String = ""

    constructor()

    constructor(name: String, theme: String, task: String, date: String, users: String) {
        this.name = name
        this.theme = theme
        this.task = task
        this.date = date
        this.users = users
    }
}