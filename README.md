Hi, I'm a student at the University of Montpellier in France and i'm currently studying engineering in Polytech. I'm very interested by computer science, development and operational system. I'm currently learning Java though a SkyBlock and Clash of Clans Minecraft project. I'm posting today a small project that I made for manage and save custom player's data like password or anything else though a PlayerProfile class. 
This template implements an auto save and auto unregister system.
This project is just a simple template, to really use it you need to edit the code.
I hope you will enjoy my work and don't hesitate to gave me some feedback and advices.

## How to use PlayerProfileManager
Create a new instance of PlayerProfileManager in your main plugin class.
```java
public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        new PlayerProfileManager(this);
    }
}
```
Then you can use the PlayerProfileManager instance to check if a PlayerProfile is loaded or not or if a PlayerProfile is going to be unloaded.
```
PlayerProfileManager playerProfileManager = PlayerProfileManager.getInstance();
playerProfileManager.isProfileLoaded(UUID);
```
Now if you want to get a PlayerProfile instance you can use the following method.
```
PlayerProfile playerProfile = PlayerProfile.from(UUID);
```
To add your own data to the PlayerProfile which need to be saved you need to edit PlayerProfile class to add variables. Don't forget to add the variable to the save and load methods.