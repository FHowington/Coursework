package cs445.a4;

/**
 * This abstract data type represents the backend for a streaming radio service.
 * It stores the songs, stations, and users in the system, as well as the
 * like/dislike ratings that users assign to songs.
 */
public interface StreamingRadio {

    /**
     * Adds a song to the system. Will add a song to the system if the song
     * does not already exist within the system and is valid. If the song is already
     * listed in the system, then the method will throw an IllegalArgumentException since
     * a song should not be listed twice within the system. If the song is a null value, 
     * then this method will throw a NullPointerException
     * @param theSong song to be added to the system
     * @throws NullPointerException if the song is null
     * @throws IllegalArgumentException if the song already exists within the system
     */
    public void addSong(Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Removes a specified song from the system. Will remove a song from the
     * system if the song is valid and exists already within the system.
     * If the song does not exist within the system, an IllegalArgumentException
     * is thrown. If the song to be removed is null, a NullPointerException is
     * thrown. 
     * @param theSong The song to be removed from the system
     * @throws IllegalArgumentException if the song does not currently exist
     * within the system
     * @throws NullPointerException if the song to be removed is null
     */
    public void removeSong(Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Adds a song to the playlist for a specified radio station.
     * If the song is not already on the playlist of the radio station
     * and both the radio station and the song exist the song will be 
     * added. If either the song or the station are null, a 
     * NullPointerException is thrown. If the song is already on the list for
     * the given station, an IllegalArgumentException is thrown.
     * @param theStation The station to which a song is being added
     * @param theSong The song that is being added to the station's playlist
     * @throws IllegalArgumentException if the song already is part of the station's
     * playlist.
     * @throws NullPointerException if either the station or the song is null
     */
    public void addToStation(Station theStation, Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Removes a song from the playlist for a specified radio station.
     * If the song is  on the playlist of the radio station
     * and both the radio station and the song exist the song will be 
     * removed. If either the song or the station are null, a 
     * NullPointerException is thrown. If the song is not on the list for
     * the given station, an IllegalArgumentException is thrown.
     * @param theStation The station from which a song is being removed
     * @param theSong The song that is being removed from the station's playlist
     * @throws IllegalArgumentException if the song is not part of the station's
     * playlist.
     * @throws NullPointerException if either the station or the song is null
     */
    public void removeFromStation(Station theStation, Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Sets a user's rating of the song. The rating will be set if the song
     * does not already have a rating, and the song, user, and rating are all
     * valid. If the song already has a rating, an IllegalArgumentException will
     * be thrown. If any of the user, the song, or the rating are null, a 
     * NullPointerException will be thrown. 
     * @param theUser The user that is setting a rating for a song
     * @param theSong The song that is having it's rating set
     * @param theRating the rating for the song as a boolean value, with true being a
     * like rating and false being a dislike rating.
     * @throws IllegalArgumentException if the song already has a rating
     * @throws NullPointerException if any of the user, song, or rating are null
     */
    public void rateSong(Boolean theRating, Song theSong, User theUser)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Clears a user's rating on a song. If this user has rated this song and
     * the rating has not already been cleared, then the rating is cleared and
     * the state will appear as if the rating was never made. If there is no
     * such rating on record (either because this user has not rated this song,
     * or because the rating has already been cleared), then this method will
     * throw an IllegalArgumentException.
     * @param theUser user whose rating should be cleared
     * @param theSong song from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a
     * rating on record for the song
     * @throws NullPointerException if either the user or the song is null
     */
    public void clearRating(User theUser, Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Returns the predicted user's rating for a song as a boolean value,
     * with true indicating that the user will like the song and false that
     * the user will not like the song. The value will be returned if the song
     * does not already have a rating, and the user and the song are both valid
     * and the song does not already have a rating. If the song does have a rating,
     * an IllegalArguementException will be thrown. If either the song or the user
     * null, a NullPointerException will the thrown.
     * @param theUser user whose song rating is being predicted
     * @param theSong song that's rating is being predicted
     * @return the predicted rating of the song, with true indication like and false
     * indicating not like
     * @throws IllegalArgumentException if the song already has a rating
     * @throws NullPointerException if either the user or the song is null
     */
    public boolean predictRating(Song theSong, User theUser)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Returns a song for the given user that the user is predicted to like.
     * Will return a song if the user is valid and if there is a song that the 
     * user is predicted to like. If no song can be found for the user, then an 
     * IllegalArgumentException is thrown. If the user is null, a NullPointerException
     * is thrown.
     * @param theUser user for whom a song is being predicted
     * @return a song that the user is predicted to like
     * @throws IllegalArgumentException if no song can be found which the user is
     * predicted to like
     * @throws NullPointerException if either the user is null
     */
    public Song suggestSong(User theUser)
    throws IllegalArgumentException, NullPointerException;

}

