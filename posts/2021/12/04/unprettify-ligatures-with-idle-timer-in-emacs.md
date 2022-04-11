Tags: emacs

# Unprettify ligatures with idle timer in emacs

Emacs prog-mode provide prettify symbol for ligatures.
It also has a `prettify-symbols-unprettify-at-point` option to unprettify symbols when cursor is on the symbol.
Below is code snippet to only unprettify when emacs is idle for 1 second.

```elisp
(after! prog-mode
  ;; unprettify when idle for 1 seconds
  (defadvice! +prettify-symbols--post-command-hook (orig-fn)
    :around #'prettify-symbols--post-command-hook
    (run-with-timer 1 nil
                    (cmd! (let ((ti (current-idle-time)))
                            (when (or (> (nth 1 ti) 0)
                                      (> (nth 2 ti) 900000))
                              (funcall orig-fn)))))))
```

Note: `after!`, `defadvice!`, `cmd!` are all macros from doom-emacs.
