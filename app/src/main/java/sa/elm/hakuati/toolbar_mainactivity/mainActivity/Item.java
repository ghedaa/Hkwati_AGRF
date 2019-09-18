package sa.elm.hakuati.toolbar_mainactivity.mainActivity;

public class Item {
    private String title;
    private String views;
    private String channelName;
    private int image;
    private int channelImage;
    private int soundImage;

    public Item(String title, int image, int channelImage, String views,String channelName,int soundImage) {
        this.title = title;
        this.image = image;
        this.channelImage = channelImage;
        this.soundImage = soundImage;
        this.views = views;
        this.channelName=channelName;
    }

    public Item(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getChannelImage() {
        return channelImage;
    }

    public void setChannelImage(int channelImage) {
        this.channelImage = channelImage;
    }

    public int getSoundImage() {
        return soundImage;
    }

    public void setSoundImage(int soundImage) {
        this.soundImage = soundImage;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
