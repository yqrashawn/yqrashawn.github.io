Tags: macOS

# Reset macOS notification setting

They other day I tried two macOS app Serenity and NoNotify.

The first one can mute notification sound of selected app.
The second one can mute all notification when selected app is focused.

Maybe there’re conflicts between these two apps, I noticed shortly that NoNotify is taking up 100% of one core and there’re multiple duplicate apps in the System Preferences -\> Notifications setting panel.

I thought they might be manipulating the same file at the same time and try to remove the duplication. But there’s no way to delete them from the setting panel UI.

Here’s what I find that works on macOS 11.4.

1. Remove the apps section in `~/Library/Preferences/com.apple.ncprefs.plist`. ( You can use a [PrefsEditor][1] or the [macOS defaults cli][2])
2. Run `sudo run killall NotificationCenter && killall usernoted`

[1]:	https://apps.tempel.org/PrefsEditor/#download
[2]:	https://www.wikiwand.com/en/Defaults_(software)