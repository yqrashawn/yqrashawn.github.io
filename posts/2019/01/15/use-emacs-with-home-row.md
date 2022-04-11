---
date: 2019-01-15 Tue 11:54
tags: goku, macOS, karabiner, emacs
---

# Use Emacs With Home Row

After writing about [switch buffer in emacs][1] last year. I keep looking for the way to switch buffer quicker and more comfortably. I mentioned the package [awesome-tab][2] in that post and I'm still using it.

{{more}}

The package shows buffers as tabs in emacs [header line][3], and provides functions to switch to next/prev, first/last tab. It also groups buffers depends on project and major-mode.

I write a [patch][4] for the package to add function to jump to specific tab recently. So that user can jump to specific tabs with different shortcuts.

I also write a [patch][5] for [company-mode][6] to customize the company candidate numbers when `company-show-numbers` is set to t.

Now I can use the home row the way I want in emacs. Check the screenshot below.

![image showing home row navigation for company and tab][image-1]

As you can see in the picture, instead of numbers, there're "asdf" for both tabs and completion candidates. Next step is to switch/select tabs/candidates with home row shortcuts.

```clojure
  {:des "Emacs m/v select company candidates"
   :rules [:emacs-m-mode
           [:a [:!Tx :!T6 :1]]
           [:s [:!Tx :!T6 :2]]
           [:d [:!Tx :!T6 :3]]
           [:f [:!Tx :!T6 :4]]
           [:g [:!Tx :!T6 :5]]
           :emacs-v-mode
           [:h [:!Tx :!T6 :6]]
           [:j [:!Tx :!T6 :7]]
           [:k [:!Tx :!T6 :8]]
           [:l [:!Tx :!T6 :9]]
           [:semicolon [:!Tx :!T6 :0]]]}
```

Above code uses [goku][7] and [karabiner][8] to map <kbd>m+asdfg</kbd> and <kbd>v+hjkl;</kbd> to <kbd>C-x C-6 1234567890</kbd>. That's `'m' key down then click 'a'` to <kbd>C-x C-6 1</kbd> in emacs. Then I map <kbd>C-x C-6</kbd> + numbers to functions that switch/select tabs/candidates in emacs.

```lisp
  ;; jump to specific in emacs
  (define-key awesome-tab-mode-map (kbd "C-x C-6 1") (lambda () (interactive) (awesome-tab-jump ?a)))
  (define-key awesome-tab-mode-map (kbd "C-x C-6 2") (lambda () (interactive) (awesome-tab-jump ?s)))
  (define-key awesome-tab-mode-map (kbd "C-x C-6 3") (lambda () (interactive) (awesome-tab-jump ?d)))

  ;; select specific candidates in company-active-map
  (define-key company-active-map (kbd "C-x C-6 1") (kbd "M-1"))
  (define-key company-active-map (kbd "C-x C-6 2") (kbd "M-2"))
  (define-key company-active-map (kbd "C-x C-6 3") (kbd "M-3"))
```

We can use magic now!

<kbd>ma</kbd> switch to first tab. <kbd>mg</kbd> select 5th candidates when completing stuff.

You can find above code in my [goku config][9] and [.emacs.d][10].

[1]: /2018/09/26/switch-buffer-in-emacs
[2]: https://github.com/manateelazycat/awesome-tab
[3]: http://www.gnu.org/s/emacs/manual/html_node/elisp/Header-Lines.html "links to gnu elisp doc"
[4]: https://github.com/yqrashawn/awesome-tab "fork gh repo of awesome-tab"
[5]: https://github.com/company-mode/company-mode/pull/859 "pr to emacs company-mode"
[6]: https://github.com/company-mode/company-mode "github repo"
[7]: https://github.com/yqrashawn/GokuRakuJoudo
[8]: https://github.com/tekezo/Karabiner-Elements
[9]: https://github.com/yqrashawn/yqdotfiles/blob/master/.config/karabiner.edn
[10]: https://github.com/yqrashawn/.emacs.d
[image-1]: ./_emacs-home-row.png "emacs home row"
