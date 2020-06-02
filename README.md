# What is Melody?
<p><b>Melody</b> is a spigot plugin that allows you to play Music you (or others!) have composed in-game, for whatever purpose you would like!</p>
<p>Whether you want to play music for your RPG, add some spice to your adventure map server, or simply want your players to be able to listen to music, Melody has you covered!</p>

# Installation
<p>Drop the provided melody.jar into your plugins folder. Done. :)</p>

# Okay... But how do I make songs?
<p>For now, we unfortunately only support a rather hard to use CLI (https://github.com/Akadeax/MelodyConsoleClient), but our way more user-friendly electron client will release in the near future! (https://github.com/Akadeax/MelodyClient)</p>
<p>As soon as you have composed your track and exported it, you get your .mel file!</p>

# Great! but what now?
<p>now you can place that .mel file inside of Melody's data folder (".../plugins/Melody/") and play it in-game, with "/play 'filename'.mel". Success!</p>

# For developers
<p>You want to interact with Melody in your own code? Awesome! we provide an API at https://github.com/Akadeax/MelodyAPI that you can include in your plugin to interact with Melody.
Please do <b>not</b> use the same artifact that servers have to install.</p>
