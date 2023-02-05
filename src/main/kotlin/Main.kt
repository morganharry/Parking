class Seat (var number:Int = 0, var reg:String = "empty", var color:String = "empty") {
    fun printSeat () {
        println ("${this.number} ${this.reg} ${this.color}")
    }

    fun parkSeat (reg:String, color:String) {
        this.reg = reg
        this.color = color
        println ("${this.color} car parked in spot ${this.number}.")
    }

    fun leaveSeat () {
        this.reg = "empty"
        this.color = "empty"
        println ("Spot ${this.number} is free.")
    }

    fun findSeat (a:String, b:String):Boolean {
        var result = false
        when (a) {
            "reg" -> if (this.reg.uppercase() == b.uppercase()) result = true
            "color" -> if (this.color.uppercase() == b.uppercase()) result = true
            }
        return result
    }
}

fun start(parking: MutableList<Seat>): MutableList<String> {
    var comand = mutableListOf<String>()
    val listOfStatus = listOf ("create", "status", "park", "leave", "reg", "spot", "exit")
    if (parking.isEmpty()) {
        do {
            //println ("What do you want: create, status, park, leave, reg, spot, exit?")
            comand = readln().split(" ", "_").toMutableList()
            if (comand.first() != "create" && comand.first() != "exit") println ("Sorry, a parking lot has not been created.")
        } while (comand.first() != "create" && comand.first() != "exit")
    }
    else {
        do {
            //println ("What do you want: create, status, park, leave, reg, spot, exit?")
            comand = readln().split(" ", "_").toMutableList()
        } while (comand.first() !in listOfStatus)
    }
    return comand
}

fun create (num:Int): MutableList<Seat> {
    var parking = mutableListOf<Seat>()
    for (i in 1 .. num) {
        var seat  = Seat (i)
        parking.add (seat)
    }
    println ("Created a parking lot with ${parking.size} spots.")
    return parking
}

fun status(parking: MutableList<Seat>) {
    val listOfFound = findSeat(parking,"reg","empty")
    if (listOfFound.size == parking.size) {
        println ("Parking lot is empty.")
    }
    else {
        for (i in parking.indices) {
            if (!parking[i].findSeat("reg","empty")) {
                parking[i].printSeat()
            }
        }
    }
}

fun findSeat (parking: MutableList<Seat>, a: String, b: String): MutableList<Seat> {
    var listOfFound = mutableListOf<Seat>()
    for (i in parking.indices) {
        if (parking[i].findSeat(a,b))
            listOfFound.add (parking[i])
    }
    return listOfFound
}

fun park(comand: MutableList<String>, parking: MutableList<Seat> ): MutableList<Seat> {
    val listOfFound = findSeat(parking,"reg","empty")
    if (listOfFound.isEmpty()) {
        println ("Sorry, the parking lot is full.")
    }
    else {
        var seat = 0
        while (!parking[seat].findSeat("reg","empty")) {
            seat ++
        }
        parking[seat].parkSeat(comand [1],comand [2])
    }
    return parking
}

fun leave(comand: MutableList<String>, parking: MutableList<Seat> ): MutableList<Seat> {
    parking [comand [1].toInt() - 1].leaveSeat()
    return parking
}

fun reg (comand: MutableList<String>, parking: MutableList<Seat> ) {
    val foundSeat = findSeat(parking,comand [2],comand [3])
    if (foundSeat.isEmpty()) {
        println ("No cars with color ${comand [3]} were found.")
    }
    else {
        val listOfColor= mutableListOf<String>()
        for (i in foundSeat.indices) {
            listOfColor.add(foundSeat[i].reg)
        }
        println (listOfColor.joinToString(", "))
    }
}

fun spot (comand: MutableList<String>, parking: MutableList<Seat> ) {
    val foundSeat = findSeat(parking,comand[2],comand [3])
    val listOfFound= mutableListOf<String>()
    for (i in foundSeat.indices) {
        listOfFound.add(foundSeat[i].number.toString())
    }
    if (listOfFound.isNotEmpty()) println (listOfFound.joinToString(", "))
    else {
        var type = "color"
        if (comand [2] == "reg") type = "registration number"
            println ("No cars with ${type} ${comand [3]} were found.")
    }
}

fun exit() {
    //println ("Goodbye")
}

fun main() {
    var statusMenu = mutableListOf<String>()
    var parking = mutableListOf<Seat>()
    do {
        statusMenu =start(parking)
        when (statusMenu.first()) {
            "create" -> parking = create (statusMenu[1].toInt())
            "status" -> status (parking)
            "park" -> parking = park (statusMenu, parking)
            "leave" -> parking = leave (statusMenu, parking)
            "reg" ->  reg (statusMenu, parking)
            "spot" -> spot (statusMenu, parking)
            "exit" ->  exit ()
        }
    } while (statusMenu.first() != "exit")
}