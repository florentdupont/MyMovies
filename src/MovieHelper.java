
public class MovieHelper {

	public static Movie[] getMovieList() {
		
		Movie[] movies = new Movie[6];
		
		String[] images = {"affiche-Le-Labyrinthe-de-Pan-El-Laberinto-del-Fauno-2005-10.jpg",
				"affiche-La-Vie-des-autres-Das-Leben-der-Anderen-2006-2.jpg",
				"affiche_Voisins_les_Hommes_2004_4.jpg",
				"affiche_Star_Wars__Episode_3__La_revanche_des_Sith_2004_4.jpg",
				"affiche_Aventuriers_de_l_Arche_perdue_1981_1.jpg",
				"affiche_Empire_Contre_attaque_1980_1.jpg"};
		
		
		movies[0] = new Movie();
		movies[0].image = ImageHelper.getImageFromResources("resources/", images[0]);
		
		movies[1] = new Movie();
		movies[1].image = ImageHelper.getImageFromResources("resources/", images[1]);
		
		movies[2] = new Movie();
		movies[2].image = ImageHelper.getImageFromResources("resources/", images[2]);
		
		movies[3] = new Movie();
		movies[3].image = ImageHelper.getImageFromResources("resources/", images[3]);
		
		movies[4] = new Movie();
		movies[4].image = ImageHelper.getImageFromResources("resources/", images[4]);
		
		movies[5] = new Movie();
		movies[5].image = ImageHelper.getImageFromResources("resources/", images[5]);
		
		return movies;
	}
	
	
}
