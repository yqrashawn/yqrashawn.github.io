---
date: 2017-11-25 Sat
tags: agenda, org-mode, emacs
---

# Org Agenda Bitbar Plugin

UPDATE: The agenda plugin has been merged into the official plugin repo. You can now find the plugin [here][1].

[Bitbar][2] is a macOS application that can add custom menu bar buttons. I'm recently learning managing projects with org-mode. And we all know that org-agenda is great.

So I write a bitbar plugin, [agenda][3] to display some of my agenda in menu bar.

---

### feature

It can display your chosen org-agenda-custom-commands in menu bar.
Support customize color for priority.
Support different color for filter.
Make item with links clickable(only support http/https url).

Like this.

![img][image-1]

### usage

User need to specify the command to export the agenda-view to txt file.

I'm using emacs [--batch][4] feature to generate the txt file silently. There may better way for doing this, like `org-batch-agenda` [check it out here][5]. But when my emacs configuration piles up, there's lots of initializing message which makes it hard to extract the agenda-view data. So I just use the `org-agenda-write` command to write agenda-view to disk.

```shell
emacs --batch -l ~/.emacs.d/init.el --eval "(run-hooks 'emacs-startup-hook)" \
      --eval '(progn (org-agenda nil "r") (org-agenda-write "~/agendas/work/todos.txt") (spacemacs/kill-emacs))'
```

Note: for [spacemacs][6] user, you need to add `(run-hooks 'emacs-startup-hook)` to let emacs read your `dotspacemacs/user-config` [issue#4504][7].
Or you can extract your org-agenda configuration into a separate file and `--eval` it alone.

For using this plugin. You need to specify `agenda_directory` `agenda_file_name`.

```ruby
# Change to your todo directory path
agenda_directory = "#{Dir.home}/agendas/work/"
agenda_name = 'todos.txt'
```

And also the agenda custom command key, you may in your configuration file, you can find your setting using `(describe-variable 'org-agenda-custom-commands)`.

```ruby
# the agenda custome command which brings the agenda view that you want to export
agenda_custome_command = 'B'
```

The problem of using `--batch` is to [really kill emacs][8].

```ruby
# function to REALLY kill emacs
# for spacemacs user use (spacemacs/kill-emacs)
# kill_emacs_function = '(spacemacs/kill-emacs)'
kill_emacs_function = '(let (kill-emacs-hook) (kill-emacs))'
```

You can also change colors of filter line and lines with priority.

```ruby
# Change priority color here
tag_color = 'orange'

# Customise label color-code here (these colors are optimised for a dark theme menubar)
labels = {
  '[#A]' => 'red',
  '[#B]' => 'yellow',
  '[#C]' => 'violet'
}
```

If you use packages that will change filters' format in agenda view, you need to specify `tag_indicator` .

```ruby
tag_indicator = 'Headlines with TAGS match: '
```

### limitation

Current version don't support `agenda-view` when you add, say you add the below code in the chosen `org-agenda-custom-commands`.

```emacs-lisp
(agenda ((org-agenda-ndays 7)))
```

[1]: https://getbitbar.com/plugins/Lifestyle/org-agenda.30m.rb
[2]: https://github.com/matryer/bitbar
[3]: https://github.com/yqrashawn/bitbar-plugin-agenda
[4]: https://www.emacswiki.org/emacs/BatchMode
[5]: http://orgmode.org/manual/Extracting-agenda-information.html
[6]: https://github.com/syl20bnr/spacemacs
[7]: https://github.com/syl20bnr/spacemacs/issues/4504
[8]: https://emacs.stackexchange.com/questions/5451/how-do-i-force-kill-emacs/5456
[image-1]: _bitbar-ext-org-agenda.png
