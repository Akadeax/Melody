# Melody
<p><b>Melody</b> is a spigot plugin that allows you to play Music you (or others!) have composed in-game, for whatever purpose you would like!</p>
<p>Whether you want to play background music for your RPG, add some spice to your adventure map server, or simply want your players to be able to listen to music, Melody has you covered!</p>

## Installation
<p>Drop the provided melody.jar into your plugins folder. Done. :)</p>

### Okay... But how do I make songs?
For now, we unfortunately only support a [rather hard to use CLI](https://github.com/Akadeax/MelodyConsoleClient), but our way more [user-friendly electron client](https://github.com/Akadeax/MelodyClient) will release in the near future! 
<p>As soon as you have composed your track and exported it, you get your .mel file!

### Great! but what now?
<p>now you can place that .mel file inside of Melody's data folder (".../plugins/Melody/") and play it in-game, with "/play 'filename'.mel". Success!</p>

# For developers
You want to interact with Melody in your own code? Awesome! we provide an API [here](https://github.com/Akadeax/MelodyAPI) that you can include in your plugin to interact with Melody.
<p>We do <b>not</b> recommend depending on the same artifact that servers have to install.</p>
