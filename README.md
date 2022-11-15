# AutoReplant
Automates the Replant Process so you don't have to care about that on your Server!

Let your users be lazy and treat yourself with always cultivated farms. Now not replanting crops is a willful offence and can get punished with the full force of the banhammer!

Individually toggle the plugin for yourself with the "**/autoreplant**" or short "**/arp**" command. This needs the user-permission!

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
Also you can add, remove or change the toggle for players, in the "player_list" section. Use this scheme:

`player-uuid: true`


Don't forget to "**/arp reload**" (needs admin-permission) when done editig!
### Have fun.
