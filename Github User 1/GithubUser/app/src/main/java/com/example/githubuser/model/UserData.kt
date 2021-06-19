package com.example.githubuser.model

import com.example.githubuser.R

object UserData {
    fun generateDataUser(): List<User> {

        val githubUser = ArrayList<User>()

        githubUser.add(
            User(
                "Jake Wharton",
                "Pittsburgh, PA, USA",
                "Google, Inc.",
                102,
                56995,
                12,
                R.drawable.ic_user1

            )
        )
        githubUser.add(
            User(
                "Amit Shekhar",
                "New Delhi, India",
                "MindOrksOpenSource",
                37,
                5153,
                2,
                R.drawable.ic_user2


            )
        )
        githubUser.add(
            User(
                "Romain Guy",
                "California",
                "Google",
                9,
                7972,
                0,
                R.drawable.ic_user3
            )
        )
        githubUser.add(
            User(
                "Chris Banes",
                "Sydney, Australia",
                "Google working on @android",
                30,
                14725,
                1,
                R.drawable.ic_user4

            )

        )
        githubUser.add(
            User(
                "David",
                "Trondheim, Norway",
                "Working Group Two",
                56,
                788,
                0,
                R.drawable.ic_user5

            )
        )
        githubUser.add(
            User(
                "Ravi Tamada",
                "India",
                "AndroidHive | Droid5",
                28,
                18628,
                3,
                R.drawable.ic_user6

            )
        )
        githubUser.add(
            User(
                "Deny Prasetyo",
                "Kotagede, Yogyakarta, Indonesia",
                "gojek-engineering",
                44,
                277,
                39,
                R.drawable.ic_user7
            )
        )
        githubUser.add(
            User(
                "Budi Oktaviyan",
                "Jakarta, Indonesia",
                "KotlinID",
                110,
                178,
                23,
                R.drawable.ic_user8

            )
        )
        githubUser.add(
            User(
                "Hendi Santika",
                "Bojongsoang - Bandung Jawa Barat",
                "JVMDeveloperID @KotlinID @IDDevOps",
                1064,
                428,
                61,
                R.drawable.ic_user9
            )
        )
        githubUser.add(
            User(
                "Sidiq Permana",
                "Jakarta Indonesia",
                "Nusantara Beta Studio",
                65,
                465,
                10,
                R.drawable.ic_user10

            )
        )

        return githubUser
    }
}