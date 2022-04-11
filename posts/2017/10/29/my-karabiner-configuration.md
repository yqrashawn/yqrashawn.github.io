---
date: 2018-09-19 Wed 08:52
tags: coding, tool, efficiency, karabiner
---

# My Karabiner Elements Configuration

UPDATE 2018-09-19 Wed

My config in [KE-complex-modifications][1] is outdated. I'm now using [GokuRakuJoudo][2] to config karabiner. It enables user to write the configuration in a much more compact karabiner.edn file, you can check out [mine][3]. I'll write a post about it soon. If your karabiner.json is bloated, you may want to try it. Recommend to check the [tutorial][4] first.

ORIGINAL

This post are descriptions and tutorial of my [personal KE complex modification][5].

I [merged][6] my personal [Karabiner Elements][7] configuration into the [official repo][8] several days ago. You can import my personal rules [here][9]. To achieve efficiency, I use karabiner with [Alfred][10] mainly through [alfred workflow external trigger][11] and [osascript][12] (command line tool for calling AppleScript code).

Here are these configurations. Please note that I update my configurations every day. So I can't keep this post up to date. The key here is to introduce a system for launching apps, invoking scripts and all stuffs that can be done with shell (everything). When you can get anything you want with few key strokes, **it feels great**.

<del>[Download all my workflow from dropbox ][13], please contact [me][14] when this link broken.</del> I may update this post for each specific config if I have time in the future (may not happen). If you have any problems or ideas, please [open an issue][15] in my dotfile repo. And you can also check [KE-complex<sub>modifications</sub>][16] official repo for more rules and discussion.

Update: The old download link is broken and I don't have time to maintain all these workflows up to date. Now that the core thing here is to use alfred workflow's external trigger feature, I create a [app launcher workflow][17] as a sample.

[1]: https://github.com/pqrs-org/KE-complex_modifications
[2]: https://github.com/yqrashawn/GokuRakuJoudo
[3]: https://github.com/yqrashawn/yqdotfiles/blob/master/.config/karabiner.edn
[4]: https://github.com/yqrashawn/GokuRakuJoudo/blob/master/Tutorial.org
[5]: https://github.com/yqrashawn/yqdotfiles/blob/master/.config/karabiner/karabiner.json
[6]: https://github.com/pqrs-org/KE-complex_modifications/pull/162
[7]: https://github.com/tekezo/Karabiner-Elements
[8]: https://github.com/pqrs-org/KE-complex_modifications
[9]: https://ke-complex-modifications.pqrs.org/?q=yqrashawn
[10]: https://www.alfredapp.com/
[11]: https://www.alfredapp.com/help/workflows/triggers/external/
[12]: https://developer.apple.com/legacy/library/documentation/Darwin/Reference/ManPages/man1/osascript.1.html
[13]: https://www.dropbox.com/s/qepdfdu0djyed7v/workflows.zip?dl=0
[14]: https://github.com/yqrashawn
[15]: https://github.com/yqrashawn/yqdotfiles/issues/new
[16]: https://github.com/pqrs-org/KE-complex_modifications
[17]: https://github.com/yqrashawn/alfred-launcher-workflow
