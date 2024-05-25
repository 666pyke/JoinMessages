# JoinMessages

● A **Minecraft** plugin that allows you to add **special messages** when a player **joins** or **leaves** the server. 

● This plugin lets you set **special messages** for **Donors**, **Staff Members**, or even for **all** players.

Configuration:
```
# ****************************
#
# You can change the 'permission' parameter to whatever you want and provide the respective
# permission to the rank to which you want to associate the respective messages. If you want only
# special ranks, such as donors and staff members, to have special messages, don't give players
# permission from the 'DEFAULT' category. I hope you won't encounter any issues using my plugin.
#
# ****************************
donator:
  join-message: "%user% joined the server"
  leave-message: "%user% left the server"
  permission: joinmessages.donator

staff:
  join-message: "%user% joined the server"
  leave-message: "%user% left the server"
  permission: joinmessages.staff

default:
  join-message: "%user% joined the server"
  leave-message: "%user% left the server"
  permission: joinmessages.default

options:
  disable-vanilla-message: true # the default yellow messages such as "mareleepyke joined the game"

# priority for checking permissions will be staff > donator > default

messages:
  reloaded: "Plugin reloaded."
```

● This **plugin** can also completely **disable** the messages that appear when someone joins or leaves the server, which can be annoying or visually unappealing. 

![image](https://github.com/pykew/JoinMessages/assets/140652366/97849789-62ce-4ec8-8765-0a1e94430328)

