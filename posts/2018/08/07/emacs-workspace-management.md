---
date: 2018-08-07 Tue 16:46
tags: emacs, workspace
---

# Emacs Workspace Management

Recently I'm asked to help on a project. The problem is there're a lot files with same name in different directories and same name with my other project. I'm using `ivy-switch-buffer` to switch between opened buffer. And it needs more keystrokes in this project to switch to the desired buffer. So I'm looking for some workspace management approach in emacs to make my finger not that tired after work.

After googling for a little while, I find there are so many ways to manage workspace in emacs.

- use session: [use desktop and bookmark+][1]
- use multiple emacs instance
- `persp-mode`
- `purpose-mode` `frame-purpose-mode`
- also there's `ibuffer` with `ibuffer-vc` or `ibuffer-projectile`
- even `tabbar` with custom filter functions

The cons of the first two is that you need to deliberately switch workspace when you are going to open files in other workspace. The pros are that they provide perfect isolation, no need to tweak buffer switch functions.

For `persp-mode`, the pros is that with custom code, maybe it's not hard to implement the workspace auto-switching function. It has eye candy in mode line or title bar if you want them. The cons are that you need to implement the auto-switching function or remember to switch workspace manually.

For `purpose-mode`, you may need multiple high resolution screen.

For `ibuffer`, it's not typically "workspace". It depends on how many buffers you have and how familiar you are with the ibuffer filter functionality.

For `tabbar`, eye candy and custom function is needed.

So it really depends on

- your currently familiar workflow
- can you tolerate manually switch workspace
- if you can comfortably change your workflow
- how much isolation you want
- screen amount and size

I may try `persp-mode` first. Wish me luck.

[1]: https://www.emacswiki.org/emacs/Desktop#toc6
