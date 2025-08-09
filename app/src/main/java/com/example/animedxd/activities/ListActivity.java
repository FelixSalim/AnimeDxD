package com.example.animedxd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animedxd.R;
import com.example.animedxd.adapters.AnimeListCardAdapter;
import com.example.animedxd.models.AnimeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton menuIcon = findViewById(R.id.dropdownMenu);
        menuIcon.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(this);
            View popupView = inflater.inflate(R.layout.logout_menu, null);
            PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setTouchable(true);
            popupWindow.setElevation(10f);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            popupWindow.setAnimationStyle(R.style.PopupAnimation);

            LinearLayout overlay = findViewById(R.id.overlay);
            overlay.setOnClickListener(vl -> popupWindow.dismiss());
            popupWindow.setOnDismissListener(() -> overlay.setVisibility(View.GONE));
            overlay.setVisibility(View.VISIBLE);

            popupWindow.showAsDropDown(menuIcon, 0, -400);

            TextView logout = popupView.findViewById(R.id.logoutBtn);
            logout.setOnClickListener(vl -> {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            });
        });

        String username = getIntent().getStringExtra("username");

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.navList);
        bottomNav.setOnItemSelectedListener(item -> {
            Intent intent;
            if (item.getItemId() == R.id.navHome) {
                intent = new Intent(this, HomeActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.navList) {
                intent = new Intent(this, ListActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.navAbout) {
                intent = new Intent(this, AboutActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                return true;
            }
            return false;
        });

        AnimeModel[] animeList = {
                new AnimeModel(
                        "Naruto",
                        "Action, Adventure, Martial Arts, Shounen",
                        "A young ninja named Naruto Uzumaki dreams of becoming the Hokage, the strongest ninja and leader of his village, while struggling to control the powerful Nine-Tailed Fox sealed inside him.",
                        R.drawable.naruto_cover),

                new AnimeModel(
                        "Solo Leveling",
                        "Action, Fantasy, Supernatural",
                        "Sung Jin-Woo, the weakest hunter, gets a second chance at life when he obtains a mysterious power that allows him to level up by himself. His journey to become the strongest begins.",
                        R.drawable.solo_leveling_cover),

                new AnimeModel(
                        "Spy x Family",
                        "Action, Comedy, Slice of Life, Spy, Family",
                        "A spy codenamed Twilight must build a fake family to carry out a secret mission—unaware that his new wife is an assassin and his adopted daughter is a telepath.",
                        R.drawable.spy_x_family_cover),

                new AnimeModel(
                        "Demon Slayer",
                        "Action, Supernatural, Historical, Drama",
                        "After his family is slaughtered by demons and his sister is turned into one, Tanjiro Kamado becomes a Demon Slayer to avenge his family and find a cure for his sister.",
                        R.drawable.demon_slayer_cover),

                new AnimeModel(
                        "My Hero Academia",
                        "Action, Superhero, School, Shounen",
                        "In a world where nearly everyone has superpowers, Izuku Midoriya, a powerless boy, inherits the power of the world’s greatest hero and strives to become a true hero himself.",
                        R.drawable.my_hero_academia_cover),

                new AnimeModel(
                        "Haikyuu!!",
                        "Sports, School, Drama, Comedy",
                        "Despite his short height, Shoyo Hinata dreams of becoming a top volleyball player. He joins Karasuno High's volleyball team and aims for the national tournament with his teammates.",
                        R.drawable.haikyuu_cover),

                new AnimeModel(
                        "Attack on Titan",
                        "Action, Post-Apocalyptic, Drama, Mystery",
                        "In a world terrorized by man-eating Titans, Eren Yeager vows to destroy them all after one of them kills his mother and breaks through humanity’s last defenses.",
                        R.drawable.attack_on_titan_cover),

                new AnimeModel(
                        "Sword Art Online",
                        "Action, Adventure, Fantasy, Sci-Fi",
                        "Kirito and friends join a new AR game called Ordinal Scale, but strange events begin when players start losing their memories. Kirito must uncover the truth and protect his friends before it’s too late.",
                        R.drawable.sword_art_online_cover_jpeg),

                new AnimeModel(
                        "Jujutsu Kaisen",
                        "Action, Supernatural, Dark Fantasy",
                        "Yuji Itadori swallows a cursed object to save his friends and ends up hosting the most powerful curse spirit. He joins a school of sorcerers to fight evil curses.",
                        R.drawable.jujutsu_kaisen_cover),

                new AnimeModel(
                        "Kuroko’s Basketball",
                        "Sports, School, Comedy",
                        "Kuroko, the \"invisible\" sixth man of a legendary basketball team, joins Seirin High and teams up with power forward Kagami to take on his former teammates and become the best in Japan.",
                        R.drawable.kuroko_basketball_cover),

                new AnimeModel(
                        "Hunter x Hunter",
                        "Action, Adventure, Fantasy, Superpower",
                        "Gon Freecss sets out on an adventure to become a Hunter and find his long-lost father. Along the way, he makes new friends and faces deadly challenges.",
                        R.drawable.hunter_x_hunter_cover),

                new AnimeModel(
                        "My Neighbor Totoro (Tonari no Totoro)",
                        "Fantasy, Slice of Life, Family",
                        "Two young sisters move to the countryside and discover a world of magical creatures, including the friendly forest spirit Totoro, who takes them on whimsical adventures.",
                        R.drawable.my_neighbor_totoro_cover),

                new AnimeModel(
                        "Blue Lock",
                        "Sports, Psychological, Drama",
                        "After Japan's failure in the World Cup, a radical program is launched to create the ultimate striker. Yoichi Isagi must survive intense competition in the Blue Lock facility.",
                        R.drawable.blue_lock_cover),

                new AnimeModel(
                        "One Piece",
                        "Action, Adventure, Fantasy, Comedy",
                        "Monkey D. Luffy sails across the Grand Line with his crew in search of the legendary treasure \"One Piece\" to become the Pirate King.",
                        R.drawable.one_piece_cover),

                new AnimeModel(
                        "Suzume",
                        "Fantasy, Drama, Romance",
                        "A teenage girl named Suzume discovers mysterious doors that unleash disasters across Japan. Along with a stranger, she must find and lock the doors to save the world.",
                        R.drawable.suzume_cover)
        };

        RecyclerView animeListView = findViewById(R.id.animeList);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        animeListView.setLayoutManager(layoutManager);

        animeListView.setAdapter(new AnimeListCardAdapter(this, animeList));
    }
}