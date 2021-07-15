package com.example.traineeratingg

class User {
    var login:String = ""
    var password:String = ""
    var role:String = ""
    var middleRating:String = ""
    var team:String = ""
    var imgProfile:String = ""
    var name:String = ""
    var placeOfStudy:String = ""
    var specialty:String = ""
    var skills:String = ""

    constructor()

    constructor(login: String, password: String, role: String, middleRating: String, team: String, imgProfile: String,
                name: String, placeOfStudy: String, specialty: String, skills: String) {
        this.login = login
        this.password = password
        this.role = role
        this.middleRating = middleRating
        this.team = team
        this.imgProfile = imgProfile
        this.name = name
        this.placeOfStudy = placeOfStudy
        this.specialty = specialty
        this.skills = skills
    }
}