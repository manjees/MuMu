package com.manjee.firebase.database

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.manjee.firebase.FirebaseConst.RANKING_DATABASE
import com.manjee.firebase.model.RankingDataModel
import com.manjee.model.Artist
import javax.inject.Inject
import javax.inject.Named

class RankingDatabase @Inject constructor(
    @Named(RANKING_DATABASE) private val fireDb: DatabaseReference
) {

    fun initData() {
        val dataList = listOf(
            RankingDataModel(0, "BTS", 0),
            RankingDataModel(1, "SUZY", 0),
            RankingDataModel(2, "BLACKPINK", 0),
            RankingDataModel(3, "TWICE", 0),
            RankingDataModel(4, "EXO", 0),
            RankingDataModel(5, "Red Velvet", 0),
            RankingDataModel(6, "IU", 0),
            RankingDataModel(7, "NCT", 0),
            RankingDataModel(8, "Seventeen", 0),
            RankingDataModel(9, "Stray Kids", 0),
            RankingDataModel(10, "Taylor Swift", 0),
            RankingDataModel(11, "Ed Sheeran", 0),
            RankingDataModel(12, "Ariana Grande", 0),
            RankingDataModel(13, "Justin Bieber", 0),
            RankingDataModel(14, "Beyonce", 0),
            RankingDataModel(15, "Billie Eilish", 0),
            RankingDataModel(16, "Drake", 0),
            RankingDataModel(17, "Adele", 0),
            RankingDataModel(18, "Bruno Mars", 0),
            RankingDataModel(19, "Katy Perry", 0),
            RankingDataModel(20, "Rihanna", 0),
            RankingDataModel(21, "Shawn Mendes", 0),
            RankingDataModel(22, "Lady Gaga", 0),
            RankingDataModel(23, "The Weeknd", 0),
            RankingDataModel(24, "Sam Smith", 0),
            RankingDataModel(25, "Post Malone", 0),
            RankingDataModel(26, "Harry Styles", 0),
            RankingDataModel(27, "Dua Lipa", 0),
            RankingDataModel(28, "Camila Cabello", 0),
            RankingDataModel(29, "Olivia Rodrigo", 0),
            RankingDataModel(30, "Cardi B", 0),
            RankingDataModel(31, "Doja Cat", 0),
            RankingDataModel(32, "Halsey", 0),
            RankingDataModel(33, "Megan Thee Stallion", 0),
            RankingDataModel(34, "Lizzo", 0),
            RankingDataModel(35, "Sia", 0),
            RankingDataModel(36, "Maroon 5", 0),
            RankingDataModel(37, "Imagine Dragons", 0),
            RankingDataModel(38, "Coldplay", 0),
            RankingDataModel(39, "P!nk", 0),
            RankingDataModel(40, "Alicia Keys", 0),
            RankingDataModel(41, "Kelly Clarkson", 0),
            RankingDataModel(42, "John Legend", 0),
            RankingDataModel(43, "Ellie Goulding", 0),
            RankingDataModel(44, "Lana Del Rey", 0),
            RankingDataModel(45, "Miley Cyrus", 0),
            RankingDataModel(46, "Nicki Minaj", 0),
            RankingDataModel(47, "Khalid", 0),
            RankingDataModel(48, "Zayn Malik", 0),
            RankingDataModel(49, "SZA", 0),
            RankingDataModel(50, "Travis Scott", 0),
            RankingDataModel(51, "Jennifer Lopez", 0),
            RankingDataModel(52, "Jason Derulo", 0),
            RankingDataModel(53, "Pitbull", 0),
            RankingDataModel(54, "Selena Gomez", 0),
            RankingDataModel(55, "Shakira", 0),
            RankingDataModel(56, "The Chainsmokers", 0),
            RankingDataModel(57, "Charlie Puth", 0),
            RankingDataModel(58, "Marshmello", 0),
            RankingDataModel(59, "Zedd", 0),
            RankingDataModel(60, "Calvin Harris", 0),
            RankingDataModel(61, "David Guetta", 0),
            RankingDataModel(62, "Avicii", 0),
            RankingDataModel(63, "Martin Garrix", 0),
            RankingDataModel(64, "Kygo", 0),
            RankingDataModel(65, "Alan Walker", 0),
            RankingDataModel(66, "Major Lazer", 0),
            RankingDataModel(67, "Diplo", 0),
            RankingDataModel(68, "DJ Snake", 0),
            RankingDataModel(69, "Tiesto", 0),
            RankingDataModel(70, "Armin van Buuren", 0),
            RankingDataModel(71, "Paul van Dyk", 0),
            RankingDataModel(72, "Above & Beyond", 0),
            RankingDataModel(73, "Deadmau5", 0),
            RankingDataModel(74, "Skrillex", 0),
            RankingDataModel(75, "Steve Aoki", 0),
            RankingDataModel(76, "Kaskade", 0),
            RankingDataModel(77, "Tove Lo", 0),
            RankingDataModel(78, "Bebe Rexha", 0),
            RankingDataModel(79, "Hailee Steinfeld", 0),
            RankingDataModel(80, "Julia Michaels", 0),
            RankingDataModel(81, "Alessia Cara", 0),
            RankingDataModel(82, "Jessie J", 0),
            RankingDataModel(83, "Meghan Trainor", 0),
            RankingDataModel(84, "Carly Rae Jepsen", 0),
            RankingDataModel(85, "Fifth Harmony", 0),
            RankingDataModel(86, "Little Mix", 0),
            RankingDataModel(87, "One Direction", 0),
            RankingDataModel(88, "Jonas Brothers", 0),
            RankingDataModel(89, "Backstreet Boys", 0),
            RankingDataModel(90, "NSYNC", 0),
            RankingDataModel(91, "Westlife", 0),
            RankingDataModel(92, "Take That", 0),
            RankingDataModel(93, "Spice Girls", 0),
            RankingDataModel(94, "Destiny's Child", 0),
            RankingDataModel(95, "TLC", 0),
            RankingDataModel(96, "En Vogue", 0),
            RankingDataModel(97, "All Saints", 0),
            RankingDataModel(98, "Sugababes", 0),
            RankingDataModel(99, "Girls Aloud", 0),
            RankingDataModel(100, "IVE", 0),
            RankingDataModel(101, "Red Velvet", 0),
            RankingDataModel(102, "New Jeans", 0),
            RankingDataModel(103, "ITZY", 0),
            RankingDataModel(104, "G-Idle", 0),
            RankingDataModel(105, "KISS OF LIFE", 0),
            RankingDataModel(106, "NMIXX", 0)
        )

        val dataMap = dataList.map { rankingDataModel ->
            rankingDataModel.toMap()
        }

        fireDb.setValue(dataMap)
    }

    fun getArtistData(callback: (List<Artist>) -> Unit) {
        fireDb.get().addOnSuccessListener {
            val dataList = it.value as List<Map<String, Any>>?
            val artistList = dataList?.map { dataMap ->
                val id = dataMap["id"] as Long
                val name = dataMap["name"] as String
                val score = dataMap["score"] as Long

                Artist(id, name, score)
            } ?: emptyList()

            callback(artistList)
        }
    }

    fun updateData() {
        fireDb.child("1").setValue(RankingDataModel(1, "SUZY", 1).toMap())
    }
}