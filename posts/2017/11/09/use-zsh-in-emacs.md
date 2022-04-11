---
date: 2017-10-31 Tue 16:27
tags: shell, zsh, emacs
---

# Use Zsh In Emacs

I used to use vim with tmux in a terminal emulator. After changed to emacs. It didn't take much time for me to realize that emacs in GUI works better.

The problem is I still want the old pattern. I tried about three times using my zsh shell in emacs. I'm not here telling those failed attempts. I want to write it down because of a recent discovery.

When I restructure my zsh configurations, I find the z-shell works fine with zero config. That's when I finally discover that it's the plugins and configurations that make zsh and emacs incompatible. What's more, I find these lines below. They must come from some emacs package's readme instruction. The original one is telling zsh don't do something when it's in emacs.

```zsh
if [ -n "$INSIDE_EMACS" ]; then
...
else
...
fi
```

So I thought, I can just use these line with my zsh configuration [prezto][1]. It works fine now after some little test. It turns out the default [zsh-autocompletion][2] is not playing well with emacs. I use the `if...else` code telling zsh don't load autosuggestion while in emacs. And everything is fine now.

That's it. Maybe I'll dig in for getting autocompletion back in emacs someday.

[1]: https://github.com/sorin-ionescu/prezto
[2]: https://github.com/zsh-users/zsh-completions
