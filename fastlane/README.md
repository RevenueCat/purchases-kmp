fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing
_fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

### bump

```sh
[bundle exec] fastlane bump
```

Bumps version, edits changelog, and creates pull request

### automatic_bump

```sh
[bundle exec] fastlane automatic_bump
```

Automatically determines next version, bumps it, edits changelog, and creates pull request

### update_hybrid_common

```sh
[bundle exec] fastlane update_hybrid_common
```

Update purchases-hybrid-common dependency

### tag_current_branch

```sh
[bundle exec] fastlane tag_current_branch
```

Tags the current branch with the current version number

### publish_if_snapshot

```sh
[bundle exec] fastlane publish_if_snapshot
```

Publish the SDK if the current version is a SNAPSHOT version

### publish_if_release

```sh
[bundle exec] fastlane publish_if_release
```

Publish the SDK if the current version is a release version

### github_release_current_version

```sh
[bundle exec] fastlane github_release_current_version
```

Creates a GitHub release for the current version

### github_release

```sh
[bundle exec] fastlane github_release
```

Creates a GitHub release

----

This README.md is auto-generated and will be re-generated every time [
_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
