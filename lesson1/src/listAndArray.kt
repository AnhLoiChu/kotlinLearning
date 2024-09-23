fun main(){
    val instruments = listOf("trumpet","piano","violin")
    println(instruments)
    println(instruments[1])

    val myList = mutableListOf("trumpet","piano","violin")
    myList.remove("violin")
    println(myList)

    val pets= arrayOf("dog","cat","canary")
    println(java.util.Arrays.toString(pets))
    println(pets)
    pets[0]= "dugs"

    var n: Int?= 6
    n = null
    println(n)

    var s: String = "hell"
    val len= s!!.length
    println(len)
}