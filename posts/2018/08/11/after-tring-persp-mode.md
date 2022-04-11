---
date: 2018-08-11 Sat 17:07
tags: emacs, workspace
---

# After Trying Persp Mode

After [last post about trying emacs workspace management][1], I tried `persp-mode`. It comes out that it's not that simple to implement a workspace auto-switch feature. It's not just `find-file-hook` and advice for `switch-buffer`. There are too many commands need to advice and hook (`select-window`, `kill-buffer-hook` ...). Cause there's no "buffer-focus-in-hook". And it's not easy to build one with `persp-mode`.

There is one package in melpa that implement per buffer `buffer-focus-in-hook` and `buffer-focus-out-hook` by hacking on `buffer-list-update-hook`. But `persp-mode` also manipulate the `buffer-list`. It won't work out.

Then I realize that I just want to have workspace on buffer management, like the `ibuffer` option above. I can easily get that feature. I just rebind my buffer-switch key to `counsel-projectile-switch-to-buffer` and everything works great.

It turns out that compared with projectile, persp only offers workspace specific window configuration. So I don't need it if I only want buffer level workspace. And it also add unexpected behavior when I test my auto-switch implementation (buffer and window configuration switched at the same time), which makes me wonder that workspace auto-switch is a bad idea.

Although it looks like that I waste bunch of time to find a single rebind. I do find lots useful stuff on these workspace, layout, window management packages and implementations. I might write another post for about that.

[1]: https://yqrashawn.com/2018/08/07/emacs-workspace-management/
