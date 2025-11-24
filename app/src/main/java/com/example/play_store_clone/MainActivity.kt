package com.example.play_store_clone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                setupWindowInsets()
                setupRecyclerView()
        }

        private fun setupWindowInsets() {
                val header =
                        findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.header)

                ViewCompat.setOnApplyWindowInsetsListener(header) { view, insets ->
                        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                        view.setPadding(
                                view.paddingLeft,
                                systemBars.top + view.paddingTop,
                                view.paddingRight,
                                view.paddingBottom
                        )
                        insets
                }
        }

        private fun setupRecyclerView() {
                val recyclerView: RecyclerView = findViewById(R.id.mainRecyclerView)
                val items = mutableListOf<Any>()

                // Section 1: Suggested for you (Vertical List)
                items.add("Sponsored • Suggested for you")
                items.add(
                        AppItem(
                                1,
                                "Mech Assemble: Zombie Swarm",
                                "Action • Role Playing • Roguelike",
                                4.8,
                                "624 MB",
                                "",
                                0
                        )
                )
                items.add(AppItem(2, "MU: Hong Hoa Dao", "Role Playing", 4.8, "339 MB", "", 0))
                items.add(
                        AppItem(
                                3,
                                "War Inc: Rising",
                                "Strategy • Tower defense",
                                4.9,
                                "231 MB",
                                "",
                                0
                        )
                )

                // Section 2: Recommended for you (Horizontal List)
                items.add("Recommended for you")
                val recommendedApps =
                        listOf(
                                AppItem(4, "Suno - AI Music", "Music & Audio", 4.5, "50 MB", "", 1),
                                AppItem(
                                        5,
                                        "Claude by Anthropic",
                                        "Productivity",
                                        4.7,
                                        "30 MB",
                                        "",
                                        1
                                ),
                                AppItem(6, "DramaBox", "Entertainment", 4.3, "80 MB", "", 1),
                                AppItem(7, "Pinterest", "Lifestyle", 4.6, "45 MB", "", 1)
                        )
                items.add(HorizontalSectionWrapper(recommendedApps))

                // Section 3: New & updated games (Horizontal List)
                items.add("New & updated games")
                val newGames =
                        listOf(
                                AppItem(8, "Genshin Impact", "Adventure", 4.5, "500 MB", "", 1),
                                AppItem(
                                        9,
                                        "Honkai: Star Rail",
                                        "Role Playing",
                                        4.7,
                                        "400 MB",
                                        "",
                                        1
                                ),
                                AppItem(10, "PUBG Mobile", "Action", 4.2, "900 MB", "", 1)
                        )
                items.add(HorizontalSectionWrapper(newGames))

                // Section 4: More Vertical Apps
                items.add("Based on your recent activity")
                items.add(AppItem(11, "Duolingo", "Education", 4.8, "60 MB", "", 0))
                items.add(AppItem(12, "LinkedIn", "Business", 4.4, "120 MB", "", 0))

                val adapter = MainAdapter(items)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter
        }
}
