---
date: 2018-10-03 Wed 02:30
tags: karabiner, emacs, macOS, efficiency
---

# Switch Things Faster

Talking about buffer switching in emacs in the [last post][1] , I said that I have keybindings to switch between the two and three most recent used buffers. And the author of the [reddit post][2] chose to use [buffer-flip][3] package.

The buffer-flip package makes switching buffer like those switching applications behavior in operating system, like `alt-tab` in MS Windows and `command-tab` in macOS. VSCode also can switch among opened files with `control-tab`. The problem of these methods are obvious. They can only switching in a specific order and they all bind to these uncomfortable keys.

There're two other options here.

### Search + shortcuts for recent visited item

I already talk about the first solution, switching with packages like `ivy` or `helm`, or `alfred` or `contexts` on macOS, which you can switching by searching. Plus two shortcuts switching between the most recent two and three item, so that we don't need to press too much to just switch to prev item.

### home-row shortcuts + smart trigger

There's another solution. It's very similar to editing text in emacs. When I want to change a word in current line, I got to move my cursor to that word. If the word is in the beginning or end of this line, I use `C-a` or `C-e` then maybe `C-f`, `C-b` to move to that word (I actually use evil keybindings). If it's in the middle of the line, I use `isearch`, `swiper` or `evil-fine-char` to that word.

It's the same when we switch buffer. Check the code first.

```emacs-lisp
;; smart trigger function, if not in minibuffer, trigger ivy
;; if in minibuffer, do ivy actions
;; (there must be better way to identify if we are in ivy-switch-buffer context)
(defun +ivy-switch-buffer-next-line ()
  (interactive)
  (if (minibufferp) (ivy-next-line)
    (ivy-switch-buffer)))

(defun +ivy-switch-buffer-prevouse-line ()
  (interactive)
  (if (minibufferp) (ivy-previous-line)
    (ivy-switch-buffer)))

;; then we bind them to some key
(global-set-key (kbd "C-x C-9 l") #'ivy-alt-done)
(global-set-key (kbd "C-x C-9 j") '+ivy-switch-buffer-next-line)
(global-set-key (kbd "C-x C-9 k") '+ivy-switch-buffer-prevouse-line)
(global-set-key (kbd "C-x C-9 a" ) #'ivy-beginning-of-buffer)
(global-set-key (kbd "C-x C-9 e" ) #'ivy-end-of-buffer)
(global-set-key (kbd "C-x C-9 u" ) #'ivy-scroll-down-command)
(global-set-key (kbd "C-x C-9 d" ) #'ivy-scroll-up-command)
```

So this is unusable for now. Cause the keybindings are too hard and slow to reach. That's why we don't do these kind of configuration. We don't have enough key for this.

But what if we can make any key a modifier key like `control`, `alt` or `command`. There's [karabiner][4] on macOS can make this happen. And I think this is doable on other operating systems as well.

```clojure
{:applications {:Emacs ["^org\\.gnu\\.Emacs$"]} ;; let karabiner know which app is emacs
 :simlayer {:emacs-s-mode {:key :s :condi :Emacs}} ;; use s as modifer key only in emacs
 :mains [{:des "s-mode in emacs"
          :rules [:emacs-s-mode ;; bellow rebinds only works under :emacs-s-mode condition
                  [:j [:!Tx :!T9 :j]] ;; ivy-next-line or ivy-switch-buffer
                  [:k [:!Tx :!T9 :k]] ;; prev line
                  [:l [:!Tx :!T9 :l]] ;; ivy-alt-done
                  [:i [:!Tx :!T9 :a]] ;; top of ivy buffer
                  [:o [:!Tx :!T9 :e]] ;; bottom
                  [:o [:!Tx :!T9 :u]] ;; scroll up
                  [:o [:!Tx :!T9 :d]]]}]}
```

I have this `emacs-s-mode` use `s` key as a modifier key mapping `s+jklaeud` to `C-x C-9 jklaeud`. So when I want to switch buffer, I can just press `s+j/k` to trigger `ivy-switch-buffer` and keep pressing either of this key to find my buffer. Or use `s + u or d` to scroll quicker, `s+a/e` to go to the top and bottom of the list and `s+l` to select the buffer. I can always release `s` key and type to search the right buffer, without fearing releasing keys may select wrong buffer (happens when switching application with `command+tab`). And it won't switch to buffers between current one and target one making emacs rendering every buffer (buffer-flip behaviour).

Note that the karabiner configuration above needs [GokuRakuJoudo][5] to convert to the json format that karabiner can read, you can't use it directly.

If you know how to make normal keys as modifier key in other operating system, please let me know. I'll add them to this post.

[1]: http://yqrashawn.com/2018/09/26/switch-buffer-in-emacs/
[2]: https://www.reddit.com/r/emacs/comments/9hmh8n/whats_your_preferred_method_of_switching_buffers/
[3]: https://github.com/killdash9/buffer-flip.el
[4]: https://pqrs.org/osx/karabiner/
[5]: https://github.com/yqrashawn/GokuRakuJoudo
