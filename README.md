# AutoReplant
Automates the Replant Process so you don't have to care about that on your Server!

Let your users be lazy and treat yourself with always cultivated farms. Now not replanting crops is a willful offence and can get punished with the full force of the banhammer!

Individually toggle the plugin for yourself with the "**/autoreplant**" or short "**/arp**" command. This needs the user-permission!

While sneaking, any configuration will be ignored and the crop can be harvested as known in vanilla minecraft.

## Customizable!
Change the output phrases in the config.yml or adjust the permissions to your liking!


```
default perms:
  user_perm: 'zf.autoreplant.user'
  admin_perm: 'zf.autoreplant.admin'
```

Change what Crops should be affected! Defaults:
```
material_list:
  - BEETROOTS
  - CARROTS
  - NETHER_WART
  - POTATOES
  - WHEAT
```

Since Version 0.1.0 you can manage which worlds should be affected. Toggle them ingame with "**/arp toggleworld**" (admin permission required), or via the config in the world_blacklist section like this:
```
world_blacklist:
  - ExampleWorld
```
Also you can add, remove or change the toggle for players, in the "player_list" section. Use this scheme:

`player-uuid: true`


Don't forget to "**/arp reload**" (needs admin-permission) when done editig!
### Have fun.
