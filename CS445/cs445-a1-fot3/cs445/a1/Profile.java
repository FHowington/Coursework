package cs445.a1;

/**
 * Created by forbes on 1/19/17.
 */
public class Profile implements ProfileInterface {
    private String fullName, aboutMe;
    private Set<ProfileInterface> followers = new Set<ProfileInterface>();


    public Profile() {
        this(null, null);
    }

    public Profile(String name, String about) {
        if (name == null)
            fullName = "";
        else
            fullName = name;
        if (about == null)
            aboutMe = "";
        else
            aboutMe = about;
    }

    public void setName(String newName) throws IllegalArgumentException {
        if (newName == null)
            throw new IllegalArgumentException("Name cannot be null");
        else
            fullName = newName;
    }

    public String getName() {
        return fullName;
    }

    public void setAbout(String newAbout) throws IllegalArgumentException {
        if (newAbout == null)
            throw new IllegalArgumentException("About cannot be null");
        else
            aboutMe = newAbout;
    }

    public String getAbout() {
        return aboutMe;
    }

    public boolean follow(ProfileInterface other) {
        boolean result;
        if (other == null)
            result = false;
        else
            result = followers.add(other);
        return result;
    }

    public boolean unfollow(ProfileInterface other) {
        boolean result;
        if (other == null)
            result = false;
        else if(followers.contains(other))
            result = followers.remove(other);
        else result=false;
        return result;
    }


    public ProfileInterface[] following(int howMany) {
        ProfileInterface[] result;
        int arrayLength = followers.getCurrentSize();

        if (howMany < arrayLength)
            arrayLength = howMany;

        Object[] temp = followers.toArray();
        result = new ProfileInterface[arrayLength];

        for (int ii = 0; ii < arrayLength; ii++)
            result[ii] = (ProfileInterface) temp[ii];

        temp = null;
        return result;
    }

    public ProfileInterface recommend() {
        boolean found = false;
        int index = 0;
        int index2;
        ProfileInterface result = null;
        Object[] allFollowing = followers.toArray();
        Object[] followerFollowers;

        while (!found && index < followers.getCurrentSize()) {
            index2 = 0;
            followerFollowers = ((ProfileInterface)allFollowing[index]).following(((Profile)allFollowing[index]).followers.getCurrentSize());
            while (!found && index2 < followerFollowers.length) {
                if (followers.contains((ProfileInterface)followerFollowers[index2]) || this.equals((ProfileInterface)followerFollowers[index2]))
                    index2++;
                else {
                    result = (ProfileInterface) followerFollowers[index2];
                    found = true;
                }
            }
            index++;
        }
        return result;
    }
}
