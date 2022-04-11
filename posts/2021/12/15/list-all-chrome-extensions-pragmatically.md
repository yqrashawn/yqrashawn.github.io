Tags: chrome, extension

# List all chrome extensions pragmatically

My QA colleague asked me the other day about how to list all chrome extensions pragmatically.

Found two solutions:

1. use the `extensions` field in chrome://system/ with webdriver
2. write a custom extension and use [chrome.management.getAll](https://developer.chrome.com/docs/extensions/reference/management/#method-getAll) api
