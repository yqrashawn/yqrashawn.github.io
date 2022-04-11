---
tags: web extension, frontend
---

# Inject script into pages as early as possible with web extension content script

## TLDR

1. set `run_at` to `document_start` in `manifest.json`
1. inject code before `<head>`
1. set the injected script tag to `async=false`
1. inject with `scriptTag.textContent='inpage code'` instead of using the `src=`

## Background knowledge requirement

1. know whats web extension [`manifest.json`](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/manifest.json/content_scripts#run_at)
1. know whats [content script](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/Content_scripts)

## Main article

Web extensions can inject js into web pages with [content script](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/Content_scripts).

I'm developing a crypto wallet as browser extension like [MetaMask](https://metamask.io/).

These kinds of browser extension usually inject JavaScript code into users' web pages so that the developer of the web page (usually [dapp](https://en.wikipedia.org/wiki/Decentralized_application) developers) can use them to contact with users' wallet.

The problem I was facing early this week is how to inject the js as early as possible so that dapp devs can assume the wallet is available when their code loaded.

What I did is:

1. set the [`run_at` in `content_scripts` in `manifest.json`](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/manifest.json/content_scripts#run_at) to `document_start`
1. use the content script to inject a `<script async=false src="web-extension-xxx/inpage.js></script>"` tag into user's page before `<head>` tag and after `<html>` tag.

Since I set `async=false`, the script is supposed to be loaded, parsed and executed synchronously. And my injected api methods should be present when the page's js is executed. The truth I found is, it won't. Actually, it did work once or twice in 10 refresh.

I then changed the content script to inject the inpage.js content directly with `scriptTag.textContent = inpageJSContent`. The tag is now `<script async=false>...inpage.js code...</script>`, which requires me to tweak the build process and use `mustache` and [`js-string-escape`](https://www.npmjs.com/package/js-string-escape) to generate the content script file.

And it works.

You can checkout the code:

1. [the `run_at` in manifest.json](https://github.com/Conflux-Chain/helios/blob/ecfc3f280fd2ab7507fd9b9b32913acebfb6353d/packages/browser-extension/manifest.json.mustache#L55)
1. [code in content script that inject the script tag](https://github.com/Conflux-Chain/helios/blob/ecfc3f280fd2ab7507fd9b9b32913acebfb6353d/packages/content-script/index.js.mustache#L14)
1. [build script to add the compiled inpage.js string into content script](https://github.com/Conflux-Chain/helios/blob/ecfc3f280fd2ab7507fd9b9b32913acebfb6353d/scripts/build-content-script.js#L24)
