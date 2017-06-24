package cs445.a1;

// There are no abstract methods to override in this interface, but declaring
// that you implement it allows objects of this type to be written out to disk
// or over the network.
import java.io.Serializable;

/**
 * ProfileInterface describes a social media profile. It stores a user's name,
 * "about me" blurb, and a set of profiles that the user follows. It can also
 * suggest new profiles to follow based on the "followers of followers"
 * technique.
 */
public interface ProfileInterface extends Serializable {

    /**
     * Sets this profile's name.
     *
     * <p> If newName is not null, then setName modifies this profile so that
     * its name is newName. If newName is null, then setName throws
     * IllegalArgumentException without modifying the profile, for example:
     *
     * <p> {@code throw new IllegalArgumentException("Name cannot be null")}
     *
     * @param newName  The new name
     * @throws IllegalArgumentException  If newName is null
     */
    public void setName(String newName) throws IllegalArgumentException;

    /**
     * Gets this profile's name.
     *
     * @return  The name
     */
    public String getName();

    /**
     * Sets this profile's "about me" blurb.
     *
     * <p> If newAbout is not null, then setAbout modifies this profile so that
     * its about blurb is newAbout. If newAbout is null, then setAbout throws
     * IllegalArgumentException without modifying the profile.
     *
     * @param newAbout  The new blurb
     * @throws IllegalArgumentException  If newAbout is null
     */
    public void setAbout(String newAbout) throws IllegalArgumentException;

    /**
     * Gets this profile's "about me" blurb
     *
     * @return  The blurb
     */
    public String getAbout();

    /**
     * Adds another profile to this profile's following set.
     *
     * <p> If this profile's following set is at capacity, or if other is null,
     * then follow returns false without modifying the profile. Otherwise, other
     * is added to this profile's following set.
     *
     * @param other  The profile to follow
     * @return  True if successful, false otherwise
     */
    public boolean follow(ProfileInterface other);

    /**
     * Removes the specified profile from this profile's following set.
     *
     * <p> If this profile's following set does not contain other, or if other
     * is null, then unfollow returns false without modifying the profile.
     * Otherwise, this profile in modified in such a way that other is removed
     * from this profile's following set.
     *
     * @param other  The profile to follow
     * @return  True if successful, false otherwise
     */
    public boolean unfollow(ProfileInterface other);

    /**
     * Returns a preview of this profile's following set.
     *
     * <p> The howMany parameter is a maximum desired size. The returned array
     * may be less than the requested size if this profile is following fewer
     * than howMany other profiles. Clients of this method must be careful to
     * check the size of the returned array to avoid
     * ArrayIndexOutOfBoundsException.
     *
     * <p> Specifically, following returns an array of size min(howMany, [number
     * of profiles that this profile is following]). This array is populated
     * with arbitrary profiles that this profile follows.
     *
     * @param howMany  The maximum number of profiles to return
     * @return  An array of size &le;howMany, containing profiles that this
     * profile follows
     */
    public ProfileInterface[] following(int howMany);

    /**
     * Recommends a profile for this profile to follow. This returns a profile
     * followed by one of this profile's followed profiles. Should not recommend
     * this profile to follow someone they already follow, and should not
     * recommend to follow oneself.
     *
     * <p> For example, if this profile is Alex, and Alex follows Bart, and Bart
     * follows Crissy, this method might return Crissy.
     *
     * @return  The profile to suggest, or null if no suitable profile is
     * possible.
     */
    public ProfileInterface recommend();
}

