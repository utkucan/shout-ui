package com.shoutapp;

import java.util.ArrayList;

public class Model {
	public static String userName;
	public static String profile_pic_url;

	public static ArrayList<BadgeObject> getBadge() {
		ArrayList<BadgeObject> badges = new ArrayList<BadgeObject>();
		badges.add(new BadgeObject(R.drawable.events_cat_bicycle, "01.01.2013", "Go Healthy"));
		badges.add(new BadgeObject(R.drawable.events_cat_camp, "23.05.2013", "Camper"));
		badges.add(new BadgeObject(R.drawable.events_cat_food, "09.12.2013", "Chronical Hungry"));
		badges.add(new BadgeObject(R.drawable.events_cat_game, "13.04.2013", "Game Addict"));
		badges.add(new BadgeObject(R.drawable.events_cat_movie, "20.01.2013", "Dr. Movie"));
		badges.add(new BadgeObject(R.drawable.events_cat_night, "17.11.2013", "Night watcher"));
		badges.add(new BadgeObject(R.drawable.events_cat_uknowwhatimean, "23.11.2013", "Socialize"));
		badges.add(new BadgeObject(R.drawable.events_cat_wood, "12.05.2013", "Green Activist"));
		badges.add(new BadgeObject(R.drawable.events_count_1, "12.11.2012", "First time"));
		badges.add(new BadgeObject(R.drawable.events_count_10, "23.01.2013", "Tenth first time"));
		badges.add(new BadgeObject(R.drawable.events_count_25, "11.03.2013", "25 Combo"));
		badges.add(new BadgeObject(R.drawable.events_count_50, "05.04.2013", "Half of hundred"));
		badges.add(new BadgeObject(R.drawable.events_count_100, "23.06.2012", "SHOUT!"));
		badges.add(new BadgeObject(R.drawable.events_count_250, "19.08.2013", "Leader"));
		badges.add(new BadgeObject(R.drawable.events_count_500, "05.10.2013", "Thats too much"));
		badges.add(new BadgeObject(R.drawable.events_count_king, "01.12.2013", "Celebrity"));
		return badges;
	}

}
