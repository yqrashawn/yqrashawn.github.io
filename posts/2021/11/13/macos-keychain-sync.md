---
tags: macOS
---

# macOS keychain

1. there're passwords with type `internet-password`, `application password`, `web form password` stored in macOS keychain
1. only items with `kSecAttrSynchronizable` are synced
1. wifi password and password from safari (`web form password`) are created with `kSecAttrSynchronizable` by default
1. you can't query `web form password` with macOS `security` command line tool
1. you can't create items with `kSecAttrSynchronizable` with macOS `security` command line tool
1. keybase/go-keychain has methods to create item with `kSecAttrSynchronizable`

Related links:

1. [apple's doc for keychain syncing](https://support.apple.com/en-sg/guide/security/sec0a319b35f/web)
1. [github repo keybase/go-keychain](https://github.com/keybase/go-keychain)
